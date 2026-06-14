package br.maua.domain;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import br.maua.infrastructure.DAO.QuestaoUploadDAO;

public class QuestaoUpload extends Questao {
    private File arquivo;
    private String titulo;
    private Map<String, String> listaArquivos;

    public QuestaoUpload(){}
    public QuestaoUpload(int idQuestao, String enunciado, Tarefa tarefa){
        super(idQuestao, enunciado, tarefa);
    }
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

    @Override
    public void questaoCommit(Connection cx) throws SQLException {
        QuestaoUploadDAO.commit(this, cx);
    }
}
