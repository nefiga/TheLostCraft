package menu;

import game.Game;
import game.GameLoop;
import game.Screen;
import game.fonts.Font;
import menu.component.ListView;
import menu.component.MenuComponent;
import menu.component.MenuComponent.OnClickListener;
import menu.component.Button;

public class MainMenu extends Menu implements OnClickListener {

    private Game game;
    private Button[] bTest = new Button[1];
    private ListView listView = new ListView(101, 300, 300, 300, 200, new Button("test", 0, 150, 50), new Button("longnesss", 0, 250, 50), new Button("strings", 0, 200, 50));

    private String string = "";

    private String[] currentList;
    private String[] savedGames;
    private String[] savedMaps;
    private String[] options = new String[]{"load game", "new game", "load map", "new map", "exit game"};

    private int viewWidth, viewHeight, viewX, viewY;

    private int state;

    public MainMenu(int width, int height, int drawSize, int tileSet, String[] savedGames, String[] savedMaps, Game game) {
        super(width, height, drawSize, tileSet);
        this.savedGames = savedGames;
        this.savedMaps = savedMaps;
        this.game = game;
        currentList = options;
        listView.setTopPadding(10);
        listView.setSpacing(5);
        listView.setCenter(true);
        for (int i = 0; i < bTest.length; i++) {
            bTest[i] = new Button(i, 100, 100, 150, 30);
            bTest[i].setOnClickListener(this);
            bTest[i].setText("testing");
            bTest[i].setPadding(10);
            bTest[i].setTextSize(20);
        }

    }

    public void update(long delta) {
        for (int i = 0; i < bTest.length; i++) {
            bTest[i].update(delta);
        }
        listView.update(delta);
    }

    public void render() {
        super.render();
        font.setTextSize(25);
        switch (state) {
            // Options
            case 0:
                font.begin();
                for (int i = 0; i < currentList.length; i++) {
                    String string = currentList[i];
                    font.drawString(string, insetX[i], insetY[i]);
                }
                font.end();
                break;
            // Load Game
            case 1:
                font.begin();
                for (int i = 0; i < currentList.length; i++) {
                    String string = currentList[i];
                    font.drawString(string, insetX[i], insetY[i]);
                }
                font.end();
                break;
            // New Game
            case 2:
                font.begin();
                for (int i = 0; i < currentList.length; i++) {
                    String string = currentList[i];
                    font.drawString(string, insetX[i], insetY[i]);
                }
                font.end();
                break;
            // Load Map
            case 3:
                font.begin();
                for (int i = 0; i < currentList.length; i++) {
                    String string = currentList[i];
                    font.drawString(string, insetX[i], insetY[i]);
                }
                font.end();
                break;
            // New Map
            case 4:
                menuBatch.begin();
                menuBatch.draw(viewX, viewY, viewWidth, viewHeight, textViewImage[0], textViewImage[1], textViewImage[2], textViewImage[3]);
                menuBatch.end();

                font.begin();
                font.drawString(string, viewX + 5, viewY + (viewHeight - font.getTextSize()) / 2);
                font.end();
                break;
            // Name New Game
            case 5:
                menuBatch.begin();
                menuBatch.draw(viewX, viewY, viewWidth, viewHeight, textViewImage[0], textViewImage[1], textViewImage[2], textViewImage[3]);
                menuBatch.end();

                font.begin();
                font.drawString(string, viewX + 5, viewY + (viewHeight - font.getTextSize()) / 2);
                font.end();
                break;
        }

        MenuComponent.batch.begin();
        for (int i = 0; i < bTest.length; i++) {
            bTest[i].render(MenuComponent.batch);
        }
        listView.render(MenuComponent.batch);
        MenuComponent.batch.end();
        Font.generalFont.begin();
        for (int i = 0; i < bTest.length; i++) {
            bTest[i].renderString(Font.generalFont);
        }
        listView.renderString(font);
        Font.generalFont.end();
    }

    @Override
    public void click(int button, int x, int y) {
        if (button == 0) {
            for (int i = 0; i < bTest.length; i++) {
                    bTest[i].press(x, y);
            }
            listView.press(x, y);
        }
    }

    @Override
    public void release(int button, int x, int y) {
        if (button == 0) {
            for (int i = 0; i < bTest.length; i++) {
                    bTest[i].release(x, y);
            }
            listView.release(x, y);
        }
    }

