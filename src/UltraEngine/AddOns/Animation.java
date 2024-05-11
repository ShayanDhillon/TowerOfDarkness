package UltraEngine.AddOns;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Animation {
  private int speed;
  
  private int frames;
  
  private int index = 0;
  
  public int count = 0;
  
  public BufferedImage[] images;
  
  public BufferedImage currentImg;
  
  public Animation(int speed, BufferedImage... args) {
    this.speed = speed;
    this.images = new BufferedImage[args.length];
    for (int i = 0; i < args.length; i++)
      this.images[i] = args[i]; 
    this.frames = args.length;
  }
  
  public void runAnimation() {
    this.index++;
    if (this.index > this.speed) {
      this.index = 0;
      nextFrame();
    } 
  }
  
  public void runAnimationWithoutLoop() {
    this.index++;
    if (this.index > this.speed) {
      this.index = 0;
      nextFrameWithoutLoop();
    } 
  }
  
  private void nextFrame() {
    for (int i = 0; i < this.frames; i++) {
      if (this.count == i)
        this.currentImg = this.images[i]; 
    } 
    this.count++;
    if (this.count > this.frames)
      this.count = 0; 
  }
  
  private void nextFrameWithoutLoop() {
    if (this.count < this.frames) {
      for (int i = 0; i < this.frames; i++) {
        if (this.count == i)
          this.currentImg = this.images[i]; 
      } 
      this.count++;
    } 
  }
  
  public void drawAnimation(Graphics2D g, int x, int y) {
    g.drawImage(this.currentImg, x, y, (ImageObserver)null);
  }
  
  public void drawAnimation(Graphics2D g, int x, int y, int width, int height) {
    g.drawImage(this.currentImg, x, y, width, height, null);
  }
  
  public void reset() {
    this.index = 1;
    this.count = 1;
  }
}
