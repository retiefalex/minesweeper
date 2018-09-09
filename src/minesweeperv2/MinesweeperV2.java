package minesweeperv2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * This project attempts to improve upon my initial attempt of designing 
 * a minesweeper game by refining the code to make it more class based.
 * @author Alex Retief
 */
public class MinesweeperV2 {
    private JFrame frame;
    
    /*
     * Creates the minesweeper game and shows it on screen
     */
    public MinesweeperV2() {
        makeFrame();
        makeMenuBar(frame);      
        
        // Create instance of minefield here 
        Game game = new Game();
        game.newGame(frame);
    }

    /*
     * Create the main frame's menu bar.
     * @param frame The frame the menu bar should be added to.
     */
    public void makeMenuBar(JFrame frame) {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        
        JMenuItem newItem = new JMenuItem("New");
        fileMenu.add(newItem);
        
        JMenuItem quitItem = new JMenuItem("Quit");
        fileMenu.add(quitItem);
    }
    
    /*
     * Create the main frame
     */
    public void makeFrame() {
        frame = new JFrame("Minesweeper");
        
        // Sets window to already be in maximised mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        MinesweeperV2 minesweeper = new MinesweeperV2();
        
    }
    
}
