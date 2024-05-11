package UltraEngine.AddOns;

import UltraEngine.Shapes.Tile;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLClassLoader;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class TileMap {
  private int x;
  
  private int y;
  
  private int tileCellSize;
  
  private int tileSize;
  
  private int[][] map;
  
  private int mapWidth;
  
  private int mapHeight;
  
  private BufferedImage spriteSheet;
  
  public BufferedImage[] allTiles;
  
  private ArrayList<BufferedImage> allTilesAL = new ArrayList<>();
  
  public ArrayList<Tile> tiles = new ArrayList<>();
  
  public TileMap(String s, String tileSet, int tileCellSize, int tileSize) {
    this.tileCellSize = tileCellSize;
    this.tileSize = tileSize;
    loadSpriteSheet(tileSet);
    try {
      InputStreamReader isr = new InputStreamReader(URLClassLoader.getSystemResourceAsStream("maps/" + s + ".txt"));
      BufferedReader br = new BufferedReader(isr);
      this.mapWidth = Integer.parseInt(br.readLine());
      this.mapHeight = Integer.parseInt(br.readLine());
      this.map = new int[this.mapHeight][this.mapWidth];
      String delims = " ";
      for (int row = 0; row < this.mapHeight; row++) {
        String line = br.readLine();
        String[] tokens = line.split(delims);
        for (int col = 0; col < this.mapWidth; col++)
          this.map[row][col] = Integer.parseInt(tokens[col]); 
      } 
      br.close();
      loadTiles();
      addTiles();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void translate(double x, double y) {
    for (Tile t : this.tiles) {
      t.x = (int)(t.x + x);
      t.y = (int)(t.y + y);
      t.hitBox.x = t.x;
      t.hitBox.y = t.y;
    } 
  }
  
  private void loadTiles() {
    for (int y = 0; y < this.spriteSheet.getHeight() / this.tileCellSize; y++) {
      for (int x = 0; x < this.spriteSheet.getWidth() / this.tileCellSize; x++)
        this.allTilesAL.add(this.spriteSheet.getSubimage(x * this.tileCellSize, y * this.tileCellSize, this.tileCellSize, this.tileCellSize)); 
    } 
    this.allTiles = new BufferedImage[this.allTilesAL.size()];
    for (int i = 0; i < this.allTilesAL.size(); i++)
      this.allTiles[i] = this.allTilesAL.get(i); 
  }
  
  private void addTiles() {
    for (int row = 0; row < this.mapHeight; row++) {
      for (int col = 0; col < this.mapWidth; col++) {
        int rc = this.map[row][col] - 1;
        if (rc != -1)
          this.tiles.add(new Tile(rc, this.allTiles[rc], this.x + col * this.tileSize, 
                this.y + row * this.tileSize, this.tileSize, this.tileSize)); 
      } 
    } 
  }
  
  public void loadSpriteSheet(String tileSet) {
    try {
      this.spriteSheet = ImageIO.read(getClass().getResourceAsStream("/TileSets/" + tileSet + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void draw(Graphics2D g) {
    ArrayList<Tile> tilesClone = (ArrayList<Tile>)this.tiles.clone();
    for (Tile t : tilesClone)
      t.draw(g); 
  }
}
