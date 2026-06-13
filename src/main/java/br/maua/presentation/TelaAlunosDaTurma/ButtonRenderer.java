package br.maua.presentation.TelaAlunosDaTurma;

import javax.swing.*;
import java.awt.*;

public class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {

    public ButtonRenderer() {
        setText("Acessar");
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        setText(value == null ? "Acessar" : value.toString());
        return this;
    }
}
