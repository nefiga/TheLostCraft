package collision.shapes;

import math.Vector2;

public class Triangle extends Shape {

    private Vector2 V2, V3;

    public Triangle(Vector2 V1, int width, int height) {
        super(V1, width, height);
        V2 = new Vector2(V1.x + width / 2, V1.y + height);
        V3 = new Vector2(V1.x - width / 2, V1.y + height);
        vertices = new Vector2[]{V1, V2, V3};
        loadAxes();
    }

    @Override
    public Vector2 getCenter() {
        return new Vector2(V1.x, V1.y + height / 2);
    }

    @Override
    public void setPosition(float x, float y) {
        V1.setPosition(x + width / 2, y);
        V2.setPosition(x + width, y + height);
        V3.setPosition(x, y + height);
    }
}
