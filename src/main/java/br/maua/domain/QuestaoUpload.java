package br.maua.domain;


import br.maua.exception.UpdateException;
import br.maua.infrastructure.DAO.QuestaoUploadDAO;

import javax.swing.*;
import java.util.Map;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class QuestaoUpload extends Questao {
    private File arquivo;
    private String titulo;
    private Map<String, String> listaArquivos;

    public QuestaoUpload(){}
    public QuestaoUpload(String descricao, String titulo, File arquivo, Tarefa tarefa) {
        super(descricao, tarefa);
        setTitulo(titulo);
        setArquivo(arquivo);
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public File getArquivo() {
        return arquivo;
    }

    public Map<String, String> getListaArquivos()  {
        return listaArquivos;
    }

    public String getTitulo() {
        return titulo;
    }

    public File gerarArquivoDestino(String pastaDestino) throws UpdateException {
        if (arquivo == null) return null;

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

        File pasta = new File(pastaDestino);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File novoArquivo = new File(pasta, nomeBase + extensao);

        int contador = 1;
        while (novoArquivo.exists()) {
            String nomeComContador = nomeBase + "_" + contador + extensao;
            novoArquivo = new File(pasta, nomeComContador);
            contador++;
        }

        System.out.println("Novo Arquivo: " + novoArquivo.getAbsolutePath());
        return novoArquivo;
    }

    @Override
    public void questaoCommit(Connection cx) throws SQLException {
        QuestaoUploadDAO.commit(this, cx);
    }
}
