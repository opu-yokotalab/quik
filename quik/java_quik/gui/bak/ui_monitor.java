// User Interface
// Monitor Window
// Programed By Sugihara
// 
// JDK1.1
// From 1998/02/15
// Last Update 1998/02/15

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_Monitor extends UI_Base
implements ActionListener, WindowListener {
  TextArea taCommand, taAnswer;
  String rule, query;
  Label lb1, lb2, lb3;
  Button btCommandClear, btAnswerClear;
  MenuItem miFileExit, miLatticeOpen;

  public static int lat=0;
  LAT_Main lataf;
  public static String lattice = " ";
  public String filename = "";    /* 1998/07/10 Sugimoto */

  DBG_Frame debug;
  UI_MediSelect sel;
  UI_MediTree mTree;
  UI_Client parent;

  // $B%3%s%9%H%i%/%?(B
  public UI_Monitor(UI_Client parent) {
    super("QUIK Monitor");
    this.parent = parent;

    // $B%a%K%e!<%P!<(B
    MenuBar mb = new MenuBar();
    setMenuBar(mb);

    // $B%U%!%$%k(B
    Menu meFile = new Menu("File");
    mb.add(meFile);

    miFileExit = addMenuItem("Exit", miFileExit, meFile);

    // $B%&%#%s%I%&$N:n@.(B

    // $B%/%$%C%/%i%Y%k(B
    QuikLabel lab = new QuikLabel();
    lab.setSize(500,50);

    // $B%\%?%s$N@_CV(B
    Panel pnButton = new Panel();
    btCommandClear = addButton("Clear", btCommandClear, pnButton);
    btAnswerClear = addButton("Clear", btAnswerClear, pnButton);

    // $B%F%-%9%H%(%j%"(B1
    Panel pnText1 = new Panel();
    pnText1.setLayout(new BorderLayout(0, 0));

    lb1 = new Label("CURRENT MEDIATOR MESSAGE");
    taCommand = new TextArea(10, 20);
    taCommand.setEditable(false);

    pnText1.add("North", lb1);
    pnText1.add("Center", taCommand);
    pnText1.add("South", btCommandClear);

    // $B%F%-%9%H%(%j%"(B2
    Panel pnText2 = new Panel();
    pnText2.setLayout(new BorderLayout(0, 0));

    lb2 = new Label("ALL MEDIATOR MESSAGE");
    taAnswer = new TextArea(5, 20);
    taAnswer.setEditable(false);

    pnText2.add("North", lb2);
    pnText2.add("Center", taAnswer);
    pnText2.add("South", btAnswerClear);

    // $BA4It$r9gBN(B
    Panel pn1 = new Panel();
    pn1.setLayout(new BorderLayout(0, 0));
    pn1.add("Center", lab);
    pn1.add("South", pnButton);

    Panel pn2 = new Panel();
    pn2.setLayout(new BorderLayout(0, 0));
    pn2.add("Center", pnText1);
    pn2.add("South", pnText2);

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
    // $B%\%?%s$,2!$5$l$?;~(B
    // COMMAND Window $B$N%F%-%9%H$N>C5n(B
    } else if (obj == btCommandClear) {
      taCommand.setText("");
    // ANSWER Window $B$N%F%-%9%H$N>C5n(B
    } else if (obj == btAnswerClear) {
      taAnswer.setText("");
    }
  }

  // I/O $B>pJs$NI=<((B
  public void dispIOinfo() {
    appendText(taCommand, "IO_INFO\n");
    
  }

  // $B%m!<%+%kJQ?t$NI=<((B
  public void dispVar() {
    appendText(taCommand, "VAR\n");
  }

  // $B%k!<%k$NI=<((B
  public void dispRule() {
    appendText(taCommand, "RULE\n");
  }

  // $B%*%s%H%m%8!<$NI=<((B
  public void dispOntology() {
    appendText(taCommand, "ONTOLOGY\n");
  }

  // $BB+$NI=<((B
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
