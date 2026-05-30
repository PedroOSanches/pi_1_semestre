package br.maua.presentation.TelaCriarTarefa.Components;

import br.maua.domain.Questao;
import br.maua.domain.QuestaoUpload;
import br.maua.domain.Tarefa;

import javax.swing.*;
import java.io.File;
public class QuestaoUploadUI extends QuestaoUI {
    public JTextArea enunciado;
    public JTextField titulo;
    public File arquivo ;
    @Override
    public void salvar(Tarefa tarefa) {
        QuestaoUpload qu =  new QuestaoUpload(
                enunciado.getText(),
                titulo.getText(),
                arquivo,
                tarefa
        );
        tarefa.addQuestao(qu);
    }
}
