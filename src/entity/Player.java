package entity;

import game.Game;
import game.graphics.SpriteBatcher;

public class Player extends LivingEntity{

    public Player(int positionX, int positionY, int textureX, int textureY, int textureWidth, int textureHeight) {
        super(positionX, positionY, textureX, textureY, textureWidth, textureHeight);
    }

    public void update(long delta) {
    }

    public void render(SpriteBatcher batcher) {
        batcher.draw(x - Game.getXOffset(), y - Game.getYOffset(), width, height, textureX, textureY + (direction * height));
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[direction], (int) y + interactY[direction], 10, 32);
    }
}
