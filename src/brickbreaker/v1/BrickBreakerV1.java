package brickbreaker.v1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Fidek
 */
public class BrickBreakerV1 extends JPanel implements ActionListener{
    private Container container;
    private JFrame menu;
    private JButton startButt;
    private Image obrazok;
    private Image obrazokIkony;
    private Font menoAutora;

    /**
     * Konštruktor
     */
    public BrickBreakerV1() {
        this.menu = new JFrame("BrickBreaker menu");
        this.startButt = new JButton("Play");

        //nastavenie pozadia v menu
        ImageIcon pozadie = new ImageIcon(this.getClass().getResource("Brick.jpg"));
        this.obrazok = pozadie.getImage();
        this.obrazok = this.obrazok.getScaledInstance(640, 480, Image.SCALE_SMOOTH);

        //nastavenie textu menoAutora
        this.menoAutora = new Font("Arial", Font.BOLD, 12);

        //obrazok ikony
        this.obrazokIkony = new ImageIcon(this.getClass().getResource("gulicka2.png")).getImage();

        this.startButt.setPreferredSize(new Dimension(100, 100));
        this.startButt.setBounds(270, 250, 100, 50);
        this.startButt.addActionListener(this);

        this.menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.container = this.menu.getContentPane();
        this.menu.setResizable(false);
        this.setLayout(null);
        this.add(this.startButt);
        this.container.add(this);
        this.setPreferredSize(new Dimension(640, 480));
        this.menu.pack();
        this.menu.setVisible(true);
        this.menu.setLocationRelativeTo(null);
        this.menu.setIconImage(this.obrazokIkony);
    }

    /**
     * Metoda vykreslí obrázok na pozadie menu a meno autora hry
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.obrazok, 0, 0, this);
        g.setFont(this.menoAutora);
        g.setColor(Color.black);
        g.drawString("Pavol Fidek", 540, 470);
    }

    /**
     * Metóda zavolá konštruktor triedy
     * @param args
     */
    public static void main(String[] args) {
        new BrickBreakerV1();
    }

    /**
     * Po stlačení tlačidla Start sa okno menu zruší a zavolá sa okno na ktorom beží hra
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object a = e.getSource();
        if (a == this.startButt) {
            this.menu.dispose();
            new BreakObrazovka();
        }
    }
}




