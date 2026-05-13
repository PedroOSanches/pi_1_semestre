package br.maua.domain;

import java.util.List;

public class Tarefa {
    private int idTarefa;
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

  public int getIdTarefa() {
        return idTarefa;
  }
  public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
  }

  public String getPrazo() {
        return prazo;
  }
  public void setPrazo(String prazo) {
        this.prazo = prazo;
  }
  public Casa getCasa() {
        return casa;
  }
  public void setCasa(Casa casa) {
        this.casa = casa;
  }

  public void exibirQuestao() {}
}