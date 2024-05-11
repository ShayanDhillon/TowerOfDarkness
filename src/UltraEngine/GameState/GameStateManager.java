package UltraEngine.GameState;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {
  public ArrayList<GameState> states = new ArrayList<>();
  
  public int currentState = 0;
  
  public Font f = new Font("Times New Roman", 0, 50);
  
  public int minutes;
  
  public int seconds;
  
  public int millis;
  
  public int fMinutes;
  
  public int fSeconds;
  
  public int fMillis;
  
  public GameStateManager() {
    this.states.add(new MenuState(this));
    this.states.add(new ControlState(this));
    this.states.add(new TowerState(this));
    this.states.add(new FinalState(this));
    this.states.add(new EndState(this));
  }
  
  public void update() {
    ((GameState)this.states.get(this.currentState)).update();
  }
  
  public void draw(Graphics2D g) {
    ((GameState)this.states.get(this.currentState)).draw(g);
  }
  
  public void keyPressed(int key, char ch) {
    ((GameState)this.states.get(this.currentState)).keyPressed(key, ch);
  }
  
  public void keyReleased(int key, char ch) {
    ((GameState)this.states.get(this.currentState)).keyReleased(key, ch);
  }
  
  public void mouseClicked(int x, int y) {
    ((GameState)this.states.get(this.currentState)).mouseClicked(x, y);
  }
  
  public void mouseMoved(int x, int y) {
    ((GameState)this.states.get(this.currentState)).mouseMoved(x, y);
  }
}
