package game.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.*;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class
        SpriteBatcher {

    TextureManager texture;
    Texture texture1;
    ShaderManager shader;

    FloatBuffer vertex, texCords;
    ShortBuffer elements;

    int vboID, vaoID, texID, eboID;
    int points = 0;
    short ep = 0;

    /**
     * Creates a new SpriteBatcher Object
     * @param size Number of sprites to be rendered
     * @param texture The sprite sheet to be used
     */
    public SpriteBatcher(int size, TextureManager texture) {
        vertex = BufferUtils.createFloatBuffer(size * 8);
        texCords = BufferUtils.createFloatBuffer(size * 8);
        elements = BufferUtils.createShortBuffer(size * 10);

        this.texture = texture;
        shader = new ShaderManager();
        shader.attachVertexShader("game/glsl/tile.vert");
        shader.attachFragmentShader("game/glsl/tile.frag");
        shader.link();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();

        texID = glGenBuffers();

        eboID = glGenBuffers();

        glBindVertexArray(0);

        texture.setActiveTextureUnit(0);

        // Turning on blending so alpha channel will be transparent
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Binds the vertex array object and resets buffers and vertex point data
     */
    public void begin() {
        texture.bind();
        glBindVertexArray(vaoID);

        vertex.clear();
        texCords.clear();
        elements.clear();
        points = 0;
        ep = 0;
    }

    /**
     * Flips all the buffers, sets the vertex attribute pointers and binds the element buffer object
     */
    public void end() {
        vertex.flip();
        texCords.flip();
        elements.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, texID);
        glBufferData(GL_ARRAY_BUFFER, texCords, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

        render();
    }

    /**
     * Draws all the sprites in the buffers.
     */
    private void render() {

        shader.bind();

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLE_STRIP, points, GL_UNSIGNED_SHORT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        shader.unBind();
    }

    public void draw(BufferedImage image, float x, float y) {
        int disWidth = Display.getWidth();
        int disHeight = Display.getHeight();
        int texWidth = texture.getWidth();
        int texHeight = texture.getHeight();

        int width = image.getWidth();
        int height = image.getHeight();
        int u = 0;
        int v = 0;

        float x1 = x / disWidth * 2 - 1;
        float y1 = 1 - y / disHeight * 2;
        float x2 = x / disWidth * 2 + width / disWidth * 2 - 1;
        float y2 = (1 - y / disHeight * 2) - (height / disHeight * 2);

        float tx1 = u / texWidth;
        float ty1 = v / texHeight;
        float tx2 = u / texWidth + width / texWidth;
        float ty2 = v / texHeight + height / texHeight;

        vertex.put(x1).put(y1); // Top left
        vertex.put(x2).put(y1);// Top right
        vertex.put(x1).put(y2);// Bottom  left
        vertex.put(x2).put(y2);// Bottom right

        texCords.put(tx1).put(ty1);// Top left
        texCords.put(tx2).put(ty1);// Top right
        texCords.put(tx1).put(ty2);// Bottom left
        texCords.put(tx2).put(ty2);// Bottom right

        if (ep == 0) {
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            elements.put(ep);
            ep++;
            points += 5;
        }
        else  {
            elements.put(ep);
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            elements.put(ep);
            ep++;
            points += 6;
        }
    }

    /**
     * Adds the vertices and texture coordinates to the corresponding buffer
     * @param x The starting X position of where the sprite is to be drawn
     * @param y The starting Y position of where the sprite is to be drawn
     * @param width The width of the sprite in the sprite sheet
     * @param height The height of the sprite in the sprite sheet
     * @param u The starting X position of the sprite in the sprite sheet
     * @param v The starting Y posotion of the sprite in the sprite sheet
     */
    public void draw(float x, float y, float width, float height, float u, float v) {
        int disWidth = Display.getWidth();
        int disHeight = Display.getHeight();
        int texWidth = texture.getWidth();
        int texHeight = texture.getHeight();

        float x1 = x / disWidth * 2 - 1;
        float y1 = 1 - y / disHeight * 2;
        float x2 = x / disWidth * 2 + width / disWidth * 2 - 1;
        float y2 = (1 - y / disHeight * 2) - (height / disHeight * 2);

        float tx1 = u / texWidth;
        float ty1 = v / texHeight;
        float tx2 = u / texWidth + width / texWidth;
        float ty2 = v / texHeight + height / texHeight;

        vertex.put(x1).put(y1); // Top left
        vertex.put(x2).put(y1);// Top right
        vertex.put(x1).put(y2);// Bottom  left
        vertex.put(x2).put(y2);// Bottom right

        texCords.put(tx1).put(ty1);// Top left
        texCords.put(tx2).put(ty1);// Top right
        texCords.put(tx1).put(ty2);// Bottom left
        texCords.put(tx2).put(ty2);// Bottom right

        if (ep == 0) {
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            elements.put(ep);
            ep++;
            points += 5;
        }
        else  {
            elements.put(ep);
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            ep++;
            elements.put(ep);
            elements.put(ep);
            ep++;
            points += 6;
        }
    }
}
