package input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public abstract class Input {

    protected  List<Action> actions = new ArrayList<Action>();

    protected boolean[] charPressed = new boolean[250];
    protected int key;

    protected Action leftButton = new Action("Left Button", 0);
    protected Action rightButton = new Action("Right Button", 1);

    public Input() {
        initActions();
    }

    public abstract void initActions();

    public void update() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                key = Keyboard.getEventCharacter();
                if (!charPressed[key]) {
                    charPressed[key] = true;
                    getPressedChar(Keyboard.getEventCharacter());
                }
            }
            else {
                charPressed[key] = false;
            }
            for (Action action : actions) {
                if (Keyboard.getEventKey() == action.getKey()) {
                    if (Keyboard.getEventKeyState()) {
                        action.press();
                    }
                    else {
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

    public abstract void checkInput();

    public abstract void getPressedChar(char c);

}
