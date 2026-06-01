package br.maua.domain;

import java.util.List;

public class QuestaoAlternativa extends Questao {
    private List<Alternativa> alternativas;
    
    public List<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }
}

