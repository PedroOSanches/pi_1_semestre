package br.maua;

public class Tentativa {

    private Double nota;
    private Aluno aluno;
    private Tarefa tarefa;

    public Tentativa(Double nota, Aluno aluno, Tarefa tarefa){
        this.setNota(nota);
        this.aluno = aluno;
        this.tarefa = tarefa;
    }

    public Tentativa(Aluno aluno, Tarefa tarefa){
        this.aluno = aluno;
        this.tarefa = tarefa;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}