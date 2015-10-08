package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;

import com.ibm.aglet.*;
import com.ibm.aglet.event.*;
import com.ibm.aglet.util.*;

import com.ibm.agletx.util.SimpleItinerary;

public class AG_Aglet extends Aglet {
  public String Uid = "",
                myHost = "";
  public int    CMDid = 0;
  public int    Priority = QuikStatus.NORMAL;
  public int    AgletsID = 0;
  public Object object = null;

  public SimpleItinerary itinerary = null;

  public void onCreation( Object init) {
    itinerary = new SimpleItinerary(this);
  }

  public String getUid() {
    return Uid;
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

  public String getHost() {
    return myHost;
  }

  public int getAgletsID() {
    return AgletsID;
  }

  public void setUid( String uid) {
    Uid = uid;
  }

  public void setHost( String host) {
    myHost = host;
  }

  public void setCMDid( int cmdid) {
    CMDid = cmdid;
  }

  public void setPriority( int priority) {
    Priority = priority;
  }

  public void setObject( Object obj) {
    object = obj;
  }

  public void setAgletID( int agletsid) {
    AgletsID = agletsid;
  }
}
