package br.maua.infrastructure.DAO;

import br.maua.domain.Casa;
import br.maua.domain.Secao;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CasaDAO {
    Casa casa;

    public CasaDAO(Casa casa){
        this.casa = casa;
    }

    public static List<Casa> listarCasas() throws SQLException {
        String sql = "SELECT titulo_casa, titulo_secao, id_casa  FROM casa INNER JOIN secao USING(id_secao) order by id_secao, id_casa";
        Secao secao = new Secao();
        try(
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){
            List<Casa> casas = new ArrayList<>();
            while(rs.next()){
                String tituloCasa = rs.getString("titulo_casa");
                String tituloSecao = rs.getString("titulo_secao");

                int idCasa = rs.getInt("id_casa");
                tituloCasa = String.format("%s - %s", tituloCasa, tituloSecao);
                Casa casa = new  Casa(tituloCasa, idCasa);
                casas.add(casa);
            }
            return casas;
        }
    }

    public static List<Casa> carregarCasasTabuleiro() throws SQLException {
        String sql = "SELECT c.id_casa, c.titulo_casa, c.data_limite_casa, " +
                     "s.id_secao, s.titulo_secao, s.descricao_secao " +
                     "FROM casa c " +
                     "INNER JOIN secao s USING(id_secao) " +
                     "ORDER BY s.id_secao, c.id_casa";

        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            List<Casa> casas = new ArrayList<>();

            while (rs.next()) {
                Secao secao = new Secao(rs.getInt("id_secao"), rs.getString("titulo_secao"));

                Casa casaItem = new Casa(rs.getString("titulo_casa"), rs.getInt("id_casa"));
                casaItem.setSecao(secao);

                casaItem.setDataLimiteCasa(rs.getTimestamp("data_limite_casa"));
                secao.setDescricaoSecao(rs.getString("descricao_secao"));

                casas.add(casaItem);
            }
            return casas;
        }
    }

    public static Integer descobrirProximaCasaObrigatoria(int idAluno) throws SQLException {
        String sql = "SELECT c.id_casa, " +
                "  (SELECT COUNT(*) FROM tarefa t2 WHERE t2.id_casa = c.id_casa) AS total_tarefas, " +
                "  COUNT(DISTINCT CASE WHEN r.nota_resposta >= 6.0 THEN t.id_tarefa END) AS tarefas_completas " +
                "FROM secao s " +
                "JOIN casa c ON c.id_secao = s.id_secao " +
                "LEFT JOIN tarefa t ON t.id_casa = c.id_casa " +
                "LEFT JOIN tentativa ten ON ten.id_tarefa = t.id_tarefa " +
                "    AND ten.id_usuario = ? " +
                "    AND ten.status_tentativa = 'corrigida' " +
                "LEFT JOIN resposta r ON r.id_tentativa = ten.id_tentativa " +
                "GROUP BY s.id_secao, c.id_casa " +
                "HAVING total_tarefas > tarefas_completas " +
                "ORDER BY s.id_secao, c.id_casa " +
                "LIMIT 1";

        try (Connection conexao = ConnectionFactory.obterConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idAluno);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id_casa");
                }
            }
        }
        return null;
    }
    public static boolean isSecaoConcluidaComSucesso(int idAluno, int idSecao) throws SQLException {
        String sql = "SELECT " +
                "  (SELECT COUNT(*) FROM tarefa t2 JOIN casa c2 ON t2.id_casa = c2.id_casa WHERE c2.id_secao = s.id_secao) AS total_tarefas, " +
                "  COUNT(DISTINCT CASE WHEN r.nota_resposta >= 6.0 THEN t.id_tarefa END) AS tarefas_completas " +
                "FROM secao s " +
                "JOIN casa c ON c.id_secao = s.id_secao " +
                "LEFT JOIN tarefa t ON t.id_casa = c.id_casa " +
                "LEFT JOIN tentativa ten ON ten.id_tarefa = t.id_tarefa AND ten.id_usuario = ? AND ten.status_tentativa = 'corrigida' " +
                "LEFT JOIN resposta r ON r.id_tentativa = ten.id_tentativa " +
                "WHERE s.id_secao = ? " +
                "GROUP BY s.id_secao";

        try (Connection conexao = ConnectionFactory.obterConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idAluno);
            comando.setInt(2, idSecao);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    int total = resultado.getInt("total_tarefas");
                    int completas = resultado.getInt("tarefas_completas");
                    return total > 0 && total == completas;
                }
            }
        }
        return false;
    }
}

