package java_quik.commgr.common;
import java_quik.*;
import java_quik.send_om.*;
import java_quik.gui.*;
import java_quik.commgr.*;

import java.io.*;
import java.net.*;

import java.io.Serializable;
import java.util.*;

public class DM_main {

  public Main Parent = null;

  DM_writeSock ws = new DM_writeSock(this);
  DM_readSock rs = new DM_readSock(this);

  public int Iam = 0; /* 0:server 1:client */
  public String myIP = "";
  public String wserver = "163.225.213.66"; /* default: alpha */
//  public String wserver = "163.225.213.50"; /* default: alpha */

  public String retDClient = "";

  //  コンストラクタ
  public DM_main(Object parent) {
    // 呼び出されているクラスの登録
    Parent = (Main)parent;
  }

  //  コンストラクタ
  public DM_main() {}


  public void init(int iam) {
    int port = 9000;

    try {
      InetAddress iadr = InetAddress.getLocalHost();
      myIP = iadr.getHostAddress().toString();
    }
    catch (Exception e) {
      System.out.println("Exception: " + e);
    }

    Iam = iam;
    if(iam == 0)
      port = 9003; /* server */
    else if(iam == 1)
      port = 9002; /* client */

    rs.onCreation(port);
    rs.start();

  }

  //  メイン
  public static void main( String args[] ) {
  }


  /*****  UI method  *****/
  //  コマンドを送る (start, continue, save, getTree)
  public void sendCommand(String msg, Object obj) { /* obj = media | tree */
    DM_Message dmobj = new DM_Message();
    CM_command command = new CM_command();
    int CMDid = -1;

    CMDid = command.selCommand( msg );
    if ( CMDid < -1 )
      CMDid = QuikStatus.QUERY;
    dmobj.setMessage(msg);
    dmobj.setObject(obj);
    dmobj.setCMDid( CMDid );
//    System.out.println("CMDid:" + CMDid);

    sendObject(dmobj);
  }

  //  状態を送る
  public void sendStatus(int stat) {
    DM_Message dmobj = new DM_Message();

    /* dmobj.setMessage(); */
    dmobj.setCMDid(QuikStatus.rSTATUS);

    Integer sstat = new Integer(stat);
    dmobj.setObject((Object)sstat);

    sendObject(dmobj);
  }

  //  サーバをセットする
  public void setServer(String adr) {
    wserver = adr;
  }
  /*****  ここまで UI method  *****/

  //  オブジェクトを送る
  public void sendObject(DM_Message dmobj) {
    int wport = 9002;

    // 行先解析しなくちゃね
    if(Iam == 0){ // 自分がサーバ側なら
      wserver = retDClient;
      wport = 9002;
    } else if (Iam == 1){ // 自分がクライアント側なら
      wport = 9003;
      dmobj.setClient(myIP); /* 自ホスト名書きこみ */
    }

    ws.sendObject((Object)dmobj, wserver, wport);

  }

  //  オブジェクトを受け取って処理する
  public void rcvdObject(DM_Message dmobj) {

    int CMDid  = dmobj.CMDid;
//    System.out.println("CMDid:" + Integer.toHexString(CMDid));
    retDClient = dmobj.client;

    /***** 自分がサーバ側ならAgletsにわたす *****/
    if(Iam == 0){

      if ( CMDid >= QuikStatus.STARTUP && CMDid <= QuikStatus.ABORT ) {
        Parent.commgr.sendMessage(dmobj.message);

      //  オブジェクトをサーバに送る (START, CONTINUE, SAVE, getTREE)
      } else if(CMDid >= QuikStatus.START && CMDid <= QuikStatus.getTREE) {
        Parent.commgr.sendCSObject((Object)dmobj);

      //  状態をサーバに返す (rStatus)
      } else if(CMDid == QuikStatus.rSTATUS) {
        Parent.commgr.resumeStatus(CMDid, dmobj.object);

      //  状態をサーバに返す
      } else if(CMDid >= QuikStatus.QUERY) {
        Parent.commgr.resumeStatus(CMDid, dmobj.object);
      }

    /***** 自分がクライアント側ならUIにわたす *****/
    }else if (Iam == 1){

      //  解をクライアントに返す
      if(CMDid == QuikStatus.ANSWER) {
        //Parent.uiClient.messagePrint(dmobj.message);

      //  サーバからの状態をクライアントに返す
      } else if(CMDid >= QuikStatus.SUCCESS) {
        Parent.uiClient.getState(CMDid, (Object)(dmobj.statobj));

      //  シーングラフをクライアントに返す
      } else if(CMDid == QuikStatus.retTREE) {
        Parent.uiClient.setTree((Exec_Tree)(dmobj.object));

      //  OM_Mediaとそのtypeをクライアントに返す
      } else if(CMDid >= QuikStatus.OM_ActionPointer) {
        Parent.uiClient.receivedMedia((OM_ActionPointer)(dmobj.object), CMDid);
      }
    }
  }
}
