package input;

import editor.MapEditor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class EditorInputReceiver extends InputReceiver {

    MapEditor mapEditor;

    Action up, right, down, left, a, s, d, w, zoomIn, zoomOut, shift, control, escape;

    public EditorInputReceiver(MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    public void initActions() {
        up = new Action("Up", Keyboard.KEY_UP);
        right = new Action("Right", Keyboard.KEY_RIGHT);
        down = new Action("Down", Keyboard.KEY_DOWN);
        left = new Action("Left", Keyboard.KEY_LEFT);
        a = new Action("A", Keyboard.KEY_A);
        s = new Action("S", Keyboard.KEY_S);
        d = new Action("D", Keyboard.KEY_D);
        w = new Action("W", Keyboard.KEY_W);
        zoomIn = new Action("Zoom In", Keyboard.KEY_Z);
        zoomOut = new Action("Zoom  Out", Keyboard.KEY_X);
        shift = new Action("Shift", Keyboard.KEY_LSHIFT);
        control = new Action("Control", Keyboard.KEY_LCONTROL);
        escape = new Action("Escape", Keyboard.KEY_ESCAPE);

        actions.add(up);
        actions.add(right);
        actions.add(down);
        actions.add(left);
        actions.add(a);
        actions.add(s);
        actions.add(d);
        actions.add(w);
        actions.add(zoomIn);
        actions.add(zoomOut);
        actions.add(shift);
        actions.add(control);
        actions.add(escape);
    }

    public void checkInput() {
        int velocityX = 0, velocityY = 0;
        if (right.isHolding() || d.isHolding()) {
            velocityX = 3;
        }
        if (left.isHolding() || a.isHolding()) {
            velocityX = -3;
        }
        if (up.isHolding() || w.isHolding()) {
            velocityY = -3;
        }
        if (down.isHolding() || s.isHolding()) {
            velocityY = 3;
        }
        mapEditor.moveMap(velocityX, velocityY);

        if (escape.isPressed()) mapEditor.onEscapePressed();
        if (zoomIn.isPressed()) mapEditor.zoomIn();
        if (zoomOut.isPressed()) mapEditor.zoomOut();

        if (shift.isHolding()) {
            if (leftButton.isPressed()) {
                mapEditor.shiftClick(Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
            }
        }
        else if (leftButton.isHolding()) {
            mapEditor.leftClick(Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
        }
        else if (rightButton.isPressed()) {
            mapEditor.rightClick(Mouse.getX(), Math.abs(Mouse.getY() - Display.getHeight()));
        }
    }

    @Override
    public void getPressedChar(char c) {

    }

    @Override
    public void getHoldingChar(char c) {

    }
}
