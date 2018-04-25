package brickbreaker.v1.bloky;

import brickbreaker.v1.objekty.Minca;
import brickbreaker.v1.objekty.PowerBar;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Fidek
 */
public class OdolnejsiBlok extends Blok implements IBlok {
    private final Image odolImage;
    private final Image odolImage1;
    private final Random random;
    private final int nahodneCislo;
    private final Minca minca;
    private int bonusy;

    /**
     * Konštruktor
     * @param x
     * @param y
     */
    public OdolnejsiBlok(int x, int y) {
        super(x, y, 2, 18);
        this.minca = new Minca(x + (this.getSirka() / 2), y);
        this.odolImage = new ImageIcon(this.getClass().getResource("odolBlock2.png")).getImage();
        this.odolImage1 = new ImageIcon(this.getClass().getResource("odolBlock1.png")).getImage();
        this.random = new Random();
        this.nahodneCislo = this.random.nextInt(10);
    }


    /**
     * Metoda vykresluje obrazok s veľkostou a umiestnením bloku podla jeho života, pokiaľ sme ho este nezasiahli vykresluje
     * sa odolImage(červený blok) po zasahu sa jeho zivot zníži takže sa vykresluje odolImage1(oranžový blok)
     * kedže sa obrázky farebne líšia hráč vidí koľko života bloku ešte ostáva.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (this.getZivot() == 2) {
            g.drawImage(this.odolImage, this.getX(), this.getY(), this.getSirka(), this.getVyska(), null);
        } else {
            g.drawImage(this.odolImage1, this.getX(), this.getY(), this.getSirka(), this.getVyska(), null);
        }
    }

    /**
     * Ak náhodné číslo vygenerované pomocov random bude menšie/rovné ako 5 tak sa vykreslí minca so suradnicami bloku
     * @param g
     */
    @Override
    public void bonusovaMinca(Graphics g) {
        if (this.nahodneCislo <= 5) {
            this.minca.paint(g);
        }
    }

    /**
     * Ak je nahodné číslo je menšie/rovné ako 5 tak sa spustí pohyb mince a priratava sa bonus ktorý obdržíme ak mincu chitíme
     * @param bar
     */
    @Override
    public void pohybMince(PowerBar bar) {
        this.bonusy = 0;
        if (this.nahodneCislo <= 5) {
            this.minca.pohybMince(bar);
            this.bonusy += this.minca.getBonusZaMincu();
        }
    }

    /**
     * @return bonusy
     */
    @Override
    public int getBonusy() {
        return this.bonusy;
    }
}
