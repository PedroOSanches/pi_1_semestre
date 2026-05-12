package br.maua;

import java.util.List;

public class Tarefa {
  
  private String prazo;
  private List <Questao> questoes;

  public Tarefa(String prazo, List <Questao> questoes) {

    this.prazo = prazo;
    this.questoes = questoes;

  }

  public Questao adicionarQuestao() {

    Questao questao = new Questao();
    questoes.add(questao);
    return questao;

  }

  public void exibirQuestao() {

  }
}