package br.maua;

import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection cx = ConnectionFactory.obterConexao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            System.out.println("Projeto Inicializado com Sucesso!");
            java.awt.EventQueue.invokeLater(() -> {
                new br.maua.presentation.TelaLogin.TelaLogin().setVisible(true);
    });
    }
    
}