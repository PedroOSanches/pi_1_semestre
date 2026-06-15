package br.maua.domain;

import br.maua.exception.UpdateException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Resposta {
    private int idResposta;
    private Questao questao;
    private String enunciado;
    private float nota;
    private Tentativa tentativa;

    public Resposta() {
    }
    public Resposta(Tentativa tentativa, Questao questao) {
        setTentativa(tentativa);
        setQuestao(questao);
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public float getNota() {
        return nota;
    }

    public Tentativa getTentativa() {
        return tentativa;
    }

    public void setTentativa(Tentativa tentativa) {
        this.tentativa = tentativa;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public abstract void commitResposta(Connection cx) throws SQLException, UpdateException;
}

