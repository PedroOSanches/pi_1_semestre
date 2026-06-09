package br.maua.infrastructure;

import br.maua.domain.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TentativaDAO {

    public Tentativa buscarTentativa(int idTentativa){

        Tentativa tentativa = new Tentativa(idTentativa);

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
                                        respostaAlternativa.setIdAlternativaAssinalada(rsAlternativa.getInt("id_alternativa"));
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
                                        respostaDissertativa.setRespostaAluno(rsDissertativa.getString("resposta"));
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
                                        respostaUpload.setPathArquivo(rsUpload.getString("arquivo_resposta"));
                                        resposta = respostaUpload;
                                    }
                                }
                            }
                        }

                        if (resposta != null) {
                            resposta.setIdResposta(idResposta);
                            resposta.setIdQuestao(idQuestao);
                            resposta.setEnunciado(enunciado);
                            resposta.setNota(notaResposta);
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

    public List <String[]> buscarNotasAlunosPorTurma(int idTurma, int idSubturma, int idCurso, int idSemestre){

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

    public boolean atualizarNota(Tentativa tentativa){

        String sql = "UPDATE resposta" +
                "SET nota_resposta = ? " +
                "WHERE id_resposta = ?";

        try (Connection cx = ConnectionFactory.obterConexao();
            PreparedStatement ps = cx.prepareStatement(sql)){

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
}
