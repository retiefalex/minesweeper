package minesweeperv2;

// Imports for java window
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Contains all the game play logic related to the minesweeper game
 * @author Alex Retief
 */
public class Game extends MineField {
    private int correctFlags;
    
    public Game() {
        super();
        correctFlags = 0;
    }
    
    /*
     * Conducts the lose sequence once a mine has been pushed by the player
     * @param btns The two dimensional array of buttons on the grid
     * @param btnInfo The two dimensional array of info related to each button
     * @param horizLength The maximum length of the grid row
     * @param vertLength The maximum length of the grid column
     */
    public void lose() {
        System.out.println("YOU LOSE");
        for(int i = 0; i < getHorizLength(); i++) {
            for(int j = 0; j < getVertLength(); j++) {
                getButton(i, j).setEnabled(false);
            }
        }
    }
    
    /*
     * Conducts the win sequence once flags have been put on all mines
     */
    public void win() {
        
    }
    
    public void checkWin() {
        if(getCorrectFlags() == getNoOfMines()) {
            win();
        }
    }
    
    /*
     * Generates action events for all buttons depending on the text displayed
     * upon them
     * @param btns The two dimensional array of buttons on the grid
     * @param btnInfo The two dimensional array of info related to each button
     * @param horizLength The maximum length of the grid row
     * @param vertLength The maximum length of the grid column
     */
    public void createActionEvents(JButton[][] btns) {
        for(int i = 0; i < getHorizLength(); i++) {
            for(int j = 0; j < getVertLength(); j++) {
                final int ii = i;
                final int jj = j;
                final String btnText = getButtonInfo(ii, jj);
                System.out.println(btnText);
                
                if(btnText.equals("M")) {
                    btns[ii][jj].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("M");
                            JButton btn = (JButton) e.getSource();
                            btn.setText(btnText);
                            btn.setEnabled(false);
                            lose();
                        }
                    });
                } else if(btnText.equals("")){
                    btns[ii][jj].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("0");
                            expandSafeZone(ii, jj);
                        }
                    });
                } else {
                    btns[ii][jj].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("123");
                            JButton btn = (JButton) e.getSource();
                            btn.setText(btnText);
                            btn.setEnabled(false);
                        }
                    });
                }
            }   
        }    
    }
    
    /*
     * Grants each button the ability to have flags through right clicking
     * @param btns The two dimensional array of buttons on the grid
     * @param btnInfo The two dimensional array of info related to each button
     * @param horizLength The maximum length of the grid row
     * @param vertLength The maximum length of the grid column
     */
    public void createFlagEvents(JButton[][] btns) {
        for(int i = 0; i < getHorizLength(); i++) {
            for(int j = 0; j < getVertLength(); j++) {
                final String btnText = getButtonInfo(i, j);
                btns[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            JButton btn = (JButton) e.getSource();
                            if(!btn.getText().equals("F")) {
                                btn.setText("F");
                            } else {
                                btn.setText(btnText);
                            }
                        }
                    }
                });
            }
        }
    }
    
    /*
     * Performed if a button is selected which no mines in its vicinity, it
     * expands the safe area by locating other such buttons in the grid
     * @param btns The two dimensional array of buttons on the grid
     * @param btnInfo The two dimensional array of info related to each button
     * @param i The x coordinate of the chosen button
     * @param j The y coordinate of the chosen button
     */
    public void expandSafeZone(int i, int j) {
        getButton(i, j).setEnabled(false);
        for(int ii = i-1; ii <= i+1; ii++) {
            for(int jj = j-1; jj <= j+1; jj++) {
                if(!(ii == i && jj == j)) {
                    try {
                        if(getButtonInfo(ii, jj).equals("") &&
                                getButton(ii, jj).isEnabled()) { 
                            getButton(ii, jj).setEnabled(false);
                            expandSafeZone(ii, jj);
                        } else if(getButtonInfo(ii, jj).equals("M")) {
                            // Keep mine hidden by doing nothing
                        } else {
                            setButtonText(getButtonInfo(ii, jj), ii, jj);
                            getButton(ii, jj).setEnabled(false);
                        }
                    } catch(ArrayIndexOutOfBoundsException exception) {
                        // ignore buttons referenced outside the grid border
                    }    
                }
            }
        }
    }
    
    public int getCorrectFlags() {
        return correctFlags;
    }
    
    public void addCorrectFlag() {
        correctFlags++;
    }
}
