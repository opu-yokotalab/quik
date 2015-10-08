package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;

// import DM_Message;

import java.io.*;

import com.ibm.aglet.*;
import com.ibm.aglet.system.*;

import com.ibm.awb.util.Resource;
import com.ibm.atp.daemon.Daemon;

public class CM_agletsServer implements ContextListener {
  String myHost = null;
  int    State = 0;

  Daemon       daemon = null;
  AgletRuntime runtime = null;
  AgletContext cxt = null;

  final int YET     = 0;
  final int ALREADY = 1;

  CM_communicationManeger Parent;

  CM_agletsServer(Object parent) {
    Parent = (CM_communicationManeger)parent;
  }

  CM_agletsServer() {}

  //  ポートを指定する
  public boolean setPort( int port ) {
    String args[] = new String[2];

    if ( State == ALREADY )
      return false;

    args[0] = "-port";
    args[1] = "" + port;
    return setArguments(args);
  }

  //  引数オプションの処理を行なう
  public boolean setArguments( String args[] ) {
    try {
      com.ibm.atp.daemon.Main.importOptions(args);
    } catch( IOException ie ) {
      System.out.println("Arguments set error!!");
      System.out.println( ie );
      return false;
    }
    return true;
  }

  //  Aglets サーバーの構築
  public void BuildServer() {
    State = ALREADY;
    try {
      daemon = Daemon.init(null);

      setLogs(false);    

      runtime = AgletRuntime.init(null);
      cxt = runtime.createAgletContext("");
      cxt.addContextListener(this);

      daemon.start("aglets");
      cxt.start();
      myHost = cxt.getHostingURL().toString();
    } catch( Exception e ) {
      System.out.println("Server build error!!");
      System.out.println("Exeption:" + e );
      e.printStackTrace();
    }
  }

  //  ログの出力の設定
  public void setLogs( boolean Flag ) {
    try {
      if ( !Flag ) {
        daemon.setAccessLogStream(System.out);
        daemon.setMessageLogStream(System.out);
        daemon.setErrorLogStream(System.err);
      } else {
        daemon.setAccessLogFile("log.access");
        daemon.setMessageLogFile("log.message");
        daemon.setErrorLogFile("log.error");
      }
    } catch ( IOException ie ) {
      System.out.println("Log set error!!");
      System.out.println("" + ie );
      ie.printStackTrace();
    }
    return; 
  }

  //  Aglets の作成
  public AgletProxy makeAglets( String aglet ) {
    AgletProxy ap = null;
    try {
      ap = cxt.createAglet(null, aglet, this);
    } catch ( Exception e ) {
      System.out.println("aglets make error!!");
      System.out.println("Exception:" + e);
    }
    return ap;
  }

  //  sendMessage
  public void sendMessage( String Message, int status, String distination) {
    AgletProxy ap = null;
    AG_quikMessage qMessage = null;

    ap = makeAglets("java_quik.commgr.AG_quikMessage");
    try {
      qMessage = (AG_quikMessage)ap.getAglet();
    } catch ( InvalidAgletException e) {}
    qMessage.message = Message;
    qMessage.myHost  = myHost;
    qMessage.Uid     = myHost;
    qMessage.CMDid   = status;

    sendAglets( ap, distination);
  }

  //  sendMessage のオーバーロード
  public void sendMessage( String Message, int status, CM_AddressBook adrbok) {
    sendMessage( Message, status, adrbok.getATP());
  }

  //  sendObject - sever method
  //  hirano
  public void sendObject( Object obj, int status, String distination) {
    AgletProxy ap = null;
    AG_quikMessage qMessage = null;

    ap = makeAglets("java_quik.commgr.AG_quikMessage");
    try {
      qMessage = (AG_quikMessage)ap.getAglet();
    } catch ( InvalidAgletException e) {}
    qMessage.message = ""; /* OK? */
    qMessage.myHost  = myHost;
    qMessage.Uid     = myHost;
    qMessage.CMDid   = status;

    qMessage.object  = obj;

    sendAglets( ap, distination);
  }

  //  sendObject のオーバーロード - sever method
  //  hirano
  public void sendObject( Object obj, int status, CM_AddressBook adrbok) {
    sendObject( obj, status, adrbok.getATP());
  }

  //  sendCSObject - client method
  //  hirano
  public void sendCSObject( Object dmobj, int status, String distination) {
    AgletProxy ap = null;
    AG_quikMessage qMessage = null;

    ap = makeAglets("java_quik.commgr.AG_quikMessage");
    try {
      qMessage = (AG_quikMessage)ap.getAglet();
    } catch ( InvalidAgletException e) {}
    qMessage.message = ((DM_Message)dmobj).message;
    qMessage.myHost  = myHost;
    qMessage.Uid     = ((DM_Message)dmobj).client;
    qMessage.CMDid   = status;

    qMessage.object  = ((DM_Message)dmobj).object;
    /* qMessage.client  = ((DM_Message)dmobj).client; */

    sendAglets( ap, distination);
  }

  //  sendCSObject のオーバーロード - client method
  //  hirano
  public void sendCSObject( Object dmobj, int status, CM_AddressBook adrbok) {
    sendCSObject( dmobj, status, adrbok.getATP());
  }

  //  状態を返す - client and server method
  //  Hirano
  //  client: obj <- ((DM_Message)dmobj).statobj
  public void returnStatus( int Errid, Object obj, String distination) {
    AgletProxy ap = null;
    AG_quikStatus qStatus = null;

    ap = makeAglets("java_quik.commgr.AG_quikStatus");
    try {
      qStatus = (AG_quikStatus)ap.getAglet();
    } catch ( InvalidAgletException ie ) {}
    qStatus.medi   = myHost;
    qStatus.CMDid  = Errid;
    qStatus.obj    = obj;
    qStatus.myHost = myHost;

    sendAglets( ap, distination);
  }

  public void sendAglets( AgletProxy ap, String distination) {
    try {
      Message msg = new Message("startTrip", distination);
      ap.sendMessage(msg);
    } catch ( Exception e ) {
      System.out.println("Aglets Send Error!!");
      System.out.println("Exception:" + e);
    }
  }

  public void agletArrived(ContextEvent ce) {
    AG_Aglet Aaglets = null;

    try {
      //  到着した Aglets の取得
      Aaglets = (AG_Aglet)ce.getAgletProxy().getAglet();
    } catch ( InvalidAgletException ie ) {}
    //  そのサーバーを自サーバーに変更

    Parent.addQueue(Aaglets);
  }
  public void contextStarted(ContextEvent ce) {}
  public void contextShutdown(ContextEvent ce) {}
  public void agletCreated(ContextEvent ce) {}
  public void agletCloned(ContextEvent ce) {}
  public void agletActivated(ContextEvent ce) {}
  public void agletReverted(ContextEvent ce) {}
  public void agletDisposed(ContextEvent ce) {}
  public void agletDispatched(ContextEvent ce) {}
  public void agletDeactivated(ContextEvent ce) {}
  public void agletStateChanged(ContextEvent ce) {}
  public void showDocument(ContextEvent ce) {}
  public void showMessage(ContextEvent ce) {}
}
