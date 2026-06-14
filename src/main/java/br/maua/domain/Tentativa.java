package br.maua.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.maua.infrastructure.ConnectionFactory;

public class Tentativa {

    private Double nota;
    private Aluno aluno;
    private Tarefa tarefa;
    private boolean concluida = false;
    private int idTentativa;
    private List<Resposta> respostas = new ArrayList<>();

    public Tentativa(Double nota, Aluno aluno, Tarefa tarefa){
        this.setNota(nota);
        this.setAluno(aluno);
        this.setTarefa(tarefa);
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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public boolean isConcluida() {
        return concluida;
    }
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public int getIdTentativa() {
        return idTentativa;
    }

    public void setIdTentativa(int idTentativa) {
        this.idTentativa = idTentativa;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}