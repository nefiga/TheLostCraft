package entity;

import collision.shapes.Square;
import math.Vector2;
import menu.Menu;

public class Player extends LivingEntity{

    Menu menu;

    public Player(int x, int y) {
        super(x, y);
        setTexture("player");
        shape = new Square(new Vector2(x, y), 64, 64);
    }

    public void update(long delta) {
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 32);
    }

    public void onEscapedPressed() {
        level.onEscapePressed();
    }
}
