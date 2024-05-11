package UltraEngine.Particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FireParticle extends Particle {
  Color c = new Color(255, 165, 0);
  
  Rectangle r;
  
  public FireParticle(int x, int y, int w, int h) {
    this.r = new Rectangle(x, y, w + (int)(Math.random() * 4.0D), h);
  }
  
  public void update() {
    if (this.r.width > 0) {
      this.r.x += this.r.width / 11;
      this.r.y -= 3;
      this.r.width--;
    } 
    if (this.r.height > 0)
      this.r.height--; 
  }
  
  public void draw(Graphics2D g) {
    g.setColor(this.c);
    g.fill(this.r);
  }
}
