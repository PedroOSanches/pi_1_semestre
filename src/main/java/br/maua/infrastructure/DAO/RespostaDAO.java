package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.maua.domain.Resposta;
import br.maua.infrastructure.ConnectionFactory;

public class RespostaDAO {
    public void salvar(Resposta resposta) {
        String sqlResposta = "INSERT INTO resposta (id_tentativa, id_questao, nota_resposta) VALUES (?, ?, ?)";
        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sqlResposta, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, resposta.getTentativa().getIdTentativa());
            ps.setInt(2, resposta.getQuestao().getIdQuestao());
            ps.setDouble(3, resposta.getNota() != null ? resposta.getNota() : 0.0);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    resposta.setIdResposta(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta", e);
        }
    }
}
