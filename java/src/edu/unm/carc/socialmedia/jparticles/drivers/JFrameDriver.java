package edu.unm.carc.socialmedia.jparticles.drivers;

import edu.unm.carc.socialmedia.jparticles.gui.JParticlePanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Sixstring982
 * @since 5/13/2015
 */
public class JFrameDriver {
    public static void main(String[] args) {
        new JFrameDriver().show();
    }

    private JFrame frame = new JFrame();
    private JParticlePanel panel = new JParticlePanel();

    public JFrameDriver() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("JParticle Engine");
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.addMouseListener(mouseAdapter);
    }

    public void show() {
        frame.setVisible(true);
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            panel.mouseReleased(e);
        }
    };
}
