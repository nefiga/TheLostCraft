package input;

import editor.Editor;
import org.lwjgl.input.Keyboard;

public class EditorInput extends Input {

    Editor editor;

    Action up, right, down, left, zoomIn, zoomOut;

    public EditorInput(Editor editor) {
        this.editor = editor;
    }

    public void initActions() {
        up = new Action("Up", Keyboard.KEY_UP);
        right = new Action("Right", Keyboard.KEY_RIGHT);
        down = new Action("Down", Keyboard.KEY_DOWN);
        left = new Action("Left", Keyboard.KEY_LEFT);
        zoomIn = new Action("Zoom In", Keyboard.KEY_ADD);
        zoomOut = new Action("Zoom  Out", Keyboard.KEY_SUBTRACT);

        actions.add(up);
        actions.add(right);
        actions.add(down);
        actions.add(left);
        actions.add(zoomIn);
        actions.add(zoomOut);
    }

    public void checkInput() {
        int velocityX = 0, velocityY = 0;
        if (right.isHolding()) {
            velocityX = 3;
        }
        if (left.isHolding()) {
            velocityX = -3;
        }
        if (up.isHolding()) {
            velocityY = -3;
        }
        if (down.isHolding()) {
            velocityY = 3;
        }
        editor.move(velocityX, velocityY);

        if (zoomIn.isPressed()) editor.zoomIn();
        if (zoomOut.isPressed()) editor.zoomOut();
    }
}
