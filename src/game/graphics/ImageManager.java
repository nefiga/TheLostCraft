package game.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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

    public static int combineColors(int c1, int c2, int c3) {
        System.out.println(c1 + "   " + c2 + "  " + c3);
        int a1 = c1 >> 24 & 0xff;
        int r1 = c1 >> 16 & 0xff;
        int g1 = c1 >> 8 & 0xff;
        int b1 = c1 & 0xff;

        int a2 = c2 >> 24 & 0xff;
        int r2 = c2 >> 16 & 0xff;
        int g2 = c2 >> 8 & 0xff;
        int b2 = c2 & 0xff;

        int a3 = c3 >> 24 & 0xff;
        int r3 = c3 >> 16 & 0xff;
        int g3 = c3 >> 8 & 0xff;
        int b3 = c3 & 0xff;

        int a = a1 + a2 + a3;
        int r = r1 + r2 + r3;
        int g = g1 + g2 + g3;
        int b = b1 + b2 + b3;

        if (r > 0 || g >  0 || b > 0) {
            a = 0;
        }

        return (a << 24 | r << 16 | g << 8 | b);
    }
}
