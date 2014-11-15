package menu;

import game.Game;
import game.Screen;
import game.fonts.Font;
import menu.component.*;
import menu.component.MenuComponent.OnClickListener;

public class SingleComponentMenu extends Menu implements OnClickListener {

    public SingleComponentMenu(int x, int y, int width, int height, int drawSize, int tileSet, MenuComponent component) {
        super(x, y, width, height, drawSize, tileSet, component);
    }

    public void update(long delta) {
        component.update(delta);
    }

    public void render() {
        //super.render();

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
    public void release(int button, int x, int y) {
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
    public void onClick(MenuComponent c) {

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
