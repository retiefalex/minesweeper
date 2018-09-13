package minesweeperv2;

// Imports for java window
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Contains all the game play logic related to the minesweeper game
 * @author Alex Retief
 */
public class Game {
    private int correctFlags;
    private MineField mf;
    private JLayeredPane pane;
    private JPanel winPnl;
    private JPanel losePnl;
    
    public Game() {
        correctFlags = 0;
        mf = new MineField();
        pane = new JLayeredPane();
        winPnl = new JPanel();
        losePnl = new JPanel();
    }
    
    /*
     * Starts a new game by creating a minefield and setting up action events
     * @param frame The frame that the minefield will be added to
     */
    public void newGame(JFrame frame) {   
        
        // Remove title screen to make way for layered pane
        frame.getContentPane().removeAll();
        frame.add(pane, BorderLayout.CENTER);
        mf.createField(frame);
        frame.revalidate();

        createActionEvents();
        createFlagEvents();
        createFinishPnls(frame);
        pane.add(winPnl, 0, 0);   
        pane.add(losePnl, 1, 0);   
        pane.add(mf.getPanel(), 2, 0);
    }
    
    /*
     * Creates the final message after you either win or lose the game
     * @param msg String referring to whether you lost or won the game
     */
    public void createFinishPnls(JFrame frame) {
        JLabel winLbl = new JLabel("YOU WIN!");
        JLabel loseLbl = new JLabel("YOU LOSE!");
        winLbl.setFont(new Font(null, Font.PLAIN, 40));
        loseLbl.setFont(new Font(null, Font.PLAIN, 40));
        int width = (int)frame.getSize().getWidth()
                -MinesweeperV2.WIDTH_OVERLAP;
        int height = (int)frame.getSize().getHeight()
                -MinesweeperV2.HEIGHT_OVERLAP;
        
        // Gridbag layout centres the label
        winPnl.setLayout(new GridBagLayout());
        winPnl.setSize(width, height);
        winPnl.setOpaque(false);
        winPnl.add(winLbl);
        losePnl.setLayout(new GridBagLayout());
        losePnl.setSize(width, height);
        losePnl.setOpaque(false);
        losePnl.add(loseLbl);
        
        frame.revalidate();
    }
    
//    /*
//     * Creates the final message after you either win or lose the game
//     * @param msg String referring to whether you lost or won the game
//     */
//    public void createFinishMsg(String msg) {
//        JPanel finishPnl = new JPanel();
//        JLabel finishLbl = new JLabel(msg);
//        int width = (int)frame.getSize().getWidth();
//        int height = (int)frame.getSize().getHeight();
//        finishLbl.setFont(new Font(null, Font.PLAIN, 40));
//        
//        // Gridbag layout centres the label
//        finishPnl.setLayout(new GridBagLayout());
//        finishPnl.setSize(width, height);
//        finishPnl.setOpaque(false);
//        finishPnl.add(finishLbl);
//        pane.add(finishPnl, 1, 0);
//        
//        frame.revalidate();
//    }
    
    /*
     * Conducts the lose sequence once a mine has been pushed by the player
     */
    public void lose() {
        System.out.println("YOU LOSE!");
        mf.disableButtons();
        pane.setLayer(losePnl, 3);
    }
    
    /*
     * Checks whether enough all correct flags have been placed then conducts 
     * win sequence
     */
    public void checkWin() {
        if(correctFlags == mf.getNoOfMines()) {
            System.out.println("YOU WIN!");
            mf.disableButtons();
            pane.setLayer(winPnl, 3);
        }
    }
    
    /*
     * Generates action events for all buttons depending on the text displayed
     * upon them
     */
    public void createActionEvents() {
        for(int i = 0; i < mf.getVertLength(); i++) {
            for(int j = 0; j < mf.getHorizLength(); j++) {
                final int ii = i;
                final int jj = j;
                final String btnText = mf.getButtonInfo(ii, jj);
                
                if(btnText.equals("M")) {
                    mf.getButton(ii, jj).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("M");
                            JButton btn = (JButton) e.getSource();
                            btn.setText(btnText);
                            btn.setEnabled(false);
                            lose();
                        }
                    });
                } else if(btnText.equals("")){
                    mf.getButton(ii, jj).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("0");
                            expandSafeZone(ii, jj);
                        }
                    });
                } else {
                    mf.getButton(ii, jj).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("123");
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
     */
    public void createFlagEvents() {
        for(int i = 0; i < mf.getVertLength(); i++) {
            for(int j = 0; j < mf.getHorizLength(); j++) {
                final String btnText = mf.getButtonInfo(i, j);
                
                mf.getButton(i, j).addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            JButton btn = (JButton) e.getSource();
                            if(!btn.getText().equals("F")) {
                                btn.setText("F");
                                if(btnText.equals("M")) {
                                    correctFlags++;
                                    checkWin();
                                }
                            } else {
                                btn.setText(btnText);
                                if(btnText.equals("M")) {
                                    correctFlags--;
                                }
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
     * @param i The x coordinate of the chosen button
     * @param j The y coordinate of the chosen button
     */
    public void expandSafeZone(int i, int j) {
        mf.getButton(i, j).setEnabled(false);
        for(int ii = i-1; ii <= i+1; ii++) {
            for(int jj = j-1; jj <= j+1; jj++) {
                if(!(ii == i && jj == j)) {
                    try {
                        if(mf.getButtonInfo(ii, jj).equals("") &&
                                mf.getButton(ii, jj).isEnabled()) { 
                            mf.getButton(ii, jj).setEnabled(false);
                            expandSafeZone(ii, jj);
                        } else if(mf.getButtonInfo(ii, jj).equals("M")) {
                            // Keep mine hidden by doing nothing
                        } else {
                            mf.setButtonText(mf.getButtonInfo(ii, jj), ii, jj);
                            mf.getButton(ii, jj).setEnabled(false);
                        }
                    } catch(ArrayIndexOutOfBoundsException exception) {
                        // ignore buttons referenced outside the grid border
                    }    
                }
            }
        }
    }
}
