package game;

import entity.Player;
import input.Input;
import input.PlayerInput;
import level.Level;
import level.LevelManager;
import level.RandomMapGenerator;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Game extends GameLoop{

    LevelManager levelManager;
    Player player;
    Input playerInput;

    private static int xOffset, yOffset;

    public void init() {

        player = new Player(200, 200, 0, 0, 64, 64);
        playerInput = new PlayerInput(player);

        levelManager = new LevelManager();
        levelManager.addLevel("Testing", new Level(RandomMapGenerator.generateMap(1000), player));
        levelManager.setCurrentLevel("Testing");
    }

    public void update(long delta) {
        playerInput.update();
        xOffset = (int) (player.getX() - (Display.getWidth() / 2 -32));
        yOffset = (int) player.getY() - (Display.getHeight() / 2 -32);
        levelManager.update(delta);
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        levelManager.render();
    }

    public static int getXOffset() {
        return xOffset;
    }

    public static int getYOffset() {
        return yOffset;
    }

    public static int tileToPixel(int tile) {
        return tile << 6;
    }

    public static int pixelToTile(int pixel) {
        return pixel >> 6;
    }

    public static void main(String[] args) {
        GameLoop game = new Game();
    }
}
