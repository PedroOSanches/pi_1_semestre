package br.maua.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.maua.infrastructure.ConnectionFactory;

public class RespostaAlternativa extends Resposta {
    private int idAlternativa;

    public String gerarRespostaBanco(int idAlternativa) {
        String sql = String.format("(%d)", idAlternativa);
        return sql;
    }

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(int idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    public void salvar() {
        String sql = "INSERT INTO resposta_alternativa (id_resposta, id_alternativa) VALUES (?, ?)";

        try (
            Connection cx = ConnectionFactory.obterConexao();
            PreparedStatement ps = cx.prepareStatement(sql)
        ) {

            ps.setInt(1, getIdResposta());
            ps.setInt(2, idAlternativa);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}