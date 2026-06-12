package br.maua.infrastructure.DAO;

import br.maua.domain.Aluno;
import br.maua.domain.Turma;
import br.maua.infrastructure.ConnectionFactory;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurmaDAO {
    public static List<Turma> listarTurmas() throws SQLException, RuntimeException {
        String sql = "SELECT id_turma, cod_turma FROM turma ORDER BY cod_turma ";
        List<Turma> turmas = new ArrayList<>();
        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            try {
                while (rs.next()) {
                    int idTurma = rs.getInt("id_turma");
                    String codTurma = rs.getString("cod_turma");
                    turmas.add(new Turma(idTurma, codTurma));
                }
                return turmas;
            } catch (NullPointerException ex) {
                Logger.getLogger(TurmaDAO.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException("Tabela turma vazia");
            }
        }
    }

    public static List<Aluno> buscaAlunos(Turma turma) throws SQLException {
        String sql = """
                SELECT
                id_usuario, 
                nome_usuario, 
                sobrenome_usuario, 
                username_usuario,
                    media
                FROM turma_usuario 
                JOIN usuario USING(id_usuario) 
                JOIN (
                    SELECT 
                        id_usuario, AVG(maiores_notas) as media 
                        FROM (
                            SELECT id_tarefa, id_usuario, MAX(nota_total) maiores_notas 
                            FROM (
                                SELECT id_tentativa,id_tarefa, id_usuario, SUM(nota_resposta) as nota_total 
                                FROM tentativa 
                                    JOIN resposta USING(id_tentativa) 
                                    GROUP BY id_tentativa, id_tarefa, id_usuario
                                    ) r 
                                GROUP BY id_tarefa, id_usuario 
                            ) maior GROUP BY id_usuario 
                    ) tab_media USING (id_usuario) 
                    WHERE id_turma_subturma = ? AND tipo_usuario = 'aluno';
                """;
        if(!(turma.getIdTurma() > 0)){
            throw new InvalidParameterException("Id de turma deve ser maior que 0");
        }
        try(
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql)
                ){
            ps.setInt(1, turma.getIdTurma());
            ResultSet rs = ps.executeQuery();
            List<Aluno> alunos = new ArrayList<>();
            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nomeUsuario = rs.getString("nome_usuario");
                String sobrenomeUsuario = rs.getString("sobrenome_usuario");
                String usernameUsuario = rs.getString("username_usuario");
                Aluno aluno = new Aluno(idUsuario, nomeUsuario, sobrenomeUsuario, usernameUsuario);
                aluno.setMedia(rs.getFloat("media"));
                alunos.add(aluno);
            }
            return alunos;
        }
    }
    public static void salvar(Turma turma) throws SQLException, RuntimeException {
        String sql = "INSERT INTO turma(cod_turma) VALUES (?)";
        try (
                PreparedStatement ps = ConnectionFactory.obterConexao().prepareStatement(sql)
        ) {
            ps.setString(1, turma.getNomeTurma());
            ps.executeUpdate();
        }
    }
}
