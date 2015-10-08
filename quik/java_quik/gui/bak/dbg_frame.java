// 1998/10/11 Sugihara
// Debug Window

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class DBG_Frame extends Frame
implements ActionListener, WindowListener {
  Button bt1, btProg, btIO, btOnt, btNode, btRules;
  MenuItem mif1, mic1;
  DBG_Main dbgMain;
  DBG_Config config;
  DBG_ShowProgram showprogram;
  DBG_ShowRules showrules;

  // コンストラクタ
  public DBG_Frame() {
    super("Debug Window");

    // メニューバー
    MenuBar mb = new MenuBar();
    setMenuBar(mb);
    // Frame Bar
    Menu meFrame = new Menu("Frame Bar");
    mb.add(meFrame);
    mif1 = addMenuItem("Quit", mif1, meFrame);
    // Configure
    Menu meConfig = new Menu("Configure Bar");
    mb.add(meConfig);
    mic1 = addMenuItem("Configure", mic1, meConfig);

    dbgMain = new DBG_Main();
    dbgMain.setBackground(Color.white);

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(layout);

    // 表示ボタン
    c.anchor = GridBagConstraints.WEST;
    btProg = addButton("Program", btProg, layout, c);
    btIO = addButton("I/O", btIO, layout, c);
    btOnt = addButton("Ontology", btOnt, layout, c);
    btNode = addButton("Node", btNode, layout, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    //c.gridwidth = GridBagConstraints.RELATIVE;
    btRules = addButton("Rules", btNode, layout, c);

    // ラベル
    Label lb1 = new Label("This is Debug Window", Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));
    c.anchor = GridBagConstraints.CENTER;
    layout.setConstraints(lb1, c);
    add(lb1);

    // ツリー
    layout.setConstraints(dbgMain, c);
    add(dbgMain);

    // ボタン
    c.fill = GridBagConstraints.BOTH;
    bt1 = addButton("Set BreakPoint", bt1, layout, c);

    setSize(410,540);
    addWindowListener(this);
  }

  // メニューアイテムの追加
  protected MenuItem addMenuItem(String label, MenuItem mi, Menu menu) {
    mi = new MenuItem(label);
    menu.add(mi);
    mi.addActionListener(this);
    return mi;
  }

  // ボタンの追加
  protected Button addButton(String label, Button bt,
			     GridBagLayout layout, GridBagConstraints c) {
    bt = new Button(label);
    layout.setConstraints(bt, c);
    add(bt);
    bt.addActionListener(this);
    return bt;
  }

  public static void main(String argv[]) {
    DBG_Frame f = new DBG_Frame();

    f.start();
  }

  public void start() {
    show();
    dbgMain.init();
    dbgMain.start();
    addWindowListener(this);
    //removeWindowListener(this);
  }

  //  public void paint() {
  //  }

  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    //String str = e.getActionCommand();

    // BPセットのボタンが押された
    if (obj == bt1) {
      System.out.println("Push button.");
      DBG_DataMedi.setBreakPoint();
    // コンフィグのメニューが選択された
    } else if (obj == mic1) {
      if (config == null) {
        config = new DBG_Config();
        config.start();
      } else {
        if (config.isShowing())
          config.setVisible(false);
        else
	  config.show();
      }
    // ShowProgram が選択された
    } else if (obj == btProg) {
      if (showprogram == null) {
        showprogram = new DBG_ShowProgram();
        showprogram.start();
      } else {
        if (showprogram.isShowing())
          showprogram.setVisible(false);
        else
	  showprogram.show();
      }
    // ShowRules が選択された
    } else if (obj == btRules) {
      if (showrules == null) {
        showrules = new DBG_ShowRules();
        showrules.start();
      } else {
        if (showrules.isShowing())
          showrules.setVisible(false);
        else
	  showrules.show();
      }
    }
  }

  // フレームのクローズ
  public void windowClosing(WindowEvent we) {
    setVisible(false);
  }

  public void windowClosed(WindowEvent we) {}
  public void windowOpened(WindowEvent we) {}
  public void windowIconified(WindowEvent we) {}
  public void windowDeiconified(WindowEvent we) {}
  public void windowActivated(WindowEvent we) {}
  public void windowDeactivated(WindowEvent we) {}
}
