package java_quik.commgr.common;
import java_quik.*;
import java_quik.gui.*;
import java_quik.commgr.*;

import java.io.*;
import java.net.*;

import java.io.Serializable;
import java.util.*;  // Date

public class DM_writeSock {
  String server;
  int port;

  DM_main Parent;

  //  コンストラクタ
  public DM_writeSock(DM_main parent) {
    // 呼び出されているクラスの登録
    Parent = parent;
  }

  //  コンストラクタ
  public DM_writeSock() {}


  public void sendObject(Object dmobj, String wserver, int wport) {
    Socket s = null;

    try {
      s = new Socket(wserver, wport);

      OutputStream os = s.getOutputStream();
      ObjectOutputStream oout = new ObjectOutputStream(os);

      oout.writeObject(dmobj);

      oout.flush();

      oout.close();
      os.close();

      s.close();
    }
    catch (Exception e) {
      System.out.println("[write error]");
      System.out.println("Exception: " + e);
    }
  }

}
