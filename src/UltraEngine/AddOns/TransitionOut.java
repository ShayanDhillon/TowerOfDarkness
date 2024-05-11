package UltraEngine.AddOns;

import UltraEngine.Renderer.Panel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class TransitionOut {
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
      this.tb.add(new Rectangle(0, 0, Panel.width, Panel.height / 2));
      this.tb.add(new Rectangle(0, 0, Panel.width / 2, Panel.height));
      this.tb.add(new Rectangle(0, Panel.height / 2, Panel.width, Panel.height / 2));
      this.tb.add(new Rectangle(Panel.width / 2, 0, Panel.width / 2, Panel.height));
    } 
    if (this.eventCount > 1 && this.eventCount < 60) {
      ((Rectangle)this.tb.get(0)).height -= 8;
      ((Rectangle)this.tb.get(1)).width -= 12;
      ((Rectangle)this.tb.get(2)).y += 8;
      ((Rectangle)this.tb.get(3)).x += 12;
    } 
    if (this.eventCount == 60) {
      this.eventCount = 0;
      this.eventTransition = false;
      this.tb.clear();
    } 
  }
  
  public void draw(Graphics2D g) {
    g.setColor(Color.BLACK);
    for (int i = 0; i < this.tb.size(); i++)
      g.fill(this.tb.get(i)); 
  }
}
