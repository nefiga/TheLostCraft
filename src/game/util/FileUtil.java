package game.util;

import game.GameLoop;
import game.graphics.ShaderManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtil {

    /**
     * Returns the entire source of a file as a single String
     */
    public static String readFromFile(String name) {
        StringBuffer source = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderManager.class.getClassLoader().getResourceAsStream(name)));

            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line).append("\n");
            }

            reader.close();
        }
        catch (Exception e) {
            System.err.println("Error loading source code: " + name);
            e.printStackTrace();
            GameLoop.exit();
        }

        return source.toString();
    }

    /**
     * @return An array of all the lines of a file
     */
    public static String[] readAllLines(String name) {
        return readFromFile(name).split("\n");
    }
}
