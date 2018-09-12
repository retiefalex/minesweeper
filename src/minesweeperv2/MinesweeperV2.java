package minesweeperv2;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
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
    private Dimension frameSize;
    
    /*
     * Creates the minesweeper game and shows it on screen
     */
    public MinesweeperV2() {
        frame = new JFrame("Minesweeper");
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frameSize = frame.getSize();
        JPanel titlePnl = makeTitleScreen();
        frame.add(titlePnl);
        makeMenuBar(); 
        frameResize();
        
        // Set window size to account for different panel preferred sizes
        frame.pack();        
    }
    
    public void frameResize() {
        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                frameSize = new Dimension((int)(frame.getSize().getWidth()-10), 
                        (int)(frame.getSize().getHeight()-20));
                List<Component> compList =
                        getChildComponents((Container) frame);
                for(Component comp : compList) { 
                    System.out.println(comp.getName());
                    if(comp instanceof JPanel) {
                        comp.setSize(frameSize);
                    }
                }
                frame.revalidate();
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }
    
    public List<Component> getChildComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<>();
        for(Component comp : comps) {
            compList.add(comp);
            if(comp instanceof Container) {
                compList.addAll(getChildComponents((Container) comp));
            }
        }
        return compList;
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
                game.newGame(frame, frameSize);
            }
        });
        
        JMenuItem quitItem = new JMenuItem("Quit");
        fileMenu.add(quitItem);
    }
    
    public static void main(String[] args) {
        MinesweeperV2 minesweeper = new MinesweeperV2();
    }
    
}
