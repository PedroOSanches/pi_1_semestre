package br.maua.infrastructure.DAO;

import br.maua.domain.QuestaoDissertativa;
import java.sql.*;

import java.sql.SQLException;

public class QuestaoDissertativaDAO {
    public static void commit(QuestaoDissertativa qd, Connection cx) throws SQLException {
            QuestaoDAO.commit(qd, cx, "dissertativa");
            String sql;
            sql = "INSERT INTO dissertativa(resposta_modelo_dissertativa, id_questao) VALUES (?, ?)";
            try (
                    PreparedStatement ps = cx.prepareStatement(sql);
            ) {
                ps.setString(1, qd.getRepostaModelo());
                ps.setInt(2, qd.getIdQuestao());

                ps.executeUpdate();
            }
        }
    }
