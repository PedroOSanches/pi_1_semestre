package br.maua.infrastructure.DAO;

import br.maua.domain.*;
import br.maua.exception.UploadException;
import br.maua.infrastructure.ConnectionFactory;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static br.maua.config.AppConfig.BASE_ALUNO;

public class TentativaDAO {

    public Tentativa buscarTentativa(int idTentativa) {

        Tentativa tentativa = new Tentativa(idTentativa);
        tentativa.setRespostas(new ArrayList<>());

        String sqlTarefa = "SELECT te.id_tarefa, ta.titulo_tarefa FROM tentativa te " +
                "JOIN tarefa ta USING(id_tarefa) WHERE te.id_tentativa = ?";
        String sqlRespostas = "SELECT r.id_resposta, r.id_questao, r.nota_resposta, " +
                "q.enunciado_questao, q.tipo_questao " +
                "FROM resposta r " +
                "JOIN questao q USING(id_questao) " +
                "WHERE r.id_tentativa = ?";

        try (Connection cx = ConnectionFactory.obterConexao()) {

            try (PreparedStatement psTarefa = cx.prepareStatement(sqlTarefa)) {
                psTarefa.setInt(1, idTentativa);
                try (ResultSet rsTarefa = psTarefa.executeQuery()) {
                    if (rsTarefa.next()) {
                        Tarefa tarefa = new Tarefa(rsTarefa.getInt("id_tarefa"));
                        tentativa.setTarefa(tarefa);
                    }
                }
            }

            try (PreparedStatement psRespostas = cx.prepareStatement(sqlRespostas)) {
                psRespostas.setInt(1, idTentativa);
                try (ResultSet rsRespostas = psRespostas.executeQuery()) {

                    while (rsRespostas.next()) {
                        int idResposta = rsRespostas.getInt("id_resposta");
                        int idQuestao = rsRespostas.getInt("id_questao");
                        float notaResposta = rsRespostas.getFloat("nota_resposta");
                        String enunciado = rsRespostas.getString("enunciado_questao");
                        String tipoQuestao = rsRespostas.getString("tipo_questao");

                        Resposta resposta = null;

                        if ("alternativa".equalsIgnoreCase(tipoQuestao)) {
                            String sqlAlternativa = "SELECT id_alternativa FROM resposta_alternativa WHERE id_resposta = ?";
                            try (PreparedStatement psAlternativa = cx.prepareStatement(sqlAlternativa)) {
                                psAlternativa.setInt(1, idResposta);
                                try (ResultSet rsAlternativa = psAlternativa.executeQuery()) {
                                    if (rsAlternativa.next()) {
                                        RespostaAlternativa respostaAlternativa = new RespostaAlternativa();
                                        respostaAlternativa.setIdResposta(idResposta);
                                        respostaAlternativa.setNota(notaResposta);
                                        respostaAlternativa.setEnunciado(enunciado);
                                        respostaAlternativa.setIdAlternativaAssinalada(rsAlternativa.getInt("id_alternativa"));

                                        QuestaoAlternativa questaoAlternativa = new QuestaoAlternativa();
                                        java.util.List<Alternativa> listaAlternativas = new java.util.ArrayList<>();

                                        String sqlAlternativas = "SELECT id_alternativa, texto_alternativa FROM alternativa " +
                                                "WHERE id_questao = (SELECT id_questao FROM resposta WHERE id_resposta = ?)";

                                        try (PreparedStatement psAlternativas = cx.prepareStatement(sqlAlternativas)) {
                                            psAlternativas.setInt(1, idResposta);
                                            try (ResultSet rsAlternativas = psAlternativas.executeQuery()) {
                                                while (rsAlternativas.next()) {

                                                    Alternativa alternativa = new Alternativa();
                                                    alternativa.setIdAlternativa(rsAlternativas.getInt("id_alternativa"));
                                                    alternativa.setEnunciado(rsAlternativas.getString("texto_alternativa"));
                                                    listaAlternativas.add(alternativa);
                                                }
                                            }
                                        }
                                        questaoAlternativa.setAlternativas(listaAlternativas);
                                        respostaAlternativa.setQuestao(questaoAlternativa);
                                        resposta = respostaAlternativa;
                                    }
                                }
                            }
                        } else if ("dissertativa".equalsIgnoreCase(tipoQuestao)) {
                            String sqlDissertativa = "SELECT resposta FROM resposta_dissertativa WHERE id_resposta = ?";
                            try (PreparedStatement psDissertativa = cx.prepareStatement(sqlDissertativa)) {
                                psDissertativa.setInt(1, idResposta);
                                try (ResultSet rsDissertativa = psDissertativa.executeQuery()) {
                                    if (rsDissertativa.next()) {
                                        RespostaDissertativa respostaDissertativa = new RespostaDissertativa();
                                        respostaDissertativa.setIdResposta(idResposta);
                                        respostaDissertativa.setNota(notaResposta);
                                        respostaDissertativa.setEnunciado(enunciado);
                                        respostaDissertativa.setTextoResposta(rsDissertativa.getString("resposta"));
                                        resposta = respostaDissertativa;
                                    }
                                }
                            }
                        } else if ("upload".equalsIgnoreCase(tipoQuestao)) {
                            String sqlUpload = "SELECT arquivo_resposta FROM resposta_upload WHERE id_resposta = ?";
                            try (PreparedStatement psUpload = cx.prepareStatement(sqlUpload)) {
                                psUpload.setInt(1, idResposta);
                                try (ResultSet rsUpload = psUpload.executeQuery()) {
                                    if (rsUpload.next()) {
                                        RespostaUpload respostaUpload = new RespostaUpload();
                                        respostaUpload.setIdResposta(idResposta);
                                        respostaUpload.setNota(notaResposta);
                                        respostaUpload.setEnunciado(enunciado);
                                        respostaUpload.setArquivo(new File(BASE_ALUNO, rsUpload.getString("arquivo_resposta")));
                                        resposta = respostaUpload;
                                    }
                                }
                            }
                        }
                        if (resposta != null) {
                            tentativa.getRespostas().add(resposta);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tentativa;
    }

    public List<String[]> buscarNotasAlunosPorTurma(int idTurma, int idSubturma, int idCurso, int idSemestre) {

        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT u.nome_usuario, t.id_tentativa, SUM(r.nota_resposta) AS nota_total " +
                "FROM turma_subturma tst " +
                "INNER JOIN usuario u ON tst.id_usuario = u.id_usuario " +
                "INNER JOIN tentativa t ON u.id_usuario = t.id_usuario AND tst.id_tarefa = t.id_tarefa " +
                "LEFT JOIN resposta r ON t.id_tentativa = r.id_tentativa " +
                "WHERE tst.id_turma = ? AND tst.id_subturma = ? AND tst.id_curso = ? AND tst.id_semestre = ? " +
                "GROUP BY u.id_usuario, t.id_tentativa";

        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, idTurma);
            ps.setInt(2, idSubturma);
            ps.setInt(3, idCurso);
            ps.setInt(4, idSemestre);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new String[]{
                            rs.getString("nome_usuario"),
                            String.valueOf(rs.getInt("id_tentativa")),
                            rs.getString("nota_total") != null ? rs.getString("nota_total") : "Não avaliado"
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizarNota(Tentativa tentativa) {

        String sql = "UPDATE resposta " +
                "SET nota_resposta = ? " +
                "WHERE id_resposta = ?";

        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            for (Resposta resposta : tentativa.getRespostas()) {
                ps.setFloat(1, resposta.getNota());
                ps.setInt(2, resposta.getIdResposta());
                ps.executeUpdate();
            }
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public record TarefaTentadaDTO(int idTarefa, String tituloTarefa) {
    }

    public static List<TarefaTentadaDTO> buscarTarefasTentadasPeloAluno(int idAluno, Integer idCasa) throws SQLException {
        List<TarefaTentadaDTO> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.id_tarefa, t.titulo_tarefa ");
        sql.append("FROM tarefa t ");
        sql.append("INNER JOIN tentativa te ON te.id_tarefa = t.id_tarefa ");
        sql.append("WHERE te.id_usuario = ? ");

        if (idCasa != null) {
            sql.append("AND t.id_casa = ? ");
        }

        sql.append("GROUP BY t.id_tarefa, t.titulo_tarefa ORDER BY t.id_tarefa");

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql.toString())) {

            comando.setInt(1, idAluno);
            if (idCasa != null) {
                comando.setInt(2, idCasa);
            }

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    lista.add(new TarefaTentadaDTO(
                            resultado.getInt("id_tarefa"),
                            resultado.getString("titulo_tarefa")
                    ));
                }
            }
        }
        return lista;
    }

    public static int buscarIdTentativaPorAlunoETarefa(int idAluno, int idTarefa) throws SQLException {
        String sql = "SELECT id_tentativa FROM tentativa WHERE id_usuario = ? AND id_tarefa = ? LIMIT 1";
        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idAluno);
            comando.setInt(2, idTarefa);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id_tentativa");
                }
            }
        }
        return -1;
    }
    public static void salvarTentativa(Tentativa tentativa) throws SQLException, UploadException {
        String sql = """
                INSERT INTO
                tentativa(
                    status_tentativa,\s
                    data_tentativa,\s
                    id_usuario,\s
                    id_tarefa
                )\s
                VALUES(
                    'concluida', NOW(), ?, ?
                );
                """;

        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            cx.setAutoCommit(false);
            ps.setInt(1, tentativa.getAluno().getId());
            ps.setInt(2, tentativa.getTarefa().getIdTarefa());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (!rs.next())
                throw new SQLException("Erro ao gerar Tentativa");
            tentativa.setIdTentativa(rs.getInt(1));
            List<Resposta> respostas = tentativa.getRespostas();
            for (Resposta resposta : respostas) {
                try {
                    resposta.commitResposta(cx);
                } catch (SQLException ex) {
                    Logger.getLogger(TentativaDAO.class.getName()).log(Level.SEVERE, null, ex);
                    cx.rollback();
                    throw new SQLException("Erro ao gerar Tentativa!");
                }
            }
            cx.commit();
        }

    }
}
