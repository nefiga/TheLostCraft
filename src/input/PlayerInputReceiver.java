package input;

import entity.Player;
import org.lwjgl.input.Keyboard;

public class PlayerInputReceiver extends InputReceiver {

    static Player player;

    Action up, right, down, left, a, s, d, w, interact, escape;

    public void initActions() {
        up = new Action("Up", Keyboard.KEY_UP);
        right = new Action("Right", Keyboard.KEY_RIGHT);
        down = new Action("Down", Keyboard.KEY_DOWN);
        left = new Action("Left", Keyboard.KEY_LEFT);
        a = new Action("A", Keyboard.KEY_A);
        s = new Action("S", Keyboard.KEY_S);
        d = new Action("D", Keyboard.KEY_D);
        w = new Action("W", Keyboard.KEY_W);
        interact = new Action("Interact", Keyboard.KEY_F);
        escape = new Action("Escape", Keyboard.KEY_ESCAPE);

        actions.add(up);
        actions.add(right);
        actions.add(down);
        actions.add(left);
        actions.add(a);
        actions.add(s);
        actions.add(d);
        actions.add(w);
        actions.add(interact);
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
        if (interact.isPressed()) {
            player.interactWith();
        }
        if (escape.isPressed()) player.onEscapedPressed();
        if (velocityX != 0 || velocityY != 0) {
            player.move(velocityX, velocityY);
        }

    }

    @Override
    public void getPressedChar(char c) {

    }

    public static void setPlayer(Player p) {
        player = p;
    }
}
