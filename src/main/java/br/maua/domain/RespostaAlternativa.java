package br.maua.domain;

import br.maua.exception.UpdateException;
import br.maua.infrastructure.DAO.RespostaAlternativaDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class RespostaAlternativa extends Resposta {

    private int idAlternativaAssinalada;


    @Override
    public QuestaoAlternativa getQuestao() {
        return (QuestaoAlternativa) super.getQuestao();
    }

    public int getIdAlternativaAssinalada() {
        return idAlternativaAssinalada;
    }

    public void setIdAlternativaAssinalada(int idAlternativaAssinalada) {
        this.idAlternativaAssinalada = idAlternativaAssinalada;
    }

    public RespostaAlternativa() {
    }
    public RespostaAlternativa(
            Tentativa tentativa, QuestaoAlternativa questao, int idAlternativa
    ) {
        super(tentativa, questao);
        setIdAlternativaAssinalada(idAlternativa);
    }


    @Override
    public void commitResposta(Connection cx) throws SQLException, UpdateException {
        RespostaAlternativaDAO.commitResposta(cx, this);
    }
}