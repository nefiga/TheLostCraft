package menu;

import game.graphics.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Button extends MenuComponent {

    private TextureAtlas atlas;
    private SpriteBatch batch;
    private OnClickListener listener;

    private int[] image = new int[4];
    private int[] imagePressed = new int[4];
    private int[] imageFocused = new int[4];

    private String text;

    private boolean hasFocus, pressed, holding;

    public Button(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);

        if (atlas == null) {
            atlas = new TextureAtlas(TextureAtlas.SMALL);
            image = atlas.addTexture(ImageManager.getImage("/menu/button"));
            imagePressed = atlas.addTexture(ImageManager.getImage("/menu/button_pressed"));
            imageFocused = atlas.addTexture(ImageManager.getImage("/menu/button_focused"));
        }
        batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 10);
    }

    public void update(long delta) {
        if (inBounds(Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()))) {
            focus();
        }
        else unFocus();
    }

    public void press() {
        pressed = true;
        holding = true;
    }

    public void release() {
        if (pressed) listener.onClick(this);
        pressed = false;
        holding = false;
    }

    /**
     * Sets hasFocus to true
     */
    public void focus() {
        hasFocus = true;
    }

    /**
     * Sets hasFocus to false
     */
    public void unFocus() {
        hasFocus = false;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Returns true if the x and y coordinates are in the bounds of this button
     */
    public boolean inBounds(int xPosition, int yPostion) {
        if (xPosition > x && xPosition < horizontalBounds && yPostion > y && yPostion < verticalBounds) {
            return true;
        }
        return false;
    }

    public boolean isHolding() {
        return holding;
    }

    public boolean isPressed() {
        if (pressed) {
            pressed = false;
            return true;
        }
        return false;
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public String getText() {
        return text;
    }

    public void render() {
        batch.begin();
        if (pressed) batch.draw(x, y, imagePressed[0], imagePressed[1], imagePressed[2], imagePressed[3]);
        else if (hasFocus) batch.draw(x, y, imageFocused[0], imageFocused[1], imageFocused[2], imageFocused[3]);
        else batch.draw(x, y, image[0], image[1], image[2], image[3]);
        batch.end();
    }
}
