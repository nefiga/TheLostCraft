package game.util;

import java.io.*;

public class FileIO {

    public static void saveClass(String fileName, Object classObject) {
        try {
            FileOutputStream f_out = new FileOutputStream("saves" + File.separator + fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(f_out);
            objectOut.writeObject(classObject);
            objectOut.close();
        } catch (IOException e) {
            System.out.println("Could not serialize class");
            e.printStackTrace();

        }
    }

    public static Object loadClass(String fileName) {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("saves/" + fileName));
            Object returnObject = objectIn.readObject();
            objectIn.close();
            return returnObject;
        } catch (Exception e) {
            System.out.println("Could not load serialized class");
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getSavedGames() {
        File folder = new File("saves" + File.separator + "games");
        File[] files = folder.listFiles();
        String[] strings;
        if (files != null){
            strings = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    strings[i] = files[i].getName();
                }
            }
            return strings;
        }
        return null;
    }

    public static String[] getSavedMaps() {
        File folder = new File("saves" + File.separator + "maps");
        File[] files = folder.listFiles();
        String[] strings;
        if (files != null){
            strings = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    strings[i] = files[i].getName();
                }
            }
            return strings;
        }
        return null;
    }
}
