package menu;

import game.Game;
import game.Screen;

public class StringMenu extends Menu{

    private String[] stringList;

    /**
     * Creates a new menu. The size of the menu can be controlled in two ways.
     * You can control the width and height of the tiles as well as how many tiles used.
     *
     * @param width       The numbers of tiles wide
     * @param height      The number of tiles tall
     * @param drawSize    The width and height the tiles will be drawn on screen
     */
    public StringMenu(int width, int height, int drawSize, int tileSet) {
        super(width, height, drawSize, tileSet);
    }

    public void render() {
        super.render();

        font.begin();
        for (int i = 0; i < stringList.length; i++) {
            String string = stringList[i];
            font.drawString(string, insetX[i], insetY[i]);
        }
        font.end();
    }

    @Override
    public void click(int button, int x, int y) {

    }

    @Override
    public void release(int button, int x, int y) {

    }

    @Override
    public void moveCursorUp() {
        currentSelection--;
        if (currentSelection < 0) currentSelection = stringList.length - 1;
    }

    @Override
    public void moveCursorRight() {

    }

    @Override
    public void moveCursorDown() {
        currentSelection++;
        if (currentSelection > stringList.length - 1) currentSelection = 0;
    }

    @Override
    public void moveCursorLeft() {

    }

    @Override
    public void charPressed(char c) {

    }

    @Override
    public void charHolding(char c) {

    }

    @Override
    public void select() {
        result.setReturnString(stringList[currentSelection]);
        result.setSelection(currentSelection);
        returnResult();
    }

    @Override
    public void back() {

    }

    @Override
    public void screenResized(int width, int height) {

    }

    @Override
    public void openForResult(Result result, Screen screen, int x, int y) {
        this.result = result;
        this.screen = screen;
        this.x = x;
        this.y = y;
        Game.openMenu(this);
        stringList = result.getStrings();
        insetX = new int[stringList.length];
        insetY = new int[stringList.length];

        for (int i = 0; i < stringList.length; i++) {
            insetX[i] = x + (width * drawSize - font.getStringWidth(stringList[i])) / 2 + 10;
            insetY[i] = y + i * (font.getTextSize() + verticalSpacing) + 20;
        }
        if (stringList.length < 1) renderCursor = false;
    }

    @Override
    public void open(int x, int y) {

    }

    @Override
    public void returnResult() {
        screen.returnResult(result);
    }
}
