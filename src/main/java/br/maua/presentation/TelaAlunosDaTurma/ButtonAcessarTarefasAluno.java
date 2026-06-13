package br.maua.presentation.TelaAlunosDaTurma;

import br.maua.domain.Aluno;
import br.maua.presentation.TelaTarefasAluno.TelaTarefaAluno;

import javax.swing.*;
import java.awt.*;

public class ButtonAcessarTarefasAluno extends DefaultCellEditor {
    private final JButton button;
    private int row;
    private JFrame telaBotao;

    public ButtonAcessarTarefasAluno(JCheckBox checkBox, JTable tabela, JFrame telaBotao) {
        super(checkBox);
        this.telaBotao = telaBotao;
        button = new JButton();
        button.addActionListener(e -> {
            fireEditingStopped();

            System.out.println("Clicou linha " + row);

            Aluno aluno = (Aluno) tabela.getValueAt(row, 0);
            java.awt.EventQueue.invokeLater(() -> {
                        
                        new TelaTarefaAluno(telaBotao, aluno.getId(), null).setVisible(true);
                        if (telaBotao != null) {
                            telaBotao.setVisible(false);
                        }
                    }
            );
        });
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value,
            boolean isSelected, int row, int column) {

        this.row = row;

        button.setText(value == null ? "Acessar" : value.toString());

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Acessar";
    }
}