package menu.component;

import game.fonts.Font;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;

public class MenuComponent {

    protected static TextureAtlas atlas = new TextureAtlas(TextureAtlas.MEDIUM);
    public static SpriteBatch batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);

    private OnClickListener listener;

    /**
     * Insets what this component is holding in this order (top, right, bottom, left).
     */
    protected int topPadding, rightPadding, bottomPadding, leftPadding;

    private int id;

    protected int x, y, width, height, verticalBounds, horizontalBounds;

    protected int textSize = 25;

    protected boolean hasFocus, pressed, holding;

    /**
     * Creates a new MenuComponent when the location of the component is not known.
     */
    public MenuComponent(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a new MenuComponent at the location of x, y
     */
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

    public void render(SpriteBatch batch) {

    }

    /**
     * Renders the Strings of this component if it has them
     */
    public void renderString(Font font) {

    }

    public void press(int x, int y) {
        if (inBounds(x, y)){
            System.out.println("Press");
            pressed = true;
            holding = true;
        }
    }

    public void release(int x, int y) {
        if (inBounds(x, y)){
            if (pressed && hasFocus && listener != null) listener.onClick(this);
            pressed = false;
            holding = false;;
        }
    }

    /**
     * Sets hasFocus to true
     */
    public void focus() {
        hasFocus = true;
    }

    /**
     * Sets hasFocus to false
     */
    public void unFocus() {
        hasFocus = false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        horizontalBounds = x + width;
        verticalBounds = y + height;
    }

    /**
     * Sets the padding for all 4 edges
     */
    public void setPadding(int padding) {
        topPadding = rightPadding = bottomPadding = leftPadding = padding;
    }

    public void setTopPadding(int padding) {
        topPadding = padding;
    }

    public void setRightPadding(int padding) {
        rightPadding = padding;
    }

    public void setBottomPadding(int padding) {
        bottomPadding = padding;
    }

    public void setLeftPadding(int padding) {
        leftPadding = padding;
    }

    public void setWidth(int width) {
        this.width = width;
        horizontalBounds = x + this.width;
    }

    public void setHeight(int height) {
        this.height = height;
        verticalBounds = y + this.height;
    }

    public void setTextSize(int size) {
        textSize = size;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Adds an image to the MenuComponent TextureAtlas and returns the position of the image in the TextureAtlas.
     * If the TextureAtlas already contains the image the position of the image will be returned.
     * Also updates the SpriteBatch Texture if needs be.
     */
    public static int[] addImage(String name) {
        int beforeAtlasSize = atlas.getSize();

        int[] imagePosition;
        imagePosition = atlas.addTexture("/menu/" + name);

        int afterAtlasSize = atlas.getSize();
        if (beforeAtlasSize < afterAtlasSize) batch.updateTexture();

        return imagePosition;
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

    public boolean isHolding() {
        return holding;
    }

    /**
     * Returns true if the x and y coordinates are in the bounds of this button
     */
    public boolean inBounds(int xPosition, int yPosition) {
        if (xPosition > x && xPosition < horizontalBounds && yPosition > y && yPosition < verticalBounds) {
            return true;
        }
        return false;
    }

    public boolean isPressed() {
        if (pressed) {
            pressed = false;
            return true;
        }
        return false;
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    /**
     * Returns the farthest positive x position of this component
     */
    public int getVerticalBounds() {
        return verticalBounds;
    }

    /**
     * Returns the farthest positive y position of this component
     */
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
