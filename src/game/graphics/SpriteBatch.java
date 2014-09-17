package game.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SpriteBatch {

    Texture texture;
    ShaderManager shader;

    FloatBuffer vertex, texCords;
    ShortBuffer elements;

    int vboID, vaoID, texID, eboID;
    int points = 0;
    short ep = 0;

    /**
     * Creates a new SpriteBatcher Object
     */
    public SpriteBatch(Texture texture, int size) {
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

        this.texture.setActiveTexture(0);

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

    /**
     * Adds the texture to the Buffer that will be rendered
     *
     * @param width    The width of the texture and the width to draw the texture
     * @param height   The height of the texture and the height to draw the texture
     * @param x        The starting x point on the screen
     * @param y        The starting y point on the screen
     * @param textureX The starting x point of the texture in the texture atlas
     * @param textureY The starting y point of the texture in the texture atlas
     */
    public void draw(int width, int height, float  x, float y, int textureX, int textureY) {
        draw(width, height, x, y, textureX, textureY, width, height);
    }

    /**
     * Add the texture to the Buffer that will be rendered
     *
     * @param drawWidth     The width to draw the texture
     * @param drawHeight    The height to draw the texture
     * @param x             The starting x point of the screen
     * @param y             The starting y point on the screen
     * @param textureX      The starting x point of the texture in the texture atlas
     * @param textureY      The starting y point of the texture in the texture atlas
     * @param textureWidth  The width of the texture in the texture atlas
     * @param textureHeight The height of the texture in the texture atlas
     */
    public void draw(int drawWidth, int drawHeight, float x, float y, int textureX, int textureY, int textureWidth, int textureHeight) {
        float disWidth = Display.getWidth();
        float disHeight = Display.getHeight();
        float atlasWidth = texture.getWidth();
        float atlasHeight = texture.getHeight();

        float x1 = x / disWidth * 2 - 1;
        float y1 = 1 - y / disHeight * 2;
        float x2 = x / disWidth * 2 + drawWidth / disWidth * 2 - 1;
        float y2 = (1 - y / disHeight * 2) - (drawHeight / disHeight * 2);

        float tx1 = (textureX * textureWidth) / atlasWidth;
        float ty1 = (textureY * textureHeight) / atlasHeight;
        float tx2 = ((textureX * textureWidth) / atlasWidth) + textureWidth / atlasWidth;
        float ty2 = (textureY * textureHeight) / atlasHeight + textureHeight / atlasHeight;

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
        } else {
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

    public void subTexture(int[] piexels, int offsetX, int offsetY, int width, int height) {
        texture.subTexture(piexels, offsetX, offsetY, width, height);
    }
}