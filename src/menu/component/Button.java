package menu.component;

import game.fonts.Font;
import game.graphics.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Button extends MenuComponent {

    private OnClickListener listener;
    Font font = Font.generalFont;

    private int[] image;
    private int[] imagePressed;
    private int[] imageFocused;

    private String text;

    private int textX, textY;

    private boolean hasFocus, pressed, holding;

    public Button(int id, int width, int height) {
        super(id, width, height);
    }

    public Button(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        image = MenuComponent.addImage("button");
        imagePressed = MenuComponent.addImage("button_pressed");
        imageFocused = MenuComponent.addImage("button_focused");
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
        if (pressed && hasFocus) listener.onClick(this);
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
        textX = x + ((width - font.getStringWidth(text)) / 2);
        textY = y + ((height - font.getTextSize()) / 2);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Returns true if the x and y coordinates are in the bounds of this button
     */
    public boolean inBounds(int xPosition, int yPosition) {
        if (xPosition > x && xPosition < horizontalBounds && yPosition > y && yPosition < verticalBounds) {
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

    public void render(SpriteBatch batch) {
        if (pressed)
            batch.draw(x, y, width, height, imagePressed[0], imagePressed[1], imagePressed[2], imagePressed[3]);
        else if (hasFocus)
            batch.draw(x, y, width, height, imageFocused[0], imageFocused[1], imageFocused[2], imageFocused[3]);
        else batch.draw(x, y, width, height, image[0], image[1], image[2], image[3]);
    }

    public void renderString(Font font) {
        if (text != null) {
            font.drawString(text, textX, textY);
        }
    }
}
