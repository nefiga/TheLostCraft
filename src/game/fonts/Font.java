package game.fonts;

import game.graphics.*;

import java.awt.image.BufferedImage;

public class Font {

    // Char count 69
    //  Character sequence: 0 - 9, a - z, space ! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ ` { | } ~

    public static Font generalFont = new GeneralFont(ImageManager.getImage("/fonts/temp_font"));

    public static final int CHARACTER_COUNT = 69;

    protected TextureAtlas fontAtlas;
    protected SpriteBatch fontBatch;

    protected int width, height;

    private int textSize = 25;

    public static final int CHAR_SIZE = 16;

    protected Sprite[] characters;

    public Font(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        characters = new Sprite[CHARACTER_COUNT];
        fontAtlas = new TextureAtlas(TextureAtlas.SMALL);
        loadChars(image);
        fontBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(fontAtlas), 1000);
    }

    private void loadChars(BufferedImage image) {
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        for (int startY = 0; startY < height / CHAR_SIZE; startY++) {
            for (int startX = 0; startX < width / CHAR_SIZE; startX++) {
                if (startX + startY * (width / CHAR_SIZE) >= CHARACTER_COUNT) continue;
                int[] tempChar = new int[CHAR_SIZE * CHAR_SIZE];
                for (int y = 0; y < CHAR_SIZE; y++) {
                    for (int x = 0; x < CHAR_SIZE; x++) {
                        tempChar[x + y * CHAR_SIZE] = pixels[(x + startX * CHAR_SIZE) + (y + startY * CHAR_SIZE) * width];
                    }
                }
                characters[startX + startY * (width / CHAR_SIZE)] = fontAtlas.addTexture(tempChar, CHAR_SIZE, CHAR_SIZE);
            }
        }
    }

    public void begin() {
        fontBatch.begin();
    }

    public void end() {
        fontBatch.end();
    }

    public void drawString(String string, int x, int y) {
        int index = 0;
        for (int i = 0; i < string.length(); i++) {
            Sprite charSprite = getChar(string.charAt(i));
            if (charSprite == null) continue;
            fontBatch.draw(x + index * textSize, y,  charSprite, CHAR_SIZE, CHAR_SIZE);
            index++;
        }
    }

    public void drawString(String string, int textSize, int x, int y) {
        int index = 0;
        for (int i = 0; i < string.length(); i++) {
            Sprite charSprite = getChar(string.charAt(i));
            if (charSprite == null) continue;
            fontBatch.draw(x + index * textSize, y, charSprite, CHAR_SIZE, CHAR_SIZE);
            index++;
        }
    }

    public void setTextSize(int size) {
        textSize = size;
    }

    public SpriteBatch getSpriteBatch() {
        return fontBatch;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getStringWidth(String string) {
        return string.length() * textSize;
    }

    public int getStringWidth(String string, int size) {
        return string.length() * size;
    }

    /**
     * Returns a image stored in an array, of the matching character
     */
    protected Sprite getChar(int character) {
        // Space - /
        if (character > 31 && character < 48) {
            return characters[character + 4];
        }
        // 0 - 9
        else if (character >  47 && character < 58) {
            return characters[character - 48];
        }
        //  : - @
        else if (character > 57 && character < 65) {
            return characters[character - 6];
        }
        // [ - `
        else if (character > 90 && character < 97) {
            return characters[character - 32];
        }
        // a - z
        else if (character > 96 && character < 123) {
            return characters[character - 87];
        }
        // { - ~
        else if (character > 122 && character < 127) {
            return characters[character - 58];
        }
        return null;
    }

    public static boolean isPrintableChar(int character) {
        // Space - /
        if (character > 31 && character < 48) {
            return true;
        }
        // 0 - 9
        else if (character >  47 && character < 58) {
            return true;
        }
        //  : - @
        else if (character > 57 && character < 65) {
            return true;
        }
        // [ - `
        else if (character > 90 && character < 97) {
            return true;
        }
        // a - z
        else if (character > 96 && character < 123) {
            return true;
        }
        // { - ~
        else if (character > 122 && character < 127) {
            return true;
        }
        return false;
    }

}
