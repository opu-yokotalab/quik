// User Interface
// Client Main Window
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/13
// Last Update 1998/01/30

package java_quik.gui;
import java_quik.*;
import java_quik.send_om.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_Client extends UI_Base
implements ActionListener, WindowListener {
  TextArea taCommand, taAnswer;
  String rule, query;
  Label lb1, lb2, lb3;
  Button btStartup, btActivate, btOpen, btClose, btSend,
    btCommandClear, btAnswerClear;
  MenuItem miFileExit, miLatticeOpen, miMonitorOpen, miTracerOpen;

  //  LAT_Main lataf;
  String lattice = " ";
  public String filename = "";    /* 1998/07/10 Sugimoto */

  UI_MediSelect sel;
  UI_MediTree mTree;
  UI_WaitingPanel wp;
//  UI_Monitor mon;
//  UI_Tracer tracer;
  Main parent;

  public boolean Flag = false;

  // コンストラクタ
  public UI_Client(Main parent) {
    super("QUIK Client");
    this.parent = parent;

    // メニューバー
    MenuBar mb = new MenuBar();
    setMenuBar(mb);

    // ファイル
    Menu meFile = new Menu("File");
    mb.add(meFile);

    miFileExit = addMenuItem("Exit", miFileExit, meFile);

/**
    // ラティス
    Menu meLattice  = new Menu("Lattice");
    mb.add(meLattice);

    miLatticeOpen = addMenuItem("Open Lattice", miLatticeOpen, meLattice);

    // デバッグ
    Menu meMonitor = new Menu("Monitor");
    mb.add(meMonitor);

    miMonitorOpen = addMenuItem("Open MonitorWindow", miMonitorOpen, meMonitor);

    // トレーサ
    Menu meTracer  = new Menu("Tracer");
    mb.add(meTracer);

    miTracerOpen = addMenuItem("Open Tracer", miTracerOpen, meTracer);
*/

    // ウィンドウの作成

    // クイックラベル
    QuikLabel lab = new QuikLabel();
    lab.setSize(500,50);

    // ボタンの設置
    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1, 5));
    btStartup = addButton("Startup", btStartup, pnButton);
    btActivate = addButton("Activate", btActivate, pnButton);
    btOpen = addButton("Open", btOpen, pnButton);
    btClose = addButton("Close", btClose, pnButton);
    btSend = addButton("Send Query", btSend, pnButton);

    // テキストエリアのラベル
    Panel pnLabel = new Panel();
    pnLabel.setLayout(new GridLayout(1, 2));

    lb1 = new Label("COMMAND WINDOW");
    lb2 = new Label("ANSWER WINDOW");

    pnLabel.add(lb1);
    pnLabel.add(lb2);

    // テキストエリア
    Panel pnText = new Panel();
    pnText.setLayout(new GridLayout(1, 2));

    taCommand = new TextArea(13, 20);
    taAnswer = new TextArea(13, 20);
    taAnswer.setEditable(false);

    pnText.add(taCommand);
    pnText.add(taAnswer);

    // テキストエリアの消去ボタン
    Panel pnClear = new Panel();
    pnClear.setLayout(new GridLayout(1, 2));

    btCommandClear = addButton("Clear", btCommandClear, pnClear);
    btAnswerClear = addButton("Clear", btAnswerClear, pnClear);

    // 全部を合体
    Panel pn1 = new Panel();
    pn1.setLayout(new BorderLayout(0, 0));
    pn1.add("Center", lab);
    pn1.add("East", wp = new UI_WaitingPanel());
    pn1.add("South", pnButton);

    Panel pn2 = new Panel();
    pn2.setLayout(new BorderLayout(0, 0));
    pn2.add("North", pnLabel);
    pn2.add("Center", pnText);
    pn2.add("South", pnClear);

    ScrollPane sp1 = new ScrollPane();
    sp1.setBackground(Color.white);
    mTree = new UI_MediTree();
    sp1.add(mTree);

    Panel pn3 = new Panel();
    pn3.setLayout(new BorderLayout(0, 0));
    pn3.add("North", new Label("Mediator"));
    pn3.add("Center", sp1);

    add("North", pn1);
    add("Center", pn2);
    add("West", pn3);

    addWindowListener(this);
  }

  public void start( boolean flag ) {
    Flag = flag;

    setSize(600,500);
    if ( !flag ) {
      show();

      mTree.init();
      mTree.start();

      mTree.addTreeTest(mTree.top);
      mTree.makeTree();
      mTree.repaint();

      wp.init();
    } else {
      System.out.println("\nQuik Client is Standing by !!\n");
    }

    addWindowListener(this);
  }

  //  コンストラクタ
  public void start() {
    start( false );
  }

  //  2000/02/13 Earsh
  public void waiting() {
    if ( !Flag )
      wp.change();
  }

  //  2000/02/15 Earsh
  //  PC 側のメソッド用ダミーメソッド
  public void movement( int n ) {
  }

  public void actionPerformed(ActionEvent ev) {
    Object obj;

    //  MenuItem Objects
    if( (obj = ev.getSource()) == miFileExit ) {          //  Quit
      //System.out.println("Exit...");
      dispose();
      System.exit(0);

/**
    } else if ( obj == miLatticeOpen ) {                     //  Open Lattice
      if(lataf == null) {
        disp_lattice();
      } else {
        if( lataf.isShowing() )
          lataf.setVisible(false);
        else
          lataf.show();
      }

    } else if ( obj == miMonitorOpen ) {                     //  Monitor Frame
      if ( mon == null ) {
        mon = new UI_Monitor(this);
        mon.start();
      } else {
        if( mon.isShowing() )
          mon.setVisible(false);
        else
	  mon.show();
      }
    } else if ( obj == miTracerOpen ) {                     //  Tracer Frame
      if ( tracer == null ) {
        tracer = new UI_Tracer(this);
        tracer.start();
      } else {
        if( tracer.isShowing() )
          tracer.setVisible(false);
        else
	  tracer.show();
      }
*/

    // ボタンが押された時
    // Startup
    } else if (obj == btStartup) {
      query = "&startup " + mTree.cur.name + ".";
      messagePrint(query);
      parent.commgr.sendMessage(query);
    // Activate
    } else if (obj == btActivate) {
      query = "&activate " + mTree.cur.name + ".";
      messagePrint(query);
      parent.commgr.sendMessage(query);
    // 実験実験 Que_Dialog
    } else if (obj == btOpen) {
    // Send Query
    } else if (obj == btSend) {
      query = taCommand.getText();
      parent.commgr.sendMessage(query);
      //inputProc(query);
    // COMMAND Window のテキストの消去
    } else if (obj == btCommandClear) {
      taCommand.setText("");
    } else if (obj == btAnswerClear) {
      taAnswer.setText("");
    }
  }

