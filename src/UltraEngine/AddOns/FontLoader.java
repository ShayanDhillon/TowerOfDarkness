package UltraEngine.AddOns;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;

public class FontLoader {
  public static Font getFont(String fileName, int size) {
    try {
      return Font.createFont(0, new File(fileName)).deriveFont(0, size);
    } catch (FontFormatException|java.io.IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
}
