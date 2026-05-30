package br.maua.infrastructure.DAO;

import br.maua.domain.Aluno;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {
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

    public void salvarNoBanco(Aluno aluno) throws SQLException {
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

    public Aluno obterAlunoCompleto(String username, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE username_usuario = ? AND senha_usuario = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, username);
            comando.setString(2, senha);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setNome(resultado.getString("nome_usuario"));
                    aluno.setSobrenome(resultado.getString("sobrenome_usuario"));
                    aluno.setUsername(resultado.getString("username_usuario"));
                    aluno.setSenha(resultado.getString("senha_usuario"));
                    return aluno;
                }
                return null;
            }
        }
    }
}
