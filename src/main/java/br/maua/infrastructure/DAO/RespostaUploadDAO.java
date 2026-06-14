package br.maua.infrastructure.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.maua.domain.RespostaUpload;
import br.maua.infrastructure.ConnectionFactory;

public class RespostaUploadDAO {
    public static void commitResposta(Connection cx, RespostaUpload respostaupload, File arquivo) throws SQLException {
        RespostaDAO.gerarResposta(cx, respostaupload);

        String sqlUpload = "INSERT INTO resposta_upload (id_resposta, arquivo_resposta) VALUES (?, ?)";
        try (
                PreparedStatement ps = cx.prepareStatement(sqlUpload)
        ) {

            ps.setInt(1, respostaupload.getIdResposta());
            ps.setString(2, arquivo.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(RespostaUploadDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Erro ao gerarResposta resposta upload", e);
        }
    }    
}
