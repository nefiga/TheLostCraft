package game;

import game.graphics.*;
import game.util.GameData;
import menu.*;
import menu.component.*;
import menu.component.MenuComponent.OnClickListener;
import org.lwjgl.opengl.Display;

public class MainScreen implements Screen, OnClickListener{

    public TextureAtlas atlas = new TextureAtlas(TextureAtlas.SMALL);
    public SpriteBatch batch;
    private Game game;

    public static final String NAME = "MainMenu";

    private final int LOAD_GAME = 0, NEW_GAME = 1, LOAD_MAP = 2, NEW_MAP = 3, EXIT_GAME = 4, CANCEL = 5, OK_NEW_GAME = 6, OK_NEW_MAP = 7;

    private TextView textView;
    private Button[] options;
    private VerticalListView verticalListView;
    private HorizontalListView horizontalListView;

    private StringComponent[] savedMaps = new StringComponent[GameData.MAX_SAVES];

    private StringComponent[] savedGames = new StringComponent[GameData.MAX_SAVES];


    private int[] background;

    public MainScreen(Game game) {
        this.game = game;
        background = atlas.addTexture("/menu/main_background");
        batch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1000);
    }

    public void load(String[] savedGames, String[] savedMaps) {
        for (int i = 0; i < savedGames.length; i++) {
            this.savedGames[i] = new StringComponent(savedGames[i], i + 10, 0, 0);
        }
        for (int i = 0; i < savedMaps.length; i++) {
            this.savedMaps[i] = new StringComponent(savedMaps[i], i + 20, 0, 0);
        }
        loadMenuComponents();
    }

    public void loadMenuComponents() {
        textView = new TextView(5, 225, 30);

        options = new Button[]{new Button("load game", LOAD_GAME, 250, 40), new Button("new game", NEW_GAME, 250, 40),
                new Button("load map", LOAD_MAP, 250, 40), new Button("new map", NEW_MAP, 250, 40), new Button("exit game", EXIT_GAME, 250, 40)};

        verticalListView = new VerticalListView(-1, 0, 0, 300, 295);
        verticalListView.setTopPadding(15);
        verticalListView.setSpacing(15);
        verticalListView.reloadComponents(options);
        verticalListView.setOnClickListener(this);

        horizontalListView = new HorizontalListView(-1, 300, 30);
        horizontalListView.setLeftPadding(16);
        horizontalListView.setSpacing(15);
        horizontalListView.setRenderBackground(false);
        horizontalListView.setOnClickListener(this);

        SingleComponentMenu menu = new SingleComponentMenu(Display.getWidth() / 2 - 200, Display.getHeight() / 2 - 300, 25, 15, 16, 0,  verticalListView);
        menu.open();
    }

    @Override
    public void update(long delta) {
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(0, 0, Display.getWidth(), Display.getHeight(), background[0], background[1], background[2], background[3]);
        batch.end();
    }

    @Override
    public void returnResult(Result result) {

    }

    @Override
    public void screenResized(int width, int height) {

    }

    boolean loadMap;
    @Override
    public void onClick(MenuComponent c) {
        switch (c.getId()) {
            case LOAD_GAME:
                verticalListView.setTopPadding(20);
                verticalListView.setSpacing(20);
                verticalListView.reloadComponents(savedGames);
                verticalListView.addComponent(new Button("cancel", CANCEL, 175, 30));
                verticalListView.setOnClickListener(this);
                break;
            case NEW_GAME:
                textView.clear();
                verticalListView.reloadComponents(textView);
                horizontalListView.reloadComponents(new Button("ok", OK_NEW_GAME, 75, 30), new Button("cancel", CANCEL, 175, 30));
                verticalListView.addComponent(horizontalListView);
                verticalListView.setOnClickListener(this);
                loadMap = false;
                break;
            case LOAD_MAP:
                verticalListView.setTopPadding(20);
                verticalListView.setSpacing(20);
                verticalListView.reloadComponents(savedMaps);
                verticalListView.addComponent(new Button("cancel", CANCEL, 175, 30));
                verticalListView.setOnClickListener(this);
                loadMap = true;
                break;
            case NEW_MAP:
                textView.clear();
                verticalListView.reloadComponents(textView);
                horizontalListView.reloadComponents(new Button("ok", OK_NEW_MAP, 75, 30), new Button("cancel", CANCEL, 175, 30));
                verticalListView.addComponent(horizontalListView);
                verticalListView.setOnClickListener(this);
                break;
            case EXIT_GAME:
                GameLoop.exit();
                break;
            case CANCEL:
                verticalListView.setSpacing(15);
                verticalListView.setTopPadding(15);
                verticalListView.reloadComponents(options);
                verticalListView.setOnClickListener(this);
                break;
            case OK_NEW_GAME:
                verticalListView.setTopPadding(20);
                verticalListView.setSpacing(20);
                verticalListView.reloadComponents(savedMaps);
                verticalListView.addComponent(new Button("cancel", CANCEL, 175, 30));
                verticalListView.setOnClickListener(this);
                break;
            case OK_NEW_MAP:
                game.loadNewMap(textView.getString());
                Game.closeMenu();
                break;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                String gameString = ((StringComponent) c).getString();
                if (!gameString.equals("empty")){
                    game.loadGame(gameString);
                    Game.closeMenu();
                }
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                String mapString = ((StringComponent)c).getString();
                if (!mapString.equals("empty")){
                    if (loadMap) {
                        game.loadMapEditor(mapString);
                        Game.closeMenu();
                    } else {
                        game.loadNewGame(textView.getString(), mapString);
                        Game.closeMenu();
                    }
                }
                break;

        }
    }
}
