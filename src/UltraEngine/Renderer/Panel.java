package UltraEngine.Renderer;

import UltraEngine.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {
  public static int width;
  
  public static int height;
  
  Thread t;
  
  boolean running;
  
  BufferedImage img;
  
  Graphics2D g;
  
  Game game;
  
  public Panel(Game game, int width, int height) {
    this.game = game;
    Panel.width = width;
    Panel.height = height;
    setPreferredSize(new Dimension(width, height));
    this.img = new BufferedImage(width, height, 1);
    this.g = (Graphics2D)this.img.getGraphics();
  }
  
  public void addNotify() {
    super.addNotify();
    if (this.t == null) {
      this.t = new Thread(this);
      this.t.start();
      this.running = true;
    } 
  }
  
  public void run() {
    long l = System.nanoTime();
    double ns = 1.6666666666666666E7D;
    double d = 0.0D;
    while (this.running) {
      long n = System.nanoTime();
      d += (n - l) / 1.6666666666666666E7D;
      l = n;
      while (d >= 1.0D) {
        update();
        d--;
        draw();
        drawToScreen();
      } 
    } 
  }
  
  private void update() {
    this.game.update();
  }
  
  private void draw() {
    this.g.setColor(Color.black);
    this.g.fillRect(0, 0, width, height);
    this.game.draw(this.g);
  }
  
  private void drawToScreen() {
    Graphics2D g2 = (Graphics2D)getGraphics();
    g2.drawImage(this.img, 0, 0, width, height, null);
    g2.dispose();
  }
}
