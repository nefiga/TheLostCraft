package collision.shapes;

import math.Vector2;

public class Square extends Shape {

    protected Vector2 V2, V3, V4;

    public Square(Vector2 V1, int width, int height) {
        super(V1, width, height);
        V2 = new Vector2(V1.x + width, V1.y);
        V3 = new Vector2(V2.x, V1.y + height);
        V4 = new Vector2(V1.x, V3.y);
        vertices = new Vector2[]{this.V1, V2, V3, V4};
        loadAxes();
    }

    @Override
    public void setPosition(float x, float y) {
        V1.setX(x);
        V1.setY(y);
        V2.setX(V1.x + width);
        V2.setY(V1.y);
        V3.setX(V2.x);
        V3.setY(V1.y + height);
        V4.setX(V1.x);
        V4.setY(V3.y);
    }

    @Override
    public Vector2 getCenter() {
        return new Vector2(V1.x + width / 2, V2.y + height / 2);
    }
}
