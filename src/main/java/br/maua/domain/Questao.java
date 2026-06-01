package br.maua.domain;

import java.sql.SQLException;
import java.sql.Connection;

public abstract class Questao {

    private int idQuestao;
    private String enunciado;
    private Tarefa tarefa;

    public Questao(){}

    public Questao(int idQuestao, String enunciado, Tarefa tarefa){
        setIdQuestao(idQuestao);
        setEnunciado(enunciado);
        setTarefa(tarefa);
    }

    public Questao(Tarefa tarefa){
        setTarefa(tarefa);
    }
    public Questao(String enunciado, Tarefa tarefa){
        setEnunciado(enunciado);
        setTarefa(tarefa);
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public abstract void questaoCommit(Connection cx) throws SQLException;
}