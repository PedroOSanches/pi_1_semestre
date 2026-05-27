package br.maua.domain;

public class Professor {
    private String nome;
    private String sobrenome;
    private String username;
    private String senha;

    public Professor() {
    }

    public Professor(String nome, String sobrenome, String username, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.username = username;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public void atribuirNota(double nota, Tentativa tentativa) {
        tentativa.setNota(nota);
    }

    public void corrigirTarefa(Tentativa tentativa) {
        atribuirNota(0.0, tentativa);
    }
}