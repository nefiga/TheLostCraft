package menu.component;

import game.fonts.Font;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;
import input.InputReceiver;
import menu.Menu;

public class MenuComponent {

    protected static TextureAtlas atlas = new TextureAtlas(TextureAtlas.LARGE);
    public static SpriteBatch batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);

    private OnClickListener listener;

    /**
     * Insets the contents of this component. Check with the specific component for implementation.
     */
    protected int topPadding, rightPadding, bottomPadding, leftPadding;

    private int id;

    /**
     * The x and y position of where the component will be drawn on the screen
     */
    protected int screenX, screenY;
    /**
     * The x and y position of the component in the menu
     */
    protected int  menuX, menuY;

    protected int width, height;
    /**
     * The farthest horizontal point and the farthest vertical point of this component
     */
    protected int verticalBounds, horizontalBounds;

    protected boolean center = true;

    protected boolean renderBackground = true;

    protected int textSize = 25;

    protected int spacing = 5;

    protected boolean hasFocus, leftButtonPressed, leftButtonHolding,  rightButtonPressed, rightButtonHolding;

    /**
     * Creates a new MenuComponent when the location of the component is not known.
     */
    public MenuComponent(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a new MenuComponent at the location of menuX, menuY
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

    public void renderString(Font font) {

    }

    public void press(int button, int x, int y) {
        if (inBounds(x, y)) {
            if (button == InputReceiver.MOUSE_LEFT_BUTTON) {
                if (listener != null)
                    listener.onLeftPressed(this);

                leftButtonPressed = true;
                leftButtonHolding = true;
            }
            else {
                if (listener != null)
                    listener.onRightPressed(this);

                rightButtonPressed = true;
                rightButtonHolding = true;
            }
        }
    }

    public void release(int button, int x, int y) {
        if (inBounds(x, y)) {
            if (button == InputReceiver.MOUSE_LEFT_BUTTON) {
                if (leftButtonPressed && hasFocus && listener != null)
                    listener.onLeftClick(this);
                else if(listener != null)
                    listener.onLeftReleased(this);

                leftButtonPressed = false;
                leftButtonHolding = false;
            }
            else {
                if (rightButtonPressed && hasFocus && listener != null)
                    listener.onRightClick(this);
                else if (listener != null)
                    listener.onRightReleased(this);

                rightButtonPressed = false;
                rightButtonHolding = false;
            }
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

    public void renderBackground(boolean renderBackground) {
        this.renderBackground = renderBackground;
    }

    public void setPositionInMenu(Menu menu) {
        this.screenX = menu.getX() + menuX;
        this.screenY = menu.getY() + menuY;
        horizontalBounds = screenX + width;
        verticalBounds = screenY + height;
        adjustComponents();
    }

    public void setPosition(int x, int y) {
        this.screenX = x;
        this.screenY = y;
        horizontalBounds = x + width;
        verticalBounds = y + height;
        adjustComponents();
    }

    public void setCenter(boolean center) {
        this.center = center;
        adjustComponents();
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

    public void setSpacing(int spacing) {
        this.spacing = spacing;
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

    /**
     * Returns the x position of this component on the screen
     */
    public int getScreenX() {
        return screenX;
    }

    /**
     * Returns the y position of this component on the screen
     */
    public int getScreenY() {
        return screenY;
    }

    public int getSpacing() {
        return spacing;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isLeftButtonHolding() {
        return leftButtonHolding;
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

    /**
     * Returns true if the mouse is over this component
     */
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

        public void onLeftPressed(MenuComponent c);

        public void onLeftReleased(MenuComponent c);

        public void onLeftClick(MenuComponent c);

        public void onRightPressed(MenuComponent c);

        public void onRightReleased(MenuComponent c);

        public void onRightClick(MenuComponent c);
    }
}
