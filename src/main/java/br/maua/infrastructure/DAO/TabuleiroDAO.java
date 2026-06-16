package br.maua.infrastructure.DAO;

import br.maua.infrastructure.ConnectionFactory;
import br.maua.domain.Casa;
import br.maua.domain.Secao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabuleiroDAO {

    public List<Casa> buscarCasasDoTabuleiro() {
        List<Casa> lista = new ArrayList<>();
        String sql = "SELECT c.id_casa, c.titulo_casa, c.id_secao, s.titulo_secao " +
                "FROM casa c " +
                "JOIN secao s ON c.id_secao = s.id_secao " +
                "ORDER BY c.id_secao, c.id_casa";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Casa casa = new Casa(resultado.getString("titulo_casa"), resultado.getInt("id_casa"));
                Secao secao = new Secao(resultado.getInt("id_secao"), resultado.getString("titulo_secao"));
                casa.setSecao(secao);
                lista.add(casa);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar casas do tabuleiro: " + e.getMessage());
        }
        return lista;
    }

    
    public Map<Integer, Timestamp> buscarPrazosDasCasas() {
        Map<Integer, Timestamp> prazos = new HashMap<>();
        String sql = "SELECT id_casa, data_limite_casa FROM casa";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                prazos.put(resultado.getInt("id_casa"), resultado.getTimestamp("data_limite_casa"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prazos: " + e.getMessage());
        }
        return prazos;
    }   

 
    public Integer descobrirProximaCasaObrigatoria(int idAluno) {
        String sql = 
            "SELECT c.id_casa " +
            "FROM casa c " +
            "JOIN tarefa t ON c.id_casa = t.id_casa " +
            "LEFT JOIN tentativa ten ON t.id_tarefa = ten.id_tarefa AND ten.id_usuario = ? " +
            "LEFT JOIN resposta r ON ten.id_tentativa = r.id_tentativa " + 
            "GROUP BY c.id_casa, c.id_secao " +
            "HAVING SUM(r.nota) IS NULL OR SUM(r.nota) < 6.0 " +
            "ORDER BY c.id_secao, c.id_casa " +
            "LIMIT 1";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idAluno);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id_casa");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao descobrir próxima casa obrigatória: " + e.getMessage());
        }
        return null;
    }

    public double calcularESalvarNotaDaTentativa(int idTentativa) {
        String sqlBuscarNota = "SELECT SUM(nota) AS nota_final FROM resposta WHERE id_tentativa = ?";
        String sqlAtualizarStatus = "UPDATE tentativa SET status_tentativa = 'concluida' WHERE id_tentativa = ?";
        
        double notaFinal = 0.0;

        try (Connection con = ConnectionFactory.obterConexao()) {
            
            try (PreparedStatement ps = con.prepareStatement(sqlBuscarNota)) {
                ps.setInt(1, idTentativa);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        notaFinal = rs.getDouble("nota_final");
                    }
                }
            }
            
            if (notaFinal >= 6.0) {
                try (PreparedStatement psStatus = con.prepareStatement(sqlAtualizarStatus)) {
                    psStatus.setInt(1, idTentativa);
                    psStatus.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular e salvar nota da tentativa no DAO: " + e.getMessage());
        }
        
        return notaFinal;
    }

    public boolean verificarPendenciasNaSecao(int idAluno, int idSecao) {
        String sql = "SELECT c.id_casa " +
                "FROM casa c " +
                "JOIN tarefa t ON c.id_casa = t.id_casa " +
                "LEFT JOIN tentativa ten ON t.id_tarefa = ten.id_tarefa AND ten.id_usuario = ? " +
                "LEFT JOIN resposta r ON ten.id_tentativa = r.id_tentativa " +
                "WHERE c.id_secao = ? AND c.data_limite_casa > NOW() " +
                "GROUP BY c.id_casa " +
                "HAVING SUM(r.nota) IS NULL OR SUM(r.nota) < 6.0 " +
                "LIMIT 1";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idAluno);
            comando.setInt(2, idSecao);

            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next(); 
            }

        } catch (SQLException e) {
            System.err.println("Erro ao verificar pendências da seção: " + e.getMessage());
            return true;
        }
    }
}