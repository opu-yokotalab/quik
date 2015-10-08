// 1998/12/21 Sugihara
// Debug ShowProgram Window

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class DBG_ShowProgram extends Frame
implements ActionListener, WindowListener {
  TextArea ta1;
  Button btOK;

  // $B%3%s%9%H%i%/%?(B
  public DBG_ShowProgram() {
    super("Debug ShowProgram Window");

    setLayout(new BorderLayout());

    // $B%i%Y%k(B
    Label lb1 = new Label("Program", Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));
    add("North", lb1);

    // $B%F%-%9%H%(%j%"(B
    ta1 = new TextArea();
    add("Center", ta1);

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
      setVisible(false);
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
