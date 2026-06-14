package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.maua.domain.Resposta;
public class RespostaDAO {

    public static void gerarResposta(Connection cx, Resposta resposta) throws SQLException {
        String sqlResposta = """
                        INSERT INTO 
                            resposta (id_tentativa, id_questao, nota_resposta) 
                        VALUES (?, ?, ?);
                """;
        try (
                PreparedStatement ps = cx.prepareStatement(sqlResposta, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {

            ps.setInt(1, resposta.getTentativa().getIdTentativa());
            ps.setInt(2, resposta.getQuestao().getIdQuestao());
            ps.setDouble(3, resposta.getNota());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (!rs.next())
                throw new SQLException("Erro ao registrar resposta");
            resposta.setIdResposta(rs.getInt(1));

        }
    }
}
