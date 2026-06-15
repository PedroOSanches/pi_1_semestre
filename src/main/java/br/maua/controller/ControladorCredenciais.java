package br.maua.controller;

import java.security.InvalidParameterException;
import java.sql.SQLException;

import br.maua.domain.Usuario;
import br.maua.infrastructure.DAO.UsuarioDAO;

public class ControladorCredenciais {
    public static Usuario login(String email, String senha) throws SQLException {
        if (email.isEmpty() || senha.isEmpty()) {
            throw new InvalidParameterException("Complete os campos solicitados!");
        }
        Usuario user = UsuarioDAO.autenticar(email, senha);
        user.preencheAtributos();
        return user;
    }
}
