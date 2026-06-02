package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.maua.domain.RespostaAlternativa;
import br.maua.infrastructure.ConnectionFactory;

public class RespostaAlternativaDAO {
    public void salvar(RespostaAlternativa respostaAlternativa) {
        String sql = "INSERT INTO resposta_alternativa (id_resposta, id_alternativa) VALUES (?, ?)";

        try (
            Connection cx = ConnectionFactory.obterConexao();
            PreparedStatement ps = cx.prepareStatement(sql)
        ) {

            ps.setInt(1, respostaAlternativa.getIdResposta());
            ps.setInt(2, respostaAlternativa.getIdAlternativa());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
