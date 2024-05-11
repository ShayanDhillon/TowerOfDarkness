package UltraEngine.GameState;

import UltraEngine.Systems.SoundSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class EndState extends GameState {
  Font f = new Font("Times New Roman", 0, 25);
  
  boolean init;
  
  public EndState(GameStateManager gsm) {
    super(gsm);
    this.init = false;
  }
  
  public void update() {
    if (!this.init) {
      this.init = true;
      SoundSystem.stopClips();
      SoundSystem.playSound("clear", -10.0F, false);
    } 
  }
  
  public void draw(Graphics2D g) {
    g.setFont(this.f);
    g.setColor(Color.white.darker().darker());
    g.drawString("See you next time, tower-climber.", 150, 202);
    g.setColor(Color.white);
    g.drawString("See you next time, tower-climber.", 150, 200);
    g.setFont(this.f.deriveFont(0, 50.0F));
    g.setColor(Color.red.darker().darker().darker());
    g.drawString(String.valueOf(this.gsm.fMinutes) + ":" + this.gsm.fSeconds + ":" + this.gsm.fMillis, 225, 304);
    g.setColor(Color.red.darker().darker());
    g.drawString(String.valueOf(this.gsm.fMinutes) + ":" + this.gsm.fSeconds + ":" + this.gsm.fMillis, 225, 302);
    g.setColor(Color.red);
    g.drawString(String.valueOf(this.gsm.fMinutes) + ":" + this.gsm.fSeconds + ":" + this.gsm.fMillis, 225, 300);
  }
  
  public void keyPressed(int key, char ch) {}
  
  public void keyReleased(int key, char ch) {}
  
  public void mouseClicked(int x, int y) {}
  
  public void mouseMoved(int x, int y) {}
}
