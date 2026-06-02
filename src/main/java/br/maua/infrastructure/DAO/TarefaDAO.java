package br.maua.infrastructure.DAO;

import br.maua.domain.Questao;
import br.maua.domain.Tarefa;
import br.maua.infrastructure.ConnectionFactory;

import javax.swing.*;
import java.sql.*;

public class TarefaDAO {

    public static void commitTarefa(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO tarefa(titulo_tarefa, id_casa, prazo_tarefa) VALUES(?, ?, ?);";
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            cx.setAutoCommit(false);
            try {
                ps.setString(1, tarefa.getTitulo());
                ps.setInt(2, tarefa.getCasa().getIdCasa());
                ps.setDate(3, tarefa.getPrazo());

                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys();) {
                    if (!rs.next()) {
                        cx.rollback();
                        throw new SQLException("Erro ao gerar tarefa");
                        }
                        int idTarefa = rs.getInt(1);
                        tarefa.setIdTarefa(idTarefa);
                        for (Questao q : tarefa.getQuestoes()) {
                            q.questaoCommit(cx);
                    }
                        cx.commit();
                    }
                } catch (SQLException ex) {
                    cx.rollback();
                    throw ex;
                }
            } catch (SQLException e) {
                throw e;
            }
        }

    }
