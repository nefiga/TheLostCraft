package entity;

import collision.shapes.Square;
import gear.Stats;
import gear.tool.PickAxe;
import gear.tool.Tool;
import item.part.Bracket;
import item.part.LongHandle;
import item.part.PickHead;
import math.Vector2;
import menu.Menu;

public class Player extends LivingEntity{

    Menu menu;

    public Player(int x, int y) {
        super(x, y);
        setTexture("player");
        shape = new Square(new Vector2(x, y), 64, 64);
        this.currentTool = new PickAxe("Pick Axe", new LongHandle("handle", new Stats(10, 2, 0)), new PickHead("pick_head", new Stats(5, 0, 0)), new Bracket("bracket", new Stats(10, 0, 0)));
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
