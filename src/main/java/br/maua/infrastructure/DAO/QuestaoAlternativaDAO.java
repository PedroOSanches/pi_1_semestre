package br.maua.infrastructure.DAO;

import br.maua.domain.Alternativa;
import br.maua.domain.QuestaoAlternativa;
import br.maua.exception.QuestoesException;

import java.sql.SQLException;
import java.sql.*;

public class QuestaoAlternativaDAO{

    public static void commit(QuestaoAlternativa qa, Connection cx) throws SQLException {
             QuestaoDAO.commit(qa, cx, "alternativa");
                for (Alternativa a: qa.getAlternativas()) {
                    a.alternativaCommit(cx);
            }
        }
    }
