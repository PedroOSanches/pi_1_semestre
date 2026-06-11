package br.maua.infrastructure.DAO;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.maua.domain.Aluno;
import br.maua.domain.Professor;
import br.maua.domain.Usuario;
import br.maua.infrastructure.ConnectionFactory;

public class UsuarioDAO {
    public static Usuario autenticar(String username, String senha) throws SQLException {

        String sql = "SELECT id_usuario, nome_usuario, sobrenome_usuario, username_usuario, tipo_usuario FROM usuario WHERE username_usuario = ? AND senha_usuario = ?";

        try (
                Connection conexao = ConnectionFactory.obterConexao();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, username);
            comando.setString(2, senha);
            ResultSet rs = comando.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("nome_usuario");
                String sobrenome = rs.getString("sobrenome_usuario");
                String username_usuario = rs.getString("username_usuario");
                String tipo = rs.getString("tipo_usuario");

                if (tipo.equals("aluno"))
                    return new Aluno(id, nome, sobrenome, username_usuario);
                else
                    return new Professor(id, nome, sobrenome, username_usuario);
            } else {
                throw new InvalidParameterException("Usuário ou Senha incorreta");
            }

        }
    }
}
