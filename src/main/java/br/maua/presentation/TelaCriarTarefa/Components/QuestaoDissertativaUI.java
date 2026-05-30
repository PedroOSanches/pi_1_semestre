package br.maua.presentation.TelaCriarTarefa.Components;

import br.maua.domain.QuestaoDissertativa;
import br.maua.domain.Tarefa;

import javax.swing.*;

public class QuestaoDissertativaUI extends QuestaoUI {
    public JTextArea enunciado;
    public JTextArea resposta;

    @Override
    public void salvar(Tarefa tarefa) {
        QuestaoDissertativa qd = new QuestaoDissertativa(
                enunciado.getText(),
                resposta.getText(),
                tarefa
        );
        tarefa.addQuestao(qd);
    }
}
