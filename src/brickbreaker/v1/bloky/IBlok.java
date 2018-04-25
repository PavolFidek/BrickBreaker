package brickbreaker.v1.bloky;

import brickbreaker.v1.objekty.Gulicka;
import brickbreaker.v1.objekty.PowerBar;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Fidek
 */
public interface IBlok {
    void zasah(Gulicka g);

    void paint(Graphics g);

    void bonusovaMinca(Graphics g);

    void pohybMince(PowerBar b);

    Rectangle getOdrazHorizontalnyZHora();

    Rectangle getOdrazHorizontalnyZDola();

    Rectangle getOdrazVertikalnyZLava();

    Rectangle getOdrazVertikalnyZPrava();

    int getZivot();

    int getSkore();

    int getX();

    int getY();

    int getBonusy();
}
