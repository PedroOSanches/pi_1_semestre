package br.maua.infrastructure.DAO;

import br.maua.domain.Questao;
import br.maua.exception.QuestoesException;

import java.sql.*;
import java.sql.SQLException;

public class QuestaoDAO{
    public static void commit(Questao q, Connection cx, String tipo) throws SQLException {
        String sql;
        sql = "INSERT INTO questao(id_tarefa, tipo_questao, enunciado_questao) VALUES (?, ?, ?);";

        try(
                PreparedStatement ps = cx.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
        ){

            ps.setInt(1, q.getTarefa().getIdTarefa());
            ps.setString(2, tipo);
            ps.setString(3, q.getEnunciado());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(!rs.next()){
                throw new QuestoesException(q.getEnunciado());
            }
            q.setIdQuestao(rs.getInt(1));
    }
}
}
