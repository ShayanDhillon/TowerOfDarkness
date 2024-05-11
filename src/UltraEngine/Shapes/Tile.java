package UltraEngine.Shapes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Tile {
  public int x;
  
  public int y;
  
  int width;
  
  int height;
  
  Image img;
  
  public Rectangle hitBox;
  
  public int id;
  
  public Tile(int id, Image img2, int x, int y, int width, int height) {
    this.id = id;
    this.img = img2;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.hitBox = new Rectangle(x, y, width, height);
  }
  
  public void draw(Graphics2D g) {
    g.drawImage(this.img, this.x, this.y, this.width, this.height, null);
  }
}
