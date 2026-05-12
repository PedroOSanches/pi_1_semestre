package br.maua;

public class Professor {
  
  private String nome;
  private String sobrenome;
  private String username;

  public Tarefa criarTarefa() {

    Tarefa tarefa = new Tarefa("", null);

    return tarefa;

  }

  public void atribuirNota(double nota, Tentativa tentativa) {

   tentativa.setNota(nota);
    
  }

  public void corrigirTarefa(Tentativa tentativa) {

    atribuirNota(0.0, tentativa);

  }
}