package menu;

import game.Screen;
import game.fonts.Font;
import menu.component.*;

public class SingleComponentGUI extends GUI {

    public SingleComponentGUI(int x, int y, MenuComponent component) {
        super(x, y, component);
    }

    public SingleComponentGUI(int x, int y) {
        super(x, y);
    }

    public void update(long delta) {
        mainComponent.update(delta);
    }

    public void render() {

        MenuComponent.batch.begin();
        mainComponent.render(MenuComponent.batch);
        MenuComponent.batch.end();

        Font.generalFont.begin();
        mainComponent.renderString(font);
        Font.generalFont.end();
    }

    @Override
    public void onMouseButtonPressed(int button, int x, int y) {
        mainComponent.press(button, x, y);
    }

    @Override
    public void onMouseButtonReleased(int button, int x, int y) {
        mainComponent.release(button, x, y);
    }

    @Override
    public void charPressed(char c) {
        mainComponent.onCharPressed(c);
    }

    @Override
    public void charHolding(char c) {
        mainComponent.onCharHolding(c);
    }

    @Override
    public void screenResized(int width, int height) {

    }

    @Override
    public void openForResult(Result result, Screen screen) {
    }

    @Override
    public void returnResult() {
        screen.returnResult(result);
    }

    @Override
    public void moveCursorUp() {
    }

    @Override
    public void moveCursorRight() {

    }

    @Override
    public void moveCursorDown() {
    }

    @Override
    public void moveCursorLeft() {

    }

    @Override
    public void select() {
    }

    @Override
    public void back() {

    }
}
