package br.maua;

import java.sql.Connection;
import java.sql.SQLException;

import br.maua.infrastructure.ConnectionFactory;
import br.maua.presentation.TelaLogin.TelaLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            Connection cx = ConnectionFactory.obterConexao();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Projeto Inicializado com Sucesso!");

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}