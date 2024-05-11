package entity;

import UltraEngine.AddOns.Animation;
import UltraEngine.GameState.GameState;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Door {
  public int x;
  
  public int y;
  
  public int w;
  
  public int h;
  
  public Rectangle hitBox;
  
  BufferedImage[] doorImages;
  
  Animation open;
  
  public boolean toggled = false;
  
  GameState gs;
  
  public Door(GameState gs, int x, int y, int w, int h) {
    this.gs = gs;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.hitBox = new Rectangle(x, y, w, h);
    loadAssets();
  }
  
  private void loadAssets() {
    this.doorImages = new BufferedImage[4];
    for (int i = 0; i < 4; i++) {
      try {
        this.doorImages[i] = ImageIO.read(getClass().getResourceAsStream("/Door/door_16x16_" + (i + 1) + ".png"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    this.open = new Animation(5, this.doorImages);
  }
  
  public void draw(Graphics2D g) {
    this.hitBox.x = this.x;
    this.hitBox.y = this.y;
    if (this.toggled) {
      this.open.runAnimationWithoutLoop();
      this.open.drawAnimation(g, this.x, this.y, this.w, this.h);
    } else {
      g.drawImage(this.doorImages[0], this.x, this.y, this.w, this.h, null);
    } 
  }
}
