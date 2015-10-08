// User Interface
// UI_WaitingPanel
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/27
// Last Update 1998/01/27
//PixelGrabber つかってみんさい

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;

public class UI_WaitingPanel extends Canvas
implements Runnable, MouseListener, MouseMotionListener {
  final int rSize = 12;

  int sleepTime = 100;
  int width, height;
  int zSpeed = 6;
  int rSpeed = 15 * 2;
  int theta;
  boolean move;

  Image dbuff, bgim0, bgim1, im[];
  Graphics bg, bgg0, bgg1;
  Thread runner;
  Color bgColor = Color.black;

  // コンストラクタ
  public UI_WaitingPanel() {
    this(36, 36);
  }

  public UI_WaitingPanel(int w, int h) {
    super();
    setSize(width = w, height = h);
  }

  // 初期化
  public void init() {
    init_bg();
    init_image();

    move = true;

    theta = rSpeed;
    //while (theta >= rSpeed) change();
  }

  // バックグラウンドの初期化
  public void init_bg() {
    dbuff = createImage(width, height);
    bg = dbuff.getGraphics();
    bgim0 = createImage(width, height);
    bgg0 = bgim0.getGraphics();
    bgim1 = createImage(width - zSpeed * 2, height - zSpeed * 2);
    bgg1 = bgim1.getGraphics();

    // イメージバッファのクリア
    bgg0.setColor(bgColor);
    bgg0.fillRect(0, 0, width, height);
  }

  // イメージの初期化
  public void init_image() {
    im = new Image[3];

    im[0] = transImage(myDrawBall(Color.yellow));
    im[1] = transImage(myDrawBall(Color.red));
    im[2] = transImage(myDrawBall(Color.green));
  }

  // イメージの初期化(イメージファイルの読み込み)
  public void init_loadImage() {
    im = new Image[3];
    im[0] = Toolkit.getDefaultToolkit().getImage("quikn0.gif");
    im[1] = Toolkit.getDefaultToolkit().getImage("quikn1.gif");
    im[2] = Toolkit.getDefaultToolkit().getImage("quikn1.gif");
  }

  // イメージの透過
  public Image transImage(Image img) {
    int pix[] = new int[rSize * rSize];
    PixelGrabber pg = new PixelGrabber(img, 0, 0, rSize, rSize,
				       pix, 0, rSize);
    try {
      pg.grabPixels();
    } catch (Throwable e) {
      System.out.println("interruptted waiting for pixels!");
      return img;
    }

    if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
      System.err.println("image fetch aborted or errored");
      return img;
    }

    // 透過の処理
    for (int i = 0; i < rSize * rSize; i++) {
      // 要素の意味は 0xAARRGGBB となる。
      // ちなみに A は α (透過のパーセンテージ)、
      // RGB は、それぞれ赤、緑、青の輝度である。
      // 下の処理は、黒を透過させている。
      if (pix[i] == 0xff000000)
        pix[i] &= 0x00ffffff;
    }

    // 新しくイメージデータを作成して返す
    return createImage(new MemoryImageSource(rSize, rSize, pix, 0, rSize));
  }

  // スレッドの開始
  public void start() {
    runner = new Thread(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    runner.start();
    requestFocus();
  }

  // スレッドの停止
  public void stop() {
    runner.stop();
    removeMouseListener(this);
    removeMouseMotionListener(this);
  }

  // スレッドの実行
  public void run() {
    while (true) {
      if (move) change();
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
    // イメージバッファを画面に表示
    g.drawImage(dbuff, 0, 0, this);
    g.setColor(new Color(0xc3, 0xc3, 0xc3));
    g.setColor(new Color(0xff, 0xff, 0xff));
    g.draw3DRect(0, 0, width - 1, height - 1, true);
    g.draw3DRect(1, 1, width - 2, height - 2, true);
  }

  // 更新
  public void change() {
    bgg1.drawImage(bgim0, 0, 0, width - zSpeed * 2, height - zSpeed * 2,
		   0, 0, width, height, this);

    // イメージバッファのクリア
    bgg0.setColor(bgColor);
    bgg0.fillRect(0, 0, width, height);
    bgg0.drawImage(bgim1, zSpeed, zSpeed, this);

    theta = (theta + rSpeed) % 360;

    double r = (double)theta / 180.0 * Math.PI;
    int w = (width - rSize) / 2, h = (height - rSize) / 2;
    int s = (int)(Math.sin(r) * (w - 2));
    int c = (int)(Math.cos(r) * (h - 2));
    int x = c + s / 2, y = c - s / 2;

    bgg0.drawImage(im[0], w + x, h + y, this);
    bgg0.drawImage(im[1], width - rSize - w - x, h + y, this);
    bgg0.drawImage(im[2], w + c / 3, h + s, this);

    // 枠の描画
    bg.drawImage(bgim0, 0, 0, this);
    bg.setColor(new Color(0xc3, 0xc3, 0xc3));
    bg.draw3DRect(0, 0, width - 1, height - 1, true);
    bg.draw3DRect(1, 1, width - 2, height - 2, true);

    repaint();
  }

  // 球体描画
  public Image myDrawBall(Color col) {
    Image im = createImage(rSize, rSize);
    Graphics ig = im.getGraphics();
    ig.setColor(bgColor);
    ig.fillRect(0, 0, rSize, rSize);

    myDrawBall(ig, 0, 0, col);

    return im;
  }

  // 球体描画
  public void myDrawBall(Graphics g, int x, int y, Color col) {
    double p;
    int d, f = 4;
    int rd = col.getRed();
    int gr = col.getGreen();
    int bl = col.getBlue();

    for (int i = 0; i < f; i++) {
      p = (double)(i + 2) / (double)(f + 1);
      g.setColor(new Color((int)(rd * p), (int)(gr * p), (int)(bl * p)));

      d = rSize - 1 - i * 2;
      if (d <= 0) d = 1;
      g.fillArc(x + i, y + i, d, d, 0, 360);
    }
  }

  public void move() {
    move = true;
  }

  public void still() {
    move = false;
  }

  public void mousePressed(MouseEvent evt) {}

  // マウスがクリックされた時
  public void mouseClicked(MouseEvent evt) {
    move = !move;
  }

  public void mouseReleased(MouseEvent evt) {}
  public void mouseEntered(MouseEvent evt) {}
  public void mouseExited(MouseEvent evt) {}
  public void mouseDragged(MouseEvent evt) {}
  public void mouseMoved(MouseEvent evt) {}
}
