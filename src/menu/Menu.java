package menu;

import game.Screen;
import game.fonts.Font;
import game.graphics.*;
import menu.component.MenuComponent;

public abstract class Menu {

    protected Font font = Font.generalFont;

    protected Screen screen;

    protected Result result;

    protected MenuComponent component;

    protected int x, y;

    /**
     * Creates a new menu. The size of the menu can be controlled in two ways.
     * You can control the width and height of the tiles as well as how many tiles used.
     */
    public Menu(int x, int y, MenuComponent component) {
        this.x = x;
        this.y = y;
        this.component = component;
        this.component.setPositionInMenu(x, y);
    }

    public Menu(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(long delta) {

    }

    public void render() {

    }

    public abstract void onMouseButtonPressed(int button, int x, int y);

    public abstract void onMouseButtonReleased(int button, int x, int y);

    public abstract void moveCursorUp();

    public abstract void moveCursorRight();

    public abstract void moveCursorDown();

    public abstract void moveCursorLeft();

    public abstract void charPressed(char c);

    public abstract void charHolding(char c);

    public abstract void select();

    public abstract void back();

    public abstract void screenResized(int width, int height);

    public abstract void openForResult(Result result, Screen screen);

    public abstract void open();

    public abstract void returnResult();

    public void setFont(Font font) {
        this.font = font;
    }
}
