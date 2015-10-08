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

  //  ���󥹥ȥ饯��
  public DM_main(Object parent) {
    // �ƤӽФ���Ƥ��륯�饹����Ͽ
    Parent = (Main)parent;
  }

  //  ���󥹥ȥ饯��
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

  //  �ᥤ��
  public static void main( String args[] ) {
  }


  /*****  UI method  *****/
  //  ���ޥ�ɤ����� (start, continue, save, getTree)
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

  //  ���֤�����
  public void sendStatus(int stat) {
    DM_Message dmobj = new DM_Message();

    /* dmobj.setMessage(); */
    dmobj.setCMDid(QuikStatus.rSTATUS);

    Integer sstat = new Integer(stat);
    dmobj.setObject((Object)sstat);

    sendObject(dmobj);
  }

  //  �����Ф򥻥åȤ���
  public void setServer(String adr) {
    wserver = adr;
  }
  /*****  �����ޤ� UI method  *****/

  //  ���֥������Ȥ�����
  public void sendObject(DM_Message dmobj) {
    int wport = 9002;

    // ������Ϥ��ʤ������
    if(Iam == 0){ // ��ʬ��������¦�ʤ�
      wserver = retDClient;
      wport = 9002;
    } else if (Iam == 1){ // ��ʬ�����饤�����¦�ʤ�
      wport = 9003;
      dmobj.setClient(myIP); /* ���ۥ���̾�񤭤��� */
    }

    ws.sendObject((Object)dmobj, wserver, wport);

  }

  //  ���֥������Ȥ������äƽ�������
  public void rcvdObject(DM_Message dmobj) {

    int CMDid  = dmobj.CMDid;
//    System.out.println("CMDid:" + Integer.toHexString(CMDid));
    retDClient = dmobj.client;

    /***** ��ʬ��������¦�ʤ�Aglets�ˤ錄�� *****/
    if(Iam == 0){

      if ( CMDid >= QuikStatus.STARTUP && CMDid <= QuikStatus.ABORT ) {
        Parent.commgr.sendMessage(dmobj.message);

      //  ���֥������Ȥ򥵡��Ф����� (START, CONTINUE, SAVE, getTREE)
      } else if(CMDid >= QuikStatus.START && CMDid <= QuikStatus.getTREE) {
        Parent.commgr.sendCSObject((Object)dmobj);

      //  ���֤򥵡��Ф��֤� (rStatus)
      } else if(CMDid == QuikStatus.rSTATUS) {
        Parent.commgr.resumeStatus(CMDid, dmobj.object);

      //  ���֤򥵡��Ф��֤�
      } else if(CMDid >= QuikStatus.QUERY) {
        Parent.commgr.resumeStatus(CMDid, dmobj.object);
      }

    /***** ��ʬ�����饤�����¦�ʤ�UI�ˤ錄�� *****/
    }else if (Iam == 1){

      //  ��򥯥饤����Ȥ��֤�
      if(CMDid == QuikStatus.ANSWER) {
        //Parent.uiClient.messagePrint(dmobj.message);

      //  �����Ф���ξ��֤򥯥饤����Ȥ��֤�
      } else if(CMDid >= QuikStatus.SUCCESS) {
        Parent.uiClient.getState(CMDid, (Object)(dmobj.statobj));

      //  �����󥰥�դ򥯥饤����Ȥ��֤�
      } else if(CMDid == QuikStatus.retTREE) {
        Parent.uiClient.setTree((Exec_Tree)(dmobj.object));

      //  OM_Media�Ȥ���type�򥯥饤����Ȥ��֤�
      } else if(CMDid >= QuikStatus.OM_ActionPointer) {
        Parent.uiClient.receivedMedia((OM_ActionPointer)(dmobj.object), CMDid);
      }
    }
  }
}
