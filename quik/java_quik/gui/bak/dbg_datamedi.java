// 1998/10/11 Sugihara
// Debug Main

package java_quik.gui;
import java_quik.*;

import java.awt.*;

public class DBG_DataMedi {

  public DBG_DataMedi(String name) {
    this.name = name;
    num = 0;
    bp = false;
    up = null;
    prev = this;
    next = this;
    down = null;
  }

  public DBG_DataMedi(DBG_DataMedi up, String name) {
    this(name);
    this.up = up;
    up.addData(this);
  }

  public void addData(DBG_DataMedi medi) {
    num++;
    if (num == 1) {
      down = medi.next = medi.prev = medi;
    } else {
      medi.prev = down.prev;
      medi.next = down;
      down = medi.prev.next = medi.next.prev = medi;
    }
  }

  // キーが押された時の判定
  public static void onKey() {
    if (KeyFlags.left && dirX != 1 && cur.up != null) {
      curOld = cur;
      cur = cur.next;
      cur.up.down = cur;
      dirX = 1;
      movX += maxX;
    }
    if (KeyFlags.right && dirX != -1 && cur.up != null) {
      curOld = cur;
      cur = cur.prev;
      cur.up.down = cur;
      dirX = -1;
      movX -= maxX;
    }
    if (KeyFlags.up && cur.up != null && dirY != 1) {
      curOld = cur;
      cur = cur.up;
      dirY = 1;
      movY += maxY;
    }
    if (KeyFlags.down && cur.down != null && dirY != -1) {
      curOld = cur;
      cur = cur.down;
      dirY = -1;
      movY -= maxY;
    }
    if (KeyFlags.enter || KeyFlags.space)
      setBreakPoint();
  }

  public static void moveMedi() {
    if (dirX != 0) {
      movX = (movX + dirX) % maxX;
      if (movX == 0) {
	dirX = 0;
	curOld = cur;
      }
    }
    if (dirY != 0) {
      movY = (movY + dirY) % maxY;
      if (movY == 0) {
	dirY = 0;
	curOld = cur;
      }
    }
  }

  // 描画
  public static void dispTree(Graphics g) {
    if (curOld.down != null) {
      double r = (double)movX / (double)maxX;
      int n = curOld.up != null ? curOld.up.num : 1;
      r = (r / (double)n * 2.0 + 0.5) * Math.PI;
      int x = (int)(Math.cos(r) * 100);
      int y = (int)(Math.sin(r) * 20);
      dispMedi(g, 200 + x, 290 + y, 40, curOld.down, false);
    }
    dispMedi(g, 200, 200, 30, curOld, true);
    if (curOld.up != null)
      dispMedi(g, 200, 90, 20, curOld.up, false);
  }

  public static void dispMedi(Graphics g, int x, int y, int w,
			      DBG_DataMedi ddm, boolean turnFlag) {
    int i, j, dx, dy, n;
    boolean upFlag;
    DBG_DataMedi m = ddm;

    y += movY * 100 / maxY;

    if (upFlag = (m.up != null)) n = m.up.num;
    else n = 1;

    boolean bpFlag[] = new boolean[n];
    for (i = 0; i < n; i++) {
      bpFlag[i] = m.bp;
      m = m.prev;
    }

    for (i = 0; i < n; i++) {
      j = n / 2;
      if (i < j) j += i + n % 2;
      else j = n - i - 1;

      double r = turnFlag ? (double)movX / (double)maxX : 0.0;
      r = ((r + (double)j) / (double)n * 2.0 + 0.5) * Math.PI;

      dx = (int)(Math.cos(r) * 100) + x;
      dy = (int)(Math.sin(r) * 20) + y;
      if (upFlag) {
	g.setColor(Color.black);
	g.drawLine(x, y - 100, dx, dy);
      }
      DBG_Main.myDrawBall(g, dx, dy,
			   w + (int)(Math.sin(r) * 10) + movY * 10 / maxY,
			   bpFlag[j] ? bpColor : stColor, true);
    }
  }

  // ブレークポイントの設置
  public static void setBreakPoint() {
    cur.bp = !cur.bp;
  }

  public void printData() {
    DBG_DataMedi m = down;
    String str = name + ": ";

    for (int i = 0; i < num; i++) {
      str += m.name + " -> ";
      m = m.next;
    }
    System.out.println(str + "end.");
  }

  public String name; // メディエータの名前
  public int num; // 下につながっているメディエータの数
  public boolean bp; // 下につながっているメディエータの数
  public DBG_DataMedi up; // 上につながっているメディエータ
  public DBG_DataMedi prev; // 前につながっているメディエータ
  public DBG_DataMedi next; // 次につながっているメディエータ
  public DBG_DataMedi down; // 下につながっているメディエータの頭

  static int maxX = 8; // きざみ
  static int maxY = maxX; // きざみ

  static Color stColor = Color.gray;
  static Color bpColor = Color.green;

  public static DBG_DataMedi cur; // メディエータの現在地
  public static DBG_DataMedi curOld; // メディエータの現在地保存
  public static int dirX = 0; // 表示用
  public static int dirY = 0; // 表示用
  public static int movX = 0; // 表示用
  public static int movY = 0; // 表示用
}
