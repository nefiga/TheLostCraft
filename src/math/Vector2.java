package math;

public class Vector2 {

    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 minus(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    /**
     * Returns a Vector2 perpendicular to this Vector2
     *
     * @param leftHand If true return the perpendicular Vector2 on the left hand of this Vector2,
     *                 else return the perpendicular Vector2 on the right hand of this Vector2
     */
    public Vector2 perpendicular(boolean leftHand) {
        if (leftHand) return new Vector2(y, -x);
        else return new Vector2(-y, x);
    }

    public Vector2 times(float n) {
        return new Vector2(this.x * n, this.y * n);
    }

    public Vector2 normalise() {
        float length = (float) Math.sqrt(x * x + y * y);
        return new Vector2(x / length, y / length);
    }

    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void negate() {
        x = -x;
        y = -y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
