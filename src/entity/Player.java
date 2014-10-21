package entity;

import collision.shapes.Square;
import math.Vector2;

public class Player extends LivingEntity {

    public Player(int positionX, int positionY) {
        super(positionX, positionY);
        setTexture("player");
        shape = new Square(new Vector2(x, y), 64, 64);
    }

    public void update(long delta) {
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 32);
    }
}
