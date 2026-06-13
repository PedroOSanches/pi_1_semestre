package br.maua.infrastructure.DAO;

import br.maua.domain.*;
import br.maua.enums.SemestreEnum;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaSubturmaDAO {
    public static void commit(
            SemestreEnum semestre,
            Professor professor,
            Curso curso,
            Ano ano,
            Turma turma,
            Subturma subturma
    ) throws SQLException {
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

    public static List<Turma> buscaTurmasProfessor(Professor professor) throws SQLException {
        String sql = """
                SELECT\s
                	id_turma_subturma, cod_turma, cod_subturma, nome_curso, ano, semestre_turma_subturma
                	FROM turma_subturma\s
                	JOIN turma_usuario USING(id_turma_subturma)
                    JOIN turma using(id_turma)
                    JOIN subturma USING(id_subturma)
                    JOIN curso USING(id_curso)
                    JOIN ano USING(id_ano)
                    WHERE id_usuario = ?
                    ORDER BY nome_curso, cod_turma, cod_subturma, semestre_turma_subturma, ano;
                """;
        List<Turma> turmas = new ArrayList<>();
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql)
        ){
            ps.setInt(1, professor.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idTurma = rs.getInt("id_turma_subturma");
                String codTurma = rs.getString("cod_turma");
                Subturma subturma = new Subturma(rs.getString("cod_subturma"));
                Curso curso = new Curso(rs.getString("nome_curso"));
                Ano ano = new Ano(rs.getInt("ano"));
                SemestreEnum semestre = SemestreEnum.descobreSemestre(rs.getString("semestre_turma_subturma"));
                Turma turma = new Turma(
                        idTurma,
                        codTurma,
                        curso,
                        semestre,
                        subturma,
                        ano
                );
                turmas.add(turma);
            }
                return turmas;
        }
    }
}
