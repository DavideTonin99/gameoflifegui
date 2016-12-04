package gameoflifegui;

/**
 * Represents a cell with age
 * @author Tonin Davide davide9935@gmail.com
 * @version 1.0 2016-11-08
 */
public class ExtendedCell extends Cell{
    private int age;

    /**
     * Costruttore base
     * @param row the row
     * @param col the column 
     * @param age the age of the cell
     */
    public ExtendedCell(int row, int col, int age) {
        super(row, col);
        this.age = age;
    }
    
    /**
     * Return the age of the cell
     * @return age the age
     */
    public int getAge() {
        return age;
    }
    
    /**
     * Set the age of the cell
     */
    public void incAge() {
        if(age < 9){
            age++;
        }
    }
    
    /**
     * Set the age
     * @param age 
     */
    public void setAge(int age) {
        if (age < 9) {
            this.age = age;
        } 
        else {
            this.age = 9;
        }
    }
    /**
     * Reset the age of the cell
     */
    public void reset(){
        age = 0;
    }
}