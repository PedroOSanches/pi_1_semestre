package br.maua;

import br.maua.config.AppConfig;
import br.maua.presentation.TelaLogin.TelaLogin;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import java.util.Locale;


public class Main {
    public static void main(String[] args) {
        AppConfig.inicializarPastas();
        Locale.setDefault(new Locale("pt", "BR"));

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}