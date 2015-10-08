// User Interface SystemWindow Manual Frame
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
import java.io.*;

public class UI_ManFrame extends Frame implements ActionListener {

  Label  lb1;
  TextField tf1;
  Button bt1;
  TextArea ta1;
  MenuBar mbar;
  Menu m1, help;
  MenuItem mi1, mh1;

  GridBagLayout gblayout;
  GridBagConstraints gbc;

  public void init(int flag) {

    lb1 = new Label("Status window");

    bt1 = new Button("Close");
    bt1.addActionListener(this);

    tf1 = new TextField("", 25);
    tf1.setEditable(false);
    tf1.addActionListener(this);

    gbc = new GridBagConstraints();
    gblayout = new GridBagLayout();

    setLayout( gblayout );
    gbc.fill = GridBagConstraints.BOTH;

    gbc.gridwidth = 2;
    setObject( ta1, gblayout, gbc);
    gbc.gridy = 1;

    gbc.gridwidth = 1;
    setObject(lb1, gblayout, gbc);
    setObject( tf1, gblayout, gbc);
    gbc.gridy = 2;

    gbc.gridwidth = 2;
    setObject(bt1, gblayout, gbc);

    addWindowListener(new WindowControl());

    LoadFile(flag);

  }

  // ManFrame Windowのメソッド
  public UI_ManFrame(){
    
    ta1 = new TextArea("", 30, 45);
    ta1.setEditable(false);
    add(ta1);

    mbar = new MenuBar();
    setMenuBar(mbar);

    m1 = new Menu("File");
    mbar.add(m1);
    mi1 = new MenuItem("Close");
    m1.add(mi1);
    mi1.addActionListener(this);

    help = new Menu( "Help" );
    mbar.add(help);
    mbar.setHelpMenu(help);
    // ("Keyword Search"仮定)
    mh1 = new MenuItem( "Keyword search" );
    help.add(mh1);
    mh1.addActionListener(this);

  }

  public void setObject(Component comp, GridBagLayout layout,
                        GridBagConstraints c) {
    layout.setConstraints( comp, c);
    add(comp);
  }

  //  Window に対する操作処理
  class WindowControl extends WindowAdapter {
    public void windowClosing(WindowEvent e){
      dispose();
    }
  }

  //  メインのメソッド
  public static void main( String args[] ) {
    UI_ManFrame window = new UI_ManFrame();
    window.setTitle("Manual Window");
    window.init(0);
    window.pack();
    window.show();
  }

  //  メニューのアクション
  public void actionPerformed( ActionEvent e ) {
    //Object obj = e.getSource();
    String filename1, filename2;

    if ( e.getSource() == mi1) {
      ta1.replaceRange( "", 0, ta1.getText().length());
      dispose();
    } else if ( e.getSource() == bt1 ) {
      ta1.replaceRange( "", 0, ta1.getText().length());
      dispose();
    }
  }

  //  ファイルからの読み込み
  public void LoadFile(int flag) {
    String directory, file="", file1, file2, file3, file4, line;
    BufferedReader bfin;

    file1 = "XmlWindow.txt" ;
    file2 = "LinkEdit.txt";
    file3 = "XmlSearch.txt";
    file4 = "System.txt";

    if(flag == 0){
      file = file1;
    }else if(flag == 1){
      file = file2;
    }else if(flag == 2){
      file = file3;
    }else if(flag == 3){
      file = file4;
    }

    //  ファイルからのデータの読み込み
    try {
      //  ストリームの作成
      bfin = new BufferedReader(new FileReader( file ) );

      do {
        line = bfin.readLine();
        if( line != null )
          ta1.append(line + "\n" );
      } while( line != null);
      //  ストリームのクローズ
      bfin.close();
      tf1.setText( "Manual was loaded.\n" );
    } catch( FileNotFoundException e ) {
      //  FileNotFoundException に対する例外処理
      tf1.setText( "Can not open \"" + file + "\" file.\n");
    } catch( IOException e ) {
      //  IOException に対する例外処理
      tf1.setText("IOException:" + e + "\n");
    }
  }    
}

