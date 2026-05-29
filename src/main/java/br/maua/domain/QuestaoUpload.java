package br.maua.domain;

import java.io.File;
import java.util.Map;

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

    public Map<String, String> getListaArquivos() {
        return listaArquivos;
    }

    public String getTitulo() {
        return titulo;
    }
}
