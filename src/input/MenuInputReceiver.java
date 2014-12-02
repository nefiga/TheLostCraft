package input;

import menu.GUI;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MenuInputReceiver extends InputReceiver {

    GUI GUI;

    Action left, right, up, down, enter, back;

    public void setGUI(GUI GUI) {
        this.GUI = GUI;
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
        if (left.isPressed()) GUI.moveCursorLeft();
        if (right.isPressed()) GUI.moveCursorRight();
        if (up.isPressed()) GUI.moveCursorUp();
        if (down.isPressed()) GUI.moveCursorDown();
        if (enter.isPressed()) GUI.select();
        if (back.isPressed()) GUI.back();
        if (leftButton.isPressed()) GUI.onMouseButtonPressed(MOUSE_LEFT_BUTTON, Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
        else if (!leftButton.isHolding()) GUI.onMouseButtonReleased(MOUSE_LEFT_BUTTON, Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
        if (rightButton.isPressed()) GUI.onMouseButtonPressed(MOUSE_RIGHT_BUTTON, Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
        else if (!leftButton.isHolding()) GUI.onMouseButtonReleased(MOUSE_RIGHT_BUTTON, Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
    }

    @Override
    public void getPressedChar(char c) {
        GUI.charPressed(c);
    }

    @Override
    public void getHoldingChar(char c) {
        GUI.charHolding(c);
    }
}
