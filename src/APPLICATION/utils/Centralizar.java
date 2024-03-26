package APPLICATION.utils;

import javax.swing.table.DefaultTableCellRenderer;

public class Centralizar extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
    public Centralizar() {
        setHorizontalAlignment(CENTER); // ou JLabel.CENTER
    }
} 