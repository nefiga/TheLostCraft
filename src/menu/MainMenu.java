package menu;

import game.Game;
import game.Screen;
import game.fonts.Font;
import menu.component.*;
import menu.component.MenuComponent.OnClickListener;
import org.lwjgl.opengl.Display;

public class MainMenu extends Menu implements OnClickListener {

    private Game game;

    private final int LOAD_GAME = 0, NEW_GAME = 1, LOAD_MAP = 2, NEW_MAP = 3, EXIT_GAME = 4, CANCEL = 5, OK_NEW_GAME = 6, OK_NEW_MAP = 7;

    private boolean loadNewGame, loadMap, loadGame;

    private TextView textView;
    private Button[] options;
    private VerticalListView verticalListView;
    private HorizontalListView horizontalListView;

    private String string = "";

    private StringComponent[] savedGames;
    private StringComponent[] savedMaps;

    public MainMenu(int width, int height, int drawSize, int tileSet, String[] savedGames, String[] savedMaps, Game game) {
        super(width, height, drawSize, tileSet);
        this.savedGames = new StringComponent[savedGames.length];
        this.savedMaps = new StringComponent[savedMaps.length];
        for (int i = 0; i < savedGames.length; i++) {
            this.savedGames[i] = new StringComponent(savedGames[i], i, 0, 0);
        }
        for (int i = 0; i < savedMaps.length; i++) {
            this.savedMaps[i] = new StringComponent(savedMaps[i], i, 0, 0);
        }
        this.game = game;
        loadMenuComponents();
    }

    public void loadMenuComponents() {
        textView = new TextView(5, 225, 30);

        options = new Button[]{new Button("load game", LOAD_GAME, 250, 40), new Button("new game", NEW_GAME, 250, 40),
                new Button("load map", LOAD_MAP, 250, 40), new Button("new map", NEW_MAP, 250, 40), new Button("exit game", EXIT_GAME, 250, 40)};

        verticalListView = new VerticalListView(-1, Display.getWidth() / 2 - 150, Display.getHeight() / 2 - 300, 300, 295);
        verticalListView.setTopPadding(15);
        verticalListView.setSpacing(15);
        verticalListView.reloadComponents(options);
        verticalListView.setOnClickListener(this);

        horizontalListView = new HorizontalListView(-1, 300, 30, new Button("ok", OK_NEW_GAME, 75, 30), new Button("cancel", CANCEL, 175, 30));
        horizontalListView.setLeftPadding(16);
        horizontalListView.setSpacing(15);
        horizontalListView.setRenderBackground(false);
        horizontalListView.setOnClickListener(this);
    }

    public void update(long delta) {
        verticalListView.update(delta);
    }

    public void render() {
        MenuComponent.batch.begin();
        verticalListView.render(MenuComponent.batch);
        MenuComponent.batch.end();

        Font.generalFont.begin();
        verticalListView.renderString(font);
        Font.generalFont.end();
    }

    @Override
    public void onMouseButtonPressed(int button, int x, int y) {
        if (button == 0) {
            verticalListView.press(x, y);
        }
    }

    @Override
    public void release(int button, int x, int y) {
        if (button == 0) {
            verticalListView.release(x, y);
        }
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
        textView.onCharPressed(c);
    }

    @Override
    public void charHolding(char c) {
        textView.onCharHolding(c);
    }

    @Override
    public void select() {
    }

    @Override
    public void back() {
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
    }

    @Override
    public void returnResult() {
        screen.returnResult(result);
    }

    @Override
    public void onClick(MenuComponent c) {
        if (loadGame) loadGame();
        if (loadMap) loadMap();
        if (loadNewGame) loadNewGame();
        switch (c.getId()) {
            case LOAD_GAME:
                verticalListView.reloadComponents(savedGames);
                break;
            case NEW_GAME:
                verticalListView.reloadComponents(textView);
                verticalListView.addComponent(horizontalListView);
                verticalListView.setOnClickListener(this);
                break;
            case LOAD_MAP:
                verticalListView.reloadComponents(savedMaps);
                verticalListView.setOnClickListener(this);
                break;
            case NEW_MAP:
                verticalListView.reloadComponents(textView);
                verticalListView.addComponent(horizontalListView);
                verticalListView.setOnClickListener(this);
                break;
            case EXIT_GAME:

                break;
            case CANCEL:
                verticalListView.reloadComponents(options);
                verticalListView.setOnClickListener(this);
                break;
            case OK_NEW_GAME:
                string = textView.getString();
                verticalListView.reloadComponents(savedMaps);
                verticalListView.setOnClickListener(this);
                break;
            case OK_NEW_MAP:
                string = textView.getString();
                break;
        }
    }

    private void loadNewGame() {

    }

    private void loadMap() {

    }

    public void loadGame() {

    }
}
