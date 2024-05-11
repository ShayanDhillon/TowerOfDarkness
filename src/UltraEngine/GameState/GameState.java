package UltraEngine.GameState;

import UltraEngine.AddOns.Player;
import UltraEngine.AddOns.Player2;
import UltraEngine.AddOns.TileMap;
import UltraEngine.AddOns.TransitionIn;
import UltraEngine.AddOns.TransitionOut;
import enemies.Boss;
import enemies.Enemy;
import entity.Door;
import entity.Flash;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class GameState {
  public GameStateManager gsm;
  
  public ArrayList<Flash> flashList = new ArrayList<>();
  
  public ArrayList<Enemy> enemies = new ArrayList<>();
  
  public BufferedImage[] flashImages;
  
  public PrintWriter pw;
  
  public boolean connecting = false;
  
  public boolean disconnected = false;
  
  public Player p;
  
  public Player2 p2;
  
  public Boss b;
  
  public TileMap tm;
  
  public TileMap tm2;
  
  public Point PZ = new Point(0, 0);
  
  public Door d;
  
  public TransitionIn tin;
  
  public TransitionOut tout;
  
  public GameState(GameStateManager gsm) {
    this.gsm = gsm;
  }
  
  public abstract void update();
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  public abstract void keyPressed(int paramInt, char paramChar);
  
  public abstract void keyReleased(int paramInt, char paramChar);
  
  public abstract void mouseClicked(int paramInt1, int paramInt2);
  
  public abstract void mouseMoved(int paramInt1, int paramInt2);
}
