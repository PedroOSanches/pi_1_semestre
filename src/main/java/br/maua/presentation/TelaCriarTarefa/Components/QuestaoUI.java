package br.maua.presentation.TelaCriarTarefa.Components;

import br.maua.domain.Tarefa;

import javax.swing.*;

public abstract class QuestaoUI {
    public JTextArea enunciado;

    public abstract void salvar(Tarefa tarefa);

}
