package menu.component;

import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;

public class MenuComponent {

    protected static TextureAtlas atlas = new TextureAtlas(TextureAtlas.MEDIUM);
    public static SpriteBatch batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);

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
