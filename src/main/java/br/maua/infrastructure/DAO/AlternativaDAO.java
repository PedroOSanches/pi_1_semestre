package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.maua.domain.Alternativa;
import br.maua.domain.QuestaoAlternativa;


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

     public static void consultarAlternativas(Connection cx, QuestaoAlternativa qa) throws SQLException {
        String sql = "SELECT id_alternativa, texto_alternativa FROM alternativa WHERE id_questao = ?";
        try (var ps = cx.prepareStatement(sql)) {
            ps.setInt(1, qa.getIdQuestao());
            var rs = ps.executeQuery();
            List<Alternativa> alternativas = new java.util.ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id_alternativa");
                String textoAlternativa = rs.getString("texto_alternativa");
                Alternativa a = new Alternativa(qa, id, textoAlternativa);
                alternativas.add(a);
            }
            qa.setAlternativas(alternativas);
        }
    }

}
