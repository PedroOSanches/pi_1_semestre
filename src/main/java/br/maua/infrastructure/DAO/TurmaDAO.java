package br.maua.infrastructure.DAO;

import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurmaDAO {

    public boolean vincularAluno(int idUsuario, int idTurma, int idSubturma, int idCurso, int idSemestre) {

        String sql = "INSERT INTO turma_subturma (id_usuario, id_turma, id_subturma, id_curso, semestre_id_semestre) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idTurma);
            ps.setInt(3, idSubturma);
            ps.setInt(4, idCurso);
            ps.setInt(5, idSemestre);

            int linhas = ps.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }
    }
}
