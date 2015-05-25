package menu.component;

import game.fonts.Font;
import game.graphics.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Button extends MenuComponent {

    private Sprite image;
    private Sprite imagePressed;
    private Sprite imageFocused;

    private String text;

    /**
     * Creates a new Button at position 0, 0
     */
    public Button(int id, int width, int height) {
        super(id, width, height);
        image = MenuComponent.addImage("button");
        imagePressed = MenuComponent.addImage("button_pressed");
        imageFocused = MenuComponent.addImage("button_focused");
    }

    public Button(String text, int id, int width, int height) {
        super(id, width, height);
        setText(text);
        image = MenuComponent.addImage("button");
        imagePressed = MenuComponent.addImage("button_pressed");
        imageFocused = MenuComponent.addImage("button_focused");
    }


    /**
     * Creates a new Button at position screenX, screenY
     */
    public Button(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        image = MenuComponent.addImage("button");
        imagePressed = MenuComponent.addImage("button_pressed");
        imageFocused = MenuComponent.addImage("button_focused");
    }

    public void update(long delta) {
        if (inBounds(Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()))) {
            focus();
        } else unFocus();
    }

    public void setText(String text) {
        this.text = text;
    }

    // Override padding methods to do nothing.
    // Text will aways be centered in button
    public void setPadding(int padding) {
    }

    public void setTopPadding(int padding) {
    }

    public void setRightPadding(int padding) {
    }

    public void setBottomPadding(int padding) {
    }

    public void setLeftPadding(int padding) {
    }

    public void render(SpriteBatch batch) {
        if (renderBackground) {
            if (leftButtonPressed)
                batch.draw(screenX, screenY, imagePressed, width, height);
            else if (hasFocus)
                batch.draw(screenX, screenY, imageFocused, width, height);
            else
                batch.draw(screenX, screenY, image, width, height);
        }

    }

    public void renderString(Font font) {
        if (text != null) {
            font.drawString(text, textSize, screenX + (width - text.length() * textSize) / 2, screenY + (height - textSize) / 2);
        }
    }
}
