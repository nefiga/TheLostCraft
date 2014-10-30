package game.util;

import java.io.*;

public class FileIO {

    public static void saveClass(String fileName, Object classObject) {
        try {
            FileOutputStream f_out = new FileOutputStream("saves/" + fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(f_out);
            objectOut.writeObject(classObject);
        }
        catch (IOException e) {
            System.out.println("Could not serialize class");
            e.printStackTrace();
        }
    }

    public static Object loadClass(String fileName) {
        try {
            ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("saves/" + fileName));
            return objectIn.readObject();
        }
        catch (Exception e) {
            System.out.println("Could not load serialized class");
            e.printStackTrace();
        }
        return null;
    }
}
