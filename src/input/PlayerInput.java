package input;

import entity.LivingEntity;
import entity.Player;
import org.lwjgl.input.Keyboard;

public class PlayerInput extends Input {

    LivingEntity player;

    Action up, right, down, left, a, s, d, w, interact;

    public PlayerInput(Player player) {
        this.player = player;
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
        interact = new Action("Interact", Keyboard.KEY_F);

        actions.add(up);
        actions.add(right);
        actions.add(down);
        actions.add(left);
        actions.add(a);
        actions.add(s);
        actions.add(d);
        actions.add(w);
        actions.add(interact);
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
        if (velocityX != 0 || velocityY != 0) {
            player.move(velocityX, velocityY);
        }

    }
}
