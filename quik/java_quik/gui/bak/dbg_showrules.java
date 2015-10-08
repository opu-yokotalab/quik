// 1998/12/21 Sugihara
// Debug ShowRule Window

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class DBG_ShowRules extends Frame
implements ActionListener, WindowListener {
  List ls1;
  Button btOK;

  // $B%3%s%9%H%i%/%?(B
  public DBG_ShowRules() {
    super("Debug ShowRules Window");

    setLayout(new BorderLayout());

    // $B%i%Y%k(B
    Label lb1 = new Label("Rules", Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));
    add("North", lb1);

    // $B%j%9%H(B
    ls1 = new List();
    ls1.setMultipleMode(true);
    ls1.add("quik");
    ls1.add("sugihara");
    ls1.add("1998.12.25");
    add("Center", ls1);

    // $BI=<(%\%?%s(B
    btOK = new Button("OK");
    add("South", btOK);
    btOK.addActionListener(this);

    setSize(400, 400);
    addWindowListener(this);
  }

  public static void main(String argv[]) {
    DBG_ShowProgram c = new DBG_ShowProgram();

    c.start();
  }

  public void start() {
    show();
    addWindowListener(this);
    //removeWindowListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    String str = e.getActionCommand();

    if (obj == btOK) {
      //setVisible(false);
      int sel[] = ls1.getSelectedIndexes();
      for (int i = 0;i < sel.length; i++) {
	System.out.println(sel[i] + ": " + ls1.getItem(sel[i]));
	String s = ls1.getItem(sel[i]);
	ls1.remove(sel[i]);
	ls1.add(s + "*", sel[i]);
      }
    }
  }

  // $B%U%l!<%`$N%/%m!<%:(B
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
