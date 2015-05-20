package edu.unm.carc.socialmedia.jparticles.physics;

import edu.unm.carc.socialmedia.jparticles.gui.Screen;

import java.awt.*;

/**
 * @author Sixstring982
 * @since 5/13/2015
 */
public class Particle {
    private static final int RADIUS = 4;
    private static final int MAX_AGE = 1000;

    private Vector2 pos;
    private Vector2 vel;
    private Vector2 acc;
    private final Color waterColor;
    private final Color sparkColor;
    private boolean isSpark = false;
    private int age = 0;

    public Particle(Vector2 pos, Vector2 vel, Vector2 acc) {
        this.pos = pos;
        this.vel = vel;
        this.acc = acc;
        this.waterColor = randomWaterColor();
        this.sparkColor = randomSparkColor();
    }

    public Particle() {
        this.pos = new Vector2(Nature.rand.nextDouble() * Screen.WIDTH, 0);
        this.vel = Vector2.RIGHT.mul(
                0.1 + 0.9 * Math.abs(Nature.rand.nextDouble()));
        this.acc = Vector2.GRAVITY;
        this.waterColor = randomWaterColor();
        this.sparkColor = randomSparkColor();
    }

    private Color randomWaterColor() {
        return new Color(0, Nature.rand.nextInt(100) +  50, 255);
    }

    private Color randomSparkColor() {
        return new Color(255, waterColor.getGreen(), waterColor.getBlue());
    }

    private int randomGreen() {
        return Nature.rand.nextInt(0x100);
    }

    public void update() {
        if (pos.distance(pos.add(vel)) > 1) {
            pos = pos.add(vel.normalize());
        } else {
            pos = pos.add(vel);
        }
        vel = vel.add(acc);
        age++;
    }

    public void bounce() {
        this.isSpark = true;
        this.vel = new Vector2(vel.x,
                              -vel.y * (0.2 + 0.8 * Nature.rand.nextDouble()));
    }

    public boolean isAlive() {
        return pos.y < Screen.HEIGHT + RADIUS &&
                pos.x < Screen.WIDTH + RADIUS &&
                age < MAX_AGE;
    }

    public void render(Graphics2D g) {
        if (isSpark) {
            g.setColor(sparkColor);
        } else {
            g.setColor(waterColor);
        }

        g.fillOval((int)(pos.x - RADIUS), (int)(pos.y - RADIUS),
                   RADIUS, RADIUS);
    }

    public int getAge() {
        return age;
    }
}
