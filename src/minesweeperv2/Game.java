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
        int frameWidth = (int)frame.getSize().getWidth()
                -MinesweeperV2.WIDTH_OVERLAP;
        int frameHeight = (int)frame.getSize().getHeight()
                -MinesweeperV2.HEIGHT_OVERLAP;
        Dimension frameSize = new Dimension(frameWidth, frameHeight);
        
        // Remove title screen to make way for layered pane
        frame.getContentPane().removeAll();
        frame.add(pane, BorderLayout.CENTER);
        mf.createField(frameSize);
        createActionEvents();
        createFlagEvents();
        createFinishPnls(frameSize);
        pane.add(winPnl, 0, 0);   
        pane.add(losePnl, 1, 0);   
        pane.add(mf.getPanel(), 2, 0);
        frame.revalidate();
    }
    
    /*
     * Creates two panels, one displaying a winning message and the other a
     * losing message
     * @param frameSize The dimensions of the frame
     */
    public void createFinishPnls(Dimension frameSize) {
        JLabel winLbl = new JLabel("YOU WIN!");
        JLabel loseLbl = new JLabel("YOU LOSE!");
        winLbl.setFont(new Font(null, Font.PLAIN, 40));
        loseLbl.setFont(new Font(null, Font.PLAIN, 40));
        
        // Gridbag layout centres the label
        winPnl.setLayout(new GridBagLayout());
        winPnl.setSize(frameSize);
        winPnl.setOpaque(false);
        winPnl.add(winLbl);
        losePnl.setLayout(new GridBagLayout());
        losePnl.setSize(frameSize);
        losePnl.setOpaque(false);
        losePnl.add(loseLbl);
    }

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
        for(int x = 0; x < mf.getVertLength(); x++) {
            for(int y = 0; y < mf.getHorizLength(); y++) {
                final int i = x;
                final int j = y;
                final String btnText = mf.getButtonInfo(i, j);
                
                if(btnText.equals("M")) {
                    mf.getButton(i, j).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("M");
                            JButton btn = (JButton) e.getSource();
                            btn.setText(btnText);
                            btn.setEnabled(false);
                            lose();
                        }
                    });
                } else if(btnText.equals("")){
                    mf.getButton(i, j).addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            //System.out.println("0");
                            expandSafeZone(i, j);
                        }
                    });
                } else {
                    mf.getButton(i, j).addActionListener(new ActionListener() {
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
        for(int x = 0; x < mf.getVertLength(); x++) {
            for(int y = 0; y < mf.getHorizLength(); y++) {
                final String btnText = mf.getButtonInfo(x, y);
                
                mf.getButton(x, y).addMouseListener(new MouseAdapter() {
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
     * @param x The x coordinate of the chosen button
     * @param y The y coordinate of the chosen button
     */
    public void expandSafeZone(int x, int y) {
        mf.getButton(x, y).setEnabled(false);
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(!(i == x && j == y)) {
                    try {
                        if(mf.getButtonInfo(i, j).equals("") &&
                                mf.getButton(i, j).isEnabled()) { 
                            mf.getButton(i, j).setEnabled(false);
                            expandSafeZone(i, j);
                        } else if(mf.getButtonInfo(i, j).equals("M")) {
                            // Keep mine hidden by doing nothing
                        } else {
                            mf.setButtonText(mf.getButtonInfo(i, j), i, j);
                            mf.getButton(i, j).setEnabled(false);
                        }
                    } catch(ArrayIndexOutOfBoundsException exception) {
                        // ignore buttons referenced outside the grid border
                    }    
                }
            }
        }
    }
}
