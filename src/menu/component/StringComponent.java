package menu.component;

import game.fonts.Font;

public class StringComponent extends MenuComponent{

    private String string;

    public StringComponent(String string, int id, int width, int height) {
        super(id, width, height);
        this.string = string;
    }

    public StringComponent(String string, int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void render(Font font) {
        font.drawString(string, x, y);
    }
}
