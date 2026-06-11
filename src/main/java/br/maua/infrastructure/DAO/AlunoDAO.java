package br.maua.infrastructure.DAO;

import br.maua.domain.*;
import br.maua.enums.SemestreEnum;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AlunoDAO {


    public static void salvarNoBanco(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO usuario (nome_usuario, sobrenome_usuario, username_usuario, senha_usuario, tipo_usuario) VALUES (?, ?, ?, ?, 'aluno')";

        try (Connection conexao = ConnectionFactory.obterConexao();
                PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, aluno.getNome());
            comando.setString(2, aluno.getSobrenome());
            comando.setString(3, aluno.getUsername());
            comando.setString(4, aluno.getSenha());

            comando.executeUpdate();
        }
    }
    

    public static Map<String, Integer> obterProgressoAluno(Aluno aluno) throws SQLException {
        String sql = "SELECT tarefas_concluidas, total_tarefas, ROUND(tarefas_concluidas * 100 / total_tarefas, 0) as progresso_aluno FROM ("
                +
                "SELECT COUNT(DISTINCT id_tarefa) as tarefas_concluidas, (SELECT COUNT(*) FROM tarefa) as total_tarefas FROM tentativa WHERE id_usuario = ?) as resultado; ";
        try (
                Connection conexao = ConnectionFactory.obterConexao();
                PreparedStatement ps = conexao.prepareStatement(sql)) {
            Map<String, Integer> progresso = new HashMap<>();
            ps.setInt(1, aluno.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int tarefasConcluidas = rs.getInt("tarefas_concluidas");
                int totalTarefas = rs.getInt("total_tarefas");
                int porcentagemProgresso = rs.getInt("progresso_aluno");

                progresso.put("tarefasConcluidas", tarefasConcluidas);
                progresso.put("totalTarefas", totalTarefas);
                progresso.put("porcentagemProgresso", porcentagemProgresso);
            }
            return progresso;
        }
    }

    public static void obterTurma(Aluno aluno) throws SQLException {
        String sql = "SELECT id_turma_subturma, cod_turma, cod_subturma, nome_curso, ano, semestre_turma_subturma FROM turma_usuario JOIN usuario USING(id_usuario) JOIN turma_subturma USING(id_turma_subturma) JOIN turma USING(id_turma) JOIN subturma USING(id_subturma) JOIN curso USING(id_curso) JOIN ano USING(id_ano) WHERE id_usuario = ? ORDER BY id_turma_subturma DESC LIMIT 1";

        try (
                Connection cx = ConnectionFactory.obterConexao();
                PreparedStatement ps = cx.prepareStatement(sql)
        ) {
            ps.setInt(1, aluno.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SemestreEnum semestre;
                if (rs.getString("semestre_turma_subturma").equals(SemestreEnum.PRIMEIRO.getSemestreLower())) {
                    semestre = SemestreEnum.PRIMEIRO;
                } else {
                    semestre = SemestreEnum.SEGUNDO;
                }
                int idTurma = rs.getInt("id_turma_subturma");
                String codTurma = rs.getString("cod_turma");
                Subturma sub = new Subturma(rs.getString("cod_subturma"));
                Curso curso = new Curso(rs.getString("nome_curso"));
                Ano ano = new Ano(rs.getInt("ano"));
                aluno.setTurma(new Turma(idTurma, codTurma, curso, semestre, sub, ano));
            }
        }
    }
}
