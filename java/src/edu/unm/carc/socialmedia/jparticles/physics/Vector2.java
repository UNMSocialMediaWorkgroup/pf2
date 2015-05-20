package edu.unm.carc.socialmedia.jparticles.physics;

/**
 * @author Sixstring982
 * @since 5/13/2015
 */
public class Vector2 {
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 GRAVITY = new Vector2(0, 0.02);
    public static final Vector2 RIGHT = new Vector2(1, 0);

    public final double x;
    public final double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 mul(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public double distance(Vector2 other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double mod() {
        return distance(ZERO);
    }

    public Vector2 normalize() {
        double d = mod();
        return new Vector2(x / d, y / d);
    }
}
