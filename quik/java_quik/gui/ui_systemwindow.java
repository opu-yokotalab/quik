// User Interface SystemWindow
// Programed By Shiraki
// OverWrite By Sugihara
// 
// JDK1.1
// From 1998/10/09
// Last Update 1999/02/16

/* SystemWindow.java */
/* Heterogenious documents integrated management systemのインタフェース部 */

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.awt.event.*;

public class UI_SystemWindow extends Dialog
implements ActionListener, WindowListener {

  TextArea ta_caution;
  Button btServer, btClient;

  // 別クラスの指定
  UI_Version      ver = null;
  UI_ManFrame     man = null;

  String staff;
  public Main parent;
  public int mode;

  // コンストラクタ
  public UI_SystemWindow(Main par, String title, boolean flag) {
    super(par, title, flag);
    parent = par;
    init();
    setSize(600,300);
    addWindowListener(this);
  }

  // initメソッド
  public void init() {

    staff  = "Faculty of Computer Science and System Engineering Okayama Prefectural University\n";
    staff += "--- STAFF ---\n";
    staff += "Professor Dr.Kazumasa Yokota\n";
    staff += "Associate Professor Dr.Takeo Kunishima\n";
    staff += "Associate Professor Dr.Bojiang Liu (Faculty of Informatics Okayama University of Science)\n";
    staff += "--- '97 Labo QUIK Team Staff ---\n";
    staff += "(B4): Nobutaka Ogata, Shinichiro Tsutsumi, Yasuhiro Nakai, Tadaaki Miyake\n";
    staff += "--- '98 Labo QUIK Team Staff ---\n";
    staff += "(M1): Nobutaka Ogata\n";
    staff += "(B4): Mamoru Sugihara, Kenji Sugimoto, Noriyuki Nerio, Satoshi Yokogawa\n";

    // テキストエリア
    ta_caution = new TextArea(staff, 10, 10, ta_caution.SCROLLBARS_NONE);
    ta_caution.setEditable(false);
    ta_caution.setFont(new Font("Dialog", Font.ITALIC, 12));
    ta_caution.setBackground(Color.white);
    ScrollPane sp1 = new ScrollPane();
    sp1.add(ta_caution);

    // ボタン指定
    btServer = new Button( "Server" );
    btServer.addActionListener(this);
    btClient = new Button( "Client" );
    btClient.addActionListener(this);

    QuikLabel lab = new QuikLabel();
    lab.setSize(500,50);

    // レイアウト情報
    Panel pnTitle = new Panel();
    pnTitle.setLayout(new BorderLayout());
    pnTitle.add(lab);
    add("North",pnTitle);

    Panel pnInfor = new Panel();
    pnInfor.setLayout(new BorderLayout());
    //pnInfor.add(ta_caution);
    pnInfor.add(sp1);
    add("Center",pnInfor);

    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1,3));
    pnButton.add(btServer);
    pnButton.add(btClient);
    add("South",pnButton);

  }

  public void actionPerformed( ActionEvent e ) {
    Object obj = e.getSource();
    /* 
    if( obj == mh_ver ) {
      // バージョン情報を開く
      ShowVersion();

    } else if( obj == mh_man ) {
      // マニュアルを開く
      ShowManFrame();

    } else */if (obj == btServer) {
      mode = QuikStatus.SERVER;
      dispose();
    } else if (obj == btClient) {
      mode = QuikStatus.CLIENT;
      dispose();
    }
  }

  //  Version Windowの表示
  public void ShowVersion() {
    if ( ver != null )
      if ( ver.isShowing() ) {
        ver.show();
        return;
      }
    ver = new UI_Version();
    ver.setTitle("Version Information");
    ver.init(3);
    ver.setSize( 250, 200);
    ver.setResizable(false);
    ver.show();
  }

  //  Manual Windowの表示
  public void ShowManFrame() {
    if ( man != null )
      if ( man.isShowing() ) {
        man.show();
        return;
      }
    man = new UI_ManFrame();
    man.init(3);
    man.setTitle("Manual Information");
    man.pack();
    man.show();
  }

  // モードの取得
  public int getMode() {
    return mode;
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
