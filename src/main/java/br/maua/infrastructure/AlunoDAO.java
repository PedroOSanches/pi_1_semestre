package br.maua.infrastructure;

import br.maua.domain.Aluno;
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

    public boolean usernameEhSomenteNumeros(String username) {
        return username != null && !username.isBlank() && username.matches("\\d+");
    }

    public String determinarRole(String username) {
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
}
