package game;

import menu.Result;

/**
 * Every class that implements Screen needs to have a public static final String called "NAME".
 * This is used as an easy reference with the ScreenManager Class
 */
public interface Screen {

    public void update(long delta);

    public void render();

    public void returnResult(Result result);

    public void screenResized(int width, int height);
}
