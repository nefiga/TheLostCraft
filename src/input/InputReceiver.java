package input;

import game.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public abstract class InputReceiver {

    protected Game game;

    protected List<Action> actions = new ArrayList<Action>();

    protected boolean allowTyping;
    protected boolean[] charPressed = new boolean[250];
    protected boolean[] charHolding = new boolean[250];
    protected int key;

    protected Action leftButton = new Action("Left Button", 0);
    protected Action rightButton = new Action("Right Button", 1);

    public InputReceiver() {
        initActions();
    }

    public abstract void initActions();

    public void update() {
        while (Keyboard.next()) {
            if (allowTyping) {
                if (Keyboard.getEventKeyState()) {
                    key = Keyboard.getEventCharacter();
                    press(key);
                } else {
                    release(key);
                }
                if (isPressed(key)) getPressedChar(Keyboard.getEventCharacter());
            }

            for (Action action : actions) {
                if (Keyboard.getEventKey() == action.getKey()) {
                    if (Keyboard.getEventKeyState()) {
                        action.press();
                    } else {
                        action.release();
                    }
                }
            }
        }
        checkInput();

        if (Mouse.isButtonDown(0)) leftButton.press();
        else leftButton.release();
        if (Mouse.isButtonDown(1)) rightButton.press();
        else rightButton.release();
    }

    public void press(int key) {
        if (!charHolding[key]) {
            charPressed[key] = true;
            charHolding[key] = true;
        } else charPressed[key] = false;
    }

    public void release(int key) {
        charHolding[key] = false;
        charPressed[key] = false;
    }

    public boolean isHolding(int key) {
        return charHolding[key];
    }

    public boolean isPressed(int key) {
        if (charPressed[key]) {
            charPressed[key] = false;
            return true;
        }
        return false;
    }

    public abstract void checkInput();

    public abstract void getPressedChar(char c);

}
