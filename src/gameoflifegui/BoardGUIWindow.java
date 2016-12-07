package gameoflifegui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Represents the game window
 * @author Tonin Davide davide9935@gmail.com
 * @version 1.0 2016-11-29
 */
public class BoardGUIWindow extends JFrame implements ActionListener, Runnable{  
    private int rows;
    private int columns;
    private Board board;
    
    private JMenuBar menuBar;
    private JMenu menuOptions;
    private JMenuItem startOption;
    private JMenuItem randomOption;
    private JPanel graphicBoardPanel;
    private Container contentPane;
    private int updateDelay;
    

    /**
     * Base constructor
     * @param rows the number of the rows
     * @param columns the number of the columns
     */
    public BoardGUIWindow(int rows, int columns) {
        super("Game of Life - Davide Tonin");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setLocation(300, 300);
        this.setVisible(true);
        
        this.rows = rows;
        this.columns = columns;
        this.board = new Board(rows, columns);
        //set the menu bar
        this.menuBar = new JMenuBar();
        this.menuOptions = new JMenu("Options");
        this.startOption = new JMenuItem("Start");
        this.randomOption = new JMenuItem("RandomFill");
        //add the items of menu
        this.menuOptions.add(this.startOption);
        this.menuOptions.add(this.randomOption);
        this.menuBar.add(this.menuOptions);
        this.setJMenuBar(menuBar);
        //add menu event listeners
        this.startOption.addActionListener(this);
        this.randomOption.addActionListener(this);
        
        this.graphicBoardPanel = new JPanel();
        this.graphicBoardPanel.setLayout(new GridLayout(rows, columns));
        this.contentPane = getContentPane();
        
        paintBoard();
    }
    
    /**
     * Paint the board
     */
    public void paintBoard() {
        this.contentPane.removeAll();
        this.graphicBoardPanel.removeAll();
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {                
                ExtendedGUICell cell = new ExtendedGUICell(this.board.getMatrix()[row][col], this);
                this.graphicBoardPanel.add(cell);
            }
        }
        
        this.contentPane.add(this.graphicBoardPanel); 
        this.revalidate();
    }
    /**
     * Initialize the board
     * @param numberOfAliveCells the number of initial alive cells
     */
    public void init(int numberOfAliveCells) {
        this.board.init(numberOfAliveCells);
        this.paintBoard();
//        
//        try {
//                Thread.sleep(2000);
//            } catch(InterruptedException ex) {
//                Thread.currentThread().interrupt();
//                }
    }
    
    /**
     * Execute the transition
     * @param updateDelay 
     */
    public void transition(int updateDelay) {
        this.updateDelay = updateDelay;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("run");
            if(updateDelay > 0) {
                this.board.update(updateDelay);
                this.paintBoard();
            }
        }
    }    
    
    /**
     * Return the board
     * @return board
     */
    @Override
    public String toString() {
        return this.board.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //check if the source is a menu's item or a cell(button)
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem source = (JMenuItem)e.getSource();
            //check which item was pressed
            if (source.getText() == this.startOption.getText()) {                  
                this.transition(200);                  
            }
            else if (source.getText() == this.randomOption.getText()){
                try {
                    //ask the number of alive cells to the user
                    int nAliveCells = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert number of alive cells:"));
                    //check if the value is minor of the maximum possible
                    if (nAliveCells < this.rows*this.columns) {
                        this.init(nAliveCells);
                    }
                    else {
                        System.out.println("Valore inserito errato");
                    }
                } catch(Exception ex) {
                    System.out.println("Valore inserito errato");
                } 
            }
        }
        else {
            ExtendedGUICell source = (ExtendedGUICell)e.getSource();
            ExtendedCell cell = source.getCell();
            if (cell.getAge() == 0) {
                cell.setAlive(true);
                cell.incAge();
            } else {
                cell.setAge(0);
                cell.setAlive(false);
            }
            this.board.setElement(cell);
        }
    }

}
