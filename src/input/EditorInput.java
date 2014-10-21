package input;

import editor.Editor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.event.MouseEvent;

public class EditorInput extends Input {

    Editor editor;

    Action up, right, down, left, a, s, d, w, zoomIn, zoomOut, shift, control;

    public EditorInput(Editor editor) {
        this.editor = editor;
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
        editor.move(velocityX, velocityY);

        if (zoomIn.isPressed()) editor.zoomIn();
        if (zoomOut.isPressed()) editor.zoomOut();

        if (shift.isHolding()) {
            if (leftButton.isPressed()) {
                editor.shiftClick(Mouse.getX(), Mouse.getY());
            }
        }
        else if(control.isHolding()) {
            if (leftButton.isPressed()) {
                editor.controlClick(Mouse.getX(), Mouse.getY());
            }
        }
        else if (leftButton.isHolding()) {
            editor.leftClick(Mouse.getX(), Mouse.getY());
        }
        else if (rightButton.isHolding()) {
            editor.rightClick(Mouse.getX(), Mouse.getY());
        }
    }
}
