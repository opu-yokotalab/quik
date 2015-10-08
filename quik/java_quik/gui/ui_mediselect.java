// 1998/12/21 Sugihara
// Debug ShowRule Window

package java_quik.gui;
import java_quik.*;

import java.applet.Applet;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_MediSelect extends Frame
implements ActionListener, WindowListener {
  List ls1, ls2;
  Button btOK;

  // コンストラクタ
  public UI_MediSelect() {
    super("Select Mediator Window");

    setLayout(new BorderLayout());

    // ラベル
    Label lb1 = new Label("Select Mediator", Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));

    // リスト
    ls1 = new List();
    ls1.add("m1");
    ls1.add("m2");
    ls1.add("m3");
    ls1.addActionListener(this);

    // 状態
    ls2 = new List();
    ls2.add("Startup");
    ls2.add("Activate");
    ls2.add("Nothing");

    Panel pn1 = new Panel();
    pn1.setLayout(new BorderLayout(0, 0));
    pn1.add("North", new Label("Mediator"));
    pn1.add("Center", ls1);

    Panel pn2 = new Panel();
    pn2.setLayout(new BorderLayout(0, 0));
    pn2.add("North", new Label("Status"));
    pn2.add("Center", ls2);

    // 表示ボタン
    btOK = new Button("OK");
    btOK.addActionListener(this);

    // 配置
    add("North", lb1);
    add("Center", pn1);
    add("East", pn2);
    add("South", btOK);

    setSize(400, 400);
    addWindowListener(this);
  }

  public void start() {
    show();
    addWindowListener(this);
    //removeWindowListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    String str = e.getActionCommand();

    if (obj == ls1 || obj == btOK) {
      //setVisible(false);
      int sel[] = ls1.getSelectedIndexes();
      for (int i = 0; i < sel.length; i++) {
	System.out.println(sel[i] + ": " + ls1.getItem(sel[i]));
	ls1.replaceItem(ls1.getItem(sel[i]) + "*", sel[i]);
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
