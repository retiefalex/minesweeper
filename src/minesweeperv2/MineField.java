package minesweeperv2;

// Imports for java window
import java.awt.*;
import javax.swing.*;

/**
 * Generates the virtual minefield 
 * @author Alex Retief
 */
public class MineField {
    private int noOfMines;
    private int vertLength;
    private int horizLength;
    
    private JButton[][] btns;
    private String[][] btnInfo;
    private JPanel pnl;
    
    /*
     * Contructs MineField class and provides the default medium difficulty 
     * values for the game
     */
    public MineField() {
        noOfMines = 5;
        vertLength = 5;
        horizLength = 10;
        
        btns = new JButton[vertLength][horizLength];
        btnInfo = new String[vertLength][horizLength];
        pnl = new JPanel();
        
        for(int i = 0; i < vertLength; i++) {   
            for(int j = 0; j < horizLength; j++) {
                btns[i][j] = new JButton("");
                btnInfo[i][j] = "";
            }
        }
    }
    
    /*
     * Create grid of buttons to be placed on the panel
     * @param pnl The panel the grid of buttons is to be added to
     */
    public void createField(Dimension frameSize) {
        /*
         * Generates a grid of user-specified dimensions  with a horizontal and
         * vertical gap of 5 between each element
         */
        GridLayout layout = new GridLayout(vertLength, horizLength, 5, 5);
        pnl.setLayout(layout);
        pnl.setSize(frameSize);
        
        // Add buttons to grid
        for(int i = 0; i < vertLength; i++) {   
            for(int j = 0; j < horizLength; j++) {
                pnl.add(btns[i][j]);
            }
        }
        createMines();
        createNumberedSpaces();    
    }
    
    /*
     * Creates and randomly places a select number of mines on the grid
     * @param btns The two dimensional array of buttons on the grid
     * @param game The class representing the game actions 
     */
    public void createMines() {
        int ranHoriz;
        int ranVert;
        
        for(int i = 0; i < noOfMines; i++) {
            do {
                ranHoriz = (int) Math.floor(Math.random()*vertLength);
                ranVert = (int) Math.floor(Math.random()*horizLength);
            } while(btnInfo[ranHoriz][ranVert].equals("M")); 
            btnInfo[ranHoriz][ranVert] = "M";
            //btns[ranHoriz][ranVert].setText(btnInfo[ranHoriz][ranVert]);
        }
    }
    
    /*
     * Generates numbered spaces around mines
     * @param btns The two dimensional array of buttons on the grid
     * @param game The class representing the game actions 
     */  
    public void createNumberedSpaces() {
        for(int i = 0; i < vertLength; i++) {   
            for(int j = 0; j < horizLength; j++) {
                if(!btnInfo[i][j].equals("M")) {
                    int mineCounter = countMines(i, j);
                    if(mineCounter != 0) {
                        btnInfo[i][j] = Integer.toString(mineCounter);
                        //btns[i][j].setText(btnInfo[i][j]);
                    }
                }
            }
        }
    }
    
    /*
     * Retrieves the number of mines surrounding a chosen point
     * @param btns The two dimensional array of buttons on the grid
     * @param i The x coordinate of the chosen button
     * @param j The y coordinate of the chosen button
     * @return A sum of all the mines surrounding the particular point
     */
    public int countMines(int i, int j) {
        int mineCounter = 0; 
        // Loop through all buttons surrounding chosen
        for(int ii = i-1; ii <= i+1; ii++) {
            for(int jj = j-1; jj <= j+1; jj++) {
                if(!(ii == i && jj == j)) {
                    try {
                        if(btnInfo[ii][jj].equals("M")) {
                            mineCounter++;
                        }
                    } catch(ArrayIndexOutOfBoundsException exception) {
                        // ignore buttons referenced outside the grid border
                    }    
                }
            }
        }
        return mineCounter;
    }
    
    public void disableButtons() {
        for(int i = 0; i < vertLength; i++) {
            for(int j = 0; j < horizLength; j++) {
                btns[i][j].setEnabled(false);
            }
        }
    }
    
    public int getNoOfMines() {
        return noOfMines;
    }
    
    public void setNoOfMines(int noOfMines) {
        this.noOfMines = noOfMines;
    }
    
    public int getVertLength() {
        return vertLength;
    }
    
    public void setVertLength(int horizLength) {
        this.vertLength = horizLength;
    }
    
    public int getHorizLength() {
        return horizLength;
    }
    
    public void setHorizLength(int vertLength) {
        this.horizLength = vertLength;
    }
    
    public JButton getButton(int x, int y) {
        return btns[x][y];
    }
    
    public JButton[][] getButtonGrid() {
        return btns;
    }
    
    public void setButtonText(String text, int x, int y) {
        btns[x][y].setText(text);
    }
    
    public String getButtonInfo(int x, int y) {
        return btnInfo[x][y];
    }
    
    public String[][] getButtonInfoGrid() {
        return btnInfo;
    }
    
    public void setButtonInfo(String text, int x, int y) {
        btnInfo[x][y] = text;
    }
    
    public JPanel getPanel() {
        return pnl;
    }
}
