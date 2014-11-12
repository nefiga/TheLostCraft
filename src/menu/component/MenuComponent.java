package menu.component;

import game.fonts.Font;
import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;

public class MenuComponent {

    protected static TextureAtlas atlas = new TextureAtlas(TextureAtlas.MEDIUM);
    public static SpriteBatch batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);

    private int id;

    protected int x, y, width, height, verticalBounds, horizontalBounds;

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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        horizontalBounds = x + width;
        verticalBounds = y + height;
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
