package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Key {
  public int x;
  
  public int y;
  
  public int w;
  
  public int h;
  
  public Rectangle hitBox;
  
  BufferedImage keyImage;
  
  double bounce = 0.0D;
  
  public boolean active = true;
  
  public Key(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.hitBox = new Rectangle(x, y, w, h);
    try {
      this.keyImage = ImageIO.read(getClass().getResourceAsStream("/key.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void update() {
    this.hitBox.x = this.x;
    this.hitBox.y = this.y;
  }
  
  public void draw(Graphics2D g) {
    if (this.active) {
      this.bounce += 0.1D;
      g.drawImage(this.keyImage, this.x, (int)(this.y + Math.cos(this.bounce) * 5.0D), this.w, this.h, null);
    } 
  }
}
