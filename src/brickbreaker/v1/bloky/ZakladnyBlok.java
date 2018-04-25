package brickbreaker.v1.bloky;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Fidek
 */
public class ZakladnyBlok extends Blok implements IBlok {
    private final Image blockImage;

    /**
     * Konštruktor
     * @param x
     * @param y
     */
    public ZakladnyBlok(int x, int y) {
        super(x, y, 1, 12);
        this.blockImage = new ImageIcon(this.getClass().getResource("blokmodry.png")).getImage();
    }

    /**
     * Metóda vzkresluje obrázok blockImage(modrý blok) s rozmerom a umiestnením bloku
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(this.blockImage, this.getX(), this.getY(), this.getSirka(), this.getVyska(), null);
    }
}
