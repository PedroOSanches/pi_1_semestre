package br.maua.infrastructure.DAO;


import br.maua.domain.Professor;
import br.maua.infrastructure.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.maua.domain.Professor;
import br.maua.infrastructure.ConnectionFactory;

public class ProfessorDAO {

	public boolean autenticar(String username, String senha) throws SQLException {

		String sql = "SELECT username_usuario, senha_usuario FROM usuario WHERE username_usuario = ? AND senha_usuario = ?";

		try (Connection conexao = ConnectionFactory.obterConexao();
			PreparedStatement comando = conexao.prepareStatement(sql)) {

			comando.setString(1, username);
			comando.setString(2, senha);

			try (ResultSet resultado = comando.executeQuery()) {
				return resultado.next();
			}
		}
	}

	public String obterTipoUsuario(String username, String senha) throws SQLException {
		String sql = "SELECT tipo_usuario FROM usuario WHERE username_usuario = ? AND senha_usuario = ?";

		try (Connection conexao = ConnectionFactory.obterConexao();
			PreparedStatement comando = conexao.prepareStatement(sql)) {

			comando.setString(1, username);
			comando.setString(2, senha);

			try (ResultSet resultado = comando.executeQuery()) {
				if (resultado.next()) {
					return resultado.getString("tipo_usuario");
				}
				return null;
			}
		}
	}

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
}
