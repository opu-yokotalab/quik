// 1998/12/21 Sugihara
// Debug Config Window

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class DBG_Config extends Frame
implements ActionListener, WindowListener {
  TextField tf1;
  Button btOK, btCancel;

  // コンストラクタ
  public DBG_Config() {
    super("Debug Config Window");

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(layout);

    // ラベル
    Label lb1 = new Label("kizami");
    layout.setConstraints(lb1, c);
    add(lb1);

    tf1 = new TextField("4", 2);
    c.gridwidth = GridBagConstraints.REMAINDER;
    layout.setConstraints(tf1, c);
    add(tf1);
    tf1.addActionListener(this);

    // 表示ボタン
    c.anchor = GridBagConstraints.EAST;
    c.gridwidth = GridBagConstraints.RELATIVE;
    btOK = addButton("OK", btOK, layout, c);
    c.gridwidth = GridBagConstraints.REMAINDER;
    btCancel = addButton("Cancel", btCancel, layout, c);

    setSize(300,200);
    addWindowListener(this);
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
    DBG_Config c = new DBG_Config();

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

    if (obj == tf1) {
      System.out.println("Changing DBG_DataMedi.maxX, maxY:" +
			 DBG_DataMedi.maxX + " -> " + str);
      DBG_DataMedi.maxX = DBG_DataMedi.maxY = Integer.parseInt(str);
    } else if (obj == btCancel) {
    }
    setVisible(false);
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
