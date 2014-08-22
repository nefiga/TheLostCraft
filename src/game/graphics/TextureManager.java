package game.graphics;

import game.GameLoop;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class TextureManager {

    int width, height;
    int id;

    /**
     * Private constructor to store values
     */
    private TextureManager(int width, int height, int id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    /**
     * Binds texture to the context
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * Sets the texture unit to bind this texture
     */
    public void setActiveTextureUnit(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
    }

    public static TextureManager loadTexture(String name) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(TextureManager.class.getClassLoader().getResourceAsStream(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to load texture: " + name);
            GameLoop.end();
        }

        int[] pixels  = new int[image.getWidth()  * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        // Create a byteBuffer
        ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * 4);

        // Iterate through all the pixels and add them to the byteBuffer
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Select the pixel
                int pixel = pixels[x + y * image.getWidth()];
                // Add the RED component
                buffer.put((byte) ((pixel >> 16)& 0xFF));
                // Add the GREEN component
                buffer.put((byte) ((pixel >> 8)& 0xFF));
                // Add the BLUE component
                buffer.put((byte) (pixel & 0xFF));
                // Add the ALPHA component
                buffer.put((byte) ((pixel >> 24)& 0xFF));
            }
        }
        buffer.rewind();

        int textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);

        // Set texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Send texture data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);

        return new TextureManager(image.getWidth(), image.getHeight(), textureID);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
