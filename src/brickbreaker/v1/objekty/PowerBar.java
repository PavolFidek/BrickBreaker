package brickbreaker.v1.objekty;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Fidek
 */
public class PowerBar {
    private int x;
    private int y;
    private int sirka;
    private int vyska;
    private int aSIRKA;
    private int aVYSKA;
    private boolean lavo = false;
    private boolean pravo = false;
    private final Image paddleImage;

    /**
     * Konštruktor
     * @param j
     */
    public PowerBar(JPanel j) {
        this.paddleImage = new ImageIcon(this.getClass().getResource("paddle.png")).getImage();
        this.sirka = 150;
        this.vyska = 20;
        this.aSIRKA = j.getWidth();
        this.aVYSKA = j.getHeight();
        this.y = (j.getHeight() - 30);
        this.x = (this.aSIRKA / 2) - (this.sirka / 2);
    }

    /**
     * Metóda sposobuje pohyb powerbaru po obrazovke zmenov x-ovej súradnice, smer ktorým sa má padlle pohybovať sa určí stlačením šípky lavo/pravo
     * v triede BrakObrazovka, po uvolnení šípky sa hodnota pohybu nastavý na false aby sa pohyb zastavil, obsahuje aj podmienky ktoré nedovolia
     * aby powerbar nezašiel mimo hraciu plochu
     * @param g
     */
    public void move(Gulicka g) {
        if (this.lavo) {
            this.x -= 2;
        } else if (this.pravo) {
            this.x += 2;
        }
        if (this.x + this.sirka > this.aSIRKA) {
            this.x = this.aSIRKA - this.sirka;
        }
        if (this.x < 0) {
            this.x = 0;
        }
        this.zistiZasah(g);
    }

    /**
     * Metóda zistuje či sa powerbar dotkol s gulickou a sposobý odraz gulicky zmenov jej y-ovej súradnice na opačné znamienko
     * @param g
     */
    public void zistiZasah(Gulicka g) {
        if (this.getOdraz().intersects(g.getOdraz())) {
            if (g.getY() < this.y) {
                g.hitBar();
                return;
            }
        }
    }

    /**
     * Metóda vykreslí obrázok paddleImage s veľkostou a umestnením PowerBar
     * @param g
     */
    public void paint(Graphics g) {
        g.drawImage(this.paddleImage, this.x, this.y, this.sirka, this.vyska, null);
    }

    /**
     * @param b
     */
    public void setLavo(boolean b) {
        this.lavo = b;
    }

    /**
     * @param b
     */
    public void setPravo(boolean b) {
        this.pravo = b;
    }

    /**
     * @return Rectangle
     */
    public Rectangle getOdraz() {
        return new Rectangle(this.x, this.y, this.sirka, this.vyska);
    }

    /**
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return this
     */
    public PowerBar getBar() {
        return this;
    }
}
