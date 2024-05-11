package enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Water extends Enemy {
  private int x;
  
  private int y;
  
  private int dx;
  
  int width = 50;
  
  int height = 32;
  
  BufferedImage water;
  
  public Water(int x, int y, int dx) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.hitBox = new Rectangle(x, y, this.width, this.height);
    try {
      this.water = ImageIO.read(getClass().getResourceAsStream("/water.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void update() {
    this.x += this.dx;
    this.hitBox.x = this.x;
  }
  
  public void draw(Graphics2D g) {
    if (this.dx < 0)
      g.drawImage(this.water, this.x, this.y, this.width, this.height, null); 
    if (this.dx > 0)
      g.drawImage(this.water, this.x + this.width, this.y, -this.width, this.height, null); 
  }
}
