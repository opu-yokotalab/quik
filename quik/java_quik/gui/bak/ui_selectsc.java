// User Interface
// Client Server Select Dialog
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/28
// Last Update 1998/01/28

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_SelectSC extends Dialog
implements ActionListener, WindowListener {
  Button btServer, btClient;
  int mode;

  // コンストラクタ
  public UI_SelectSC(Frame parent, String title, boolean model) {
    super(parent, "Client Server Select Dialog", model);

    Panel pn1 = new Panel();
    pn1.setLayout(new FlowLayout(FlowLayout.CENTER));
    btServer = new Button("Server");
    btClient = new Button("Client");
    pn1.add(btServer);
    pn1.add(btClient);
    btServer.addActionListener(this);
    btClient.addActionListener(this);

    setLayout(new BorderLayout());
    add("Center", new Label("Open Window", Label.CENTER));
    add("South", pn1);
    pack();

    addWindowListener(this);
  }

  public int getMode() {
    return mode;
  }

  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    if (obj == btServer) {
      mode = QuikStatus.SERVER;
    } else if (obj == btClient) {
      mode = QuikStatus.CLIENT;
    } else return;

    dispose();
  }

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
