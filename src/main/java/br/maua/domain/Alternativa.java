package br.maua.domain;


import br.maua.infrastructure.DAO.AlternativaDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class Alternativa {
    private QuestaoAlternativa questaoAlternativa;
    private String enunciado;
    private boolean alternativaAssinalada;
    private boolean alternativaCorreta;

    public Alternativa(QuestaoAlternativa questaoAlternativa, String enunciado, boolean alternativaCorreta) {
        setQuestaoAlternativa(questaoAlternativa);
        setEnunciado(enunciado);
        setAlternativaCorreta(alternativaCorreta);
    }

    public String getEnunciado() {
        return enunciado;
    }
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public boolean isAlternativaCorreta() {
        return alternativaCorreta;
    }
    public void setAlternativaCorreta(boolean alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public QuestaoAlternativa getQuestaoAlternativa() {
        return questaoAlternativa;
    }
    public void setQuestaoAlternativa(QuestaoAlternativa questaoAlternativa) {
        this.questaoAlternativa = questaoAlternativa;
    }

    public void alternativaCommit(Connection cx)throws SQLException {
        AlternativaDAO.commit(this, cx);
    }
}
