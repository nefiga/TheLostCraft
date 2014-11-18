package menu.component;

import game.fonts.Font;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;

public class MenuComponent {

    protected static TextureAtlas atlas = new TextureAtlas(TextureAtlas.LARGE);
    public static SpriteBatch batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);

    private OnClickListener listener;

    /**
     * Insets what this component is holding in this order (top, right, bottom, left).
     */
    protected int topPadding, rightPadding, bottomPadding, leftPadding;

    private int id;

    protected int screenX, screenY, menuX, menuY, width, height, verticalBounds, horizontalBounds;

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
     * Creates a new MenuComponent at the location of screenX, screenY
     */
    public MenuComponent(int id, int menuX, int menuY, int width, int height) {
        this.id = id;
        this.menuX = menuX;
        this.menuY = menuY;
        this.width = width;
        this.height = height;
        horizontalBounds = menuX + width;
        verticalBounds = menuY + height;
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
            pressed = true;
            holding = true;
        }
    }

    public void release(int x, int y) {
        if (inBounds(x, y)){
            if (pressed && hasFocus && listener != null) listener.onLeftClick(this);
            pressed = false;
            holding = false;;
        }
    }

    public void onCharPressed(char c) {

    }

    public void onCharHolding(char c) {

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

    public void setPositionInMenu(int menuX, int menuY) {
        this.screenX = this.menuX + menuX;
        this.screenY = this.menuY + menuY;
        horizontalBounds = screenX + width;
        verticalBounds = screenY + height;
        adjustComponents();
    }

    public void setPosition(int x, int y) {
        this.screenX = x;
        this.screenY = y;
        horizontalBounds = x + width;
        verticalBounds = y + height;
    }

    protected void adjustComponents() {

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
        horizontalBounds = screenX + this.width;
    }

    public void setHeight(int height) {
        this.height = height;
        verticalBounds = screenY + this.height;
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

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
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
     * Returns true if the screenX and screenY coordinates are in the bounds of this button
     */
    public boolean inBounds(int xPosition, int yPosition) {
        if (xPosition > screenX && xPosition < horizontalBounds && yPosition > screenY && yPosition < verticalBounds) {
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
     * Returns the farthest positive screenX position of this component
     */
    public int getVerticalBounds() {
        return verticalBounds;
    }

    /**
     * Returns the farthest positive screenY position of this component
     */
    public int getHorizontalBounds() {
        return horizontalBounds;
    }

    /**
     * An event listener for mouse clicks
     */
    public interface OnClickListener {
        public void onLeftClick(MenuComponent c);

        public void onRightClick(MenuComponent c);
    }
}
