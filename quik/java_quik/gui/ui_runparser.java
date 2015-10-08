// User Interface
// Run Parser
// Programed By Sugihara
// 
// JDK1.1
// From 1998/02/13
// Last Update 1998/02/13

// パーザがスレッドで動いてないため、とりあえず作ったものです。
// 必要なくなったら削除して結構です。

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

  // スレッドの開始
  public void start( String Filename ) {
    System.out.println("Filename:" + Filename);

    filename = Filename;
    runner   = new Thread(this);
    runner.start();
  }

  // おーばーろーど
  public void start() {
    this.start( "mt.quik" );
  }

  // スレッドの実行
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
