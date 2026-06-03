package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.maua.domain.RespostaUpload;
import br.maua.infrastructure.ConnectionFactory;

public class RespostaUploadDAO {
    public static void salvar(RespostaUpload respostaupload) {
    String sqlUpload = "INSERT INTO resposta_upload (id_resposta, arquivo_resposta) VALUES (?, ?)";
        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sqlUpload)) {

            ps.setInt(1, respostaupload.getIdResposta());
            ps.setString(2, respostaupload.getCaminhoArquivo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta upload", e);
        }
    }    
}
