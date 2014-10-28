package menu;

import game.Game;
import game.Screen;
import menu.result.Result;

import java.util.List;

public class StringMenu extends Menu{

    private Result result;

    private List<String> stringList;

    /**
     * Creates a new menu. The size of the menu can be controlled in two ways.
     * You can control the width and height of the tiles as well as how many tiles used.
     *
     * @param width       The numbers of tiles wide
     * @param height      The number of tiles tall
     * @param tileSize    The width and height of the tiles for this menu
     * @param drawSize    The width and height the tiles will be drawn on screen
     * @param cornerImage The file location for the corner image
     * @param sideImage   The file location for the top, bottom and side image
     * @param middleImage The file location for the middle image
     */
    public StringMenu(int width, int height, int tileSize, int drawSize, String cornerImage, String sideImage, String middleImage) {
        super(width, height, tileSize, drawSize, cornerImage, sideImage, middleImage);
    }

    public void render() {
        super.render();

        font.begin();
        for (int i = 0; i < stringList.size(); i++) {
            String string = stringList.get(i);
            font.drawString(string, insetX[i], insetY[i]);
        }
        font.end();
    }

    @Override
    public void moveCursorUp() {
        currentSelection--;
        if (currentSelection < 0) currentSelection = stringList.size() - 1;
    }

    @Override
    public void moveCursorRight() {

    }

    @Override
    public void moveCursorDown() {
        currentSelection++;
        if (currentSelection > stringList.size() - 1) currentSelection = 0;
    }

    @Override
    public void moveCursorLeft() {

    }

    @Override
    public void select() {
        result.setReturnString(stringList.get(currentSelection));
        result.setSelection(currentSelection);
        returnResultAndClose();
    }

    @Override
    public void back() {

    }

    @Override
    public void openForResult(Result result, Screen screen, int x, int y) {
        this.result = result;
        this.screen = screen;
        this.x = x;
        this.y = y;
        Game.openMenu(this);
        stringList = result.getStrings();
        insetX = new int[stringList.size()];
        insetY = new int[stringList.size()];

        for (int i = 0; i < stringList.size(); i++) {
            insetX[i] = x + (width * drawSize - font.getStringWidth(stringList.get(i))) / 2 + 10;
            insetY[i] = y + i * (font.getTextSize() + verticalSpacing) + 20;
        }

    }

    @Override
    public void returnResult() {
        screen.returnResult(result);
    }

    @Override
    public void returnResultAndClose() {
        Game.closeMenu();
        screen.returnResult(result);
    }
}