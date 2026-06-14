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

    /**
     * Busca todas as casas do banco e monta o objeto Secao dentro delas usando a sintaxe oficial.
     */
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
                // Instancia a Casa usando o construtor oficial fornecido
                Casa casa = new Casa(resultado.getString("titulo_casa"), resultado.getInt("id_casa"));

                // Instancia a Secao usando o construtor oficial fornecido
                Secao secao = new Secao(resultado.getInt("id_secao"), resultado.getString("titulo_secao"));

                // Vincula a seção dentro da casa
                casa.setSecao(secao);

                lista.add(casa);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar casas do tabuleiro: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca um mapa com os prazos de vencimento de cada casa usando o ID da casa como chave.
     */
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

    /**
     * Descobre qual ID de casa obrigatória está travando o progresso do aluno.
     */
    public Integer descobrirProximaCasaObrigatoria(int idAluno) {
        String sql = "SELECT c.id_casa FROM casa c " +
                "LEFT JOIN nota_aluno n ON c.id_casa = n.id_casa AND n.id_aluno = ? " +
                "WHERE n.nota IS NULL OR n.nota < 6.0 " +
                "ORDER BY c.id_secao, c.id_casa LIMIT 1";

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

    public boolean salvarNotaDaTentativa(int idAluno, int idCasa, double nota) {

        String sql = "INSERT INTO nota_aluno (id_aluno, id_casa, nota) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE nota = VALUES(nota)";

        try (Connection conexao = br.maua.infrastructure.ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idAluno);
            comando.setInt(2, idCasa);
            comando.setDouble(3, nota);

            return comando.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar nota da tentativa: " + e.getMessage());
            return false;
        }
    }

    public boolean verificarPendenciasNaSecao(int idAluno, int idSecao) {
        String sql = "SELECT c.id_casa FROM casa c " +
                "LEFT JOIN nota_aluno n ON c.id_casa = n.id_casa AND n.id_aluno = ? " +
                "WHERE c.id_secao = ? AND (n.nota IS NULL OR n.nota < 6.0) " +
                "AND c.data_limite_casa > NOW() LIMIT 1";

        try (Connection conexao = br.maua.infrastructure.ConnectionFactory.obterConexao();
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