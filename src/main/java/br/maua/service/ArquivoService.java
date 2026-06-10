package br.maua.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

import br.maua.domain.QuestaoUpload;
import br.maua.exception.UpdateException;

public class ArquivoService {
    public static File salvarArquivo(File origem, File destino) throws UpdateException {
        try {
            java.nio.file.Files.copy(
                    origem.toPath(),
                    destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return destino;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UpdateException("Erro ao tentar salvar arquivo" );
        }
    }

    public static File gerarArquivoDestino(QuestaoUpload qu, String destinoString) {
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

            File pasta = new File(destinoString);

            File novoArquivo = new File(pasta, nomeBase + extensao);

            int contador = 1;
            while (novoArquivo.exists()) {
                String nomeComContador = nomeBase + "_" + contador + extensao;
                novoArquivo = new File(pasta, nomeComContador);
                contador++;
            }
 
            return novoArquivo;


        } catch (Exception e) {
            e.printStackTrace();
            return arquivo;
        }
    }
}
