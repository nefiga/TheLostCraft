package game.fonts;

import game.graphics.ImageManager;
import game.graphics.SpriteBatch;
import game.graphics.TextureAtlas;

import java.awt.image.BufferedImage;

public class Font {

    // Char count 59
    //  Character sequence: 0 - 9, a - z, space ! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~

    public static Font generalFont = new GeneralFont(ImageManager.getImage("/fonts/temp_font"), 160, 160, 16, 16);

    public static final int MAX_STRING_LENGTH = 100;

    protected TextureAtlas fontAtlas;
    protected SpriteBatch fontBatch;

    protected int width, height;

    protected int charWidth, charHeight;

    protected int[][] characters;

    public Font(BufferedImage image, int width, int height, int charWidth, int charHeight) {
        this.width = width;
        this.height = height;
        this.charWidth = charWidth;
        this.charHeight = charHeight;
        characters = new int[100][charWidth * charHeight];
        loadChars(image);
    }

    private void loadChars(BufferedImage image) {
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        for (int startY = 0; startY < 10; startY++) {
            for (int startX = 0; startX < 10; startX++) {

                for (int y = 0; y < charHeight; y++) {
                    for (int x = 0; x < charWidth; x++) {
                        characters[startX + startY * 10][x + y * charWidth] = pixels[(x + startX * charWidth) + (y + startY * charHeight) * width];
                    }
                }

            }
        }
    }

    public int[] getStringImage(String string) {
        int[] image = new int[(string.length() * charWidth) * charHeight];
        int length = string.length() * charWidth;
        for (int i  = 0; i < string.length(); i++) {
            System.out.println(string.charAt(i));
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
        fontBatch.draw(100, 100, 50*16, 5 * 16, 0, 0, 160, 16);
    }

    /**
     * Returns and array of pixels for the matching character
     */
    protected int[] getChar(int character) {
        System.out.println(character);
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
