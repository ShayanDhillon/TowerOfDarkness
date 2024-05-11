package UltraEngine.Renderer;

import UltraEngine.Game;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Display extends JFrame {
  Panel p;
  
  public Display(final Game g, String title, int width, int height, boolean udc) {
    super(title);
    setDefaultCloseOperation(3);
    this.p = new Panel(g, width, height);
    setContentPane(this.p);
    setResizable(false);
    setUndecorated(udc);
    pack();
    setLocationRelativeTo((Component)null);
    setVisible(true);
    addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent ke) {
            g.keyPressed(ke.getKeyCode(), ke.getKeyChar());
          }
          
          public void keyReleased(KeyEvent ke) {
            g.keyReleased(ke.getKeyCode(), ke.getKeyChar());
          }
        });
    addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent me) {
            g.mouseClicked(me.getX(), me.getY());
          }
        });
    addMouseMotionListener(new MouseAdapter() {
          public void mouseMoved(MouseEvent me) {
            g.mouseMoved(me.getX(), me.getY());
          }
        });
    try {
      setIconImage(ImageIO.read(getClass().getResourceAsStream("/TileSets/deth.png")));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
