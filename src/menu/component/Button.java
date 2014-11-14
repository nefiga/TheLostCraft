package menu.component;

import game.fonts.Font;
import game.graphics.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Button extends MenuComponent {

    private int[] image;
    private int[] imagePressed;
    private int[] imageFocused;

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
     * Creates a new Button at position x, y
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

    public String getText() {
        return text;
    }

    public void render(SpriteBatch batch) {
        if (pressed)
            batch.draw(x, y, width, height, imagePressed[0], imagePressed[1], imagePressed[2], imagePressed[3]);
        else if (hasFocus)
            batch.draw(x, y, width, height, imageFocused[0], imageFocused[1], imageFocused[2], imageFocused[3]);
        else batch.draw(x, y, width, height, image[0], image[1], image[2], image[3]);
    }

    public void renderString(Font font) {
        if (text != null) {
            font.drawString(text, textSize,  x + (width - text.length() * textSize) / 2, y + (height - textSize) / 2);
        }
    }
}
