package UltraEngine;

import java.awt.Graphics2D;

public abstract class Game {
  public abstract void update();
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  public abstract void keyPressed(int paramInt, char paramChar);
  
  public abstract void keyReleased(int paramInt, char paramChar);
  
  public abstract void mouseClicked(int paramInt1, int paramInt2);
  
  public abstract void mouseMoved(int paramInt1, int paramInt2);
}


