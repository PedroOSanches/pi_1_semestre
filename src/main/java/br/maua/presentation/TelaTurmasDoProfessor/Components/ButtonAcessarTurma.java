package br.maua.presentation.TelaTurmasDoProfessor.Components;

import br.maua.domain.Turma;
import br.maua.presentation.TelaAlunosDaTurma.TelaAlunosDaTurma;

import javax.swing.*;
import java.awt.*;

public class ButtonAcessarTurma extends DefaultCellEditor {
    private final JButton button;
    private int row;

    public ButtonAcessarTurma(JCheckBox checkBox, JTable table, JFrame telaAtual) {
        super(checkBox);
        button = new JButton();
        button.addActionListener(e -> {
            fireEditingStopped();

            Turma turma = (Turma) table.getValueAt(row, 0);
            java.awt.EventQueue.invokeLater(() -> {

                        new TelaAlunosDaTurma(turma, telaAtual).setVisible(true);
                        if (telaAtual != null) {
                            telaAtual.setVisible(false);
                        }
                    }
            );
        });
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value,
            boolean isSelected, int row, int column
    ) {
        this.row = row;

        button.setText(value == null ? "" : value.toString());
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Acessar";
    }
}
