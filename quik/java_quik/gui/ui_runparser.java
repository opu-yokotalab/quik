// User Interface
// Run Parser
// Programed By Sugihara
// 
// JDK1.1
// From 1998/02/13
// Last Update 1998/02/13

// $B%Q!<%6$,%9%l%C%I$GF0$$$F$J$$$?$a!"$H$j$"$($::n$C$?$b$N$G$9!#(B
// $BI,MW$J$/$J$C$?$i:o=|$7$F7k9=$G$9!#(B

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_RunParser extends Thread {
  final int      sleepTime = 50;
  public boolean endFlag;
  UI_Server      parent;
  Thread         runner;
  quik_parser    parser_obj;

  String         filename = "mt.quik";
  BufferedReader br = null;

  public UI_RunParser (UI_Server uiServer) {
    parent = uiServer;
    endFlag = false;
  }

  // $B%9%l%C%I$N3+;O(B
  public void start( String Filename ) {
    System.out.println("Filename:" + Filename);

    filename = Filename;
    runner   = new Thread(this);
    runner.start();
  }

  // $B$*!<$P!<$m!<$I(B
  public void start() {
    this.start( "mt.quik" );
  }

  // $B%9%l%C%I$N<B9T(B
  public void run() {
    try{
      br = new BufferedReader( new FileReader(filename) );
      parser_obj = new quik_parser( br );
      parser_obj.Input();
    } catch (java.lang.Exception ex) {
      System.err.println("Exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(-1);
    }
    endFlag = true;
  }
}
