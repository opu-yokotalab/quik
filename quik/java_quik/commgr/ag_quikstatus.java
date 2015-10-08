package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;

import com.ibm.aglet.*;
import com.ibm.aglet.event.*;
import com.ibm.aglet.util.*;

import com.ibm.agletx.util.SimpleItinerary;

public class AG_quikStatus extends AG_Aglet {
   String medi;
   Object obj;

  SimpleItinerary itinerary = null;

  public void onCreation( Object init) {
    itinerary = new SimpleItinerary(this);
  }

  public boolean handleMessage( Message msg) {
    if ( msg.sameKind("startTrip")) {
      startTrip(msg);
    } else if ( msg.sameKind("reachServer")) {
      reachServer(msg);
    } else {
      return false;
    }
    return true;
  }

  public synchronized void startTrip( Message msg) {
    String destination = (String)msg.getArg();

    try {
      itinerary.go( destination, "reachServer");
    } catch ( Exception e ) {
      System.out.println("Aglets Send Error!!");
      e.printStackTrace();
    }
  }

  public void reachServer( Message msg) {}
}
