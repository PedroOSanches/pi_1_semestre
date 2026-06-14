package br.maua.infrastructure.DAO;

import br.maua.domain.Resposta;
import br.maua.domain.Tentativa;
import br.maua.exception.UploadException;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TentativaDAO {

    public static void salvarTentativa(Tentativa tentativa) throws SQLException, UploadException {
        String sql = """
                INSERT INTO
                tentativa(
                    status_tentativa,\s
                    data_tentativa,\s
                    id_usuario,\s
                    id_tarefa
                )\s
                VALUES(
                    'concluida', NOW(), ?, ?
                );
                """;

        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            cx.setAutoCommit(false);
            ps.setInt(1, tentativa.getAluno().getIdAluno());
            ps.setInt(2, tentativa.getTarefa().getIdTarefa());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (!rs.next())
                throw new SQLException("Erro ao gerar Tentativa");
            tentativa.setIdTentativa(rs.getInt(1));
            List<Resposta> respostas = tentativa.getRespostas();
            for (Resposta resposta : respostas) {
                try {
                    resposta.commitResposta(cx);
                } catch (SQLException ex) {
                    Logger.getLogger(TentativaDAO.class.getName()).log(Level.SEVERE, null, ex);
                    cx.rollback();
                    throw new SQLException("Erro ao gerar Tentativa!");
                }
            }
            cx.commit();
        }
    }
}
