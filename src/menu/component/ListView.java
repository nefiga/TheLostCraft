package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ListView extends MenuComponent {

    private List<MenuComponent> components = new ArrayList<MenuComponent>();

    public ListView(int id, int width, int height, MenuComponent...components) {
        super(id, width, height);

        for (int i = 0; i < components.length; i++) {
            this.components.add(components[i]);
        }
    }

    public ListView(int id, int x, int y, int width, int height, MenuComponent...components) {
        super(id, x, y, width, height);

        for (int i = 0; i < components.length; i++) {
            this.components.add(components[i]);
        }
    }

    public void addComponent(MenuComponent component) {
        components.add(component);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).render(batch);
        }
    }

    public void renderString(Font font) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).renderString(font);
        }
    }
}
