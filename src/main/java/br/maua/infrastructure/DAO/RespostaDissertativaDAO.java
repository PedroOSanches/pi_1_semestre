package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.maua.domain.RespostaDissertativa;
import br.maua.infrastructure.ConnectionFactory;

public class RespostaDissertativaDAO {
    public void salvar(RespostaDissertativa respostaDissertativa) {
        String sqlDissertativa = "INSERT INTO resposta_dissertativa (id_resposta, resposta) VALUES (?, ?)";
        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sqlDissertativa)) {

            ps.setInt(1, respostaDissertativa.getIdResposta());
            ps.setString(2, respostaDissertativa.getTextoResposta());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta dissertativa", e);
        }
    }
}
