package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class VerticalListView extends MenuComponent {

    private List<MenuComponent> components = new ArrayList<MenuComponent>();

    private int[] listView, scrollButton, focusedSB;

    private int sbWidth = 30, sbHeight = 20;

    private int spacing = 5;

    private int currentComponent;

    private boolean center = true;

    private boolean renderBackground = true;

    private boolean outOfUpperBounds, outOfLowerBounds;

    private boolean upperPressed, upperHolding, lowerPressed, lowerHolding, upperHasFocus, lowerHasFocus;

    /**
     * Creates a ListView at position 0, 0
     *
     * @param components The MenuComponents to be added to this ListView
     */
    public VerticalListView(int id, int width, int height, MenuComponent... components) {
        super(id, width, height);

        for (int i = 0; i < components.length; i++) {
            this.components.add(components[i]);
        }
    }

    /**
     * Creates a ListView at position screenX, screenY
     *
     * @param components The MenuComponents to be added to this ListView
     */
    public VerticalListView(int id, int x, int y, int width, int height, MenuComponent... components) {
        super(id, x, y, width, height);

        listView = MenuComponent.addImage("list_view");
        scrollButton = MenuComponent.addImage("scroll_button");
        focusedSB = MenuComponent.addImage("focused_scroll_button");

        for (int i = 0; i < components.length; i++) {
            this.components.add(components[i]);
        }
        adjustComponents();
    }

    public void update(long delta) {
        outOfUpperBounds = components.size() > 0 && this.screenY > components.get(0).screenY;
        outOfLowerBounds = components.size() > 0 && this.verticalBounds < components.get(components.size() - 1).verticalBounds;

        // Updating visible MenuComponents
        for (int i = 0; i < components.size(); i++) {
            if (isVisible(components.get(i)))
                components.get(i).update(delta);
        }

        int mX = Mouse.getX();
        int mY = Math.abs(Mouse.getY() - Display.getHeight());
        // Upper scrollButton mouseOver
        if (mX > this.screenX && mX < horizontalBounds && mY > this.screenY - sbHeight && mY < this.screenY) {
            upperHasFocus = true;
        } else upperHasFocus = false;
        // Lower scrollButton mouseOver
        if (mX > this.screenX && mX < horizontalBounds && mY > verticalBounds && mY < verticalBounds + sbHeight) {
            lowerHasFocus = true;
        } else lowerHasFocus = false;
    }

    public void press(int x, int y) {
        // upper scrollButton
        if (x > this.screenX && x < horizontalBounds && y > this.screenY - sbHeight && y < this.screenY) {
            upperPressed = upperHolding = true;
        }
        // Lower scrollButton
        else if (x > this.screenX && x < horizontalBounds && y > verticalBounds && y < verticalBounds + sbHeight) {
            lowerPressed = lowerHolding = true;
        } else {
            for (int i = 0; i < components.size(); i++) {
                if (isVisible(components.get(i)))
                    components.get(i).press(x, y);
            }
        }
    }

    public void release(int x, int y) {
        // upper scrollButton
        if (x > this.screenX && x < horizontalBounds && y > this.screenY - sbHeight && y < this.screenY) {
            if (upperPressed && upperHasFocus) {
                moveUp();
            }
            upperPressed = false;
            upperHolding = false;
        }
        // Lower scrollButton
        else if (x > this.screenX && x < horizontalBounds && y > verticalBounds && y < verticalBounds + sbHeight) {
            if (lowerPressed && lowerHasFocus) {
                moveDown();
            }
            lowerPressed = false;
            lowerHolding = false;
        } else {
            for (int i = 0; i < components.size(); i++) {
                if (isVisible(components.get(i)))
                    components.get(i).release(x, y);
            }
        }
    }

    public void onCharPressed(char c) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof TextView)
                components.get(i).onCharPressed(c);
        }
    }

    public void onCharHolding(char c) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof TextView)
                components.get(i).onCharHolding(c);
        }
    }

    private void moveUp() {
        if (outOfLowerBounds) {
            int moveY = 0;
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0)
                    moveY = (components.get(currentComponent).height + spacing);

                component.setPosition(this.screenX + (this.width - component.width) / 2, component.screenY - moveY);
            }
            currentComponent++;
        }
    }

    private void moveDown() {
        if (outOfUpperBounds) {
            int moveY = 0;
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0)
                    moveY = (components.get(currentComponent).height + spacing);

                component.setPosition(this.screenX + (this.width - component.width) / 2, component.screenY + moveY);
            }
            currentComponent--;
        }
    }

    public int getSpacing() {
        return spacing;
    }

    public boolean isVisible(MenuComponent component) {
        return component.screenY > this.screenY && component.verticalBounds < this.verticalBounds;
    }

    /**
     * Adjusts all the components in this list according too topPadding, leftPadding and center.
     */
    protected void adjustComponents() {
        if (center) {
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);

                if (i == 0) component.setPosition(this.screenX + (this.width - component.width) / 2, this.screenY + topPadding);
                else
                    component.setPosition(this.screenX + (this.width - component.width) / 2, components.get(i - 1).getVerticalBounds() + spacing);
            }
        } else {
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0) component.setPosition(this.screenX + leftPadding, this.screenY + topPadding);
                else component.setPosition(this.screenX + leftPadding, components.get(i - 1).getVerticalBounds() + spacing);
            }
        }
    }

    public void addComponent(MenuComponent component) {
        components.add(component);
        adjustComponents();
    }

    public void reloadComponents(MenuComponent... components) {
        this.components.clear();
        for (int i = 0; i < components.length; i++) {
            this.components.add(components[i]);
        }
        adjustComponents();
    }

    public void setOnClickListener(OnClickListener listener) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).setOnClickListener(listener);
        }
    }

    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        adjustComponents();
    }

    public void setRenderBackground(boolean renderBackground) {
        this.renderBackground = renderBackground;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public void setCenter(boolean center) {
        this.center = center;
        adjustComponents();
    }

    public void setTopPadding(int padding) {
        topPadding = padding;
        adjustComponents();
    }

    public void setRightPadding(int padding) {
        // leftPadding controls all horizontal positioning
    }

    public void setBottomPadding(int padding) {
        // topPadding controls all vertical positioning
    }

    public void setLeftPadding(int padding) {
        if (!center) {
            leftPadding = padding;
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0) component.setPosition(this.screenX + leftPadding, this.screenY + topPadding);
                else component.setPosition(this.screenX + leftPadding, components.get(i - 1).getVerticalBounds() + spacing);
            }
        }
    }

    /**
     * Renders the ListView and all of its Components.
     */
    public void render(SpriteBatch batch) {
        if (renderBackground)
            batch.draw(screenX, screenY, width, height, listView[0], listView[1], listView[2], listView[3]);

        // Upper scrollButton
        if (outOfLowerBounds) {
            if (upperHasFocus)
                batch.draw(screenX + (width / 2) - (sbWidth / 2), screenY - sbHeight, sbWidth, sbHeight, focusedSB[0], focusedSB[1], focusedSB[2], focusedSB[3]);
            else
                batch.draw(screenX + (width / 2) - (sbWidth / 2), screenY - sbHeight, sbWidth, sbHeight, scrollButton[0], scrollButton[1], scrollButton[2], scrollButton[3]);
        }

        // Lower scrollButton
        if (outOfUpperBounds) {
            if (lowerHasFocus)
                batch.draw(screenX + (width / 2) - (sbWidth / 2), verticalBounds, sbWidth, sbHeight, focusedSB[0], focusedSB[1], focusedSB[2], focusedSB[3], SpriteBatch.ROTATE_180);
            else
                batch.draw(screenX + (width / 2) - (sbWidth / 2), verticalBounds, sbWidth, sbHeight, scrollButton[0], scrollButton[1], scrollButton[2], scrollButton[3], SpriteBatch.ROTATE_180);
        }

        for (int i = 0; i < components.size(); i++) {
            if (isVisible(components.get(i)))
                components.get(i).render(batch);
        }
    }

    /**
     * Renders all the Strings from the ListView and it's Components
     */
    public void renderString(Font font) {
        for (int i = currentComponent; i < components.size(); i++) {
            if (isVisible(components.get(i)))
                components.get(i).renderString(font);
        }
    }
}
