package enemies;

import UltraEngine.AddOns.Animation;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Slime extends Enemy {
  BufferedImage[] hopImages;
  
  BufferedImage[] attackImages;
  
  BufferedImage[] hdImages;
  
  Animation hop;
  
  Animation attack;
  
  Animation hd;
  
  public Slime(int x, int y, int w, int h) {
    this.id = EnemyID.Slime;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.hitBox = new Rectangle(x + 35, y + 15, 30, 60);
    loadAssets();
  }
  
  private void loadAssets() {
    this.hopImages = new BufferedImage[13];
    this.attackImages = new BufferedImage[6];
    this.hdImages = new BufferedImage[9];
    try {
      int i;
      for (i = 0; i < 13; i++)
        this.hopImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Slime/hop/hop_" + i + ".png")); 
      for (i = 0; i < 6; i++)
        this.attackImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Slime/attack/attack_" + i + ".png")); 
      for (i = 0; i < 9; i++)
        this.hdImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Slime/hitdeath/hit and death_" + i + ".png")); 
      this.hop = new Animation(5, this.hopImages);
      this.attack = new Animation(5, this.attackImages);
      this.hd = new Animation(5, this.hdImages);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void update() {}
  
  public void draw(Graphics2D g) {
    this.hop.runAnimation();
    this.hop.drawAnimation(g, this.x, this.y, this.w, this.h);
  }
}
