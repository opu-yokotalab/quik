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

  int total = 1; // ���񤷤Ƥ����դ����
  int line = 1; // ɽ�����Ƥ����դ����
  int index_num; // ����ǥå�����������
  boolean flag; // ����ǥå�����������
  int cur_index = 0; // �������򤵤�Ƥ��륤��ǥå���
  UI_TreeData index[]; // �դΥ���ǥå���
  int max_width; // ���粣��

  UI_TreeData top; // �դ�ĺ��
  UI_TreeData cur; // �դθ�����

  // ���󥹥ȥ饯��
  public UI_MediTree() {
    this(400, 400);
  }

  public UI_MediTree(int w, int h) {
    super();
    setSize(width = w, height = h);
  }

  // �����
  public void init() {
    leftFlag = rightFlag = upFlag = downFlag = spaceFlag = false;

    init_bg();
    init_data();
    init_image();

    makeTree();
  }

  // �Хå����饦��ɤν����
  public void init_bg() {
    dbuff = createImage(width, height);
    bg = dbuff.getGraphics();
    bg.setFont(new Font("Dialog", Font.PLAIN, rSize));
  }

  // �ǡ����ν����
  public void init_data() {
    top = cur = new UI_TreeData(this, "Mediator");
    top.var = 1;
    top.state = true;
    // ���粣��
    max_width = rSize + top.name_width;
  }

  // ���᡼���ν����
  public void init_image() {
    // �ޡ���
    im = new Image[2];
    im[0] = makeMark(Color.yellow);
    im[1] = makeMark(Color.red);
    //im[0] = Toolkit.getDefaultToolkit().getImage("quikn0.gif");
    //im[1] = Toolkit.getDefaultToolkit().getImage("quikn1.gif");

    // �ץ饹�ȥޥ��ʥ�
    imPm = new Image[2];
    imPm[0] = makePm(true);
    imPm[1] = makePm(false);
  }

  // ����åɤγ���
  public void start() {
    runner = new Thread(this);
    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    runner.start();
    requestFocus();
  }

  // ����åɤ����
  public void stop() {
    runner.stop();
    removeKeyListener(this);
    removeMouseListener(this);
    removeMouseMotionListener(this);
  }

  // ����åɤμ¹�
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
    // ���᡼���Хåե�����̤�ɽ��
    g.drawImage(dbuff, 0, 0, this);
  }

  // �ڤΥޡ���������
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

  // �ץ饹�ȥޥ��ʥ��Υޡ���������
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

  // �ޤ�����
  public void drawNode(Graphics g, UI_TreeData td) {
    int x = td.up_num * rSize;
    int y = leaf_y * rSize;
    int h = rSize / 2;

    // ������
    if (td.up_num > 0)
      g.drawLine(x - rSize + h, y + h, x - 1, y + h);

    leaf_y++;

    // �Ҥ�������Υ롼��
    if (td.state)
      for (UI_TreeData loop = td.down; loop != null; loop = loop.next) {
	// �Ĥ���
	if (loop.next == null)
	  g.drawLine(x + h, y + rSize, x + h, leaf_y * rSize + h);
	drawNode(g, loop);
      }
  }

  // �դ�����
  public void drawLeaf(Graphics g, UI_TreeData td) {
    int x = td.up_num * rSize;
    int y = leaf_y * rSize;
    int h = rSize / 2;
    int pm = rSize / 2 - pmSize / 2;

    // �դ����褹��
    // �ץ饹���ޥ��ʥ�
    if (td.up_num > 0 && td.down_num > 0)
      g.drawImage(imPm[(td.state ? 1 : 0)], x - rSize + pm, y + pm, this);
    // �ޡ���
    g.drawImage(im[td.var], x, y, this);
    g.setColor(Color.black);
    // ̾��
    g.drawString(td.name, x + rSize, y + rSize - 2);

    // ���򤵤�Ƥ���ʤ鿧��ȿž
    if (cur_index == leaf_y) {
      g.setXORMode(Color.blue);
      g.setColor(Color.white);
      g.fillRect(x + rSize, y, td.name_width, rSize);
      g.setPaintMode();
    }
    leaf_y++;

    // �Ҥ�������Υ롼��
    if (td.state)
      for (UI_TreeData loop = td.down; loop != null; loop = loop.next)
	drawLeaf(g, loop);
  }

  // �ڤκ���
  public void makeTree() {
    // �����������ι���
    width = max_width;
    height = line * rSize;

    init_bg();

    // ���᡼���Хåե��Υ��ꥢ
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

  // ���粣���ι���
  public void resetWidth(UI_TreeData td) {
    int w = (td.up_num + 1) * rSize + td.name_width;
    if (w > max_width) max_width = w;
  }

  // ����ǥå����ι���
  public void resetIndex() {
    total++;
    index_num = 0;
    flag = false;
    max_width = rSize + top.name_width;
    index = new UI_TreeData[total];

    resetIndex_loop(top);

    line = index_num;
  }

  // ����ǥå����ι�����
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

  // ����å����줿��
  public void clickTree(int x, int y) {
    int n = y / rSize;

    if (n >= total) return;

    UI_TreeData td = index[n];

    int bx = td.up_num * rSize;

    // �ץ饹���ޥ��ʥ��򥯥�å�������
    if (x >= bx - rSize && x < bx && td.down_num > 0) {
      td.state = !td.state;
      resetIndex();
      if (!flag) {
	cur_index = n;
	cur = td;
      }
    // �դ򥯥�å�������
    } else if (x >= bx && x < bx + rSize + td.name_width) {
      cur_index = n;
      cur = td;
    }
  }

  // ��������
  public void keyPressed(KeyEvent evt) {
    KeyFlags.keyPressedCheck(evt.getKeyCode());
    //DBG_DataMedi.onKey();
  }

  // �������Ϥ��Ԥ�줿���˼�ưŪ�˸ƤӽФ����
  public void keyTyped(KeyEvent evt) {}

  // �����������줿���˼�ưŪ�˸ƤӽФ����
  public void keyReleased(KeyEvent evt) {
    KeyFlags.keyReleasedCheck(evt.getKeyCode());
    //System.out.println("um.");
  }

  public void mousePressed(MouseEvent evt) {}

  // �ޥ���������å����줿��
  public void mouseClicked(MouseEvent evt) {
    clickTree(evt.getX(), evt.getY());
    // �ڤκ���
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
