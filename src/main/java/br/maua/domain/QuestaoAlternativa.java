package br.maua.domain;

import java.util.ArrayList;
import java.util.List;

public class QuestaoAlternativa extends Questao {
    private Tarefa tarefa;
    private List <Alternativa> alternativas =  new ArrayList<>();

    public QuestaoAlternativa() {
        super();
    }

    public QuestaoAlternativa(String enunciado, Tarefa tarefa) {
        super(enunciado, tarefa);
    }


    public void adicionarAlternativa(Alternativa alternativa){
        this.alternativas.add(alternativa);
    }
}
