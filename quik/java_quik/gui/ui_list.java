// User Interface
// List Window for Select
// Programed By Sugihara
// 
// JDK1.1
// From 1998/02/12
// Last Update 1998/02/12

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_List extends Dialog implements ActionListener {
  List lsMain;
  Button btOK, btCancel;
  int index;

  // $B%3%s%9%H%i%/%?(B UI_Client
  public UI_List(Frame parent, String title, boolean modal) {
    super(parent, title, modal);

    // $B;H$&$N$J$i2<$N%3%s%9%H%i%/%?$HF1$8FbMF$r=q$/$3$H(B
  }

  // $B%3%s%9%H%i%/%?(B
  public UI_List(Frame parent, String title, String msg, boolean modal) {
    super(parent, title, modal);

    // $BJQ?t$N=i4|2=(B
    index = -1;

    // $B2hLL$N:n@.(B
    setLayout(new BorderLayout());

    // $B%i%Y%k(B
    Label lb1 = new Label(title, Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));
    Label lb2 = new Label(msg, Label.CENTER);

    // $B%j%9%H(B
    lsMain = new List();
    lsMain.addActionListener(this);

    Panel pnLabel = new Panel();
    pnLabel.setLayout(new GridLayout(2, 1));
    pnLabel.add(lb1);
    pnLabel.add(lb2);

    // $BI=<(%\%?%s(B
    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1, 2));
    btOK = new Button("OK");
    btOK.addActionListener(this);
    pnButton.add(btOK);
    btCancel = new Button("Cancel");
    btCancel.addActionListener(this);
    pnButton.add(btCancel);

    // $BG[CV(B
    add("North", pnLabel);
    add("Center", lsMain);
    add("South", pnButton);

    setSize(400, 400);
  }

  // $B%j%9%H$NDI2C(B
  public void addList(String str) {
    lsMain.add(str);
  }

  // $BA*Br$5$l$F$$$kHV9f$rJV$9(B
  public int getIndex() {
    return index;
  }

  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

    index = lsMain.getSelectedIndex();

    if (obj == btCancel)
      index = -1;
    else if (obj != btOK && obj != lsMain)
      return;

    dispose();
  }
}
