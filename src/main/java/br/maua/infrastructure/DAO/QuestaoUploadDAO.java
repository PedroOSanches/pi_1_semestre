package br.maua.infrastructure.DAO;

import br.maua.domain.QuestaoUpload;
import br.maua.exception.UpdateException;
import br.maua.service.ArquivoService;

import java.io.File;
import java.nio.file.StandardCopyOption;
import java.sql.*;

public class QuestaoUploadDAO {

    public static File gerarArquivoDestino(QuestaoUpload qu) {
        File arquivo = qu.getArquivo();
        String titulo = qu.getTitulo();

        if (arquivo == null) return null;

        try {
            String novoTitulo = titulo.trim()
                    .replaceAll("\\s+", "_")
                    .replaceAll("[^a-zA-Z0-9_]", "");

            if (novoTitulo.isBlank()) {
                throw new UpdateException("Título com nome vazio.");
            }

            String tituloOriginal = arquivo.getName();
            int i = tituloOriginal.lastIndexOf(".");
            String extensao = (i > 0) ? tituloOriginal.substring(i) : "";

            String prefix = "TTT0001_";
            String nomeBase = prefix + novoTitulo;

            File pasta = new File("assets/QuestoesUpload");

            File novoArquivo = new File(pasta, nomeBase + extensao);

            int contador = 1;
            while (novoArquivo.exists()) {
                String nomeComContador = nomeBase + "_" + contador + extensao;
                novoArquivo = new File(pasta, nomeComContador);
                contador++;
            }

            java.nio.file.Files.copy(arquivo.toPath(), novoArquivo.toPath(),  StandardCopyOption.REPLACE_EXISTING);
            return novoArquivo;

        } catch (Exception e) {
            e.printStackTrace();
            return arquivo;
        }
    }
    public static void commit(QuestaoUpload qu, Connection cx) throws SQLException, UpdateException {
        QuestaoDAO.commit(qu, cx, "upload");
        String sql;
        sql = "INSERT INTO upload(titulo_upload, arquivo_modelo_upload, id_questao) VALUES (?, ?, ?)";

        try(PreparedStatement ps = cx.prepareStatement(sql)){
            File arquivo = qu.gerarArquivoDestino("src/main/resources/assets/professor");

            ps.setString(1, qu.getTitulo());
            ps.setString(2, arquivo.getName());
            ps.setInt(3, qu.getIdQuestao());

            ps.executeUpdate();
            ArquivoService.salvarArquivo(qu.getArquivo(), arquivo);
        }
    }
}
