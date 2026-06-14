package br.maua.infrastructure.DAO;

import br.maua.domain.Casa;
import br.maua.domain.Secao;
import br.maua.infrastructure.ConnectionFactory;

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
        String sql = "SELECT titulo_casa, titulo_secao, id_casa  FROM casa INNER JOIN secao USING(id_secao) order by id_secao, id_casa";
        Secao secao = new Secao();
        try(
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ){
            List<Casa> casas = new ArrayList<>();
            while(rs.next()){
                String tituloCasa = rs.getString("titulo_casa");
                String tituloSecao = rs.getString("titulo_secao");

                int idCasa = rs.getInt("id_casa");
                tituloCasa = String.format("%s - %s", tituloCasa, tituloSecao);
                Casa casa = new  Casa(tituloCasa, idCasa);
                casas.add(casa);
            }
            return casas;
        }
    }
}

