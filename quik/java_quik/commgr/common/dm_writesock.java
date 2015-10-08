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

  //  ���󥹥ȥ饯��
  public DM_writeSock(DM_main parent) {
    // �ƤӽФ���Ƥ��륯�饹����Ͽ
    Parent = parent;
  }

  //  ���󥹥ȥ饯��
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
