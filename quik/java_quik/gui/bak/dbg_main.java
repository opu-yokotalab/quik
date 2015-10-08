// 1998/10/11 Sugihara
// Debug Main

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class DBG_Main extends Canvas
implements Runnable, KeyListener, MouseListener, MouseMotionListener {
  int sleepTime = 50;
  int width, height;
  int theta = 0;
  boolean leftFlag, rightFlag, upFlag, downFlag, spaceFlag;

  DBG_DataMedi dataMedi_top, dataMedi_cur, dataMedi_new, dataMedi_new2;

  Image dbuff;
  Graphics bg;
  Thread runner;

  // コンストラクタ
  public DBG_Main() {
    this(400, 400);
  }

  public DBG_Main(int w, int h) {
    super();
    setSize(width = w, height = h);
  }

  // 初期化
  public void init() {
    leftFlag = rightFlag = upFlag = downFlag = spaceFlag = false;

    dbuff = createImage(width, height);
    bg = dbuff.getGraphics();
    bg.setFont(new Font("Dialog", Font.PLAIN, 16));

    DBG_DataMedi.cur = dataMedi_top = new DBG_DataMedi("Top");
    dataMedi_cur = new DBG_DataMedi(dataMedi_top, "M1");
    dataMedi_cur = new DBG_DataMedi(dataMedi_top, "M2");
    dataMedi_cur = new DBG_DataMedi(dataMedi_top, "M3");
    dataMedi_top.printData();
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M31");
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M32");
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M33");
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M34");
    dataMedi_cur.printData();
    dataMedi_cur = dataMedi_cur.next;
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M21");
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M22");
    dataMedi_cur.printData();
    dataMedi_new2 = new DBG_DataMedi(dataMedi_new, "M221");
    dataMedi_new2 = new DBG_DataMedi(dataMedi_new, "M222");
    dataMedi_new2 = new DBG_DataMedi(dataMedi_new, "M223");
    dataMedi_new2 = new DBG_DataMedi(dataMedi_new, "M224");
    dataMedi_new2 = new DBG_DataMedi(dataMedi_new, "M225");
    dataMedi_new.printData();
    dataMedi_cur = dataMedi_cur.next;
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M11");
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M12");
    dataMedi_new = new DBG_DataMedi(dataMedi_cur, "M13");
    dataMedi_cur.printData();
    DBG_DataMedi.cur = DBG_DataMedi.curOld
      = dataMedi_cur = dataMedi_cur.next;
  }

  // スレッドの開始
  public void start() {
    runner = new Thread(this);
    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    runner.start();
    requestFocus();
  }

  // スレッドの停止
  public void stop() {
    runner.stop();
    removeKeyListener(this);
    removeMouseListener(this);
    removeMouseMotionListener(this);
  }

  // スレッドの実行
  public void run() {
    while (true) {
      repaint();
      try {
        Thread.sleep(sleepTime);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  public void paint(Graphics g){
    update(g);
  }

  public void update(Graphics g) {
    // イメージバッファのクリア
    bg.setColor(Color.white);
    bg.fillRect(0, 0, width, height);

    DBG_DataMedi.moveMedi();
    DBG_DataMedi.dispTree(bg);

    bg.setColor(Color.black);
    bg.drawRect(200 - 24, 220 - 24, 48, 48);
    bg.drawRect(200 - 23, 220 - 23, 46, 46);
    bg.drawString("メディエータ:", 250, 240);
    String str = DBG_DataMedi.cur.name;
    bg.drawString(str, 380 - bg.getFontMetrics().stringWidth(str), 260);

    // イメージバッファを画面に表示
    g.drawImage(dbuff, 0, 0, this);
  }

  // 球体描画
  public static void myDrawBall(Graphics g, int x, int y, int l,
				Color col, boolean flag) {
    int w, r, f = 10;
    double p, q, b = 0.99;
    int rd = col.getRed();
    int gr = col.getGreen();
    int bl = col.getBlue();

    for (int i = 0; i < f; i++) {
      p = (double)i / (double)(f - 1);
      q = Math.tan(p * Math.PI / 4.0);
      w = (int)(l * (1.0 - q * b));
      r = (int)((double)l * q) / 5 + w / 2;
      if (flag)
	g.setColor(new Color(rd + (int)((0xff - rd) * p),
			     gr + (int)((0xff - gr) * p),
			     bl + (int)((0xff - bl) * p)));
      else
	g.setColor(new Color((int)(rd * p), (int)(gr * p), (int)(bl * p)));

      g.fillArc(x - r, y - r, w, w, 0, 360);
    }
  }

  // 球体描画(オーバーロード)
  public void myDrawBall(Graphics g) {
    myDrawBall(bg, (int)(Math.random() * 400), (int)(Math.random() * 400),
	       (int)(Math.random() * 100), 
	       new Color((int)(Math.random() * 0xff),
	       (int)(Math.random() * 0xff), (int)(Math.random() * 0xff)),
	       Math.random() * 2 < 1);
  }

  // キー入力
  public void keyPressed(KeyEvent evt) {
    KeyFlags.keyPressedCheck(evt.getKeyCode());
    DBG_DataMedi.onKey();
  }

  //  キー入力が行われた時に自動的に呼び出される
  public void keyTyped(KeyEvent evt) {
  }

  //  キーが放された時に自動的に呼び出される
  public void keyReleased(KeyEvent evt) {
    KeyFlags.keyReleasedCheck(evt.getKeyCode());
  }

  public void mousePressed(MouseEvent evt) {
    //System.out.println("pressed");
    theta = (theta + 10) % 360;
    repaint();
    requestFocus();
  }
  public void mouseClicked(MouseEvent evt) {
    //System.out.println("clicked");
    repaint();
  }
  public void mouseReleased(MouseEvent evt) {}
  public void mouseEntered(MouseEvent evt) {}
  public void mouseExited(MouseEvent evt) {}
  public void mouseDragged(MouseEvent evt) {}
  public void mouseMoved(MouseEvent evt) {}
}
