package UltraEngine.Systems;

import UltraEngine.Particles.Particle;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ParticleSystem {
  ArrayList<Particle> particles = new ArrayList<>();
  
  public void update() {
    ArrayList<Particle> guh = (ArrayList<Particle>)this.particles.clone();
    for (Particle p : guh)
      p.update(); 
  }
  
  public void draw(Graphics2D g) {
    ArrayList<Particle> guh = (ArrayList<Particle>)this.particles.clone();
    for (Particle p : guh)
      p.draw(g); 
  }
  
  public void addParticle(Particle p) {
    this.particles.add(p);
  }
}
