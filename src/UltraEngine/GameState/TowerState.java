package UltraEngine.GameState;

import UltraEngine.AddOns.Player;
import UltraEngine.AddOns.TileMap;
import UltraEngine.AddOns.TransitionIn;
import UltraEngine.AddOns.TransitionOut;
import UltraEngine.Renderer.Panel;
import UltraEngine.Shapes.Tile;
import UltraEngine.Systems.SoundSystem;
import enemies.Enemy;
import enemies.Slime;
import entity.Door;
import entity.Flash;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class TowerState extends GameState {
  boolean init;
  
  boolean ded;
  
  public TowerState(GameStateManager gsm) {
    super(gsm);
    this.init = false;
    this.ded = false;
    this.tm = new TileMap("todtower", "tilemap", 16, 32);
    this.tm2 = new TileMap("todtower2", "tilemap", 16, 32);
    this.tm.translate(0.0D, -7925.0D);
    this.tm2.translate(0.0D, -7925.0D);
    this.d = new Door(this, 32, 64, 32, 32);
    this.d.y -= 7925;
    this.p = new Player(this, this.tm.tiles, 537, 396, 32, 32);
    this.p.canMove = true;
    this.tout = new TransitionOut();
    loadFlash();
    populateEnemies();
  }
  
  private void loadFlash() {
    this.flashImages = new java.awt.image.BufferedImage[5];
    for (int i = 0; i < 5; i++) {
      try {
        this.flashImages[i] = ImageIO.read(getClass().getResourceAsStream("/BlockFlash/BlockFlash_" + i + ".png"));
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  private void populateEnemies() {
    this.enemies.add(new Slime(175, 192, 100, 100));
    this.enemies.add(new Slime(250, -415, 100, 100));
    this.enemies.add(new Slime(240, -640, 100, 100));
    this.enemies.add(new Slime(255, -1150, 100, 100));
  }
  
  public void update() {
    this.PZ.x = ((Tile)this.tm.tiles.get(0)).x;
    this.PZ.y = ((Tile)this.tm.tiles.get(0)).y;
    if (!this.connecting) {
      if (!this.init) {
        this.init = true;
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                SoundSystem.playSound("theme", -7.0F, true);
              }
            },1000L);
        (new Timer()).scheduleAtFixedRate(new TimerTask() {
              public void run() {
                if (TowerState.this.gsm.millis < 1000) {
                  TowerState.this.gsm.millis++;
                } else {
                  if (TowerState.this.gsm.seconds < 60) {
                    TowerState.this.gsm.seconds++;
                  } else {
                    TowerState.this.gsm.minutes++;
                    TowerState.this.gsm.seconds = 0;
                  } 
                  TowerState.this.gsm.millis = 0;
                } 
              }
            },  1L, 1L);
      } 
      if (this.p.hearts <= 0 && 
        !this.ded) {
        this.ded = true;
        this.tin = new TransitionIn();
        SoundSystem.stopClips();
        (new Timer()).schedule(new TimerTask() {
              public void run() {
                TowerState.this.reset();
                TowerState.this.ded = false;
                TowerState.this.tout = new TransitionOut();
                TowerState.this.tin = null;
              }
            },  2000L);
      } 
      this.p.update();
      if (this.p2 != null)
        this.p2.update(); 
      for (Enemy e : this.enemies)
        e.update(); 
      this.tout.update();
      if (this.tin != null)
        this.tin.update(); 
      purgeFlashes();
    } 
  }
  
  private void purgeFlashes() {
    ArrayList<Flash> flClone = (ArrayList<Flash>)this.flashList.clone();
    for (Flash f : flClone) {
      if (f.anim.count == f.anim.images.length)
        this.flashList.remove(f); 
    } 
  }
  
  public void draw(Graphics2D g) {
    if (!this.connecting) {
      this.tm.draw(g);
      this.d.draw(g);
      this.p.draw(g);
      if (this.p2 != null)
        this.p2.draw(g); 
      ArrayList<Enemy> ec = (ArrayList<Enemy>)this.enemies.clone();
      for (Enemy e : ec)
        e.draw(g); 
      this.tm2.draw(g);
      this.tout.draw(g);
      g.setFont(this.gsm.f.deriveFont(0, 20.0F));
      g.setColor(Color.white);
      g.drawString(String.valueOf(this.gsm.minutes) + ":" + this.gsm.seconds + ":" + this.gsm.millis, Panel.width - 350, 20);
      if (this.tin != null)
        this.tin.draw(g); 
      ArrayList<Flash> flClone = (ArrayList<Flash>)this.flashList.clone();
      for (Flash f : flClone)
        f.draw(g); 
    } else {
      g.setFont(this.gsm.f.deriveFont(0, 25.0F));
      g.setColor(Color.gray);
      g.drawString("WAITING FOR OTHER PLAYER...", 135, 202);
      g.setColor(Color.white);
      g.drawString("WAITING FOR OTHER PLAYER...", 135, 200);
    } 
    if (this.disconnected) {
      g.setFont(this.gsm.f.deriveFont(0, 20.0F));
      g.setColor(Color.green.darker().darker());
      g.drawString("Friend has disconnected!", 225, 470);
      g.setColor(Color.green);
      g.drawString("Friend has disconnected!", 225, 468);
    } 
  }
  
  public void keyPressed(int key, char ch) {
    this.p.keyPressed(key);
    if (ch == '`')
      reset(); 
  }
  
  private void reset() {
    SoundSystem.stopClips();
    this.d = new Door(this, 32, 64, 32, 32);
    this.d.y -= 7925;
    this.tm = new TileMap("todtower", "tilemap", 16, 32);
    this.tm2 = new TileMap("todtower2", "tilemap", 16, 32);
    this.tm.translate(0.0D, -7925.0D);
    this.tm2.translate(0.0D, -7925.0D);
    this.p = new Player(this, this.tm.tiles, 537, 396, 32, 32);
    this.p.canMove = true;
    this.tout = new TransitionOut();
    this.gsm.seconds = 0;
    this.gsm.minutes = 0;
    this.gsm.millis = 0;
    this.enemies.clear();
    populateEnemies();
    SoundSystem.playSound("theme", -7.0F, true);
  }
  
  public void keyReleased(int key, char ch) {
    this.p.keyReleased(key);
  }
  
  public void mouseClicked(int x, int y) {}
  
  public void mouseMoved(int x, int y) {}
}
