package entity;

import collision.shapes.Square;
import level.Level;
import math.Vector2;

public class Player extends LivingEntity {

    public Player() {
        super(0, 0);
        setTexture("player");
        shape = new Square(new Vector2(x, y), 64, 64);
    }

    public void update(long delta) {
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 32);
    }
}
