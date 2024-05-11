package enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Enemy {
  public int health = 100;
  
  public int x;
  
  public int y;
  
  public int w;
  
  public int h;
  
  public Rectangle hitBox;
  
  public EnemyID id;
  
  public void translate(int x, int y) {
    this.x += x;
    this.y += y;
    this.hitBox.x += x;
    this.hitBox.y += y;
  }
  
  public abstract void update();
  
  public abstract void draw(Graphics2D paramGraphics2D);
}
