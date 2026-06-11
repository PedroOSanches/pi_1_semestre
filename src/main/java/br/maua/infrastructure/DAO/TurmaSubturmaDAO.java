package br.maua.infrastructure.DAO;

import br.maua.domain.*;
import br.maua.enums.SemestreEnum;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;

public class TurmaSubturmaDAO {
    public static void commit(SemestreEnum semestre,Professor professor, Curso curso, Ano ano, Turma turma, Subturma subturma) throws SQLException {
        String sql = "INSERT INTO turma_subturma(id_turma, id_subturma, id_curso, id_ano, semestre_turma_subturma) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            try {
                conn.setAutoCommit(false);
                stmt.setInt(1, turma.getIdTurma());
                stmt.setInt(2, subturma.getIdSubTurma());
                stmt.setInt(3, curso.getIdCurso());
                stmt.setInt(4, ano.getIdAno());
                stmt.setString(5, semestre.toString().toLowerCase());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    ProfessorDAO.salvarNaTurma(conn, professor, rs.getInt(1));
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            }
        }
    }
}
