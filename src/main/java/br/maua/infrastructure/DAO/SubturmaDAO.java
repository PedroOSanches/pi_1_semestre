package br.maua.infrastructure.DAO;

import br.maua.domain.Subturma;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubturmaDAO {
    public static List<Subturma> listarSubturmas() throws SQLException {
        String sql = "SELECT id_subturma, cod_subturma FROM subturma ORDER BY cod_subturma";
        List<Subturma> subturmas = new ArrayList<>();
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            try {
                while (rs.next()) {
                    subturmas.add(
                            new Subturma(
                                    rs.getInt("id_subturma"),
                                    rs.getString("cod_subturma")
                            )
                    );
                }
                return subturmas;
            } catch (NullPointerException e) {
                throw new SQLException("Tabela Subturma vazia");
            }
        }
    }

    public static void salvar(Subturma subturma) throws SQLException {
        String sql = "INSERT INTO subturma(cod_subtuma) VALUES (?)";
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql)
        ) {
            ps.setString(1, subturma.getCodSubTurma());
            ps.executeUpdate();
        }
    }
}
