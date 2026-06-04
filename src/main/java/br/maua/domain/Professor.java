package br.maua.domain;

import java.util.List;
import java.util.regex.Pattern;

public class Professor extends Usuario {
    private List<Turma> turmas;
    public Professor() {
    }

    public Professor(String nome) {
        setNome(nome);
        setSobrenome("");
    }

    public Professor(int idProfessor, String nome, String sobrenome) {
        super(idProfessor, nome, sobrenome);
    }

    public Professor(String nome, String sobrenome, String username, String senha) {
        super(nome, sobrenome, senha);
        setUsername(username);
    }

    @Override
    public void setUsername(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@maua\\.br$");
        if (!pattern.matcher(username).matches()) {
            throw new IllegalArgumentException("Username Inválido!");
        }
        super.setUsername(username);
    }

}