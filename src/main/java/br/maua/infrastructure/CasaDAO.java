package br.maua.infrastructure;

import br.maua.domain.Casa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CasaDAO {
    Casa casa;

    public CasaDAO(Casa casa){
        this.casa = casa;
    }

    public static List<Casa> listarCasas() throws SQLException {
        String sql = "SELECT titulo_casa, titulo_secao  FROM casa INNER JOIN secao USING(id_secao)";

        try(
        Connection cx = ConnectionFactory.obterConexao();
        PreparedStatement ps = cx.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ){
            List<Casa> casas = new ArrayList<>();
            while(rs.next()){
                String titulo_casa = rs.getString("titulo_casa");
                String titulo_secao = rs.getString("titulo_secao");
                titulo_casa = String.format("%s - %s",  titulo_casa, titulo_secao);
                Casa casa = new  Casa(titulo_casa);
                casas.add(casa);
            }

            System.out.println(casas);
            return casas;
        }
    }
}
