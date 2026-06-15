package br.maua.domain;

import br.maua.exception.UpdateException;
import br.maua.infrastructure.DAO.RespostaDissertativaDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class RespostaDissertativa extends Resposta{
    private String textoResposta;

    public RespostaDissertativa() {
    }
    public RespostaDissertativa(Tentativa tentativa, QuestaoDissertativa questao, String textoResposta) {
        super(tentativa, questao);
        setTextoResposta(textoResposta);
    }


    public String getTextoResposta() { 
        return textoResposta; 
    }
    public void setTextoResposta(String textoResposta) {
         this.textoResposta = textoResposta; 
    }

    @Override
    public void commitResposta(Connection cx) throws SQLException, UpdateException {
        RespostaDissertativaDAO.commitResposta(cx, this);
    }
}
