package br.maua.domain;

public class QuestaoDissertativa extends Questao {
    private String repostaModelo;

    public QuestaoDissertativa() {}
    public QuestaoDissertativa(String enunciado, String repostaModelo, Tarefa tarefa) {
        super(enunciado, tarefa);
        setRepostaModelo(repostaModelo);
    }

    public String getRepostaModelo() {
        return repostaModelo;
    }

    public void setRepostaModelo(String repostaModelo) {
        this.repostaModelo = repostaModelo;
    }
}
