package UltraEngine.AddOns;

import UltraEngine.Renderer.Panel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class TransitionIn {
  int eventCount = 0;
  
  boolean eventTransition = true;
  
  ArrayList<Rectangle> tb = new ArrayList<>();
  
  public void update() {
    if (this.eventTransition)
      eventTick(); 
  }
  
  private void eventTick() {
    this.eventCount++;
    if (this.eventCount == 1) {
      this.tb.clear();
      this.tb.add(new Rectangle(
            Panel.width / 2, Panel.height / 2, 0, 0));
    } else if (this.eventCount > 30) {
      ((Rectangle)this.tb.get(0)).x -= 12;
      ((Rectangle)this.tb.get(0)).y -= 8;
      ((Rectangle)this.tb.get(0)).width += 24;
      ((Rectangle)this.tb.get(0)).height += 16;
    } 
  }
  
  public void draw(Graphics2D g) {
    g.setColor(Color.BLACK);
    for (int i = 0; i < this.tb.size(); i++)
      g.fill(this.tb.get(i)); 
  }
}
