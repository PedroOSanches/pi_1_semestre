package com.mycompany.telaloginnovo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class TelaLoginNovo extends JFrame {

    public TelaLoginNovo() {
        setTitle("Login");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel fundo = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage img = ImageIO.read(new File("C:\\Users\\marjo\\OneDrive\\Documentos\\NetBeansProjects\\TelaDeLogin\\src\\main\\java\\com\\mycompany\\teladelogin\\fundo2.png"));
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        fundo.setBounds(0, 0, 950, 750);
        fundo.setLayout(null);

        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 144, 255, 220));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        painel.setBounds(325, 150, 300, 380);
        painel.setOpaque(false);
        painel.setLayout(null);

        JLabel titulo = new JLabel("Login");
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(0, 20, 300, 60);
        painel.add(titulo);

        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsuario.setBounds(50, 100, 200, 20);
        painel.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 122, 200, 30);
        painel.add(txtUsuario);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSenha.setBounds(50, 170, 200, 20);
        painel.add(lblSenha);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBounds(50, 192, 200, 30);
        painel.add(txtSenha);

        JLabel lblPrimeiroAcesso = new JLabel("Primeiro acesso? Clique aqui");
        lblPrimeiroAcesso.setForeground(Color.WHITE);
        lblPrimeiroAcesso.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrimeiroAcesso.setHorizontalAlignment(SwingConstants.CENTER);
        lblPrimeiroAcesso.setBounds(25, 240, 250, 20);
        lblPrimeiroAcesso.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblPrimeiroAcesso.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Primeiro acesso!");
            }
        });
        painel.add(lblPrimeiroAcesso);

        fundo.add(painel);
        add(fundo);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaLoginNovo();
    }
}
