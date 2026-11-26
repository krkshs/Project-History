import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/** Makes one reversible test edit to an exported alpha mask. */
public final class AssetEditProbe {
    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        BufferedImage image = ImageIO.read(file);
        int color = image.getRGB(0, 0);
        int alpha = (color >>> 24) == 127 ? 128 : 127;
        image.setRGB(0, 0, (alpha << 24) | (color & 0x00ffffff));
        if (!ImageIO.write(image, "png", file)) throw new IllegalStateException("PNG encoder unavailable");
    }
}
