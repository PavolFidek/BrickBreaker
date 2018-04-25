package brickbreaker.v1;

import brickbreaker.v1.objekty.Gulicka;
import brickbreaker.v1.objekty.PowerBar;
import brickbreaker.v1.bloky.OdolnejsiBlok;
import brickbreaker.v1.bloky.ZakladnyBlok;
import brickbreaker.v1.vynimky.NechytenaGulickaException;
import brickbreaker.v1.vynimky.PrazdnaPlochaException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Fidek
 */
public class BreakObrazovka extends JPanel implements Runnable, KeyListener, ActionListener{
    private final JFrame hraciaObrazovka;
    private boolean running;
    private Thread thread;
    private final Gulicka gulicka;
    private final Container container;
    private final PowerBar playerBar;
    private final ArrayList<ZakladnyBlok> zakBloky;
    private final ArrayList<OdolnejsiBlok> odolBloky;
    private int sucet;
    private int maxmalneSkore;
    private Image obrazok1;
    private Image prehralSiImage;
    private Image vyhralSiImage;
    private boolean prehralSi;
    private boolean vyhralSi;
    private final JButton resumeButt;
    private final Font pismoVypisSkore;
    private int scitaneBonusy;

    /**
     * Konštruktor
     */
    public BreakObrazovka() {
        this.hraciaObrazovka = new JFrame("Brickout");
        this.resumeButt = new JButton("Resume");
        this.container = this.hraciaObrazovka.getContentPane();

        //nastavenie tlacidla resume
        this.resumeButt.setBounds(270, 300, 100, 50);
        this.resumeButt.addActionListener(this);

        //inicializacia arraylistov
        this.zakBloky = new ArrayList<>();
        this.odolBloky = new ArrayList<>();

        //nastavenie vypisu skore
        this.pismoVypisSkore = new Font("Arial", Font.BOLD, 30);

        //pozadie hracej plochy
        ImageIcon pozadie1 = new ImageIcon(this.getClass().getResource("pozadie.jpg"));
        this.obrazok1 = pozadie1.getImage();
        this.obrazok1 = this.obrazok1.getScaledInstance(640, 480, Image.SCALE_SMOOTH);

        //prehral si obrazok
        ImageIcon prehraIcon = new ImageIcon(this.getClass().getResource("GameOver.png"));
        this.prehralSiImage = prehraIcon.getImage();
        this.prehralSiImage = this.prehralSiImage.getScaledInstance(640, 480, Image.SCALE_SMOOTH);

        //vyhral si image
        ImageIcon vyhraIcon = new ImageIcon(this.getClass().getResource("youwin.png"));
        this.vyhralSiImage = vyhraIcon.getImage();
        this.vyhralSiImage = this.vyhralSiImage.getScaledInstance(500, 280, Image.SCALE_SMOOTH);

        this.hraciaObrazovka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.hraciaObrazovka.setSize(640, 480);
        this.setPreferredSize(new Dimension(640, 480));

        this.container.add(this);
        this.hraciaObrazovka.pack();
        this.gulicka = new Gulicka(320, 400, this);

        this.hraciaObrazovka.setVisible(true);
        this.hraciaObrazovka.setLocationRelativeTo(null);

        this.playerBar = new PowerBar(this);
        this.addKeyListener(this);
        this.requestFocusInWindow();
        this.start();
    }

