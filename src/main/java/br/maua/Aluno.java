package br.maua;

public class Aluno {
    private int idAluno;
    private String nome;
    private String sobrenome;
    private String username;

    public void realizarTentativa(Tarefa tarefa) {
        entregarTentativa(tarefa);
    }

    public Tentativa entregarTentativa(Tarefa tarefa) {
        return new Tentativa(this, tarefa);
    }
}