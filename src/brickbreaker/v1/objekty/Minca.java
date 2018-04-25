package brickbreaker.v1.objekty;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Fidek
 */
public class Minca {
    private int x;
    private int y;
    private int velkost;
    private Image bonusMinca;
    private boolean mincaZmizne;
    private int bonusZaMincu;

    /**
     * Konštruktor
     * @param x
     * @param y
     */
    public Minca(int x, int y) {
        this.x = x;
        this.y = y;
        this.velkost = 20;

        //nastavenie obrazku pre mincu
        this.bonusMinca = new ImageIcon(this.getClass().getResource("goldminca.png")).getImage();
        this.bonusMinca = this.bonusMinca.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

        this.mincaZmizne = false;
        this.bonusZaMincu = 0;
    }

    /**
     * Metoda prirátave 1 k y-ovej súradnici čím sposobuje pád mince a zároveň kontroluje či sa dotkla s
     * powerbarom, ak áno minca sa prestane vykreslovať (mincaZmizne nastavý na true) a hráčovi sa pridá bonus za chytenú mincu
     * @param b
     */
    public void pohybMince(PowerBar b) {
        this.y += 1;
        if (this.getZasah().intersects(b.getOdraz())) {
            if (this.y < b.getY()) {
                this.mincaZmizne = true;
                this.bonusZaMincu = 50;
            }
        }
    }

    /**
     * @return Rectangle
     */
    public Rectangle getZasah() {
        return new Rectangle(this.x, this.y, this.velkost, this.velkost);
    }

    /**
     * Medtóda vykresluje mincu pokiaľ sa ešte nedotkla powerbaru, takže pokial je premenná mincaZmizne false
     * @param g
     */
    public void paint(Graphics g) {
        if (!this.mincaZmizne) {
            g.drawImage(this.bonusMinca, this.x, this.y, null);
        }
    }

    /**
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return bonusZaMincu
     */
    public int getBonusZaMincu() {
        return this.bonusZaMincu;
    }
}
