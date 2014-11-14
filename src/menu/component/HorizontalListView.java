package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class HorizontalListView extends MenuComponent{

   private List<MenuComponent> components = new ArrayList<MenuComponent>();

    private int[] listView, scrollButton, focusedSB;

    private int sbWidth = 20, sbHeight = 30;

    private int spacing = 5;

    private int currentComponent;

    private boolean center = true;

    private boolean renderBackground = true;

    private boolean outOffBoundsLeft, outofBoundsRight;

    private boolean leftPressed, leftHolding, leftHasFocus, rightPressed, rightHolding, rightHasFocus;


    public HorizontalListView(int id, int width, int height, MenuComponent... components) {
        super(id, width, height);

        listView = MenuComponent.addImage("list_view");
        scrollButton = MenuComponent.addImage("scroll_button");
        focusedSB = MenuComponent.addImage("focused_scroll_button");

        for (int i = 0; i < components.length; i++) {
            this.components.add(components[i]);
        }
    }

    public HorizontalListView(int id, int x, int y, int width, int height, MenuComponent... components) {
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
        outOffBoundsLeft = components.size() > 0 && this.x > components.get(0).x;
        outofBoundsRight = components.size() > 0 && this.horizontalBounds < components.get(components.size() - 1).horizontalBounds;

        // Updating visible MenuComponents
        for (int i = 0; i < components.size(); i++) {
            if (isVisible(components.get(i)))
                components.get(i).update(delta);
        }

        int mX = Mouse.getX();
        int mY = Math.abs(Mouse.getY() - Display.getHeight());
        // Left scrollButton mouseOver
        if (mY > this.y && mY < this.verticalBounds && mX < this.x && mX > this.x - sbWidth) {
            leftHasFocus= true;
        } else leftHasFocus = false;
        // Right scrollButton mouseOver
        if (mY > this.y && mY < this.verticalBounds && mX > this.horizontalBounds && mX < this.horizontalBounds + sbWidth) {
            rightHasFocus = true;
        } else rightHasFocus = false;
    }

    public void press(int x, int y) {
        // Left scrollButton
        if (x > this.x - sbWidth && x < this.x && y > this.y && y < this.verticalBounds) {
            leftPressed = leftHolding = true;
        }
        // Right scrollButton
        else if (x > this.horizontalBounds && x < this.horizontalBounds + sbWidth && y > this.y && y < this.verticalBounds) {
            rightPressed = rightHolding = true;
        } else {
            for (int i = 0; i < components.size(); i++) {
                if (isVisible(components.get(i)))
                    components.get(i).press(x, y);
            }
        }
    }

    public void release(int x, int y) {
        // Left scrollButton
        if (x > this.x - sbWidth && x < this.x && y > this.y && y < this.verticalBounds) {
            if (leftPressed && leftHasFocus) {
                moveLeft();
            }
            leftPressed = false;
            leftHolding = false;
        }
        // Right scrollButton
        else if (x > this.horizontalBounds && x < this.horizontalBounds + sbWidth && y > this.y && y < this.verticalBounds) {
            if (rightPressed && rightHasFocus) {
                moveRight();
            }
            rightPressed = false;
            rightHolding = false;
        } else {
            for (int i = 0; i < components.size(); i++) {
                if (isVisible(components.get(i)))
                    components.get(i).release(x, y);
            }
        }
    }

    private void moveLeft() {
        if (outofBoundsRight) {
            int moveX = 0;
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0)
                    moveX = (components.get(currentComponent).width + spacing);

                component.setPosition(component.x - moveX,  y + (this.height - component.getHeight()) / 2);
            }
            currentComponent++;
        }
    }

    private void moveRight() {
        if (outOffBoundsLeft) {
            int moveX = 0;
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0)
                    moveX = (components.get(currentComponent).width + spacing);

                component.setPosition(component.x + moveX,  y + (this.height - component.getHeight()) / 2);
            }
            currentComponent--;
        }
    }

    public int getSpacing() {
        return spacing;
    }

    public boolean isVisible(MenuComponent component) {
        return component.x > this.x && component.horizontalBounds < this.horizontalBounds;
    }

    /**
     * Adjusts all the components in this list according too topPadding, leftPadding and center.
     */
    private void adjustComponents() {
        if (center) {
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);

                if (i == 0) component.setPosition(this.x + leftPadding, y + (this.height - component.getHeight()) / 2);
                else
                    component.setPosition(components.get(i - 1).getHorizontalBounds() + spacing, y + (this.height - component.getHeight()) / 2);
            }
        } else {
            for (int i = 0; i < components.size(); i++) {
                MenuComponent component = components.get(i);
                if (i == 0) component.setPosition(this.x + leftPadding, this.y + topPadding);
                else component.setPosition(components.get(i - 1).getHorizontalBounds() + spacing, this.y + topPadding);
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
        if (!center) {
            topPadding = padding;
            adjustComponents();
        }
    }

    public void setRightPadding(int padding) {
        // leftPadding controls all horizontal positioning
    }

    public void setBottomPadding(int padding) {
        // topPadding controls all vertical positioning
    }

    public void setLeftPadding(int padding) {
        leftPadding = padding;
        adjustComponents();
    }

    /**
     * Renders the ListView and all of its Components.
     */
    public void render(SpriteBatch batch) {
        if (renderBackground)
            batch.draw(x, y, width, height, listView[0], listView[1], listView[2], listView[3]);

        // Left scrollButton
        if (outOffBoundsLeft) {
            if (rightHasFocus)
                batch.draw(horizontalBounds, y + (height / 2) - (sbHeight / 2), sbWidth, sbHeight, focusedSB[0], focusedSB[1], focusedSB[2], focusedSB[3], SpriteBatch.ROTATE_90);
            else
                batch.draw(horizontalBounds, y + (height / 2) - (sbHeight / 2), sbWidth, sbHeight, scrollButton[0], scrollButton[1], scrollButton[2], scrollButton[3], SpriteBatch.ROTATE_90);
        }

        // Right scrollButton
        if (outofBoundsRight) {
            if (leftHasFocus)
                batch.draw(x - sbWidth, y + (height / 2) - (sbHeight / 2), sbWidth, sbHeight, focusedSB[0], focusedSB[1], focusedSB[2], focusedSB[3], SpriteBatch.ROTATE_270);
            else
                batch.draw(x - sbWidth, y + (height / 2) - (sbHeight / 2), sbWidth, sbHeight, scrollButton[0], scrollButton[1], scrollButton[2], scrollButton[3], SpriteBatch.ROTATE_270);
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
