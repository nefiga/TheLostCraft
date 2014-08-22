package input;

import entity.LivingEntity;
import entity.Player;
import org.lwjgl.input.Keyboard;

public class PlayerInput extends Input{

    LivingEntity player;

    Action up, right, down, left, interact;

    public PlayerInput(Player player) {
        this.player = player;
    }

    public void initActions() {
        up = new Action("Up", Keyboard.KEY_UP);
        right = new Action("Right", Keyboard.KEY_RIGHT);
        down = new Action("Down", Keyboard.KEY_DOWN);
        left = new Action("Left", Keyboard.KEY_LEFT);
        interact = new Action("Interact", Keyboard.KEY_F);

        actions.add(up);
        actions.add(right);
        actions.add(down);
        actions.add(left);
        actions.add(interact);
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
        if (interact.isPressed()) {
            player.interactWith();
        }
        player.move(velocityX, velocityY);
    }
}
