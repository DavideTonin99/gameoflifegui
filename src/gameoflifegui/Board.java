package gameoflifegui;

/**
 * Represents the game board
 * @author Tonin Davide davide9935@gmail.com
 * @version 1.0 2016-10-21
 */
import java.util.Random;

public class Board {
    private ExtendedCell matrix[][]; 
    private int rows;
    private int columns;
    /**
     * Costruttore base
     * @param rows the rows of the board
     * @param columns the columns of the board
     */
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new ExtendedCell[rows][columns];
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                matrix[row][col] = new ExtendedCell(row, col, 0);
            }
        }
    }
    
    /**
     * Return the matrix
     * @return matrix
     */
    public ExtendedCell[][] getMatrix() {
        return matrix;
    }
    
    /**
     * Set a new cell
     * @param cell the new cell
     */
    public void setElement(ExtendedCell cell) {
        this.matrix[cell.getRow()][cell.getCol()] = cell;
    }
    
    /**
     * Inizializza la board
     * @param numberOfAliveCells the number of alive cells in the board 
     */
    public void init(int numberOfAliveCells){
        Random random = new Random();
           
        for(int i = 0; i < numberOfAliveCells;) { 
            int xRandom = random.nextInt(columns);
            int yRandom = random.nextInt(rows);
            if (matrix[yRandom][xRandom].isAlive() == false){
                matrix[yRandom][xRandom].setAlive(true);
                matrix[yRandom][xRandom].incAge();
                i++;
            }
        }
    }
    
    /**
     * Reset the board, all cells dead
     */
    public void reset(){
        for(int row = 0; row < rows;row++) {
            for(int col = 0; col < columns; col++) {
                matrix[row][col].setAlive(false);
            }
        }
    }
    /**
     * Count neighbors of a cell
     * @param row the row of the cell
     * @param col the column of the cell
     * @return count the number of alive neighbors of the cell
     */
    public int countNeighbors(int row, int col){
        int count = 0;
        int[][] neighborsCoords = {{row-1, col-1}, {row-1, col}, {row-1, col+1},
                                   {row, col-1}, {row, col+1}, 
                                   {row+1, col-1}, {row+1, col}, {row+1, col+1}};
        
        for(int i=0; i < neighborsCoords.length; i++){
            try{
                if(matrix[neighborsCoords[i][0]][neighborsCoords[i][1]].isAlive()){
                    count ++;
                    }
            }catch(Exception e) {
                }
        }
        return count;
    }
            
    /**
     * Next generation matrix of a given matrix
     * result of the application of the 4 rules for the Conway's Game of Life
     * @return nextMatrix the next matrix
     */
    public ExtendedCell[][] transition(){
        int neighbors;
        ExtendedCell[][] nextMatrix;
        nextMatrix = new ExtendedCell[rows][columns];
        
        // creates new matrix equal to the first
        for(int row=0; row<rows;row++){
            for(int col=0;col<columns;col++){
                nextMatrix[row][col] = new ExtendedCell(row, col, matrix[row][col].getAge());
                nextMatrix[row][col].setAlive(matrix[row][col].isAlive()); 
            }
        }
       
        for(int row=0; row<rows;row++){
            for(int col=0; col<columns;col++){
                neighbors = countNeighbors(row, col);
                if(neighbors < 2 || neighbors > 3){
                    nextMatrix[row][col].setAlive(false);
                }
                else if(neighbors == 3){
                    nextMatrix[row][col].setAlive(true);
                }                
            }
        }
        
        if(CellsAge(matrix, nextMatrix)) {
            try {
                Thread.sleep(2000);
                System.exit(0);
            } 
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                }
        }
        return nextMatrix;   
    }
    
    /**
     * Update the board with transition
     * @param updateDelay time to wait for the next step of the game
     */
    public void update(int updateDelay){
        matrix = transition();
        
        try {
                Thread.sleep(updateDelay);
            } 
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                }
        
    }
    /**
     * Compares two matrixes
     * If they are the same, return True
     * @param matrix the current matrix
     * @param nextMatrix the next step matrix
     * @return end end of the game
     */
    public boolean CellsAge(ExtendedCell[][] matrix, ExtendedCell[][] nextMatrix){
        //check if the old matrix and the new matrix are equals
        boolean equals = true;
        for(int row=0; row<rows;){
            for(int col=0; col<columns;){
                //set the age of cells
                if(nextMatrix[row][col].isAlive()){
                    nextMatrix[row][col].incAge();
                }
                else{
                    if(matrix[row][col].isAlive()){
                        nextMatrix[row][col].reset();
                    }
                }
                if(nextMatrix[row][col].isAlive() != matrix[row][col].isAlive()){
                    equals = false;
                    //row = rows;
                    //col = columns;
                }
                col++;
            }
            row++;
        }
        return equals;
    }
    /**
     * Return the board
     * @return b
     */
    public String toString() {
        String b = "";
        
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                b += (matrix[row][col].isAlive()) ? matrix[row][col].getAge() : "°";
                b += " ";
            } 
            b += "\n";
        }
        return b;
    }
}
