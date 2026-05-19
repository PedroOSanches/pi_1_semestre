package br.maua.domain;

import java.util.List;

public class Tarefa {
    private int idTarefa;
    private String prazo;
    private Casa casa;
    private List<QuestaoAlternativa> questoesAlternativa;
    private List<QuestaoDissertativa> questoesDissertativas;
    private List<QuestaoUpload> questoesUploads;

    public Tarefa(String prazo, Casa casa) {

      this.prazo = prazo;
      this.casa = casa;

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