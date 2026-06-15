package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import br.maua.domain.Alternativa;
import br.maua.domain.QuestaoAlternativa;

public class QuestaoAlternativaDAO{
   
    public static void commit(QuestaoAlternativa qa, Connection cx) throws SQLException {
             QuestaoDAO.commit(qa, cx, "alternativa");
                for (Alternativa a: qa.getAlternativas()) {
                    a.alternativaCommit(cx);
            }
        }
    }
