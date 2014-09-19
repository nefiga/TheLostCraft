package game.graphics;

public class Animation {

    int width, height;
    int textureX, textureY;
    int numSprites;
    int life;

    int currentSprite;
    int timer;

    public Animation(int textureX, int textureY, int width, int height, int numSprites, int life) {
        this.textureX = textureX;
        this.textureY = textureY;
        this.width = width;
        this.height = height;
        this.numSprites = numSprites;
        this.life = life;
        currentSprite = 0;
        timer = 0;
    }

    public void update(long delta) {
        timer++;
        if (timer >= life) {
            changeSprite();
            timer = 0;
        }
    }

    private void changeSprite() {
        currentSprite++;
        if (currentSprite > numSprites) currentSprite = 0;
    }

    public void render(SpriteBatch batcher, int x, int y) {
        batcher.draw(x, y, width, height, textureX + (width * currentSprite), textureY);
    }
}
