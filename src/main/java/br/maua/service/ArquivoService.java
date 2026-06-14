package br.maua.service;

import br.maua.exception.UpdateException;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

import br.maua.domain.QuestaoUpload;
import br.maua.domain.RespostaAlternativa;
import br.maua.domain.RespostaUpload;
import br.maua.exception.UpdateException;
import br.maua.exception.UploadException;

import static br.maua.config.AppConfig.BASE_ALUNO;

public class ArquivoService {
    public static File salvarArquivo(File origem, File destino) throws UpdateException {
        try {
            if (origem == null || !origem.exists()) {
                throw new UpdateException("Arquivo de origem nao existe!");
            }
            java.nio.file.Files.copy(
                    origem.toPath(),
                    destino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return destino;
        } catch (IOException e) {
            e.printStackTrace();
            throw new UpdateException("Erro ao tentar gerarResposta arquivo");
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

    public static File gerarArquivoDestino(RespostaUpload resposta) throws UploadException {
        try {
            File arquivoOrigem = resposta.getArquivo();

            int i = arquivoOrigem.getName().lastIndexOf(".");
            String extensao = (i > 0) ? arquivoOrigem.getName().substring(i) : "";
            String prefix = resposta.getTentativa().getAluno().getUsername().substring(0, 10);
            String nomeBase = prefix + "_" + arquivoOrigem.getName() + extensao;

            File arquivoDestino = new File(BASE_ALUNO, nomeBase);
            int contador = 1;

            while (arquivoDestino.exists()) {
                String nomeComContador = nomeBase + "_" + contador + extensao;
                arquivoDestino = new File(BASE_ALUNO, nomeComContador);
                contador++;
            }
            return arquivoDestino;
        } catch (Exception e) {
            throw new UploadException("Erro ao gerar arquivo destino");
        }


    }
}
