package collision.shapes;

import collision.Projection;
import math.Vector2;

public abstract class Shape {

    protected Vector2 V1;
    protected Vector2[] vertices;
    protected Vector2[] axes;

    protected int width, height;

    public Shape(Vector2 V1, int width, int height){
        this.V1 = V1;
        this.width = width;
        this.height = height;
    }

    /**
     * Finds and normalises all the axes for this shape. This must be called after
     * creating and loading all the vertices into the vertices array.
     */
    protected void loadAxes() {
        axes = new Vector2[vertices.length];

        for (int i = 0; i < axes.length; i++) {
            Vector2 P1 = vertices[i];
            Vector2 P2 = vertices[i + 1 == vertices.length ? 0 : i + 1];
            Vector2 edge = P1.minus(P2);
            Vector2 normal = edge.perpendicular(true);
            axes[i] = normal.normalise();
        }
    }

    /**
     * Projects all of this Shapes vertices onto the axis.
     * Then returns a new Projection object using the minimum
     * and maximum projections of the vertices.
     */
    public Projection project(Vector2 axis) {
        float min = axis.dot(vertices[0]);
        float max = min;
        for (int i = 0; i < vertices.length; i++) {
            float p = axis.dot(vertices[i]);
            if (p < min) {
                min = p;
            }
            else if (p > max) {
                max = p;
            }
        }
        return new Projection(min, max);
    }

    public Vector2[] getAxes() {
        return axes;
    }

    public Vector2[] getVertices() {
        return vertices;
    }

    public void move(float x, float y) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].add(x, y);
        }
    }

    public abstract Vector2 getCenter();

    public abstract void setPosition(float x, float y);
}
