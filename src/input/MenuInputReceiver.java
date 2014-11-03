package input;

import menu.Menu;
import org.lwjgl.input.Keyboard;

public class MenuInputReceiver extends InputReceiver {

    Menu menu;

    Action left, right, up, down, enter, back;

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void initActions() {
        left = new Action("Left", Keyboard.KEY_LEFT);
        right = new Action("Right", Keyboard.KEY_RIGHT);
        up = new Action("Up", Keyboard.KEY_UP);
        down = new Action("Down", Keyboard.KEY_DOWN);
        enter = new Action("Enter", Keyboard.KEY_RETURN);
        back = new Action("Back", Keyboard.KEY_ESCAPE);

        actions.add(left);
        actions.add(right);
        actions.add(up);
        actions.add(down);
        actions.add(enter);
        actions.add(back);

        allowTyping = true;
    }

    @Override
    public void checkInput() {
        if (left.isPressed())menu.moveCursorLeft();
        if (right.isPressed()) menu.moveCursorRight();
        if (up.isPressed()) menu.moveCursorUp();
        if (down.isPressed()) menu.moveCursorDown();
        if (enter.isPressed()) menu.select();
        if (back.isPressed()) menu.back();
    }

    @Override
    public void getPressedChar(char c) {
        menu.charPressed(c);
    }
}