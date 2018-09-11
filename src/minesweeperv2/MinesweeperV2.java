package minesweeperv2;

import java.awt.*;
import java.awt.event.*;
import javafx.geometry.*;
import javafx.stage.*;
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
        frame = new JFrame("Minesweeper");
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        JPanel titlePnl = makeTitleScreen();
        frame.add(titlePnl);
        makeMenuBar(); 
        
        // Set window size to account for different panel preferred sizes
        frame.pack();
    }

    /*
     * Creates the title screen before you select to play the game
     */
    public JPanel makeTitleScreen() {
        JPanel pnl = new JPanel();
        JLabel title = new JLabel("Minesweeper");
        pnl.add(title);
        
        return pnl;
    }
    
    /*
     * Create the main frame's menu bar.
     * @param frame The frame the menu bar should be added to.
     */
    public void makeMenuBar() {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        
        JMenuItem newItem = new JMenuItem("New");
        fileMenu.add(newItem);
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Game game = new Game();
                game.newGame(frame);
            }
        });
        
        JMenuItem quitItem = new JMenuItem("Quit");
        fileMenu.add(quitItem);
    }
    
    public static void main(String[] args) {
        MinesweeperV2 minesweeper = new MinesweeperV2();
    }
    
}
