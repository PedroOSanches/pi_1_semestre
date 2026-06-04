package br.maua.domain;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

public class Aluno extends Usuario {
    private String curso;
    private Turma turma;

    public Aluno(){}
    public Aluno(String nome, String sobrenome, String username, String senha) {
        super(nome, sobrenome, senha);
        setUsername(username);
    }

    public Aluno(int idAluno, String nome, String sobrenome, String username, String curso, String senha){
        this(nome, sobrenome, username, senha);
        setCurso(curso);
    }

    @Override
    public void setUsername(String username) {
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{5}-\\d@maua\\.br$");
        if (!pattern.matcher(username).matches()) {
            throw new InvalidParameterException("Username invalido");
        }
        super.setUsername(username);
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void realizarTentativa(Tarefa tarefa) {
        entregarTentativa(tarefa);
    }

    public Tentativa entregarTentativa(Tarefa tarefa) {
        return new Tentativa(this, tarefa);
    }
}