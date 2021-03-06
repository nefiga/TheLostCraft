package menu.component;

import game.fonts.Font;
import game.graphics.SpriteBatch;
import input.InputReceiver;

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
        batch.draw(screenX, screenY, width, height, textView[0], textView[1], textView[2], textView[3]);
    }

    public void renderString(Font font) {
        if (text.length() > 0)
            font.drawString(text.toString(), screenX + (width - text.length() * textSize) / 2, screenY + (height - textSize) / 2);
    }

    public void clear() {
        text.setLength(0);
    }

    @Override
    public void press(int button, int x, int y) {
        if (inBounds(x, y) && button == InputReceiver.MOUSE_LEFT_BUTTON)
            focus();
        else
            unFocus();
    }

    public void onCharPressed(char c) {
        if (hasFocus) {
            if (c == 8 && text.length() > 0) {
                text.setLength(text.length() - 1);
            } else if (text.length() * textSize < width && Font.isPrintableChar(c)) {
                text.append(c);
            }
        }
    }

    public void onCharHolding(char c) {
        if (hasFocus) {
            if (c == 8 && text.length() > 0) {
                text.setLength(text.length() - 1);
            } else if (text.length() * textSize + textSize < width && Font.isPrintableChar(c)) {
                text.append(c);
            }
        }
    }

    public String getString() {
        return text.toString();
    }
}
