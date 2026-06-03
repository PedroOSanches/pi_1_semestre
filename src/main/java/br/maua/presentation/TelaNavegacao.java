package br.maua.presentation;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public final class TelaNavegacao {

    private static final Map<JFrame, JFrame> TELA_ANTERIOR = new WeakHashMap<>();

    private TelaNavegacao() {
    }

    public static void abrir(JFrame telaAtual, JFrame novaTela) {
        if (telaAtual == null || novaTela == null) {
            return;
        }

        TELA_ANTERIOR.put(novaTela, telaAtual);
        novaTela.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        novaTela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                voltar(novaTela);
            }
        });

        novaTela.setLocationRelativeTo(telaAtual);
        telaAtual.setVisible(false);
        novaTela.setVisible(true);
    }

    public static void voltar(JFrame telaAtual) {
        if (telaAtual == null) {
            return;
        }

        JFrame telaAnterior = TELA_ANTERIOR.remove(telaAtual);
        if (telaAnterior != null) {
            telaAnterior.setVisible(true);
        }

        telaAtual.dispose();
    }
}
