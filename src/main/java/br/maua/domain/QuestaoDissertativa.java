package br.maua.domain;

import java.sql.Connection;
import java.sql.SQLException;

import br.maua.infrastructure.DAO.QuestaoDissertativaDAO;

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

    @Override
    public void questaoCommit(Connection cx) throws SQLException {
        QuestaoDissertativaDAO.commit(this, cx);
    }
}


