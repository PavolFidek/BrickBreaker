package brickbreaker.v1.objekty;

import brickbreaker.v1.vynimky.NechytenaGulickaException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Fidek
 */
public class Gulicka {
    private int x;
    private int y;
    private int velkost;
    private int xv;
    private int yv;
    private int aVYSKA;
    private int aSIRKA;
    private final Image gulickaImage;

    /**
     * Konštruktor
     * @param lokaciaX
     * @param lokaciaY
     * @param j
     */
    public Gulicka(int lokaciaX, int lokaciaY, JPanel j) {
        this.gulickaImage = new ImageIcon(this.getClass().getResource("gulicka.png")).getImage();
        this.x = lokaciaX;
        this.y = lokaciaY;
        this.aSIRKA = j.getWidth();
        this.aVYSKA = j.getHeight();
        //xv a yv nastavuju rychlost gulicky
        this.xv = -1;
        this.yv = -1;
        this.velkost = 20;
    }

    /**
     * Metóda vykonáva pohyb gulicky a odrážanie od okrajov okna oby nám neutiekla, pri dotknutí spodného okraja obrazovky sa hra ukončí
     * pretože hráč guličku nechytil a teda prehral, xv a yv sa prirátavajú k súradniciam pomocov a vdaka tomu meníme smer gulicky kedže
     * xv/yv budú buď + alebo -, čím vačšia je hodnota xv/yv tak gulička sa bude pohybovať rýchlejšie ale bude to robiť problémy pri odražaní od blokov
     */
    public void move() {
        this.x += this.xv;
        this.y += this.yv;
        if (this.x < 0) {
            this.xv *= -1;
        }
        if (this.y < 0) {
            this.yv *= -1;
        }
        if (this.y + this.velkost > this.aVYSKA) {
            throw new NechytenaGulickaException();
        }
        if (this.x + this.velkost > this.aSIRKA) {
            this.xv *= -1;
        }
    }

    /**
     * Metoda vyresli obrázok s velkostou a umiestnením gulicky
     * @param g
     */
    public void paint(Graphics g) {
        g.drawImage(this.gulickaImage, this.x, this.y, this.velkost, this.velkost, null);
    }

    /**
     * Metóda vynásobí yv súradnicu -1 taže sa jej zmení znamienko na opačné a gulička zmení smer
     */
    public void hitBar() {
        this.yv *= -1;
    }

    /**
     * Metóda vynásobí xv súradnicu -1 taže sa jej zmení znamienko na opačné a gulička zmení smer
     */
    public void hitBarX() {
        this.xv *= -1;
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
     * @return Rectangle
     */
    public Rectangle getOdraz() {
        return new Rectangle(this.x, this.y, this.velkost, this.velkost);
    }

    /**
     * @return this
     */
    public Gulicka getBall() {
        return this;
    }

    /**
     * @return xv
     */
    public int getXv() {
        return this.xv;
    }

    /**
     * @return yv
     */
    public int getYv() {
        return this.yv;
    }
}
