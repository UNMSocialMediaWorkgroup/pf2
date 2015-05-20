package edu.unm.carc.socialmedia.jparticles.gui;

import edu.unm.carc.socialmedia.jparticles.physics.Bubble;
import edu.unm.carc.socialmedia.jparticles.physics.Nature;
import edu.unm.carc.socialmedia.jparticles.physics.Particle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sixstring982
 * @since 5/13/2015
 */
public class JParticlePanel extends JPanel {
    private static final int MAX_PARTICLES = 7500;
    private static final int BUBBLES_PER_CLICK = 30;
    private static final int PARTICLES_PER_FRAME = 25;
    private static final float FADE_RATE = 0.05f;
    private List<Particle> particles = new ArrayList<Particle>();
    private long lastTick = System.currentTimeMillis();

    private List<Bubble> bubbles = new ArrayList<Bubble>();

    private static final Color repaintAlpha =
            new Color(0f, 0f, 0f, FADE_RATE);

    private final BufferedImage field =
            new BufferedImage(Screen.WIDTH, Screen.HEIGHT,
                              BufferedImage.TYPE_4BYTE_ABGR);

    public JParticlePanel() {
        setPreferredSize(Screen.SIZE);
    }

    private int frameNum = 0;

    @Override
    protected void paintComponent(Graphics panelG) {
        super.paintComponent(panelG);

        Graphics2D g = (Graphics2D)field.getGraphics();

        g.setColor(repaintAlpha);
        g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);

        update();

        render(g);

        panelG.drawImage(field, 0, 0, null);

        try {
            ImageIO.write(field, "png",
                    new File("C:\\users\\six\\desktop\\jp\\frame" +
                            (frameNum++) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        clampFramerate();

        SwingUtilities.invokeLater(queueFrame());
    }

    private void clampFramerate() {
        int sleepMS = (int)((1000.0 / Screen.FPS) -
                            (System.currentTimeMillis() - lastTick));
        if (sleepMS > 0) {
            try {
                Thread.sleep(sleepMS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastTick = System.currentTimeMillis();
    }

    private void update() {

        for (Particle p : particles) {
            p.update();
        }

        for (Bubble b : bubbles) {
            b.update();
        }

        removeDeadParticles();
        addMoreParticles();

        removeDeadBubbles();
    }

    private void addMoreParticles() {
        if (particles.size() < MAX_PARTICLES) {
            int newParticles = Nature.rand.nextInt(PARTICLES_PER_FRAME);
            for (int i = 0; i < newParticles; i++) {
                particles.add(new Particle());
            }
        }
    }

    private void removeDeadParticles() {
        for (Iterator<Particle> i = particles.iterator(); i.hasNext();) {
            if (!i.next().isAlive()) {
                i.remove();
            }
        }
    }

    private void render(Graphics2D g) {
        for (Particle p : particles) {
            p.render(g);
        }

        for (Bubble b : bubbles) {
            b.render(g);
        }
    }

    private void mergeRender(Graphics2D g) {
        Particle p;
        Bubble b;
        for (int i = 0, j = 0; i < particles.size() || j < bubbles.size();) {
            if (i < particles.size()) {
                p = particles.get(i);
                if (j < bubbles.size()) {
                    b = bubbles.get(j);
                    if (p.getAge() > b.getAge()) {
                        p.render(g);
                        i++;
                    } else if (p.getAge() < b.getAge()) {
                        b.render(g);
                        j++;
                    } else {
                        b.render(g);
                        j++;
                    }
                } else {
                    p.render(g);
                    i++;
                }
            } else {
                bubbles.get(i).render(g);
                j++;
            }
        }
    }

    private Runnable queueFrame() {
        return new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        };
    }

    private void addBubbles(int count) {
        for (int i = 0; i < count; i++) {
            bubbles.add(new Bubble());
        }
    }

    private void removeDeadBubbles() {
        for (Iterator<Bubble> i = bubbles.iterator(); i.hasNext();) {
            if (!i.next().isAlive()) {
                i.remove();
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        addBubbles(Nature.rand.nextInt(BUBBLES_PER_CLICK));
    }
}
