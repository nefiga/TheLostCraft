package editor;

import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;
import org.lwjgl.opengl.Display;

public class Editor {

    SpriteBatch editorBatch;
    TextureAtlas editorAtlas;

    private int[] menuTop, menuBottom, emptyTile;

    public Editor() {
        editorAtlas = new TextureAtlas(TextureAtlas.MEDIUM);
        createTextureAtlas();
        editorBatch = new SpriteBatch(new Texture(editorAtlas), 10000);
    }

    public void update() {

    }

    public void render() {
        editorBatch.begin();

        editorBatch.draw(Display.getWidth() - 405, 0, menuTop[0], menuTop[1], menuTop[2], menuTop[3]);
        editorBatch.draw(Display.getWidth() - 405, 540, menuBottom[0], menuBottom[1], menuBottom[2], menuBottom[3]);

        editorBatch.end();

        // Draw tiles with a new batch
    }

    private void createTextureAtlas() {
        menuTop = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_top"));
        menuBottom = editorAtlas.addTexture(ImageManager.getImage("/editor/menu_bottom"));
    }
}
