package br.maua;

public class Casa {
  
  private int numeroCasa;
  private int nivelCasa;
  private Tarefa tarefa;

  public void exibirTarefa() {

    tarefa.exibirQuestao();

  }

  public void exibirCasa() {

    System.out.printf("Casa %d | Nível %d\n", numeroCasa, nivelCasa);

  }

  public void avancarCasa() {

  }
}
