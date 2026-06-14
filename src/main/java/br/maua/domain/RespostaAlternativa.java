package br.maua.domain;

import br.maua.exception.UpdateException;
import br.maua.infrastructure.DAO.RespostaAlternativaDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class RespostaAlternativa extends Resposta {
    private int idAlternativa;

    public RespostaAlternativa(
            Tentativa tentativa, QuestaoAlternativa questao, int idAlternativa
    ) {
        super(tentativa, questao);
        setIdAlternativa(idAlternativa);
    }

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(int idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    @Override
    public void commitResposta(Connection cx) throws SQLException, UpdateException {
        RespostaAlternativaDAO.commitResposta(cx, this);
    }
}