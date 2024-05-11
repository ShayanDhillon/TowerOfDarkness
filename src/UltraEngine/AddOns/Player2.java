package UltraEngine.AddOns;

import UltraEngine.GameState.GameState;
import UltraEngine.Shapes.Tile;
import entity.Flash;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player2 {
  public int x;
  
  public int y;
  
  int width;
  
  int height;
  
  public boolean facingLeft = true;
  
  Rectangle hitBox;
  
  ArrayList<Tile> walls;
  
  boolean candash = false;
  
  public int hearts = 5;
  
  BufferedImage spriteSheet;
  
  BufferedImage[] walkImages;
  
  BufferedImage[] jumpImages;
  
  BufferedImage deth;
  
  Animation walk;
  
  Animation jump;
  
  BufferedImage[] flashImages;
  
  GameState ts;
  
  BufferedImage hudSpriteSheet;
  
  BufferedImage heart;
  
  BufferedImage arrow;
  
  BufferedImage arrowSide;
  
  Rectangle atkHitbox = new Rectangle(0, 0, 32, 32);
  
  int atkPos = 1;
  
  public boolean w;
  
  public boolean a;
  
  public boolean s;
  
  public boolean d;
  
  public boolean jumping;
  
  ArrayList<Flash> flashList = new ArrayList<>();
  
  public Player2(GameState ts, int x, int y, int width, int height) {
    this.ts = ts;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.hitBox = new Rectangle(x, y, width, height);
    loadSpriteSheet();
    loadSprites();
  }
  
  public void loadSpriteSheet() {
    try {
      this.spriteSheet = ImageIO.read(getClass().getResourceAsStream("/TileSets/tilemap.png"));
      this.hudSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Hud2.gif"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void loadSprites() {
    try {
      this.deth = ImageIO.read(getClass().getResourceAsStream("/TileSets/deth.png"));
      this.arrow = ImageIO.read(getClass().getResourceAsStream("/arrow.png"));
      this.arrowSide = ImageIO.read(getClass().getResourceAsStream("/arrowSide.png"));
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    this.heart = this.hudSpriteSheet.getSubimage(0, 0, 13, 12);
    this.flashImages = new BufferedImage[5];
    int i;
    for (i = 0; i < 5; i++) {
      try {
        this.flashImages[i] = ImageIO.read(getClass().getResourceAsStream("/BlockFlash/BlockFlash_" + i + ".png"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    this.walkImages = new BufferedImage[3];
    this.jumpImages = new BufferedImage[3];
    for (i = 0; i < 3; i++) {
      this.walkImages[i] = this.spriteSheet.getSubimage(64 + i * 16, 16, 16, 16);
      this.jumpImages[i] = this.spriteSheet.getSubimage(64 + i * 16, 0, 16, 16);
    } 
    this.walk = new Animation(5, this.walkImages);
    this.jump = new Animation(5, this.jumpImages);
  }
  
  public void update() {}
  
  public void draw(Graphics2D g) {
    for (int i = 0; i < this.hearts; i++)
      g.drawImage(this.heart, 513 + i * 25, 0, 25, 25, null); 
    if ((this.a && !this.jumping) || (this.d && !this.jumping)) {
      this.walk.runAnimation();
      if (this.facingLeft) {
        this.walk.drawAnimation(g, this.x + this.width, this.y, -this.width, this.height);
      } else {
        this.walk.drawAnimation(g, this.x, this.y, this.width, this.height);
      } 
    } 
    if (this.jumping) {
      this.jump.runAnimation();
      if (this.facingLeft) {
        this.jump.drawAnimation(g, this.x + this.width, this.y, -this.width, this.height);
      } else {
        this.jump.drawAnimation(g, this.x, this.y, this.width, this.height);
      } 
    } 
    if (!this.a && !this.d && !this.jumping)
      if (this.facingLeft) {
        g.drawImage(this.walkImages[0], this.x + this.width, this.y, -this.width, this.height, null);
      } else {
        g.drawImage(this.walkImages[0], this.x, this.y, this.width, this.height, null);
      }  
  }
  
  public void keyPressed(int key) {}
  
  public void keyReleased(int key) {}
}
