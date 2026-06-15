package br.maua.infrastructure.DAO;

import java.sql.*;

import br.maua.domain.RespostaAlternativa;
import org.jetbrains.annotations.NotNull;

public class RespostaAlternativaDAO extends RespostaDAO {

    public static void commitResposta(@NotNull Connection cx, RespostaAlternativa resposta) throws SQLException {
        RespostaDAO.gerarResposta(cx, resposta);

        String sql = "INSERT INTO resposta_alternativa (id_resposta, id_alternativa) VALUES (?, ?)";

        try (
                PreparedStatement ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, resposta.getIdResposta());
            ps.setInt(2, resposta.getIdAlternativaAssinalada());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir resposta alternativa!", e);
        }
    }
}
