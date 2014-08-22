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
        batcher.draw(x - Game.getXOffset(), y - Game.getYOffset(), width, height, textureX, textureY + (dir * height));
    }

    public void interactWith() {
        level.interact(this, currentTool, (int) x + interactX[dir], (int) y + interactY[dir], 10, 32);
    }
}