    /**
     * Metóda vytvorí a spusti nové vlákno
     */
    public void start() {
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Metoda pridáva do arraylistu bloky a nastavuje im x a y suradnicu, (i*90)+50 zabezpečí rozostup medzi jednotlivými blokmi
     * aby sa neprekrívali, rozdeluje ich do riadkov
     */
    public void naplnArray() {
        for (int i = 0; i < 17; i++) {
            if (i < 4) {
                this.zakBloky.add(new ZakladnyBlok((i * 150) + 50, 160));
            } else if (i > 4 && i <= 10) {
                this.zakBloky.add(new ZakladnyBlok(((i - 5) * 90) + 50, 40));
            } else if (i > 10 && i <= 17) {
                this.zakBloky.add(new ZakladnyBlok(((i - 11) * 90) + 50, 280));
            }
        }
        for (int i = 0; i < 14; i++) {
            if (i < 7) {
                this.odolBloky.add(new OdolnejsiBlok((i * 90) + 10, 100));
            } else if (i >= 7) {
                this.odolBloky.add(new OdolnejsiBlok(((i - 7) * 90) + 10 , 220));
            }
        }
    }

    /**
     * Metóda zabezpečuje beh celej hry pred samotným cyklom whyle sa zavolá metóda ktorá pridáva bloky do arraylistov,
     * v cykle sa volajú metody update(), repaint() a metody na scitavanie a overovanie výhry ktoré kontroluju či
     * sme zničili všetky bloky a spočítavajú skóre. Celý cyklus je v try catch bloku ktorý odchytáva výnimky ktoré ukončujú hru
     * v prípade odchytenia výnimky sa nastavý príslušná premenná na true, ukončí sa hra a vzkreslí sa obráazok
     * (podľa toho či hrač vyhral alebo prehral) so skóre ktoré
     * hráč dosiahol a zárovň resume tlačidlo ktorým možeme znova spustiť hru.
     */
    @Override
    public void run() {
        this.running = true;
        this.naplnArray();
        try {
            while (this.running) {
                repaint();
                this.update();
                this.scitavanieSore();
                this.overovanieVyhry();
                try {
                    Thread.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (NechytenaGulickaException ex) {
            this.prehralSi = true;
        } catch (PrazdnaPlochaException es) {
            this.vyhralSi = true;
        }
    }

    /**
     * Metoda porovnáva aktuálne skore s maximálne dosiahnutelným skore a v prípade že ho dosiahneme vytvorý výnimku ktorá ukončí hru
     */
    public void overovanieVyhry() {
        this.scitavanieSore();
        this.zistiMaxSkore();
        if (this.sucet == this.maxmalneSkore) {
            throw new PrazdnaPlochaException();
        }
    }

    /**
     * Metóda zistí ake je maximálne dosiahnuteľné skóre, tým že spočíta bonusKuSkore každého bloku a v prípade odolnejších blokov vynásobý 2x pretože
     * tieto bloky majú 2 životy a za každý obdržíme bonusKuSKore, max. skóre ktoré možeme dosiahnuť sa uloží do
     * premennej a nasledne poľa toho overujeme výhru.
     */
    public void zistiMaxSkore() {
        this.maxmalneSkore = 0;
        for (ZakladnyBlok zakladnyBlok : this.zakBloky) {
            this.maxmalneSkore += (zakladnyBlok.getBonusKuSkore());
        }
        for (OdolnejsiBlok odolnejsiBlok : this.odolBloky) {
            this.maxmalneSkore += 2 * (odolnejsiBlok.getBonusKuSkore());
        }
    }

    /**
     * V metode sa vykresľujú všetky komponenty hracej plochy, nachadzajú sa tu aj podmienky kedy sa majú určité komponenty vykresliť
     * ako resume tlačidlo ktore sa vykreslí len v pripade že sme vyhrali/prehrali, bloky ktore ničíme sa vykreslujú
     * pokiaľ je ich život väčší ako 0.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.obrazok1, 0, 0, this);
        this.gulicka.paint(g);
        this.playerBar.paint(g);

        //vykresluje hornu radu zakladných blokov
        for (int i = 0; i < this.zakBloky.size(); i++) {
            ZakladnyBlok blok = this.zakBloky.get(i);
            if (blok.getZivot() > 0) {
                blok.paint(g);
            }
        }

        //vykresli odolnejsie bloky
        for (int i = 0; i < this.odolBloky.size(); i++) {
            OdolnejsiBlok odolBlok = this.odolBloky.get(i);
            if (odolBlok.getZivot() > 0) {
                odolBlok.paint(g);
            } else {
                odolBlok.bonusovaMinca(g);
            }
        }

        if (this.prehralSi || this.vyhralSi) {
            if (this.prehralSi) {
                g.drawImage(this.prehralSiImage, 0, 20, this);
            } else if (this.vyhralSi) {
                g.drawImage(this.vyhralSiImage, 50, 50, this);
            }

            this.add(this.resumeButt);
            int scitaneSpolu = this.sucet + this.scitaneBonusy;
            String vypis = Integer.toString(scitaneSpolu);
            g.setFont(this.pismoVypisSkore);
            g.setColor(Color.red);
            g.drawString("Tvoje skore je: " + vypis, 190, 400);
        }
    }

    /**
     * Metóda volá pohyb každej časi hracej plochy (gulička, bloky, powerbar, minca)
     */
    private void update() {
        this.gulicka.move();
        this.playerBar.move(this.gulicka.getBall());

        for (ZakladnyBlok blok : this.zakBloky) {
            if (blok.getZivot() > 0) {
                blok.zasah(this.gulicka.getBall());
            }
        }
        for (OdolnejsiBlok odolblok : this.odolBloky) {
            if (odolblok.getZivot() > 0) {
                odolblok.zasah(this.gulicka.getBall());
            } else {
                odolblok.pohybMince(this.playerBar.getBar());
            }
        }
    }

    /**
     * Metoda prejde všetky arraylisty a do premennej sucet pridáva skore jednotlivých blokov kedže si každý blok počíta skóre samostatne a prirátava
     * aj bonus ktorý obrdžíme za chitenie bonusovej mince ktorý sa priráta až na konci k celkovému súčtu.
     */
    public void scitavanieSore() {
        this.sucet =  0;
        this.scitaneBonusy = 0;
        for (ZakladnyBlok zakladnyBlok : this.zakBloky) {
            this.sucet += zakladnyBlok.getSkore();
        }
        for (OdolnejsiBlok odolBlok : this.odolBloky) {
            this.sucet += odolBlok.getSkore();
            this.scitaneBonusy += odolBlok.getBonusy();
        }
    }

    /**
     * V metode sa po stlačení šípky nastavý playerBrau pohyb na true podla toho ktorú šípku stlačíme a v playerBar sa podla toho
     * pridáva x/y hodnota vdaka čomu sa začne pohybovať na správny smer
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            this.playerBar.setLavo(true);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            this.playerBar.setPravo(true);
        }
    }


    /**
     * Po uvolneni šípky sa nastavý pohyb na false aby sa playerBar zastavil
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int kodSipky = e.getKeyCode();
        if (kodSipky == KeyEvent.VK_LEFT) {
            this.playerBar.setLavo(false);
        } else if (kodSipky == KeyEvent.VK_RIGHT) {
            this.playerBar.setPravo(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    /**
     * Metoda po stlačeni resume tlačidla spustí hru od začiatku
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object a = e.getSource();
        if (a == this.resumeButt) {
            this.hraciaObrazovka.dispose();
            new BreakObrazovka();
        }
    }
}
