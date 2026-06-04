package br.maua.domain;

public class Curso {
    private int idCurso;
    private String nome;

    public Curso(String nome) {
        setNome(nome);
    }
    public Curso(int idCurso, String nome){
        sedIdCurso(idCurso);
        setNome(nome);
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void sedIdCurso(int idCurso){
        this.idCurso = idCurso;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
