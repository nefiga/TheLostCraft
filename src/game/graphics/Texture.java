package game.graphics;

import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    /**
     * The standard textureAtlas sizes
     */
    public static final int SMALL = 320, MEDIUM = 640, LARGE = 960;

    private int width, height;

    private final int id;

    private List<BufferedImage> images;

    private int[][] position;

    private int lastBufferPosition;

    private int x, y;

    ByteBuffer buffer;

    public Texture(int size) {
        width = size;
        height = size;
        id = glGenTextures();
        buffer = BufferUtils.createByteBuffer(width * height * 4);
        images = new ArrayList<BufferedImage>();
        position = new int[500][2];
    }

    public int[] addImage(BufferedImage image) {
        int[] xy = new int[2];
        if (!images.contains(image)) {
            images.add(image);
            position[images.size() - 1][0] = x;
            position[images.size() - 1][1] = y;
            xy[0] = x;
            xy[1] = y;
            x += image.getWidth();
            y += image.getHeight();

            int[] pixels  = new int[image.getWidth()  * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            // Iterate through all the pixels and add them to the byteBuffer
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Select the pixel
                    int pixel = pixels[x + y * image.getWidth()];
                    // Add the RED component
                    buffer.put((byte) ((pixel >> 16)& 0xFF));
                    // Add the GREEN component
                    buffer.put((byte) ((pixel >> 8)& 0xFF) );
                    // Add the BLUE component
                    buffer.put((byte) (pixel & 0xFF));
                    // Add the ALPHA component
                    buffer.put((byte) ((pixel >> 24)& 0xFF));
                }
            }
            lastBufferPosition = buffer.position();
        }
        else {
            int p = images.indexOf(image);
            xy[0] = position[p][0];
            xy[1] = position[p][1];
        }
        return xy;
    }

    public void begin() {
        buffer.position(lastBufferPosition);
        glBindTexture(GL_TEXTURE_2D, id);

        // Set texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    public void end() {
        buffer.rewind();

        // Send texture data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);
    }
}
