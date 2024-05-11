package entity;

import UltraEngine.AddOns.Animation;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Flash {
  BufferedImage[] imgs;
  
  public Animation anim;
  
  public int x;
  
  public int y;
  
  int scale;
  
  public Flash(int x, int y, BufferedImage[] imgs) {
    this.scale = 64;
    this.x = x;
    this.y = y;
    this.imgs = imgs;
    this.anim = new Animation(4, imgs);
  }
  
  public void draw(Graphics2D g) {
    this.anim.runAnimationWithoutLoop();
    this.anim.drawAnimation(g, this.x - this.scale / 2, this.y - this.scale / 2, this.scale, this.scale);
  }
}
