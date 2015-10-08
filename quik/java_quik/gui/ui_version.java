// User Interface SystemWindow Version Frame
// Programed By Shiraki
// OverWrite By Sugihara
// 
// JDK1.1
// From 1998/10/09
// Last Update 1999/02/16

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.awt.event.*;

public class UI_Version extends Frame implements ActionListener {
  Label  lb1, lb2, lb3, lb4, lb5;
  Button bt1;
  GridBagLayout gblayout;
  GridBagConstraints gbc;

//    int flag;
    static String name    = "",
                  version = "",
                  date    = "",
                  name_xml       = "Xml Window",
		  version_xml    = "1.0",
		  date_xml       = "1998/10/02",
                  name_link      = "Link Editor",
		  version_link   = "1.0",
		  date_link      = "1998/12/14",
                  name_search    = "Xml Search",
		  version_search = "1.0",
		  date_search    = "1999/01/17",
                  name_system    = "DIMS",
		  version_system = "1.0",
		  date_system    = "1999/02/14";

  public void init(int flag) {

    if(flag == 0){
        name    = name_xml;
        version = version_xml;
	date    = date_xml;
    }else if(flag == 1){
        name    = name_link;
        version = version_link;
	date    = date_link;
    }else if(flag == 2){
        name    = name_search;
        version = version_search;
	date    = date_search;
    }else if(flag == 3){
        name    = name_system;
        version = version_system;
	date    = date_system;
    }

    lb1 = new Label(name, Label.CENTER);
    lb2 = new Label("Version " + version , Label.CENTER);
    lb3 = new Label("copyright " + date , Label.CENTER);
    lb4 = new Label("Programed by Yoshitaka Shiraki"  , Label.CENTER);
    lb5 = new Label("Special Thanks Earsh"  , Label.CENTER);

    bt1 = new Button("Close");
    bt1.addActionListener(this);

    gbc = new GridBagConstraints();
    gblayout = new GridBagLayout();

    setLayout( gblayout );

    setObject(lb1, gblayout, gbc);
    gbc.gridy = 1;
    setObject(lb2, gblayout, gbc);
    gbc.gridy = 2;
    setObject(lb3, gblayout, gbc);
    gbc.gridy = 3;
    setObject(lb4, gblayout, gbc);
    gbc.gridy = 4;
    setObject(lb5, gblayout, gbc);
    gbc.gridy = 5;
    setObject(bt1, gblayout, gbc);

    addWindowListener(new WindowControl());
  }

  public void setObject(Component comp, GridBagLayout layout,
                        GridBagConstraints c) {
    layout.setConstraints( comp, c);
    add(comp);
  }

  public void actionPerformed(ActionEvent e) {
    if ( e.getSource() == bt1 ) {
      dispose();
    }
  }

  //  Window に対する操作処理
  class WindowControl extends WindowAdapter {
    public void windowClosing(WindowEvent e){
      dispose();
    }
  }

  //  メインのメソッド
  public static void main( String args[] ) {
    UI_Version window = new UI_Version();
    window.setTitle("Version Information");
    window.init(0);
    window.show();
  }
}
