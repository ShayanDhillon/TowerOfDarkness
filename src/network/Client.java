package network;

import UltraEngine.AddOns.Player2;
import UltraEngine.GameState.GameState;
import UltraEngine.GameState.TowerState;
import entity.Flash;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
  Socket s;
  
  InputStreamReader isr;
  
  BufferedReader br;
  
  PrintWriter pw;
  
  TowerState ts;
  
  public void init(final TowerState ts, String address) {
    this.ts = ts;
    try {
      this.s = new Socket(address, 1337);
      ts.p2 = new Player2((GameState)ts, 537, 396, 32, 32);
      this.isr = new InputStreamReader(this.s.getInputStream());
      this.br = new BufferedReader(this.isr);
      this.pw = new PrintWriter(this.s.getOutputStream());
      ts.pw = this.pw;
      (new Timer()).scheduleAtFixedRate(new TimerTask() {
            public void run() {
              try {
                Client.this.pw.println("pu " + ts.p.x + " " + (ts.PZ.y - ts.p.y) + " " + ts.p.keyUp + " " + ts.p.keyLeft + " " + ts.p.keyDown + " " + ts.p.keyRight + " " + ts.p.jumping + " " + ts.p.facingLeft + " " + ts.p.hearts);
                Client.this.pw.flush();
                String[] tokens = Client.this.br.readLine().split(" ");
                if (tokens[0].equals("pu")) {
                  int x = Integer.parseInt(tokens[1]);
                  int y = Integer.parseInt(tokens[2]);
                  boolean w = Boolean.parseBoolean(tokens[3]);
                  boolean a = Boolean.parseBoolean(tokens[4]);
                  boolean s = Boolean.parseBoolean(tokens[5]);
                  boolean d = Boolean.parseBoolean(tokens[6]);
                  boolean jumping = Boolean.parseBoolean(tokens[7]);
                  boolean facingLeft = Boolean.parseBoolean(tokens[8]);
                  int hearts = Integer.parseInt(tokens[9]);
                  ts.p2.x = x;
                  ts.p2.y = ts.PZ.y - y;
                  ts.p2.w = w;
                  ts.p2.a = a;
                  ts.p2.s = s;
                  ts.p2.d = d;
                  ts.p2.jumping = jumping;
                  ts.p2.facingLeft = facingLeft;
                  ts.p2.hearts = hearts;
                } 
                if (tokens[0].equals("flash"))
                  ts.flashList.add(new Flash(ts.p2.x, ts.p2.y, ts.flashImages)); 
              } catch (IOException e) {
                ts.disconnected = true;
              } 
            }
          },1L, 1L);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
