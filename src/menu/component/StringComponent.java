package menu.component;

import game.fonts.Font;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class StringComponent extends MenuComponent{

    private String string;

    public StringComponent(String string, int id, int width, int height) {
        super(id, width, height);
        this.string = string;
        setWidth(string.length() * textSize);
        setHeight(textSize);
    }

    public StringComponent(String string, int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        this.string = string;
        setWidth(string.length() * textSize);
        setHeight(textSize);
    }

    public void update(long delta) {
        if (inBounds(Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()))) {
            focus();
        } else unFocus();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void renderString(Font font) {
        font.drawString(string, x, y);
    }
}
