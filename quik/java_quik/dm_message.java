package java_quik;
import java_quik.commgr.*;
import java_quik.gui.*;

// import java_quik.QuikStatus;

import java.io.*;
import java.net.*;

import java.io.Serializable;

public class DM_Message implements Serializable {
  public String Uid = "",
                myHost = "";
  public int CMDid = 0;
  public int Priority = 0; /* -> QuikStatus.NORMAL */
  public int AgletsID = 0;
  public Object object = null;

  public String message = "";
  public int id = 0;
  public Object statobj = null;
  public String medi = "";

  public String client ="";

  //  コンストラクタ
  public DM_Message() {
  }


  public void setUid(String uid) {
    Uid = uid;
  }

  public void setHost(String host) {
    myHost = host;
  }

  public void setCMDid(int cmdid) {
    CMDid = cmdid;
  }

  public void setPriority(int priority) {
    Priority = priority;
  }

  public void setObject(Object obj) {
    object = obj;
  }

  public void setAgletsID(int agletsid) {
    AgletsID = agletsid;
  }

  public void setMessage(String msg) {
    message = msg;
  }

  public void setID(int iid) {
    id = iid;
  }

  public void setMedi(String mmedi) {
    medi = mmedi;
  }

  public void setStatusObj(Object sobj) {
    statobj = sobj;
  }

  public void setClient(String cl) {
    client = cl;
  }


  public String getUid() {
    return Uid;
  }

  public String getHost() {
    return myHost;
  }

  public int getCMDid() {
    return CMDid;
  }

  public int getPriority() {
    return Priority;
  }

  public Object getObject() {
    return object;
  }

  public int getAgletsID() {
    return AgletsID;
  }

  public String getMessage() {
    return message;
  }

  public int getID() {
    return id;
  }

  public String getMedi() {
    return medi;
  }

  public Object getStatusObj() {
    return statobj;
  }

  public String getClient() {
    return client;
  }

}
