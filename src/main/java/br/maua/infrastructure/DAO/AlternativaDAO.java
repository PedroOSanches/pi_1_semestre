package br.maua.infrastructure.DAO;

import br.maua.domain.Alternativa;
import java.sql.*;


public class AlternativaDAO {

    public static void commit(Alternativa alternativa, Connection cx) throws SQLException{
        String sql = "INSERT INTO alternativa(id_questao, correta, texto_alternativa) VALUES (?,?,?);";

        try(
                PreparedStatement ps = cx.prepareStatement(sql);
                ){

            ps.setInt(1, alternativa.getQuestaoAlternativa().getIdQuestao());
            ps.setBoolean(2, alternativa.isAlternativaCorreta());
            ps.setString(3, alternativa.getEnunciado());

            ps.executeUpdate();
        }
    }
}
