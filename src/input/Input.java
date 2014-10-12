package input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class Input {

    protected  List<Action> actions = new ArrayList<Action>();

    public static final int LEFT_MOUSE_BUTTON = 500, RIGHT_MOUSE_BUTTON = 501;

    public Input() {
        initActions();
    }

    public void initActions() {
    }

    public void update() {
        while (Keyboard.next()) {
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
    }

    public void checkInput() {

    }
}
