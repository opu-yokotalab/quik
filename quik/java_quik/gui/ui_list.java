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

  // コンストラクタ UI_Client
  public UI_List(Frame parent, String title, boolean modal) {
    super(parent, title, modal);

    // 使うのなら下のコンストラクタと同じ内容を書くこと
  }

  // コンストラクタ
  public UI_List(Frame parent, String title, String msg, boolean modal) {
    super(parent, title, modal);

    // 変数の初期化
    index = -1;

    // 画面の作成
    setLayout(new BorderLayout());

    // ラベル
    Label lb1 = new Label(title, Label.CENTER);
    lb1.setFont(new Font("Dialog", Font.ITALIC, 21));
    Label lb2 = new Label(msg, Label.CENTER);

    // リスト
    lsMain = new List();
    lsMain.addActionListener(this);

    Panel pnLabel = new Panel();
    pnLabel.setLayout(new GridLayout(2, 1));
    pnLabel.add(lb1);
    pnLabel.add(lb2);

    // 表示ボタン
    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1, 2));
    btOK = new Button("OK");
    btOK.addActionListener(this);
    pnButton.add(btOK);
    btCancel = new Button("Cancel");
    btCancel.addActionListener(this);
    pnButton.add(btCancel);

    // 配置
    add("North", pnLabel);
    add("Center", lsMain);
    add("South", pnButton);

    setSize(400, 400);
  }

  // リストの追加
  public void addList(String str) {
    lsMain.add(str);
  }

  // 選択されている番号を返す
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
