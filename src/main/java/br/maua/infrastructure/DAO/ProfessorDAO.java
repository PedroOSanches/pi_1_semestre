package br.maua.infrastructure.DAO;


import br.maua.domain.*;
import br.maua.enums.SemestreEnum;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {


	public boolean usernameEhSomenteNumeros(String username) {
		return username != null && !username.isBlank() && username.matches("\\d+");
	}

	public String determinarTipoUsuario(String username) {
		return usernameEhSomenteNumeros(username) ? "aluno" : "professor";
	}

	public static void salvarNoBanco(Professor professor) throws SQLException {
		String sql = "INSERT INTO usuario (nome_usuario, sobrenome_usuario, username_usuario, senha_usuario, tipo_usuario) VALUES (?, ?, ?, ?, 'professor')";

		try (Connection conexao = ConnectionFactory.obterConexao();
			PreparedStatement comando = conexao.prepareStatement(sql)) {

			comando.setString(1, professor.getNome());
			comando.setString(2, professor.getSobrenome());
			comando.setString(3, professor.getUsername());
			comando.setString(4, professor.getSenha());

			comando.executeUpdate();
		}
	}

	public static Professor obterProfessorCompleto(String username, String senha) throws SQLException {
		String sql = "SELECT * FROM usuario WHERE username_usuario = ? AND senha_usuario = ?";

		try (Connection conexao = ConnectionFactory.obterConexao();
			PreparedStatement comando = conexao.prepareStatement(sql)) {

			comando.setString(1, username);
			comando.setString(2, senha);

			try (ResultSet resultado = comando.executeQuery()) {
				if (resultado.next()) {
					Professor professor = new Professor();
					professor.setNome(resultado.getString("nome_usuario"));
					professor.setSobrenome(resultado.getString("sobrenome_usuario"));
					professor.setUsername(resultado.getString("username_usuario"));
					professor.setSenha(resultado.getString("senha_usuario"));
					return professor;
				}
				return null;
			}
		}
	}
	public static List<Professor> listarProfessores() throws SQLException {
		String sql = "SELECT id_usuario, nome_usuario, sobrenome_usuario FROM usuario WHERE tipo_usuario ='professor'";
		try (
				Connection conexao = ConnectionFactory.obterConexao();
				PreparedStatement ps = conexao.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()
				){
			List<Professor> professores = new ArrayList<>();
			while (rs.next()) {
				int idProfessor = rs.getInt("id_usuario");
				String nomeProfessor = rs.getString("nome_usuario");
				String sobrenomeProfessor = rs.getString("sobrenome_usuario");
				Professor professor = new Professor(idProfessor, nomeProfessor, sobrenomeProfessor);
				professores.add(professor);
			}
			return professores;
		}
	}
	public static void salvarNaTurma(Connection cx, Professor professor, int idTurmaSubturma) throws SQLException {
		String sql = "INSERT INTO turma_usuario(id_usuario, id_turma_subturma) VALUES (?, ?)";
		try(
				PreparedStatement ps = cx.prepareStatement(sql)
				){
			ps.setInt(1, professor.getId());
			ps.setInt(2, idTurmaSubturma);
			ps.executeUpdate();
		}
	}

	public static void obterTurmas(Professor professor) throws SQLException {
		String sql = "SELECT id_turma_subturma, cod_turma, cod_subturma, nome_curso, ano, semestre_turma_subturma FROM turma_usuario JOIN usuario USING(id_usuario) JOIN turma_subturma USING(id_turma_subturma) JOIN turma USING(id_turma) JOIN subturma USING(id_subturma) JOIN curso USING(id_curso) JOIN ano USING(id_ano) WHERE id_usuario = ? ORDER BY id_turma_subturma ASC";
		try (
				Connection cx = ConnectionFactory.obterConexao();
				PreparedStatement ps = cx.prepareStatement(sql)
		) {
			ps.setInt(1, professor.getId());
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
				professor.addTurma(new Turma(idTurma, codTurma, curso, semestre, sub, ano));
			}
		}
	}
}
