package input;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Input {

    protected  List<Action> actions = new ArrayList<Action>();

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
