package brickbreaker.v1.bloky;

import brickbreaker.v1.objekty.Gulicka;
import brickbreaker.v1.objekty.PowerBar;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Fidek
 */
abstract class Blok {
    private int x;
    private int y;
    private int sirka;
    private int vyska;
    private int zivot;
    private int skore;
    private int bonusKuSkore;

    Blok(int x, int y, int zivot, int bonusKuSkore) {
        this.x = x;
        this.y = y;
        this.sirka = 70;
        this.vyska = 20;
        this.zivot = zivot;
        this.skore = 0;
        this.bonusKuSkore = bonusKuSkore;
    }

    /**
     * Metoda obsahuje podmienky ktore zistia pomocov .intersects() z ktorej strany sa gulicka dotkla boku a zmeni gulicke smer tym ze
     * jej hodnotu xv/yv vynasobi -1, ak sa dotkne gulicka z dola/hora tak sa zmeni yv, ak sa dotkne gulicka z prava/lava tak sa meni xv.
     * Po dotknuti gulicky s blokom sa automaticky prida skore a uberie sa jej zivot.
     * @param g
     */
    public void zasah(Gulicka g) {
        if (this.getOdrazHorizontalnyZDola().intersects(g.getOdraz())) {
            if (g.getY() > this.y) {
                g.hitBar();
                this.zivot--;
                this.skore += this.bonusKuSkore;
                return;
            }
        }
        if (this.getOdrazHorizontalnyZHora().intersects(g.getOdraz())) {
            if (g.getY() + this.vyska > this.y) {
                g.hitBar();
                this.zivot--;
                this.skore += this.bonusKuSkore;
                return;
            }
        }
        if (this.getOdrazVertikalnyZLava().intersects(g.getOdraz())) {
            if ((g.getX() < this.x ) && (g.getYv() < 0 && g.getXv() > 0)) {
                g.hitBarX();
                this.zivot--;
                this.skore += this.bonusKuSkore;
                return;
            }
            if ((g.getX() < this.x ) && (g.getY() > 0 && g.getXv() > 0)) {
                g.hitBarX();
                this.zivot--;
                this.skore += this.bonusKuSkore;
                return;
            }
        }
        if (this.getOdrazVertikalnyZPrava().intersects(g.getOdraz())) {
            if ((g.getY() > this.x) && (g.getXv() < 0 && g.getYv() > 0)) {
                g.hitBarX();
                this.zivot--;
                this.skore += this.bonusKuSkore;
                return;
            }
            if ((g.getY() < this.y) && (g.getYv() < 0 && g.getXv() < 0)) {
                g.hitBarX();
                this.zivot--;
                this.skore += this.bonusKuSkore;
                return;
            }
        }
    }

    public void bonusovaMinca(Graphics g) {
    }

    public void pohybMince(PowerBar b) {
    }

    abstract void paint(Graphics g);

    /**
     * @return Rectangle
     */
    public Rectangle getOdrazHorizontalnyZHora() {
        return new Rectangle((this.x + 1), this.y, (this.sirka - 2), (this.vyska - 17));
    }

    /**
     * @return Rectangle
     */
    public Rectangle getOdrazHorizontalnyZDola() {
        return new Rectangle((this.x + 1), (this.y + this.vyska), (this.sirka - 2), (this.vyska - 17));
    }

    /**
     * @return Rectangle
     */
    public Rectangle getOdrazVertikalnyZLava() {
        return new Rectangle(this.x, (this.y + 1), (this.sirka - 67), (this.vyska - 2));
    }

    /**
     * @return Rectangle
     */
    public Rectangle getOdrazVertikalnyZPrava() {
        return new Rectangle((this.x + this.sirka), (this.y + 1), (this.sirka - 67), (this.vyska - 2));
    }

    /**
     * @return zivot
     */
    public int getZivot() {
        return this.zivot;
    }

    /**
     * @return skore
     */
    public int getSkore() {
        return this.skore;
    }

    /**
     * @return bonusKuSkore
     */
    public int getBonusKuSkore() {
        return this.bonusKuSkore;
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
     * @return 0
     */
    public int getBonusy() {
        return 0;
    }

    /**
     * @return sirka
     */
    public int getSirka() {
        return this.sirka;
    }

    /**
     * @return vyska
     */
    public int getVyska() {
        return this.vyska;
    }
}
