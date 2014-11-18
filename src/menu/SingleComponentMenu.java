package menu;

import game.Game;
import game.Screen;
import game.fonts.Font;
import menu.component.*;

public class SingleComponentMenu extends Menu{

    public SingleComponentMenu(int x, int y, MenuComponent component) {
        super(x, y, component);
    }

    public SingleComponentMenu(int x, int y) {
        super(x, y);
    }

    public void update(long delta) {
        component.update(delta);
    }

    public void render() {

        MenuComponent.batch.begin();
        component.render(MenuComponent.batch);
        MenuComponent.batch.end();

        Font.generalFont.begin();
        component.renderString(font);
        Font.generalFont.end();
    }

    @Override
    public void onMouseButtonPressed(int button, int x, int y) {
        if (button == 0) {
            component.press(x, y);
        }
    }

    @Override
    public void onMouseButtonReleased(int button, int x, int y) {
        if (button == 0) {
            component.release(x, y);
        }
    }

    @Override
    public void charPressed(char c) {
        component.onCharPressed(c);
    }

    @Override
    public void charHolding(char c) {
        component.onCharHolding(c);
    }

    @Override
    public void screenResized(int width, int height) {

    }

    @Override
    public void openForResult(Result result, Screen screen) {
    }

    @Override
    public void open() {
        Game.openMenu(this);
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
