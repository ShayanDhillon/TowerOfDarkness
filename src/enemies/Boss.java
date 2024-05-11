package enemies;

import UltraEngine.AddOns.Animation;
import UltraEngine.GameState.FinalState;
import UltraEngine.GameState.GameState;
import UltraEngine.Systems.SoundSystem;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class Boss extends Enemy {
  public int health = 300;
  
  BufferedImage[] attackImages;
  
  BufferedImage[] dieImages;
  
  BufferedImage[] hurtImages;
  
  BufferedImage[] idleImages;
  
  BufferedImage[] moveImages;
  
  public Animation attack;
  
  Animation die;
  
  Animation hurt;
  
  Animation idle;
  
  Animation move;
  
  public boolean guhing = false;
  
  int currentState = 0;
  
  GameState gs;
  
  Point midPoint = new Point(320, 245);
  
  BufferedImage waterball;
  
  boolean hit = false;
  
  Random r = new Random();
  
  FinalState ts;
  
  public boolean active = true;
  
  boolean dieDbc;
  
  public boolean drawd;
  
  boolean wbDbc;
  
  boolean blindDbc;
  
  boolean blindDbc2;
  
  double mSin;
  
  int lastX;
  
  int lastY;
  
  int lastPos;
  
  public Boss(FinalState fs, GameState gs, int x, int y, int w, int h) {
    this.dieDbc = false;
    this.drawd = false;
    this.wbDbc = false;
    this.blindDbc = false;
    this.blindDbc2 = false;
    this.mSin = 0.0D;
    this.lastX = 0;
    this.lastY = 0;
    this.lastPos = 0;
    this.ts = fs;
    this.gs = gs;
    this.id = EnemyID.Boss;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.hitBox = new Rectangle(x, y, w, h);
    loadAssets();
  }
  
  private void loadAssets() {
    try {
      this.waterball = ImageIO.read(getClass().getResourceAsStream("/water.png"));
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    this.attackImages = new BufferedImage[5];
    this.dieImages = new BufferedImage[4];
    this.hurtImages = new BufferedImage[4];
    this.idleImages = new BufferedImage[4];
    this.moveImages = new BufferedImage[4];
    try {
      int i;
      for (i = 0; i < 5; i++)
        this.attackImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Boss/slime-attack-" + i + ".png")); 
      for (i = 0; i < 4; i++) {
        this.dieImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Boss/slime-die-" + i + ".png"));
        this.hurtImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Boss/slime-hurt-" + i + ".png"));
        this.idleImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Boss/slime-idle-" + i + ".png"));
        this.moveImages[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Boss/slime-move-" + i + ".png"));
      } 
      this.attack = new Animation(5, this.attackImages);
      this.die = new Animation(5, this.dieImages);
      this.hurt = new Animation(5, this.hurtImages);
      this.idle = new Animation(5, this.idleImages);
      this.move = new Animation(5, this.moveImages);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void update() {
    if (this.health >= 300)
      this.currentState = 0; 
    if (this.health >= 250 && this.health < 300)
      this.currentState = 1; 
    if (this.health >= 200 && this.health < 250)
      this.currentState = 2; 
    if (this.health >= 150 && this.health < 200)
      this.currentState = 3; 
    if (this.health >= 100 && this.health < 150)
      this.currentState = 4; 
    if (this.health >= 0 && this.health < 100)
      this.currentState = 5; 
    this.hitBox.setBounds(this.x + 15, this.y + 50, this.w - 30, 75);
    runStates();
  }
  
  public void hit() {
    if (!this.hit) {
      this.hit = true;
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              Boss.this.hit = false;
            }
          },  500L);
    } 
  }
  
  private void runStates() {
    if (this.currentState == 1) {
      this.x = this.midPoint.x + 150;
      if (!this.wbDbc) {
        this.wbDbc = true;
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                Boss.this.genNextWaterball(-5);
                Boss.this.wbDbc = false;
              }
            },  800L);
      } 
    } 
    if (this.currentState == 2) {
      this.x = this.midPoint.x - 250;
      if (!this.wbDbc) {
        this.wbDbc = true;
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                Boss.this.genNextWaterball(5);
                Boss.this.wbDbc = false;
              }
            },  800L);
      } 
    } 
    if (this.currentState == 3) {
      this.x = this.midPoint.x + 150;
      this.y = this.midPoint.y - 246;
      if (!this.blindDbc) {
        this.blindDbc = true;
        this.ts.blinded = true;
        SoundSystem.playSound("spark", -10.0F, false);
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                SoundSystem.playSound("spark", -10.0F, false);
                Boss.this.ts.blinded = false;
              }
            },2000L);
      } 
    } 
    if (this.currentState == 4) {
      this.x = this.midPoint.x - 250;
      this.y = this.midPoint.y - 246;
      if (!this.blindDbc2) {
        this.blindDbc2 = true;
        this.ts.blinded = true;
        SoundSystem.playSound("spark", -10.0F, false);
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                SoundSystem.playSound("spark", -10.0F, false);
                Boss.this.ts.blinded = false;
              }
            },2000L);
      } 
    } 
    if (this.currentState == 5)
      this.mSin += 0.02D; 
    this.hitBox.x = this.x + 15;
    this.hitBox.y = this.y + 50;
  }
  
  private void genNextWaterball(int dx) {
    this.lastPos++;
    int xc = this.x;
    if (dx > 0)
      xc = this.hitBox.x + 100; 
    if (this.lastPos % 2 == 0) {
      this.gs.enemies.add(new Water(xc, this.y + 40, dx));
    } else {
      this.gs.enemies.add(new Water(xc, this.y + 100, dx));
    } 
  }
  
  public void draw(Graphics2D g) {
    if (this.health > 0) {
      if (this.currentState == 0) {
        this.idle.runAnimation();
        this.idle.drawAnimation(g, this.x, this.y, this.w, this.h);
      } 
      if (this.currentState == 1) {
        this.attack.runAnimation();
        this.attack.drawAnimation(g, this.x, this.y, this.w, this.h);
      } 
      if (this.currentState == 2) {
        this.attack.runAnimation();
        this.attack.drawAnimation(g, this.x + this.w, this.y, -this.w, this.h);
      } 
      if (this.currentState == 3) {
        this.idle.runAnimation();
        this.idle.drawAnimation(g, this.x, this.y + 175, this.w, -this.h);
      } 
      if (this.currentState == 4) {
        this.idle.runAnimation();
        this.idle.drawAnimation(g, this.x + this.w, this.y + 175, -this.w, -this.h);
      } 
      if (this.currentState == 5) {
        this.move.runAnimation();
        this.x = (int)((this.midPoint.x - 60) + Math.sin(this.mSin) * 200.0D);
        this.y = 350;
        if (this.x < this.lastX) {
          this.move.drawAnimation(g, this.x, this.y, this.w, this.h);
        } else {
          this.move.drawAnimation(g, this.x + this.w, this.y, -this.w, this.h);
        } 
        this.lastX = this.x;
      } 
    } 
    if (this.health <= 0) {
      if (!this.drawd) {
        this.die.runAnimationWithoutLoop();
        this.die.drawAnimation(g, this.x, this.y, this.w, this.h);
      } 
      if (!this.dieDbc) {
        this.dieDbc = true;
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                Boss.this.drawd = true;
              }
            },  2000L);
      } 
    } 
  }
}
