package br.maua.domain;

public class Aluno {
    private int idAluno;
    private String nome;
    private String sobrenome;
    private String username;
    private String curso;
    private String senha;

    public Aluno() {
    }

    public Aluno(String nome, String sobrenome, String username, String senha) {
        setNome(nome);
        setSobrenome(sobrenome);
        setUsername(username);
        setSenha(senha);
    }

    public Aluno(int idAluno, String nome, String sobrenome, String username, String curso, String senha){
        this(nome, sobrenome, username, senha);
        setIdAluno(idAluno);
        setCurso(curso);
    }


    public int getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
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
    public String getNomeCompleto(){
        return nome + " " + sobrenome;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
    

    public void realizarTentativa(Tarefa tarefa) {
        entregarTentativa(tarefa);
    }

    public Tentativa entregarTentativa(Tarefa tarefa) {
        return new Tentativa(this, tarefa);
    }
}