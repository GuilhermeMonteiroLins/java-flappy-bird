package br.com.flappybird.view;

import javax.swing.JFrame;

/**
 *
 * @author Guilherme Monteiro
 */
public class FrmFlappyBird {
    
    public static void main (String[] args){
        int width = 360;
        int height = 640;
        
        JFrame frame = new JFrame();
        
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        PnlFlappyBird pnlFlappy = new PnlFlappyBird();
        frame.add(pnlFlappy);
        frame.pack();
        pnlFlappy.requestFocus();
        frame.setVisible(true);
    }
}
