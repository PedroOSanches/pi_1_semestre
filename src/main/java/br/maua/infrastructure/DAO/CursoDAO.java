package br.maua.infrastructure.DAO;

import br.maua.domain.Curso;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {
    public static List<Curso> listarCursos() throws SQLException {
        String sql = "SELECT id_curso, nome_curso FROM curso ORDER BY nome_curso";
        List<Curso> list = new ArrayList<>();

        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int idCurso = rs.getInt("id_curso");
                String nomeCurso = rs.getString("nome_curso");
                list.add(new Curso(idCurso, nomeCurso));
            }
            return list;
        }
    }

    public static void salvar(Curso curso) throws SQLException {
        String sql = "INSERT INTO curso (nome_curso) VALUES (?)";
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql)
        ) {
            ps.setString(1, curso.getNome());
            ps.executeUpdate();
        }
    }
}
