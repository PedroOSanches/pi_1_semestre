package br.maua.domain;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import br.maua.infrastructure.DAO.AlunoDAO;

public class Aluno extends Usuario {
    private Turma turma;

    public Aluno(){}
    public Aluno(String nome, String sobrenome, String username, String senha) {
        super(nome, sobrenome, senha);
        setUsername(username);
    }

    public Aluno(int id, String nome, String sobrenome, String username) {
        super(id, nome, sobrenome);
        setUsername(username);
    }

    public Aluno(int idAluno, String nome, String sobrenome, String username, String senha) {
        this(nome, sobrenome, username, senha);
        setId(idAluno);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setUsername(String username) {
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{5}-\\d@maua\\.br$");
        if (!pattern.matcher(username).matches()) {
            throw new InvalidParameterException("Username invalido");
        }
        super.setUsername(username);
    }


    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Turma getTurma() {
        return turma;
    }


    @Override
    public void preencheAtributos() throws SQLException {
        AlunoDAO.obterTurma(this);

    }
}