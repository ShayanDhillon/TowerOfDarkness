package game;

import UltraEngine.Game;
import UltraEngine.GameState.GameStateManager;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Launcher extends Game {
  GameStateManager gsm = new GameStateManager();
  
  public static void main(String[] args) {}
  
  public void update() {
    this.gsm.update();
  }
  
  boolean AA = false;
  
  public void draw(Graphics2D g) {
    if (!this.AA) {
      this.AA = true;
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    } 
    this.gsm.draw(g);
  }
  
  public void keyPressed(int key, char ch) {
    this.gsm.keyPressed(key, ch);
  }
  
  public void keyReleased(int key, char ch) {
    this.gsm.keyReleased(key, ch);
  }
  
  public void mouseClicked(int x, int y) {
    this.gsm.mouseClicked(x, y);
  }
  
  public void mouseMoved(int x, int y) {
    this.gsm.mouseMoved(x, y);
  }
}
