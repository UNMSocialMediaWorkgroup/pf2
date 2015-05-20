package edu.unm.carc.socialmedia.jparticles.physics;

import edu.unm.carc.socialmedia.jparticles.gui.Screen;

import java.awt.*;

/**
 * @author Sixstring982
 * @since 5/13/2015
 */
public class Bubble {
    private static final int MAX_AGE = 500;
    private static final Color COLOR = new Color(0f, 0f, 0f, 0.1f);
    private Vector2 position =
            new Vector2(Nature.rand.nextDouble() * Screen.WIDTH,
                        Nature.rand.nextDouble() * Screen.HEIGHT);
    private double radius = 0;
    private double growthRate = Nature.rand.nextDouble() * 0.1 + 0.01;
    private int age = 0;

    public void update() {
        radius += growthRate;
        age += Nature.rand.nextInt(2);
    }

    public boolean isAlive() {
        return age < MAX_AGE;
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0f, 0f, 0f, 0.001f * (500 - age)));
        g.fillOval((int) (position.x - radius),
                (int) (position.y - radius),
                (int) radius * 2, (int) radius * 2);
    }

    public int getAge() {
        return age;
    }
}
