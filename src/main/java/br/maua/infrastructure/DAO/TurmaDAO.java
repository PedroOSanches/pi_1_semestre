package br.maua.infrastructure.DAO;

import br.maua.domain.Turma;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurmaDAO {
    public static List<Turma> listarTurmas() throws SQLException, RuntimeException {
        String sql = "SELECT id_turma, cod_turma FROM turma ORDER BY cod_turma ";
        List<Turma> turmas = new ArrayList<>();
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            try {
                while (rs.next()) {
                    int idTurma = rs.getInt("id_turma");
                    String codTurma = rs.getString("cod_turma");
                    turmas.add(new Turma(idTurma, codTurma));
                }
                return turmas;
            } catch (NullPointerException ex) {
                Logger.getLogger(TurmaDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException("Tabela turma vazia");
            }
        }
    }

    public static void salvar(Turma turma) throws SQLException, RuntimeException {
        String sql = "INSERT INTO turma(cod_turma) VALUES (?)";
        try (
                PreparedStatement ps = ConnectionFactory.obterConexao().prepareStatement(sql)
        ) {
            ps.setString(1, turma.getNomeTurma());
            ps.executeUpdate();
        }
    }
}
