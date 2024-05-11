package UltraEngine.AddOns;

import UltraEngine.GameState.GameState;
import UltraEngine.Renderer.Panel;
import UltraEngine.Shapes.Tile;
import UltraEngine.Systems.SoundSystem;
import enemies.Enemy;
import entity.Flash;
import game.GameSettings;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class Player {
  public int x;
  
  public int y;
  
  int width;
  
  int height;
  
  double xspeed;
  
  double yspeed;
  
  public boolean keyUp;
  
  public boolean keyDown;
  
  public boolean keyLeft;
  
  public boolean keyRight;
  
  public boolean keyShift;
  
  public boolean facingLeft = true;
  
  public Rectangle hitBox;
  
  ArrayList<Tile> walls;
  
  public boolean jumping;
  
  boolean candash = false;
  
  boolean dashing = false;
  
  TileMap tm;
  
  TileMap tm2;
  
  BufferedImage spriteSheet;
  
  BufferedImage[] walkImages;
  
  BufferedImage[] jumpImages;
  
  BufferedImage deth;
  
  Animation walk;
  
  Animation jump;
  
  BufferedImage[] flashImages;
  
  boolean panUp = false, panDown = false;
  
  boolean hitDebounce = false;
  
  public int hearts = 5;
  
  BufferedImage hudSpriteSheet;
  
  BufferedImage heart;
  
  BufferedImage arrow;
  
  BufferedImage arrowSide;
  
  Rectangle atkHitbox = new Rectangle(0, 0, 32, 32);
  
  int atkPos = 1;
  
  GameState gs;
  
  public int doorState = 3;
  
  boolean padDbc;
  
  boolean panDbc;
  
  boolean dashTog;
  
  boolean hit;
  
  public boolean canMove;
  
  boolean jumpSoundDbc;
  
  boolean dashSoundDbc;
  
  boolean canJumpSound;
  
  boolean attacking;
  
  boolean canAttack;
  
  public void loadSpriteSheet() {
    try {
      this.spriteSheet = ImageIO.read(getClass().getResourceAsStream("/TileSets/tilemap.png"));
      this.hudSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Hud.gif"));
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
    this.walkImages = new BufferedImage[3];
    this.jumpImages = new BufferedImage[3];
    for (int i = 0; i < 3; i++) {
      this.walkImages[i] = this.spriteSheet.getSubimage(64 + i * 16, 16, 16, 16);
      this.jumpImages[i] = this.spriteSheet.getSubimage(64 + i * 16, 0, 16, 16);
    } 
    this.walk = new Animation(5, this.walkImages);
    this.jump = new Animation(5, this.jumpImages);
  }
  
  public Player(GameState gs, ArrayList<Tile> walls, int x, int y, int width, int height) {
    this.padDbc = false;
    this.panDbc = false;
    this.dashTog = false;
    this.hit = false;
    this.canMove = false;
    this.jumpSoundDbc = false;
    this.dashSoundDbc = false;
    this.canJumpSound = false;
    this.attacking = false;
    this.canAttack = true;
    this.gs = gs;
    this.tm = gs.tm;
    this.tm2 = gs.tm2;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.hitBox = new Rectangle(x, y, width, height);
    this.walls = walls;
    loadSpriteSheet();
    loadSprites();
  }
  
  public void update() {
    if (this.panUp) {
      ArrayList<Flash> flClone = (ArrayList<Flash>)this.gs.flashList.clone();
      if (this.y + this.height < Panel.height) {
        if (!this.panDbc) {
          this.panDbc = true;
          this.y = -this.height;
        } 
        this.tm.translate(0.0D, GameSettings.panSpeed);
        this.tm2.translate(0.0D, GameSettings.panSpeed);
        if (this.gs.d != null)
          this.gs.d.y += GameSettings.panSpeed; 
        if (this.gs.b != null) {
          this.gs.b.y += GameSettings.panSpeed;
          this.gs.b.hitBox = new Rectangle(this.gs.b.x + 15, this.gs.b.y + 50, this.gs.b.w - 30, 75);
        } 
        for (Flash f : flClone)
          f.y += GameSettings.panSpeed; 
        for (Enemy e : this.gs.enemies)
          e.translate(0, GameSettings.panSpeed); 
        this.y += GameSettings.panSpeed;
        this.hitBox.y = this.y;
      } else {
        this.panUp = false;
        this.panDbc = false;
      } 
    } 
    if (this.panDown)
      if (this.y > 0) {
        if (!this.panDbc) {
          this.panDbc = true;
          this.y = Panel.height;
        } 
        this.tm.translate(0.0D, -GameSettings.panSpeed);
        this.tm2.translate(0.0D, -GameSettings.panSpeed);
        if (this.gs.b != null) {
          this.gs.b.y -= GameSettings.panSpeed;
          this.gs.b.hitBox = new Rectangle(this.gs.b.x + 15, this.gs.b.y + 50, this.gs.b.w - 30, 75);
        } 
        if (this.gs.d != null)
          this.gs.d.y -= GameSettings.panSpeed; 
        ArrayList<Flash> flClone = (ArrayList<Flash>)this.gs.flashList.clone();
        for (Flash f : flClone)
          f.y -= GameSettings.panSpeed; 
        for (Enemy e : this.gs.enemies)
          e.translate(0, -GameSettings.panSpeed); 
        this.y -= GameSettings.panSpeed;
        this.hitBox.y = this.y;
      } else {
        this.panDown = false;
        this.panDbc = false;
      }  
    if (!this.panUp && !this.panDown) {
      if (this.y + this.height < 0)
        this.panUp = true; 
      if (this.y > Panel.height)
        this.panDown = true; 
      if (this.atkPos == 0) {
        this.atkHitbox.x = this.x - this.width;
        this.atkHitbox.y = this.y;
      } 
      if (this.atkPos == 1) {
        this.atkHitbox.x = this.x;
        this.atkHitbox.y = this.y - this.height;
      } 
      if (this.atkPos == 2) {
        this.atkHitbox.x = this.x + this.width;
        this.atkHitbox.y = this.y;
      } 
      if ((this.keyLeft && this.keyRight) || (!this.keyLeft && !this.keyRight))
        this.xspeed *= 0.8D; 
      if (this.hearts <= 0)
        this.canMove = false; 
      if (this.gs.b != null) {
        if (this.atkHitbox.intersects(this.gs.b.hitBox) && this.attacking) {
          this.attacking = false;
          SoundSystem.playSound("hurt", -10.0F, false);
          this.gs.b.health -= 10;
          this.gs.flashList.add(new Flash(this.atkHitbox.x, this.atkHitbox.y, this.gs.flashImages));
        } 
        if (this.hitBox.intersects(this.gs.b.hitBox) && !this.gs.b.drawd) {
          this.xspeed += -this.xspeed * GameSettings.enemyKnockbackMulti;
          if (!this.hit) {
            this.hit = true;
            this.canMove = false;
            SoundSystem.playSound("guh", 0.0F, false);
            this.gs.b.guhing = true;
            this.gs.b.hit();
            this.hearts--;
            (new Timer()).schedule(new TimerTask() {
                  public void run() {
                    Player.this.hit = false;
                    Player.this.gs.b.guhing = false;
                    Player.this.gs.b.attack.reset();
                  }
                },  500L);
            (new Timer()).schedule(new TimerTask() {
                  public void run() {
                    Player.this.canMove = true;
                  }
                },  700L);
          } 
        } 
      } 
      ArrayList<Enemy> ec = (ArrayList<Enemy>)this.gs.enemies.clone();
      for (Enemy e : ec) {
        if (this.hitBox.intersects(e.hitBox)) {
          this.xspeed += -this.xspeed * GameSettings.enemyKnockbackMulti;
          if (!this.hit) {
            this.hit = true;
            this.canMove = false;
            SoundSystem.playSound("hurt", -10.0F, false);
            this.hearts--;
            this.gs.enemies.remove(e);
            (new Timer()).schedule(new TimerTask() {
                  public void run() {
                    Player.this.hit = false;
                  }
                },  500L);
            (new Timer()).schedule(new TimerTask() {
                  public void run() {
                    Player.this.canMove = true;
                  }
                },  700L);
          } 
        } 
      } 
      if (this.canMove) {
        if (this.keyLeft)
          this.xspeed--; 
        if (this.keyRight)
          this.xspeed++; 
      } 
      if (this.xspeed > 0.0D && this.xspeed < 0.75D)
        this.xspeed = 0.0D; 
      if (this.xspeed < 0.0D && this.xspeed > -0.75D)
        this.xspeed = 0.0D; 
      if (!this.hit) {
        if (this.xspeed > 5.0D)
          this.xspeed = 5.0D; 
        if (this.xspeed < -5.0D)
          this.xspeed = -5.0D; 
      } 
      this.hitBox.x = (int)(this.hitBox.x + this.xspeed);
      for (Tile w : this.walls) {
        if (w.id == 17 && w.hitBox.intersects(this.hitBox) && !this.padDbc) {
          this.padDbc = true;
          SoundSystem.playSound("Pad", -10.0F, false);
          this.yspeed = -10.5D;
          (new Timer()).schedule(new TimerTask() {
                public void run() {
                  Player.this.padDbc = false;
                }
              },  500L);
        } 
      } 
      ArrayList<Tile> tc = (ArrayList<Tile>)this.tm2.tiles.clone();
      for (Tile t : tc) {
        if (t.id == 25 && t.hitBox.intersects(this.hitBox) && this.keyUp) {
          this.yspeed = -6.0D;
          final Tile tileClone = t;
          if (!this.jumpSoundDbc) {
            this.jumpSoundDbc = true;
            SoundSystem.playSound("Jump7", -10.0F, false);
            (new Timer()).schedule(new TimerTask() {
                  public void run() {
                    Player.this.jumpSoundDbc = false;
                  }
                },  500L);
          } 
          this.tm2.tiles.remove(t);
          (new Timer()).schedule(new TimerTask() {
                public void run() {
                  Player.this.tm2.tiles.add(tileClone);
                }
              },  1L);
        } 
        if (t.id == 32 && t.hitBox.intersects(this.hitBox)) {
          this.candash = true;
          final Tile tileClone = t;
          this.tm2.tiles.remove(t);
          (new Timer()).schedule(new TimerTask() {
                public void run() {
                  Player.this.tm2.tiles.add(tileClone);
                }
              },  1L);
        } 
      } 
      this.hitBox.x = (int)(this.hitBox.x - this.xspeed);
      if (this.keyUp) {
        this.hitBox.y++;
        for (Tile w : this.walls) {
          if (this.canMove) {
            if (w.hitBox.intersects(this.hitBox))
              this.yspeed = -6.0D; 
            this.jump.reset();
            this.jumping = true;
            if (!this.canJumpSound) {
              this.canJumpSound = true;
              SoundSystem.playSound("jump", -10.0F, false);
            } 
          } 
        } 
        this.hitBox.y--;
      } 
      this.yspeed += 0.3D;
      if (this.candash && this.dashTog && !this.hit) {
        this.candash = false;
        if (this.gs.pw != null) {
          this.gs.pw.println("flash");
          this.gs.pw.flush();
        } 
        this.dashTog = false;
        this.hit = true;
        this.canMove = false;
        if (!this.dashSoundDbc) {
          this.dashSoundDbc = true;
          SoundSystem.playSound("Dash", -10.0F, false);
          (new Timer()).schedule(new TimerTask() {
                public void run() {
                  Player.this.dashSoundDbc = false;
                }
              },  500L);
        } 
        this.gs.flashList.add(new Flash(this.x, this.y, this.gs.flashImages));
        this.xspeed *= GameSettings.dashMultiplier;
        this.yspeed = 0.0D;
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                Player.this.hit = false;
                Player.this.canMove = true;
              }
            },  200L);
      } 
      this.hitBox.x = (int)(this.hitBox.x + this.xspeed);
      for (Tile w : this.walls) {
        if (w.hitBox.intersects(this.hitBox)) {
          this.hitBox.x = (int)(this.hitBox.x - this.xspeed);
          while (!w.hitBox.intersects(this.hitBox))
            this.hitBox.x = (int)(this.hitBox.x + Math.signum(this.xspeed)); 
          this.hitBox.x = (int)(this.hitBox.x - Math.signum(this.xspeed));
          this.xspeed = 0.0D;
          this.x = this.hitBox.x;
        } 
      } 
      this.hitBox.y = (int)(this.hitBox.y + this.yspeed);
      for (Tile w : this.walls) {
        if (w.hitBox.intersects(this.hitBox)) {
          this.hitBox.y = (int)(this.hitBox.y - this.yspeed);
          while (!w.hitBox.intersects(this.hitBox))
            this.hitBox.y = (int)(this.hitBox.y + Math.signum(this.yspeed)); 
          this.hitBox.y = (int)(this.hitBox.y - Math.signum(this.yspeed));
          this.yspeed = 0.0D;
          this.y = this.hitBox.y;
          this.jumping = false;
          this.canJumpSound = false;
        } 
      } 
      this.x = (int)(this.x + this.xspeed);
      this.y = (int)(this.y + this.yspeed);
      if (this.x < 32)
        this.x = 32; 
      if (this.x + this.width > Panel.width - 32)
        this.x = Panel.width - 32 - this.width; 
      this.hitBox.x = this.x;
      this.hitBox.y = this.y;
      attack();
    } 
  }
  
  private void attack() {
    ArrayList<Enemy> ec = (ArrayList<Enemy>)this.gs.enemies.clone();
    for (Enemy e : ec) {
      if (this.attacking && e.hitBox.intersects(this.atkHitbox)) {
        SoundSystem.playSound("hurt", -10.0F, false);
        this.gs.flashList.add(new Flash(e.x + 50, e.y + 60, this.gs.flashImages));
        this.gs.enemies.remove(e);
        this.attacking = false;
      } 
    } 
  }
  
  public void draw(Graphics2D g) {
    for (int i = 0; i < this.hearts; i++)
      g.drawImage(this.heart, i * 25, 0, 25, 25, null); 
    if (this.hearts > 0) {
      if ((this.keyLeft && !this.jumping) || (this.keyRight && !this.jumping)) {
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
      if (!this.keyLeft && !this.keyRight && !this.jumping)
        if (this.facingLeft) {
          g.drawImage(this.walkImages[0], this.x + this.width, this.y, -this.width, this.height, null);
        } else {
          g.drawImage(this.walkImages[0], this.x, this.y, this.width, this.height, null);
        }  
      if (this.attacking) {
        if (this.atkPos == 0)
          g.drawImage(this.arrowSide, this.atkHitbox.x + this.atkHitbox.width, this.atkHitbox.y, -this.atkHitbox.width, this.atkHitbox.height, null); 
        if (this.atkPos == 1)
          g.drawImage(this.arrow, this.atkHitbox.x, this.atkHitbox.y, this.atkHitbox.width, this.atkHitbox.height, null); 
        if (this.atkPos == 2)
          g.drawImage(this.arrowSide, this.atkHitbox.x, this.atkHitbox.y, this.atkHitbox.width, this.atkHitbox.height, null); 
      } 
    } else {
      g.drawImage(this.deth, this.x, this.y, this.width, this.height, null);
    } 
  }
  
  private void doAttack() {
    if (this.canAttack) {
      this.canAttack = false;
      this.attacking = true;
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              Player.this.attacking = false;
            }
          },  500L);
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              Player.this.canAttack = true;
            }
          },  1500L);
    } 
  }
  
  public void keyPressed(int key) {
    if (key == 70)
      System.out.println(String.valueOf(this.x) + "," + this.y); 
    if (key == 87)
      this.keyUp = true; 
    if (key == 65) {
      this.keyLeft = true;
      this.facingLeft = true;
    } 
    if (key == 69 && 
      this.gs.d != null && 
      this.hitBox.intersects(this.gs.d.hitBox) && !this.gs.d.toggled) {
      this.gs.d.toggled = true;
      SoundSystem.playSound("door", -10.0F, false);
      this.gs.gsm.fMinutes = this.gs.gsm.minutes;
      this.gs.gsm.fSeconds = this.gs.gsm.seconds;
      this.gs.gsm.fMillis = this.gs.gsm.millis;
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              Player.this.gs.tin = new TransitionIn();
            }
          },  1000L);
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              Player.this.gs.gsm.currentState = Player.this.doorState;
            }
          },  3000L);
    } 
    if (key == 83)
      this.keyDown = true; 
    if (key == 68) {
      this.keyRight = true;
      this.facingLeft = false;
    } 
    if (key == 16)
      this.keyShift = true; 
    if (key == 37) {
      this.atkPos = 0;
      doAttack();
    } 
    if (key == 38) {
      this.atkPos = 1;
      doAttack();
    } 
    if (key == 39) {
      this.atkPos = 2;
      doAttack();
    } 
  }
  
  public void keyReleased(int key) {
    if (key == 87)
      this.keyUp = false; 
    if (key == 65)
      this.keyLeft = false; 
    if (key == 83)
      this.keyDown = false; 
    if (key == 68)
      this.keyRight = false; 
    if (key == 16) {
      this.keyShift = false;
      if (this.candash)
        this.dashTog = true; 
    } 
  }
}
