package game.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageManager {

    public static BufferedImage getImage(String fileName) {
        try {
            BufferedImage image = ImageIO.read(ImageManager.class.getResourceAsStream(fileName + ".png"));
            return image;
        } catch (IOException e) {
            throw new RuntimeException("Image did not load: " + fileName);
        }
    }

    public static BufferedImage combineImages(BufferedImage... images) {
        int width = images[0].getWidth();
        int heigth = images[0].getHeight();
        BufferedImage returnImage = new BufferedImage(width, heigth, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics g = returnImage.getGraphics();
        for (int i = 0; i < images.length; i++) {
            g.drawImage(images[i], 0, 0, null);
        }

        return returnImage;
    }
}