    @Override
    public void moveCursorUp() {
        currentSelection--;
        if (currentSelection < 0) currentSelection = currentList.length - 1;
    }

    @Override
    public void moveCursorRight() {

    }

    @Override
    public void moveCursorDown() {
        currentSelection++;
        if (currentSelection > currentList.length - 1) currentSelection = 0;
    }

    @Override
    public void moveCursorLeft() {

    }

    @Override
    public void charPressed(char c) {
        if (state == 4 || state == 5) {
            // BackSpace
            if (c == 8 && string.length() > 0) string = string.substring(0, string.length() - 1);
            else if (font.getStringWidth(string) + Font.CHAR_SIZE < viewWidth)
                string += c;
        }
    }

    @Override
    public void charHolding(char c) {
        if (state == 4 || state == 5) {
            // BackSpace
            if (c == 8 && string.length() > 0) string = string.substring(0, string.length() - 1);
            else if (font.getStringWidth(string) + Font.CHAR_SIZE < viewWidth)
                string += c;
        }
    }

    @Override
    public void select() {
        switch (state) {
            case 0:
                switch (currentSelection) {
                    // Load game
                    case 0:
                        state = 1;
                        currentList = savedGames;
                        setInsets();
                        break;
                    // New game
                    case 1:
                        state = 2;
                        currentList = savedMaps;
                        setInsets();
                        break;
                    // Load Map
                    case 2:
                        state = 3;
                        currentList = savedMaps;
                        setInsets();
                        break;
                    // New Map
                    case 3:
                        string = "";
                        state = 4;
                        renderCursor = false;
                        break;
                    // Exit Game
                    case 4:
                        GameLoop.end();
                        break;
                }
                break;
            //Load Game
            case 1:
                if (currentList.length > 0) {
                    Game.closeMenu();
                    game.loadGame(currentList[currentSelection]);
                }
                break;
            //New Game
            case 2:
                if (currentList.length > 0) {
                    string = "";
                    renderCursor = false;
                    state = 5;
                }
                break;
            // Load Map
            case 3:
                if (currentList.length > 0) {
                    Game.closeMenu();
                    game.loadMapEditor(currentList[currentSelection]);
                }
                break;
            // New Map
            case 4:
                if (string.length() > 0) {
                    Game.closeMenu();
                    string = string.substring(0, string.length() - 1);
                    game.loadNewMap(string);
                }
                break;
            // Name New Game
            case 5:
                Game.closeMenu();
                string = string.substring(0, string.length() - 1);
                game.loadNewGame(string, currentList[currentSelection]);
                break;
        }
    }

    @Override
    public void back() {
        switch (state) {
            case 0:
                // Do nothing
                break;
            case 1:
            case 2:
            case 3:
                state = 0;
                currentList = options;
                setInsets();
            case 4:
                state = 0;
                currentList = options;
                setInsets();
                renderCursor = true;
                break;
            case 5:
                state = 2;
                currentList = savedMaps;
                setInsets();
                renderCursor = true;
                break;
        }
    }

    @Override
    public void screenResized(int width, int height) {

    }

    @Override
    public void openForResult(Result result, Screen screen, int x, int y) {
    }

    @Override
    public void open(int x, int y) {
        this.x = x;
        this.y = y;
        Game.openMenu(this);
        setInsets();
        viewWidth = width * tileSize - 40;
        viewHeight = height * tileSize / 3;
        viewX = x + (width * tileSize - viewWidth) / 2 + 8;
        viewY = y + (height * tileSize - viewHeight);
    }

    private void setInsets() {
        currentSelection = 0;
        insetX = new int[currentList.length];
        insetY = new int[currentList.length];

        for (int i = 0; i < currentList.length; i++) {
            insetX[i] = x + (width * drawSize - font.getStringWidth(currentList[i])) / 2 + 10;
            insetY[i] = y + i * (font.getTextSize() + verticalSpacing) + 20;
        }
        if (currentList.length < 1) renderCursor = false;
    }

    @Override
    public void returnResult() {
        screen.returnResult(result);
    }

    @Override
    public void onClick(MenuComponent c) {
        for (int i = 0; i < bTest.length; i++) {
            if (c.getId() == bTest[i].getId()) {
            }
        }
    }
}
