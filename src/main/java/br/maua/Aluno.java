package br.maua;

public class Aluno {

  private String nome;
  private String sobrenome;
  private String username;

  public void realizarTentativa() {

    entregarTentativa();

  }

  public Tentativa entregarTentativa() {

    Tentativa tentativa = new Tentativa();

    return tentativa;
  }
}