package br.maua.domain;

import br.maua.infrastructure.DAO.QuestaoAlternativaDAO;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuestaoAlternativa extends Questao {
    private List<Alternativa> alternativas;
    private boolean alternativaAssinalada;
    private List <Alternativa> alternativas =  new ArrayList<>();

    public QuestaoAlternativa() {
        super();
    }

    public QuestaoAlternativa(String enunciado, Tarefa tarefa) {
        super(enunciado, tarefa);
    }

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public void adicionarAlternativa(Alternativa alternativa){
        this.alternativas.add(alternativa);
    }

    @Override
    public void questaoCommit(Connection cx) throws SQLException {
        QuestaoAlternativaDAO.commit(this, cx);
    }
}

