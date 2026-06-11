package br.maua.domain;
import br.maua.enums.SemestreEnum;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;
import java.util.List;

public class Turma {
    int idTurma;
    private String nomeTurma;
    private Curso curso;
    private SemestreEnum semestre;
    private Subturma subturma;
    private Ano ano;
    private List<Aluno> alunos;

    public Turma(String nomeTurma) {
        setNomeTurma(nomeTurma);
    }

    public Turma(int idTurma, String nomeTurma) {
        setIdTurma(idTurma);
        setNomeTurma(nomeTurma);
    }

    public Turma(int idTurma, String nomeTurma, Curso curso, SemestreEnum semestre, Subturma subturma, Ano ano) {
        this(idTurma, nomeTurma);
        setCurso(curso);
        setSemestre(semestre);
        setSubturma(subturma);
        setAno(ano);
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        Pattern pattern = Pattern.compile("^[A-Z]\\d{2}$");
        if (nomeTurma.equals("Carregando...")) {
            this.nomeTurma = nomeTurma;
        } else if (pattern.matcher(nomeTurma).find()) {
            this.nomeTurma = nomeTurma;
        } else {
            throw new InvalidParameterException("Código Inválido");
        }
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public SemestreEnum getSemestre() {
        return semestre;
    }

    public void setSemestre(SemestreEnum semestre) {
        this.semestre = semestre;
    }

    public Subturma getSubturma() {
        return subturma;
    }

    public void setSubturma(Subturma subturma) {
        this.subturma = subturma;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Ano getAno() {
        return ano;
    }

    public void setAno(Ano ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return nomeTurma;
    }
}