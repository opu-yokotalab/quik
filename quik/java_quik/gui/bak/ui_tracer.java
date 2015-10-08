// User Interface
// Tracer Window
// Programed By Sugihara
// 
// JDK1.1
// From 1998/02/01
// Last Update 1998/02/11

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_Tracer extends UI_Base
implements ActionListener, WindowListener {
  TextArea taCommand, taAnswer;
  String rule, query;
  Label lb1, lb2, lb3;
  Button btIOinfo, btVar, btRule, btOntology, btLattice, btClear;
  MenuItem miFileExit, miLatticeOpen;

  public static int lat=0;
  LAT_Main lataf;
  public static String lattice = " ";
  public String filename = "";    /* 1998/07/10 Sugimoto */

  DBG_Frame debug;
  UI_MediSelect sel;
  UI_MediTree mTree;
  UI_Client parent;

  // コンストラクタ
  public UI_Tracer(UI_Client parent) {
    super("QUIK Tracer");
    this.parent = parent;

    // メニューバー
    MenuBar mb = new MenuBar();
    setMenuBar(mb);

    // ファイル
    Menu meFile = new Menu("File");
    mb.add(meFile);

    miFileExit = addMenuItem("Exit", miFileExit, meFile);

    // ウィンドウの作成

    // クイックラベル
    QuikLabel lab = new QuikLabel();
    lab.setSize(500,50);

    // ボタンの設置
    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1, 6));
    btIOinfo = addButton("I/O", btIOinfo, pnButton);
    btVar = addButton("Var", btVar, pnButton);
    btRule = addButton("Rule", btRule, pnButton);
    btOntology = addButton("Ontology", btOntology, pnButton);
    btLattice = addButton("Lattice", btLattice, pnButton);
    btClear = addButton("Clear", btClear, pnButton);

    // テキストエリア1
    Panel pnCur = new Panel();
    pnCur.setLayout(new BorderLayout(0, 0));

    lb1 = new Label("CURRENT MEDIATOR INFORMATION");
    taCommand = new TextArea(30, 20);
    taCommand.setEditable(false);

    pnCur.add("North", lb1);
    pnCur.add("Center", taCommand);

    // 全部を合体
    Panel pn1 = new Panel();
    pn1.setLayout(new BorderLayout(0, 0));
    pn1.add("Center", lab);
    pn1.add("South", pnButton);

    ScrollPane sp1 = new ScrollPane();
    sp1.setBackground(Color.white);
    mTree = new UI_MediTree();
    sp1.add(mTree);

    Panel pn3 = new Panel();
    pn3.setLayout(new BorderLayout(0, 0));
    pn3.add("North", new Label("Mediator"));
    pn3.add("Center", sp1);

    add("North", pn1);
    add("Center", pnCur);
    add("West", pn3);

    addWindowListener(this);
  }

  public void start() {
    setSize(600,500);
    show();

    mTree.init();
    mTree.start();

    addWindowListener(this);

mTree.addTreeTest(mTree.top);
mTree.makeTree();
mTree.repaint();
  }

  public void actionPerformed(ActionEvent ev) {
    Object obj;

    // MenuItem Objects
    // Quit
    if((obj = ev.getSource()) == miFileExit) {
      dispose();
    // ボタンが押された時
    // IOinfo
    } else if (obj == btIOinfo) {
      dispIOinfo();
    // Var
    } else if (obj == btVar) {
      dispVar();
    // Rule
    } else if (obj == btRule) {
      dispRule();
    // Ontology
    } else if (obj == btOntology) {
      dispOntology();
    // Lattice
    } else if (obj == btLattice) {
      dispLattice();
    // COMMAND Window のテキストの消去
    } else if (obj == btClear) {
      taCommand.setText("");
    }
  }

  // I/O 情報の表示
  public void dispIOinfo() {
    appendText(taCommand, "IO_INFO\n");
    
  }

  // ローカル変数の表示
  public void dispVar() {
    appendText(taCommand, "VAR\n");
  }

  // ルールの表示
  public void dispRule() {
    appendText(taCommand, "RULE\n");
  }

  // オントロジーの表示
  public void dispOntology() {
    appendText(taCommand, "ONTOLOGY\n");
  }

  // 束の表示
  public void dispLattice() {
    appendText(taCommand, "LATTICE\n");
  }

  public void windowClosing(WindowEvent we) {
    dispose();
  }
  public void windowClosed(WindowEvent we) {}
  public void windowOpened(WindowEvent we) {}
  public void windowIconified(WindowEvent we) {}
  public void windowDeiconified(WindowEvent we) {}
  public void windowActivated(WindowEvent we) {}
  public void windowDeactivated(WindowEvent we) {}
}
