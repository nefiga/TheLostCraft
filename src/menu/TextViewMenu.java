package menu;

import game.Game;
import game.Screen;
import game.fonts.Font;
import menu.result.Result;

public class TextViewMenu extends Menu{

    private int viewWidth, viewHeight;

    private int viewX, viewY;

    private StringBuilder string = new StringBuilder("");

    public TextViewMenu(int width, int height, int tileSize, int drawSize, String cornerImage, String sideImage, String middleImage) {
        super(width, height, tileSize, drawSize, cornerImage, sideImage, middleImage);
    }

    public void render() {
        super.render();
        menuBatch.begin();
        menuBatch.draw(viewX, viewY, viewWidth, viewHeight, textViewImage[0], textViewImage[1], textViewImage[2], textViewImage[3]);
        menuBatch.end();
        font.begin();
        font.drawString(string.toString(), viewX + 5, viewY + (viewHeight - font.getTextSize()) / 2);
        font.end();
    }

    public void setTextViewSize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
        viewX = width - viewWidth / 2;
        viewY = height - viewHeight / 3 * 2;
    }

    @Override
    public void moveCursorUp() {

    }

    @Override
    public void moveCursorRight() {

    }

    @Override
    public void moveCursorDown() {

    }

    @Override
    public void moveCursorLeft() {

    }

    @Override
    public void charPressed(char c) {
        if (font.getStringWidth(string.toString()) + Font.CHAR_SIZE < viewWidth)
            string.append(c);
    }

    @Override
    public void select() {
        returnResult();
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
        renderCursor = false;
        viewWidth = width * tileSize - 40;
        viewHeight = height * tileSize / 3;
        viewX = x + (width * tileSize - viewWidth) / 2 + 8;
        viewY = y + (height * tileSize - viewHeight);
        Game.openMenu(this);
    }

    @Override
    public void returnResult() {
        result.setString(string.toString());
        screen.returnResult(result);
    }
}
