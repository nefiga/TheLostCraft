package menu;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class MenuComponent {

    private int id;

    protected int x, y, width, height, verticalBounds, horizontalBounds;

    public MenuComponent(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        horizontalBounds = x + width;
        verticalBounds = y + height;
    }

    public void update(long delta) {

    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVerticalBounds() {
        return verticalBounds;
    }

    public int getHorizontalBounds() {
        return horizontalBounds;
    }

    /**
     * An event listener for mouse clicks
     */
    public interface OnClickListener {
        public void onClick(MenuComponent c);
    }
}
