package br.maua.domain;

import br.maua.infrastructure.DAO.ProfessorDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Professor extends Usuario {
    private final List<Turma> turmas = new ArrayList<>();
    public Professor() {
    }

    public Professor(String nome) {
        setNome(nome);
        setSobrenome("");
    }

    public Professor(int idProfessor, String nome, String sobrenome) {
        super(idProfessor, nome, sobrenome);
    }

    public Professor(int idProfessor, String nome, String sobrenome, String username) {
        this(idProfessor, nome, sobrenome);
        setUsername(username);
    }

    public Professor(String nome, String sobrenome, String username, String senha) {
        super(nome, sobrenome, senha);
        setUsername(username);
    }

    public void addTurma(Turma turma) {
        turmas.add(turma);
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    @Override
    public void setUsername(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@maua\\.br$");
        if (!pattern.matcher(username).matches()) {
            throw new IllegalArgumentException("Username Inválido!");
        }
        super.setUsername(username);
    }

    @Override
    public void preencheAtributos() throws SQLException {
        ProfessorDAO.obterTurmas(this);
    }

}