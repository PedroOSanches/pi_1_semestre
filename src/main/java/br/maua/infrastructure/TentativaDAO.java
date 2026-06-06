package br.maua.infrastructure;

import br.maua.domain.Tentativa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TentativaDAO {

    public List <String[]> buscarNotasAlunosPorTurma(int idTurma, int idSubturma, int idCurso, int idSemestre){

        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT u.nome_usuario, t.id_tentativa, r.nota_resposta " +
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
                            rs.getString("nota_resposta") != null ? rs.getString("nota_resposta") : "Não avaliado"
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
                "WHERE id_tentativa = ?";

        try (Connection cx = ConnectionFactory.obterConexao();
            PreparedStatement ps = cx.prepareStatement(sql)){

            ps.setDouble(1, tentativa.getNota());
            ps.setInt(2, tentativa.getIdTentativa());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
