// User Interface
// Mediator Tree
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/19
// Last Update 1998/01/27

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_MediTree extends Canvas
implements Runnable, KeyListener, MouseListener, MouseMotionListener {
  final int rSize = 14;
  final int pmSize = 9;

  int sleepTime = 50;
  int width, height;
  int theta = 0;
  int leaf_y = 0;

  boolean leftFlag, rightFlag, upFlag, downFlag, spaceFlag;

  Image dbuff, im[], imPm[];
  Graphics bg;
  Font f;
  Thread runner;

  int total = 1; // 内包している葉の総数
  int line = 1; // 表示している葉の総数
  int index_num; // インデックス作成時用
  boolean flag; // インデックス作成時用
  int cur_index = 0; // 現在選択されているインデックス
  UI_TreeData index[]; // 葉のインデックス
  int max_width; // 最大横幅

  UI_TreeData top; // 葉の頂上
  UI_TreeData cur; // 葉の現在地

  // コンストラクタ
  public UI_MediTree() {
    this(400, 400);
  }

  public UI_MediTree(int w, int h) {
    super();
    setSize(width = w, height = h);
  }

  // 初期化
  public void init() {
    leftFlag = rightFlag = upFlag = downFlag = spaceFlag = false;

    init_bg();
    init_data();
    init_image();

    makeTree();
  }

  // バックグラウンドの初期化
  public void init_bg() {
    dbuff = createImage(width, height);
    bg = dbuff.getGraphics();
    bg.setFont(new Font("Dialog", Font.PLAIN, rSize));
  }

  // データの初期化
  public void init_data() {
    top = cur = new UI_TreeData(this, "Mediator");
    top.var = 1;
    top.state = true;
    // 最大横幅
    max_width = rSize + top.name_width;
  }

  // イメージの初期化
  public void init_image() {
    // マーク
    im = new Image[2];
    im[0] = makeMark(Color.yellow);
    im[1] = makeMark(Color.red);
    //im[0] = Toolkit.getDefaultToolkit().getImage("quikn0.gif");
    //im[1] = Toolkit.getDefaultToolkit().getImage("quikn1.gif");

    // プラスとマイナス
    imPm = new Image[2];
    imPm[0] = makePm(true);
    imPm[1] = makePm(false);
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
      //repaint();
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
  }

  // 木のマークの描画
  public Image makeMark(Color c) {
    Image im = createImage(rSize, rSize);
    Graphics ig = im.getGraphics();

    ig.setColor(Color.white);
    ig.fillRect(0, 0, rSize, rSize);
    ig.setColor(c);
    ig.fillArc(1, 1, rSize - 2, rSize - 2, 0, 360);
    ig.setColor(Color.black);
    ig.drawArc(1, 1, rSize - 2, rSize - 2, 0, 360);
    return im;
  }

  // プラスとマイナスのマークの描画
  public Image makePm(boolean b) {
    Image im = createImage(pmSize, pmSize);
    Graphics ig = im.getGraphics();
    int h = pmSize / 2;

    ig.setColor(Color.white);
    ig.fillRect(0, 0, pmSize - 1, pmSize - 1);
    ig.setColor(Color.gray);
    ig.drawRect(0, 0, pmSize - 1, pmSize - 1);
    ig.setColor(Color.black);
    ig.drawLine(2, h, pmSize - 3, h);
    if (b) ig.drawLine(h, 2, h, pmSize - 3);
    return im;
  }

  // 枝の描画
  public void drawNode(Graphics g, UI_TreeData td) {
    int x = td.up_num * rSize;
    int y = leaf_y * rSize;
    int h = rSize / 2;

    // 横の線
    if (td.up_num > 0)
      g.drawLine(x - rSize + h, y + h, x - 1, y + h);

    leaf_y++;

    // 子がいる場合のループ
    if (td.state)
      for (UI_TreeData loop = td.down; loop != null; loop = loop.next) {
	// 縦の線
	if (loop.next == null)
	  g.drawLine(x + h, y + rSize, x + h, leaf_y * rSize + h);
	drawNode(g, loop);
      }
  }

  // 葉の描画
  public void drawLeaf(Graphics g, UI_TreeData td) {
    int x = td.up_num * rSize;
    int y = leaf_y * rSize;
    int h = rSize / 2;
    int pm = rSize / 2 - pmSize / 2;

    // 葉を描画する
    // プラス、マイナス
    if (td.up_num > 0 && td.down_num > 0)
      g.drawImage(imPm[(td.state ? 1 : 0)], x - rSize + pm, y + pm, this);
    // マーク
    g.drawImage(im[td.var], x, y, this);
    g.setColor(Color.black);
    // 名前
    g.drawString(td.name, x + rSize, y + rSize - 2);

    // 選択されているなら色を反転
    if (cur_index == leaf_y) {
      g.setXORMode(Color.blue);
      g.setColor(Color.white);
      g.fillRect(x + rSize, y, td.name_width, rSize);
      g.setPaintMode();
    }
    leaf_y++;

    // 子がいる場合のループ
    if (td.state)
      for (UI_TreeData loop = td.down; loop != null; loop = loop.next)
	drawLeaf(g, loop);
  }

  // 木の作成
  public void makeTree() {
    // 縦幅、横幅の更新
    width = max_width;
    height = line * rSize;

    init_bg();

    // イメージバッファのクリア
    bg.setColor(Color.white);
    bg.fillRect(0, 0, width, height);

    leaf_y = 0;
    bg.setColor(Color.gray);
    drawNode(bg, top);
    leaf_y = 0;
    drawLeaf(bg, top);

    setSize(width, height);
    repaint();
  }

  // 最大横幅の更新
  public void resetWidth(UI_TreeData td) {
    int w = (td.up_num + 1) * rSize + td.name_width;
    if (w > max_width) max_width = w;
  }

  // インデックスの更新
  public void resetIndex() {
    total++;
    index_num = 0;
    flag = false;
    max_width = rSize + top.name_width;
    index = new UI_TreeData[total];

    resetIndex_loop(top);

    line = index_num;
  }

  // インデックスの更新用
  public void resetIndex_loop(UI_TreeData td) {
    if ((index[index_num] = td) == cur) {
      flag = true;
      cur_index = index_num;
    }
    resetWidth(td);
    index_num++;

    if (td.state)
      for (UI_TreeData loop = td.down; loop != null; loop = loop.next)
	resetIndex_loop(loop);
  }

  // クリックされた時
  public void clickTree(int x, int y) {
    int n = y / rSize;

    if (n >= total) return;

    UI_TreeData td = index[n];

    int bx = td.up_num * rSize;

    // プラス、マイナスをクリックした時
    if (x >= bx - rSize && x < bx && td.down_num > 0) {
      td.state = !td.state;
      resetIndex();
      if (!flag) {
	cur_index = n;
	cur = td;
      }
    // 葉をクリックした時
    } else if (x >= bx && x < bx + rSize + td.name_width) {
      cur_index = n;
      cur = td;
    }
  }

  // キー入力
  public void keyPressed(KeyEvent evt) {
    KeyFlags.keyPressedCheck(evt.getKeyCode());
    //DBG_DataMedi.onKey();
  }

  // キー入力が行われた時に自動的に呼び出される
  public void keyTyped(KeyEvent evt) {}

  // キーが放された時に自動的に呼び出される
  public void keyReleased(KeyEvent evt) {
    KeyFlags.keyReleasedCheck(evt.getKeyCode());
    //System.out.println("um.");
  }

  public void mousePressed(MouseEvent evt) {}

  // マウスがクリックされた時
  public void mouseClicked(MouseEvent evt) {
    clickTree(evt.getX(), evt.getY());
    // 木の作成
    makeTree();
    //System.out.println("curIndex = " + UI_TreeData.cur.name);
  }

  public void mouseReleased(MouseEvent evt) {}
  public void mouseEntered(MouseEvent evt) {}
  public void mouseExited(MouseEvent evt) {}
  public void mouseDragged(MouseEvent evt) {}
  public void mouseMoved(MouseEvent evt) {}

  // For Test
  public void addTreeTest(UI_TreeData top) {
    UI_TreeData m1 = top.add("m1");
    UI_TreeData m2 = m1.add("m2");
    m2.add("m6");
    m2.add("m9");

    top.add("m3");
    top.add("m4");
    top.add("m5");
  }
}
