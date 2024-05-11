package UltraEngine.Systems;

import java.net.URLClassLoader;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundSystem {
  static ArrayList<Clip> clips = new ArrayList<>();
  
  public static void playSound(String file, float volume, boolean loop) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(URLClassLoader.getSystemResource("audio/" + file + ".wav"));
      Clip clip = AudioSystem.getClip();
      clip.open(ais);
      FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(volume);
      clip.start();
      if (loop)
        clip.loop(-1); 
      clips.add(clip);
    } catch (UnsupportedAudioFileException|java.io.IOException|javax.sound.sampled.LineUnavailableException e) {
      e.printStackTrace();
    } 
  }
  
  public static void stopClips() {
    ArrayList<Clip> cC = (ArrayList<Clip>)clips.clone();
    for (Clip c : cC) {
      c.stop();
      clips.remove(c);
    } 
  }
}
