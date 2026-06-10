package br.maua.domain;

import java.util.ArrayList;
import java.util.List;

public class QuestaoAlternativa extends Questao {

    private List <Alternativa> alternativas = new ArrayList<>();
    private boolean alternativaAssinalada;

    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }
}

