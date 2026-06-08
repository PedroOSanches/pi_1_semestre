package br.maua.infrastructure.DAO;

import br.maua.domain.Ano;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnoDAO {
    public static List<Ano> getAnos() throws SQLException {
        String sql = "SELECT id_ano, ano FROM ano order by ano";
        List<Ano> anos = new ArrayList<>();
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int idAno = rs.getInt("id_ano");
                int ano = rs.getInt("ano");
                Ano a = new Ano(idAno, ano);
                anos.add(a);
            }
            return anos;
        }
    }

    public static void salvar(Ano ano) throws SQLException {
        String sql = "INSERT INTO ano (ano) VALUES (?)";
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql)
        ) {
            ps.setInt(1, Integer.parseInt(ano.getAno().toString()));
            ps.executeUpdate();
        }

    }
}
