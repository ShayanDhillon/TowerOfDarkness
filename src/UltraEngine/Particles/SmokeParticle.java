package UltraEngine.Particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SmokeParticle extends Particle {
  Color c = Color.gray;
  
  Rectangle r;
  
  int trans = 150;
  
  public SmokeParticle(int x, int y, int w, int h) {
    this.r = new Rectangle(x, y, w, h);
  }
  
  public void update() {
    if (this.trans > 0)
      this.trans--; 
    if (this.r.width < 50) {
      this.r.x--;
      this.r.width++;
    } 
    if (this.r.height < 50) {
      this.r.y--;
      this.r.height++;
    } 
  }
  
  public void draw(Graphics2D g) {
    g.setColor(new Color(this.c.getRed(), this.c.getGreen(), this.c.getBlue(), this.trans));
    g.fillOval(this.r.x, this.r.y, this.r.width, this.r.height);
  }
}
