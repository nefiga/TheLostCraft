package entity;

public class Player extends LivingEntity{

    public Player(int positionX, int positionY) {
        super(positionX, positionY);
        setTexture("player");
    }

    public void update(long delta) {
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 32);
    }
}
