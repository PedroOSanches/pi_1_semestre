package br.maua.domain;

public class Usuario {
    private int id;
    private String nome;
    private String sobrenome;
    private String username;
    private String senha;

    public Usuario() {}
    public Usuario(int id, String nome, String sobrenome) {
        setId(id);
        setNome(nome);
        setSobrenome(sobrenome);

    }
    public Usuario(String nome, String sobrenome, String username, String senha){
        setNome(nome);
        setSobrenome(sobrenome);
        setUsername(username);
        setSenha(senha);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public void setUsername(String username){
        this.username = username;
    };

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}
