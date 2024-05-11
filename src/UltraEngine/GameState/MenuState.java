package UltraEngine.GameState;

import UltraEngine.AddOns.TransitionIn;
import UltraEngine.AddOns.TransitionOut;
import UltraEngine.Renderer.Panel;
import UltraEngine.Systems.SoundSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import network.Client;
import network.Server;

public class MenuState extends GameState {
  Font title = new Font("Times New Roman", 0, 50);
  
  BufferedImage indicator;
  
  private String[] options = new String[] { "ENTER THE TOWER", 
      "START A SERVER", 
      "JOIN A SERVER", 
      "HOW TO PLAY", 
      "EXIT GAME" };
  
  private int currentOption = 0;
  
  TransitionIn tin;
  
  TransitionOut tout;
  
  Color orange;
  
  boolean activated;
  
  public MenuState(GameStateManager gsm) {
    super(gsm);
    this.orange = new Color(255, 69, 0);
    this.activated = false;
    try {
      this.indicator = ImageIO.read(getClass().getResourceAsStream("/TileSets/deth.png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void update() {
    if (this.tin != null)
      this.tin.update(); 
    if (this.tout != null)
      this.tout.update(); 
  }
  
  public void draw(Graphics2D g) {
    g.setColor(Color.white);
    g.setFont(this.title);
    g.drawString("TOWER OF DARKNESS", 60, 150);
    g.setFont(this.title.deriveFont(0, 25.0F));
    g.setColor(Color.gray.darker());
    g.drawString("Make it to the top, if you truly dare...", 130, 180);
    for (int i = 0; i < this.options.length; i++) {
      g.setColor(Color.gray);
      g.drawString(this.options[i], 310 - this.options[i].length() * 7, 242 + i * 8 * 5);
      g.setColor(Color.white);
      g.drawString(this.options[i], 310 - this.options[i].length() * 7, 240 + i * 8 * 5);
      if (i == this.currentOption)
        g.drawImage(this.indicator, 283 - this.options[i].length() * 7, 220 + i * 8 * 5, 20, 20, null); 
    } 
    g.setColor(this.orange);
    g.drawString("QBERT STUDIOS - 2021", 5, Panel.height - 15);
    if (this.tin != null)
      this.tin.draw(g); 
    if (this.tout != null)
      this.tout.draw(g); 
  }
  
  public void keyPressed(int key, char ch) {
    ch = Character.toLowerCase(ch);
    if (key == 10 && !this.activated)
      select(); 
    if (ch == 'w') {
      this.currentOption--;
      SoundSystem.playSound("select", -10.0F, false);
    } 
    if (ch == 's') {
      this.currentOption++;
      SoundSystem.playSound("select", -10.0F, false);
    } 
    if (this.currentOption < 0)
      this.currentOption = 0; 
    if (this.currentOption > this.options.length - 1)
      this.currentOption = this.options.length - 1; 
  }
  
  private void select() {
    this.activated = true;
    this.tin = new TransitionIn();
    SoundSystem.playSound("start", -10.0F, false);
    if (this.currentOption == 0) {
      this.tin = new TransitionIn();
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              MenuState.this.gsm.currentState = 2;
              MenuState.this.activated = false;
            }
          },  1500L);
    } 
    if (this.currentOption == 1)
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              TowerState ts = (TowerState)MenuState.this.gsm.states.get(2);
              Server s = new Server();
              s.init(ts);
              MenuState.this.gsm.currentState = 2;
              MenuState.this.activated = false;
              MenuState.this.tout = new TransitionOut();
              MenuState.this.tin = null;
            }
          },  1500L); 
    if (this.currentOption == 2) {
      final String address = JOptionPane.showInputDialog("Server Address:");
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              TowerState ts = (TowerState)MenuState.this.gsm.states.get(2);
              Client c = new Client();
              c.init(ts, address);
              MenuState.this.gsm.currentState = 2;
              MenuState.this.activated = false;
              MenuState.this.tout = new TransitionOut();
              MenuState.this.tin = null;
            }
          },1500L);
    } 
    if (this.currentOption == 3)
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              MenuState.this.gsm.currentState = 1;
              MenuState.this.activated = false;
              MenuState.this.tout = new TransitionOut();
              MenuState.this.tin = null;
            }
          },  1500L); 
    if (this.currentOption == 4)
      (new Timer()).schedule(new TimerTask() {
            public void run() {
              System.exit(0);
            }
          },  1500L); 
  }
  
  public void keyReleased(int key, char ch) {}
  
  public void mouseClicked(int x, int y) {}
  
  public void mouseMoved(int x, int y) {}
}
