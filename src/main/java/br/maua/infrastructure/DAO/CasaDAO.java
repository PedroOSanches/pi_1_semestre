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
        String sql =
            "SELECT c.id_casa, " +
            "       COUNT(DISTINCT t.id_tarefa) AS total_tarefas, " +
            "       COUNT(DISTINCT nt.id_tarefa) AS tarefas_completas " +
            "FROM casa c " +
            "LEFT JOIN tarefa t ON t.id_casa = c.id_casa " +
            "LEFT JOIN ( " +
            "    SELECT ten.id_tarefa, " +
            "           SUM(r.nota_resposta) AS nota_total " +
            "    FROM tentativa ten " +
            "    JOIN resposta r ON r.id_tentativa = ten.id_tentativa " +
            "    WHERE ten.id_usuario = ? " +
            "      AND ten.status_tentativa = 'corrigida' " +
            "    GROUP BY ten.id_tarefa " +
            "    HAVING SUM(r.nota_resposta) >= 6 " +
            ") nt ON nt.id_tarefa = t.id_tarefa " +
            "GROUP BY c.id_casa " +
            "HAVING total_tarefas > tarefas_completas " +
            "ORDER BY c.id_casa " +
            "LIMIT 1";

        try (Connection conexao = ConnectionFactory.obterConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idAluno);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Integer idCasa = resultado.getInt("id_casa");

                    System.out.println("Aluno: " + idAluno);
                    System.out.println("Próxima casa obrigatória: " + idCasa);

                    return idCasa;
                }
            }
        }
        return null;
    }
    public static boolean isSecaoConcluidaComSucesso(int idAluno, int idSecao) throws SQLException {
        String sql =
            "SELECT " +
            "    COUNT(DISTINCT t.id_tarefa) AS total_tarefas, " +
            "    COUNT(DISTINCT nt.id_tarefa) AS tarefas_completas " +
            "FROM casa c " +
            "LEFT JOIN tarefa t ON t.id_casa = c.id_casa " +
            "LEFT JOIN ( " +
            "    SELECT ten.id_tarefa, " +
            "           SUM(r.nota_resposta) AS nota_total " +
            "    FROM tentativa ten " +
            "    JOIN resposta r ON r.id_tentativa = ten.id_tentativa " +
            "    WHERE ten.id_usuario = ? " +
            "      AND ten.status_tentativa = 'corrigida' " +
            "    GROUP BY ten.id_tarefa " +
            "    HAVING SUM(r.nota_resposta) >= 6 " +
            ") nt ON nt.id_tarefa = t.id_tarefa " +
            "WHERE c.id_secao = ? ";

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

