package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.maua.domain.Questao;
import br.maua.domain.Tarefa;
import br.maua.infrastructure.ConnectionFactory;

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
                } catch (Exception ex) {
                    cx.rollback();
                    throw ex;
                }
            } catch (SQLException e) {
                throw e;
            }
        }

        public List <String> buscarTitulosPorSecao (String secao) {

            List <String> titulos = new ArrayList<>();

            String sql = "SELECT t.titulo_tarefa "
                    + "FROM tarefa t "
                    + "JOIN casa c ON t.id_casa = c.id_casa "
                    + "JOIN secao s ON c.id_secao = s.id_secao "
                    + "WHERE s.titulo_secao = ?";

            try (Connection cx = ConnectionFactory.obterConexao();
                 PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setString(1, secao);
                java.sql.ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    titulos.add(rs.getString("titulo_tarefa"));
                }

            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            return titulos;

        }

    }