/**
  void disp_lattice(){
    lataf = new LAT_Main();
    lataf.start();
  }
*/

  public void appendText1(String str) {
    taCommand.append(str + "\n");
  }

  public void messagePrint(String mess) {
    taAnswer.append(mess + "\n");
    System.out.print(mess + "\n");
  }

  // 返ってきたステータスをアンサーウィンドウに表示
  public void getState(int state, Object obj) {
    int err_num = state & QuikStatus.ERRMASK;
    switch (state & QuikStatus.MASK) {
    case QuikStatus.SUCCESS:
      messagePrint("SUCCESS: " + obj);
      break;
    case QuikStatus.ERROR:
      messagePrint("ERROR: " + obj);
      break;
    case QuikStatus.WARNING:
      //messagePrint("WARNING: " + obj);
      getWarning(err_num, obj);
      break;
    default:
      break;
    }
  }

  // 返ってきたステータスをアンサーウィンドウに表示
  public void getWarning(int state, Object obj) {
    switch (state) {
/**
    case QuikStatus.LATTICE_ERROR:
      messagePrint("LATTICE_ERROR: You Select Killing Edge Number.");
      int ret = selKillLattice((String)obj);
      parent.commgr.resumeStatus(QuikStatus.RESUME | QuikStatus.LATTICE_ERROR,
				 (Object)("" + ret));
      break;
    case QuikStatus.SHOW_LATTICE:
      LAT_Main.relation = (String)obj;
      break;
*/
    default:
      messagePrint("WARNING: " + obj);
      break;
    }
  }

  // とりあえず緊急用にラティス削除用メソッド
  public int selKillLattice(String str) {
    System.out.println("Dialog_start");
    String listStr;
    UI_List uilist = new UI_List(this, "Select Lattice",
				 "You Select Killing Edge Number.", true);
    // 始めのは消す
    //str = str.substring(str.indexOf("\n") + 1);

    // リストの作成
    while (!str.equals("")) {
      int ret = str.indexOf("\n");
      if (ret != -1) {
	listStr = str.substring(0, ret);
	str = str.substring(ret + 1);
      } else {
	listStr = str;
	str = "";
      }
      uilist.addList(listStr);
    }

    uilist.show();
    return uilist.getIndex() + 1;
  }

  public void progprint(String prog) {
    taCommand.append(prog);
  }

  //  2000/02/10  sugimoto
  //  Dummy Client 用のダミーのメソッド
  public void setTree( Exec_Tree tree ) {}
  public void receivedMedia( OM_ActionPointer media, int CMDid) {}

  public void windowClosing(WindowEvent we) {
    dispose();
    System.exit(0);
  }

  public void windowClosed(WindowEvent we) {}
  public void windowOpened(WindowEvent we) {}
  public void windowIconified(WindowEvent we) {}
  public void windowDeiconified(WindowEvent we) {}
  public void windowActivated(WindowEvent we) {}
  public void windowDeactivated(WindowEvent we) {}
}

