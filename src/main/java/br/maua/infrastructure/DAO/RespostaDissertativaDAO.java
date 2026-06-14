package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.maua.domain.RespostaDissertativa;
import org.jetbrains.annotations.NotNull;

public class RespostaDissertativaDAO {
    public static void commitResposta(@NotNull Connection cx, RespostaDissertativa resposta) throws SQLException {
        RespostaDAO.gerarResposta(cx, resposta);
        String sqlDissertativa = "INSERT INTO resposta_dissertativa (id_resposta, resposta) VALUES (?, ?)";
        try (
                PreparedStatement ps = cx.prepareStatement(sqlDissertativa, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, resposta.getIdResposta());
            ps.setString(2, resposta.getTextoResposta());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerarResposta resposta dissertativa", e);
        }
    }
}
