package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;

public class TextView extends MenuComponent {

    int[] textView;

    private StringBuilder text = new StringBuilder("");

    public TextView(int id, int width, int height) {
        super(id, width, height);
        textView = MenuComponent.addImage("text_view");
        focus();
    }

    public TextView(int id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        textView = MenuComponent.addImage("text_view");
        focus();
    }

    public void update(long delta) {
    }

    public void render(SpriteBatch batch) {
        batch.draw(x, y, width, height, textView[0], textView[1], textView[2], textView[3]);
    }

    public void renderString(Font font) {
        if (text != null)
            font.drawString(text.toString(), x + (width - text.length() * textSize) / 2, y + (height - textSize) / 2);
    }

    @Override
    public void press(int x, int y) {
        if (inBounds(x, y))
            focus();
        else
            unFocus();
    }

    public void onCharPressed(char c) {
        if (hasFocus) {
            if (c == 8 && text.length() > 0) {
                text.setLength(text.length() - 1);
            } else if (text.length() * textSize < width) {
                text.append(c);
            }
        }
    }

    public void onCharHolding(char c) {
        if (hasFocus) {
            if (c == 8 && text.length() > 0) {
                text.setLength(text.length() - 1);
            } else if (text.length() * textSize + textSize < width) {
                text.append(c);
            }
        }
    }

    public String getString() {
        return text.toString();
    }
}
