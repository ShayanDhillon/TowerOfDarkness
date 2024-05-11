package UltraEngine.GameState;

import UltraEngine.AddOns.Player;
import UltraEngine.AddOns.TileMap;
import UltraEngine.AddOns.TransitionIn;
import UltraEngine.AddOns.TransitionOut;
import UltraEngine.Renderer.Panel;
import UltraEngine.Shapes.Tile;
import UltraEngine.Systems.SoundSystem;
import enemies.Boss;
import enemies.Enemy;
import entity.Door;
import entity.Flash;
import entity.Key;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class FinalState extends GameState {
  TransitionOut to;
  
  TowerState ts;
  
  public boolean blinded = false;
  
  boolean deth = false;
  
  boolean introRan = false;
  
  boolean fightEnd = false;
  
  BufferedImage emoteSS;
  
  BufferedImage[] emotes;
  
  boolean showEmote = false;
  
  int currentEmote = 0;
  
  boolean shake = false;
  
  int shakeAmt = 2;
  
  boolean lavaRising = false;
  
  Door door;
  
  Key k;
  
  Rectangle lava = new Rectangle(0, 490, 640, 0);
  
  boolean init;
  
  Color lavaC;
  
  public FinalState(GameStateManager gsm) {
    super(gsm);
    this.init = false;
    this.lavaC = new Color(240, 0, 0);
    this.tm = new TileMap("todboss", "tilemap", 16, 32);
    this.tm2 = new TileMap("todboss2", "tilemap", 16, 32);
    this.tm.translate(0.0D, -2700.0D);
    this.tm2.translate(0.0D, -2700.0D);
    this.p = new Player(this, this.tm.tiles, 100, 396, 32, 32);
    this.tout = new TransitionOut();
    this.b = new Boss(this, this, 400, 437, 32, 32);
    this.d = new Door(this, 150, 150, 32, 32);
    this.p.doorState = 4;
    populateEnemies();
    loadFlash();
    loadEmotes();
  }
  
  private void loadEmotes() {
    try {
      this.emoteSS = ImageIO.read(getClass().getResourceAsStream("/Emotes.gif"));
      this.emotes = new BufferedImage[2];
      this.emotes[0] = this.emoteSS.getSubimage(0, 0, 14, 18);
      this.emotes[1] = this.emoteSS.getSubimage(14, 0, 14, 18);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  private void populateEnemies() {}
  
  private void reset() {
    this.k = null;
    this.enemies.clear();
    this.blinded = false;
    this.tm = new TileMap("todboss", "tilemap", 16, 32);
    this.tm2 = new TileMap("todboss2", "tilemap", 16, 32);
    this.tm.translate(0.0D, -2700.0D);
    this.tm2.translate(0.0D, -2700.0D);
    this.lavaRising = false;
    this.lava = new Rectangle(0, 490, 640, 0);
    this.p = new Player(this, this.tm.tiles, 100, 396, 32, 32);
    this.tout = new TransitionOut();
    this.b = new Boss(this, this, 400, 437, 32, 32);
    this.d = new Door(this, 150, 150, 32, 32);
    this.p.doorState = 4;
    this.introRan = false;
    this.fightEnd = false;
    intro();
  }
  
  private void intro() {
    SoundSystem.stopClips();
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.blinded = true;
            SoundSystem.playSound("spark", -10.0F, false);
          }
        },3000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.b.w = 128;
            FinalState.this.b.h = 128;
            FinalState.this.b.y -= 96;
            FinalState.this.b.hitBox = new Rectangle(FinalState.this.b.x + 15, FinalState.this.b.y + 50, FinalState.this.b.w - 30, 75);
          }
        },5000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.blinded = false;
            SoundSystem.playSound("spark", -10.0F, false);
          }
        },6000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            SoundSystem.stopClips();
          }
        },  7000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.introRan = true;
            FinalState.this.p.canMove = true;
            SoundSystem.playSound("battle", -10.0F, true);
          }
        },8000L);
  }
  
  private void purgeFlashes() {
    ArrayList<Flash> flClone = (ArrayList<Flash>)this.flashList.clone();
    for (Flash f : flClone) {
      if (f.anim.count == f.anim.images.length)
        this.flashList.remove(f); 
    } 
  }
  
  private void loadFlash() {
    this.flashImages = new BufferedImage[5];
    for (int i = 0; i < 5; i++) {
      try {
        this.flashImages[i] = ImageIO.read(getClass().getResourceAsStream("/BlockFlash/BlockFlash_" + i + ".png"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void update() {
    this.d.x = ((Tile)this.tm.tiles.get(0)).x + 300;
    this.d.y = ((Tile)this.tm.tiles.get(0)).y + 63;
    this.lava.y = ((Tile)this.tm.tiles.get(this.tm.tiles.size() - 1)).y - this.lava.height;
    if (this.lavaRising) {
      this.lava.height++;
      if (this.p.hitBox.intersects(this.lava) && !this.deth) {
        this.deth = true;
        this.tin = new TransitionIn();
        SoundSystem.stopClips();
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                FinalState.this.reset();
                FinalState.this.deth = false;
                FinalState.this.tout = new TransitionOut();
                FinalState.this.tin = null;
              }
            },  2000L);
      } 
    } 
    purgeFlashes();
    if (!this.init) {
      this.init = true;
      intro();
    } 
    if (!this.introRan)
      this.p.hearts = 5; 
    this.b.update();
    this.tout.update();
    this.p.update();
    if (this.p2 != null)
      this.p2.update(); 
    if (this.p.hearts <= 0 && !this.deth) {
      this.deth = true;
      this.tin = new TransitionIn();
      SoundSystem.stopClips();
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              FinalState.this.reset();
              FinalState.this.deth = false;
              FinalState.this.tout = new TransitionOut();
              FinalState.this.tin = null;
            }
          },  2000L);
    } 
    ArrayList<Enemy> ec = (ArrayList<Enemy>)this.enemies.clone();
    for (Enemy e : ec)
      e.update(); 
    if (this.b.health <= 0) {
      if (!this.fightEnd) {
        this.fightEnd = true;
        SoundSystem.stopClips();
        this.p.canMove = false;
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                SoundSystem.playSound("spark", -10.0F, false);
                FinalState.this.blinded = true;
              }
            },2000L);
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                FinalState.this.k = new Key(FinalState.this.b.x, FinalState.this.b.y, 32, 32);
              }
            },3000L);
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                SoundSystem.playSound("spark", -10.0F, false);
                FinalState.this.blinded = false;
                FinalState.this.p.canMove = true;
              }
            },4000L);
      } 
      if (this.shake) {
        this.shakeAmt = -this.shakeAmt;
        this.tm.translate(0.0D, this.shakeAmt);
        this.p.y += this.shakeAmt;
      } 
    } 
    if (this.k != null && this.p.hitBox.intersects(this.k.hitBox) && this.k.active) {
      this.k.active = false;
      startPhase();
    } 
  }
  
  private void startPhase() {
    SoundSystem.stopClips();
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.currentEmote = 0;
            FinalState.this.showEmote = true;
            FinalState.this.p.canMove = false;
          }
        },  6000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.shake = true;
            FinalState.this.currentEmote = 1;
            SoundSystem.playSound("shocked", -10.0F, false);
          }
        },8000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.shake = false;
            FinalState.this.showEmote = false;
            FinalState.this.p.canMove = true;
            FinalState.this.tm.tiles.remove(223);
            FinalState.this.tm.tiles.remove(223);
            FinalState.this.tm.tiles.remove(223);
            FinalState.this.tm.tiles.remove(223);
            SoundSystem.playSound("escape", 0.0F, false);
          }
        },10000L);
    (new Timer()).schedule(new TimerTask() {
          public void run() {
            FinalState.this.lavaRising = true;
          }
        },  12000L);
  }
  
  public void draw(Graphics2D g) {
    this.tm.draw(g);
    this.b.draw(g);
    this.d.draw(g);
    this.p.draw(g);
    if (this.p2 != null)
      this.p2.draw(g); 
    if (this.introRan) {
      ArrayList<Enemy> ec = (ArrayList<Enemy>)this.enemies.clone();
      for (Enemy e : ec)
        e.draw(g); 
    } 
    this.tm2.draw(g);
    this.tout.draw(g);
    if (this.introRan && 
      this.b.health > 0) {
      g.setFont(this.gsm.f.deriveFont(0, 25.0F));
      g.setColor(Color.red.darker().darker());
      g.drawString("GUH SLIME", 250, 92);
      g.setColor(Color.red.darker());
      g.drawString("GUH SLIME", 250, 91);
      g.setColor(Color.red);
      g.fillRect(170, 100, 300, 20);
      g.drawString("GUH SLIME", 250, 90);
      g.setColor(Color.green);
      g.fillRect(170, 100, this.b.health, 20);
    } 
    if (this.k != null) {
      this.k.update();
      this.k.draw(g);
    } 
    if (this.blinded) {
      g.setColor(Color.black);
      g.fillRect(0, 0, Panel.width, Panel.height);
    } 
    g.setFont(this.gsm.f.deriveFont(0, 20.0F));
    g.setColor(Color.white);
    g.drawString(String.valueOf(this.gsm.minutes) + ":" + this.gsm.seconds + ":" + this.gsm.millis, Panel.width - 340, 450);
    if (this.tin != null)
      this.tin.draw(g); 
    ArrayList<Flash> flClone = (ArrayList<Flash>)this.flashList.clone();
    for (Flash f : flClone)
      f.draw(g); 
    if (this.showEmote)
      g.drawImage(this.emotes[this.currentEmote], this.p.x, this.p.y, (ImageObserver)null); 
    g.setColor(this.lavaC);
    g.fill(this.lava);
  }
  
  public void keyPressed(int key, char ch) {
    this.p.keyPressed(key);
    if (ch == '`' && this.introRan)
      reset(); 
  }
  
  public void keyReleased(int key, char ch) {
    this.p.keyReleased(key);
  }
  
  public void mouseClicked(int x, int y) {}
  
  public void mouseMoved(int x, int y) {}
}
