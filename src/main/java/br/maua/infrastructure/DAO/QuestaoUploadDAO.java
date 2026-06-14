package br.maua.infrastructure.DAO;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import br.maua.domain.QuestaoUpload;
import br.maua.exception.UpdateException;
import br.maua.infrastructure.ConnectionFactory;
import br.maua.service.ArquivoService;

import static br.maua.config.AppConfig.BASE_PROFESSOR;


public class QuestaoUploadDAO {

    public static void commit(QuestaoUpload qu, Connection cx) throws SQLException, UpdateException {
        QuestaoDAO.commit(qu, cx, "upload");
        String sql;
        sql = "INSERT INTO upload(titulo_upload, arquivo_modelo_upload, id_questao) VALUES (?, ?, ?)";

        try (PreparedStatement ps = cx.prepareStatement(sql)) {
            File arquivo = ArquivoService.gerarArquivoDestino(qu, BASE_PROFESSOR);

            ArquivoService.salvarArquivo(qu.getArquivo(), arquivo);

            ps.setString(1, qu.getTitulo());
            ps.setString(2, arquivo.getName());
            ps.setInt(3, qu.getIdQuestao());

            ps.executeUpdate();
        }
    }

    public static void consultarArquivo(QuestaoUpload qu) throws SQLException {
        String sql = "SELECT titulo_upload, arquivo_modelo_upload FROM upload WHERE id_questao = ?";
        try(
            Connection cx = ConnectionFactory.obterConexao();
            PreparedStatement ps = cx.prepareStatement(sql)) {
            ps.setInt(1, qu.getIdQuestao());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tituloUpload = rs.getString("titulo_upload");
                String arquivoModeloUpload = rs.getString("arquivo_modelo_upload");
                qu.setTitulo(tituloUpload);
                qu.setArquivo(new File(BASE_PROFESSOR + arquivoModeloUpload));
            }
        }
    }
}
