package br.maua;

import java.util.List;

public class Tarefa {
    private String prazo;
    private Casa casa;
    private List <Questao> questoes;

    public Tarefa(String prazo, List <Questao> questoes, Casa casa) {

      this.prazo = prazo;
      this.questoes = questoes;
      this.casa = casa;

    }

  public Questao adicionarQuestao() {

    Questao questao = new Questao();
    questoes.add(questao);
    return questao;

  }

  public void exibirQuestao() {

  }
}