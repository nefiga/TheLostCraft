package game.fonts;

import game.graphics.ShaderManager;
import game.graphics.SpriteBatch;
import game.graphics.Texture;
import game.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public class GeneralFont extends Font{

    public GeneralFont(BufferedImage image, int width, int height, int charWidth, int charHeight) {
        super(image, width, height, charWidth, charHeight);
        fontAtlas = new TextureAtlas(1600, 16);
        fontAtlas.addTexture(getStringImage("{|}~      "), 10 * 16, 16);
        fontBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(fontAtlas), 1000);
    }
}
