package game.fonts;

import game.graphics.*;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Font {

    // Char count 69
    //  Character sequence: 0 - 9, a - z, space ! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~

    public static Font generalFont = new GeneralFont(ImageManager.getImage("/fonts/temp_font"), 16, 16);

    public static final int CHARACTER_COUNT = 69;

    protected TextureAtlas fontAtlas;
    protected SpriteBatch fontBatch;

    protected int width, height;

    protected int charWidth, charHeight;

    protected int[][] characters;

    public Font(BufferedImage image, int charWidth, int charHeight) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.charWidth = charWidth;
        this.charHeight = charHeight;
        characters = new int[CHARACTER_COUNT][2];
        fontAtlas = new TextureAtlas(TextureAtlas.SMALL);
        loadChars(image);
        fontBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(fontAtlas), 1000);
    }

    private void loadChars(BufferedImage image) {
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        for (int startY = 0; startY < height / charHeight; startY++) {
            for (int startX = 0; startX < width / charWidth; startX++) {
                if (startX  + startY * (width / charWidth) >= CHARACTER_COUNT) continue;
                int[] tempChar = new int[charWidth * charHeight];
                for (int y = 0; y < charHeight; y++) {
                    for (int x = 0; x < charWidth; x++) {
                        tempChar[x + y * charWidth] = pixels[(x + startX * charWidth) + (y + startY * charHeight) * width];
                    }
                }
                characters[startX + startY * (width / charWidth)] = fontAtlas.addTexture(tempChar, charWidth, charHeight);
                System.out.println(characters[startX + startY * (width / charWidth)][0] + "   " + characters[startX + startY * (width / charWidth)][1]);
            }
        }
    }

    public int[] getStringImage(String string) {
        int[] image = new int[(string.length() * charWidth) * charHeight];
        int length = string.length() * charWidth;
        for (int i  = 0; i < string.length(); i++) {
            int[] charPixels = getChar(string.charAt(i));
            for (int y = 0; y  < charHeight; y++) {
                for (int x = 0; x < charWidth; x++) {
                    image[(x + i * charWidth) + y * length] = charPixels[x + y * charWidth];
                }
            }
        }
        return image;
    }

    public void begin() {
        fontBatch.begin();
    }

    public void end() {
        fontBatch.end();
    }

    public void DrawString(String string, int x, int y) {
        for (int i = 0; i < string.length(); i++) {
            int[] position = getChar(string.charAt(i));
            fontBatch.draw(x + i *  charWidth, y, position[0], position[1], charWidth, charHeight);
        }
    }

    /**
     * Returns and array of pixels for the matching character
     */
    protected int[] getChar(int character) {
        // Space - /
        if (character > 31 && character < 48) {
            return characters[character + 4];
        }
        // 0 - 9
        else if (character < 58) {
            return characters[character - 48];
        }
        //  : - @
        else if (character < 65) {
            return characters[character - 6];
        }
        // [ - `
        else if (character > 90 && character < 97) {
            return characters[character - 32];
        }
        // a - z
        else if (character < 123) {
            return characters[character - 87];
        }
        // { - ~
        else if (character < 127) {
            return characters[character - 58];
        }
        return null;
    }
}
