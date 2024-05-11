package UltraEngine.GameState;

import UltraEngine.AddOns.TransitionIn;
import UltraEngine.AddOns.TransitionOut;
import UltraEngine.Systems.SoundSystem;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class ControlState extends GameState {
  public TransitionIn tin;
  
  public TransitionOut tout;
  
  BufferedImage control;
  
  boolean init;
  
  boolean activated;
  
  public ControlState(GameStateManager gsm) {
    super(gsm);
    this.init = false;
    this.activated = false;
    try {
      this.control = ImageIO.read(getClass().getResourceAsStream("/gggg.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void update() {
    if (!this.init) {
      this.init = true;
      this.tout = new TransitionOut();
    } 
    if (this.tin != null)
      this.tin.update(); 
    if (this.tout != null)
      this.tout.update(); 
  }
  
  public void draw(Graphics2D g) {
    g.drawImage(this.control, 0, 0, null);
    if (this.tin != null)
      this.tin.draw(g); 
    if (this.tout != null)
      this.tout.draw(g); 
  }
  
  public void keyPressed(int key, char ch) {
    ch = Character.toLowerCase(ch);
    if (key == 10 && !this.activated) {
      this.activated = true;
      this.tin = new TransitionIn();
      SoundSystem.playSound("start", -10.0F, false);
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              ControlState.this.gsm.currentState = 0;
              ControlState.this.tout = new TransitionOut();
              ControlState.this.tin = null;
              ControlState.this.activated = false;
            }
          },  1500L);
    } 
  }
  
  public void keyReleased(int key, char ch) {}
  
  public void mouseClicked(int x, int y) {}
  
  public void mouseMoved(int x, int y) {}
}
