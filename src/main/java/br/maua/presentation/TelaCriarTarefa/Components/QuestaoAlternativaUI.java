package br.maua.presentation.TelaCriarTarefa.Components;

import br.maua.domain.Alternativa;
import br.maua.domain.QuestaoAlternativa;
import br.maua.domain.Tarefa;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class QuestaoAlternativaUI extends QuestaoUI {
    public JTextArea enunciado;
    public List<AlternativaUI> alternativas = new ArrayList<>();

    @Override
    public void salvar(Tarefa tarefa){
        QuestaoAlternativa qa = new QuestaoAlternativa(enunciado.getText(), tarefa);
        for(AlternativaUI alt : alternativas){
            qa.adicionarAlternativa( new Alternativa(
                    qa,
                    alt.campo.getText(),
                    alt.radio.isSelected()
            ));
        }
        tarefa.addQuestao(qa);
    }
    public void addAlternativa(AlternativaUI alternativa){
        alternativas.add(alternativa);
    }
}
