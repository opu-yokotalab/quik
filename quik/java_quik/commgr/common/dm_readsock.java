package java_quik.commgr.common;
import java_quik.*;
import java_quik.gui.*;
import java_quik.commgr.*;

import java.io.*;
import java.net.*;

import java.io.Serializable;
import java.util.*;  // Date

public class DM_readSock extends Thread {
  public ServerSocket ss = null;
  public Socket s = null;

  DM_main Parent;

  //  コンストラクタ
  public DM_readSock(DM_main parent) {
    // 呼び出されているクラスの登録
    Parent = parent;
  }

  //  コンストラクタ
  public DM_readSock() {}


  public void onCreation(int port){ //
    try{
      ss = new ServerSocket(port);
      ss.setSoTimeout(10000);
    }
    catch (Exception e) {
      System.out.println("Exception: " + e);
    }

  }

  public void onDestroy(){
    try{
      ss.close();
    }
    catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }

  public void run() {

    while(true) {
      try{
        Thread.sleep(1000);
        s = ss.accept();

        InputStream is = s.getInputStream();
        ObjectInputStream oin = new ObjectInputStream(is);

        DM_Message dmobj = (DM_Message)(oin.readObject());
        Parent.Parent.uiClient.movement( 1 );
        Parent.rcvdObject(dmobj); /* Main に送ったる */

        oin.close();
        is.close();

        s.close();
      } catch ( InterruptedIOException iie ) {
        Parent.Parent.uiClient.movement( 0 );
      } catch (Exception e) {
        System.out.println("[read error]");
        System.out.println("Exception: " + e);
      }
    }
  }

}
