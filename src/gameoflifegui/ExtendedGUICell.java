package gameoflifegui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * @author Tonin Davide davide9935@gmail.com
 * @version 2.0 2016-12-03
 */
public class ExtendedGUICell extends JButton implements ActionListener{
    private static Color DEAD_CELL_COLOR = Color.BLACK;
    private static Color ALIVE_CELL_COLOR = new Color(102, 0, 0);
    private static Color CELL_BORDER_COLOR = Color.GRAY.darker();
    private static Color[] colors = getColors();
    
    private ExtendedCell cell;

    public ExtendedGUICell(ExtendedCell cell, BoardGUIWindow window) {
        this.cell = cell;
        this.addActionListener(this);
        this.addActionListener(window);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(CELL_BORDER_COLOR, 1));
        this.setCellColor();
    }    
    
    /**
     * Return the array of colors of a cell
     * @return colors the colors of a cell
     */
    public static Color[] getColors() {
        Color[] colors = new Color[10];
        colors[0] = DEAD_CELL_COLOR;
        for (int i=1; i<10; i++) {
            colors[i] = ALIVE_CELL_COLOR;
            ALIVE_CELL_COLOR = new Color(ALIVE_CELL_COLOR.getRed() + 17,
                    ALIVE_CELL_COLOR.getGreen(), ALIVE_CELL_COLOR.getBlue());
        }
        return colors;
    }
    
    /**
     * Set the color of the cell
     */
    public void setCellColor() {
        this.setBackground(colors[this.cell.getAge()]);
    }
    
    /**
     * Return the cell
     * @return cell
     */
    public ExtendedCell getCell() {
        return this.cell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setCellColor();
    }
}
