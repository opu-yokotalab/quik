// User Interface
// Base Class
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/30
// Last Update 1998/01/30

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_Base extends Frame implements ActionListener {

  // $B%3%s%9%H%i%/%?(B
  public UI_Base(String str) {
    super(str);
  }

  // $B%a%K%e!<%"%$%F%`$NDI2C(B
  protected MenuItem addMenuItem(String label, MenuItem mi, Menu menu) {
    mi = new MenuItem(label);
    menu.add(mi);
    mi.addActionListener(this);
    return mi;
  }

  // $B%\%?%s$NDI2C(B
  protected Button addButton(String label, Button bt, Panel p) {
    bt = new Button(label);
    p.add(bt);
    bt.addActionListener(this);
    return bt;
  }

  // $B%F%-%9%H%(%j%"$X$NJ8;zNs$NDI2C(B
  public void appendText(TextArea ta, String str) {
    ta.append(str);
  }

  // $B%U%!%$%k$NFI$_9~$_(B
  public void Filereader(TextArea ta, String file){
    String line = "";
    BufferedReader buff;

    try{
      buff = new BufferedReader(new FileReader(file));

      while((line = buff.readLine()) != null)
        appendText(ta, line);

    } catch(FileNotFoundException e) {
      System.out.println("FileNotFoundException:" + e);
    } catch(IOException e) {
      System.out.println("IOException:" + e);
    }
  }

  // $B%U%!%$%k$N=q$-9~$_(B
  public void inputProc(String str) {
    int b;
    BufferedWriter bfout;

    try {
      bfout = new BufferedWriter(new FileWriter("_program.quik"));
      bfout.write(str, 0, str.length());
      bfout.close();
    } catch (IOException e) {
          System.out.println("File error: " + e);
    }

/**
    try{
      parser parse_obj = new parser();
      FileList.init_scanner_2("_program.quik");
      parse_obj.parse();
    } catch (java.lang.Exception ex) {
      System.err.println("Exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(-1);
    }
*/
  }  

  // $B%$%Y%s%H$,$"$C$?;~(B
  public void actionPerformed(ActionEvent ev) {}
}
