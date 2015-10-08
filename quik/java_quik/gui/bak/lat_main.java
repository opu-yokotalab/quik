// JDK1.1 version update   1998/07/13  Sugimoto
/* LATTICE WINDOW  --1997-- */

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class LAT_Main extends Frame
implements ActionListener, WindowListener {
  LAT_Paintth pth;
  public LAT_Thesa th;

  /* ウインドウに表示するlatticeの初期値 */
  //String relation = new String();
  /* 応急static */
  static String relation = "";

  Button button;

  public LAT_Main() {
    //setBackground(Color.pink);
    setLayout(new BorderLayout(5,5));

    /* Label設定 */
    Label lb1 = new Label("This is Lattice Window", Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));

    pth = new LAT_Paintth(398, 398);
    button = new Button("show lattice");
    button.addActionListener(this);

    add("North", lb1);
    add("Center", pth);
    add("South", button);

    th = null;
    setSize(410,510);

    addWindowListener(this);
  }

  public void start() {
    show();
  }

  //  public void paint() {
  //  }

  public void actionPerformed(ActionEvent e) {
    //Main.mine.commgr.sendMessage(query);
    //parent.commgr.resumeStatus(QuikStatus.RESUME | QuikStatus.SHOW_LATTICE,null);
    Main.mine.commgr.resumeStatus(QuikStatus.RESUME | QuikStatus.SHOW_LATTICE,null);

    th = new LAT_Thesa(16);

    /* debug yokogawa 1999/02/12 */
    //for( OM_SubRel tmp = OM_SubRel.subrel_list; tmp != null; tmp = tmp.next )
    //relation += tmp.a1.name + " >= " + tmp.a2.name + "\n";

    while (relation.equals("")) {
      System.out.println("." + relation);
      Thread.yield();
      try {
	Thread.sleep(1000);
      } catch(InterruptedException ie) {}
    }

    try {
      LAT_Dataread dr = new LAT_Dataread(relation);
      dr.load(th);
    } catch(NullPointerException ex) {
      System.out.println("NO PROGRAM DATA.");
    }

    th.solve();
    pth.setth(th);
    pth.start();
  }

  public void windowClosing( WindowEvent we ) {
    setVisible(false);
  }
  public  void windowClosed( WindowEvent we ) {}
  public void windowOpened( WindowEvent we ) {}
  public void windowIconified( WindowEvent we ) {}
  public void windowDeiconified( WindowEvent we ) {}
  public void windowActivated( WindowEvent we ) {}
  public void windowDeactivated( WindowEvent we ) {}
}
