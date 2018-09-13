package minesweeperv2;

// Imports for window
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.List;
import java.util.ArrayList;


/*
 * This project attempts to improve upon my initial attempt of designing 
 * a minesweeper game by refining the code to make it more class based.
 * @author Alex Retief
 */
public class MinesweeperV2 {
    private JFrame frame;
    private Dimension frameSize;
    
    // Integers mark the difference between the frame size and the panel size
    static final int WIDTH_OVERLAP = 15;
    static final int HEIGHT_OVERLAP = 63;
    
    /*
     * Creates the minesweeper game and shows it on screen
     */
    public MinesweeperV2() {
        frame = new JFrame("Minesweeper");
        frame.setPreferredSize(new Dimension(700, 500));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameSize = frame.getSize();
        makeTitleScreen();
        makeMenuBar(); 
        createFrameResizeListener();
        
        // Set window size to account for different panel preferred sizes
        frame.pack();        
    }
    
    /*
     * Resizes the components within the frame when it is resized by the user
     */
    public void createFrameResizeListener() {
        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                int frameWidth = 
                        (int)(frame.getSize().getWidth()-WIDTH_OVERLAP);
                int frameHeight = 
                        (int)(frame.getSize().getHeight()-HEIGHT_OVERLAP);
                frameSize = new Dimension(frameWidth, frameHeight);
                List<Component> compList =
                        getChildComponents((Container) frame);
                for(Component comp : compList) { 
                    if(comp instanceof JPanel) {
                        comp.setSize(frameSize);
                    }
                }
                // Updates changes upon screen straight away
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
    
    /*
     * Lists all the components within the frame including sub components
     * @param c Container in which the child components are to be found from
     */
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
        frame.add(pnl);
        return pnl;
    }
    
    /*
     * Create the main frame's menu bar.
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
