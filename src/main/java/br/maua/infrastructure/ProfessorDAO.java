package br.maua.infrastructure;

import br.maua.domain.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {

	public boolean autenticar(String username, String senha) throws SQLException {

		String sql = "SELECT * FROM usuario WHERE username_usuario = ? AND senha_usuario = ?";

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

	public void salvarNoBanco(Professor professor) throws SQLException {
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

	public Professor obterProfessorCompleto(String username, String senha) throws SQLException {
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
}
