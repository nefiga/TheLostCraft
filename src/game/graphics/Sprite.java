package game.graphics;

public class Sprite {

    private float startXPosition;
    private float startYPosition;
    private float endXPosition;
    private float endYPosition;
    private int width;
    private int height;

    Sprite(float startXPosition, float startYPosition, int width, int height) {
        this.startXPosition = startXPosition;
        this.startYPosition = startYPosition;
        endXPosition = startXPosition + width;
        endYPosition = startYPosition + height;
        this.width = width;
        this.height = height;
    }

    public float getStartXPosition() {
        return startXPosition;
    }

    public float getStartYPosition() {
        return startYPosition;
    }

    public float getEndXPosition() {
        return endXPosition;
    }

    public float getEndYPosition() {
        return endYPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
