package java_quik;
import java_quik.gui.*;
import java_quik.commgr.*;
import java_quik.commgr.common.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends Frame {

  public static Main mine;

  public Main() {
    mine = this;
  }

  public CM_communicationManeger commgr;
  public DM_main                 dmmain;
  public UI_Server               uiServer;
  public UI_Client               uiClient;

  public int mode = -1;
  public int port;

  public boolean nowindow = false;

  public String  filename = "";

  public static void main( String args[] ) {
    Main window = new Main();
    window.init( args );
  }

  public void checkArguments( String args[] ) {
    for ( int i = 0; i < args.length; i++) {
      String arg = args[i];
      if ( arg.startsWith("-") ) {
        arg = arg.substring( 1 ).toLowerCase();
        if ( arg.equals("s") || arg.equals("server") ) {
            if ( mode < 0 )
              mode = QuikStatus.SERVER;
        } else if ( arg.equals("sn") || arg.equals("sn")) {
            nowindow = true;
            if ( mode < 0 )
              mode = QuikStatus.SERVER;
        } else if ( arg.equals("c") || arg.equals("client") ) {
            if ( mode < 0 )
              mode = QuikStatus.CLIENT;
        } else if ( arg.equals("cn") || arg.equals("sn")) {
            nowindow = true;
            if ( mode < 0 )
              mode = QuikStatus.CLIENT;
        } else if ( arg.equals("n") || arg.equals("nowindow") ) {
          nowindow = true;
        } else if ( arg.equals("f") || arg.equals("file") ) {
          if ( args.length > i + 1 )
            filename = args[++i];
        } else if (arg.equals("h") || arg.equals("help") || arg.equals("?")) {
          viewHelp();
        } else {
          System.out.println("no mach argument: -" + arg);
        }
      }
    }
  }

  public void init( String args[] ) {
    if ( args.length > 0 ) {
      checkArguments( args );
    }

    //        Commands.show_version();
    //        FileList.output_prompt();

    commgr = new CM_communicationManeger(this);

    if ( mode < 0 ) {
      UI_SystemWindow sel = new UI_SystemWindow(this, "QUIK", true);
      sel.show();
      mode = sel.getMode();
    }

    if (mode == QuikStatus.SERVER) {
      uiServer = new UI_Server(this);
      commgr.setServer(uiServer);
      uiServer.start( nowindow );
      port = 9001;

    } else if (mode == QuikStatus.CLIENT) {
      uiClient = new UI_Client(this);
      commgr.setClient(uiClient);
      uiClient.start( nowindow );
      port = 9000;
      dmmain = new DM_main(this);
      dmmain.init( QuikStatus.SERVER );
    }

    commgr.init(port);
  }

  public void viewHelp() {
    System.out.println("Option Help =-=-=-=-=-=-=-=-=-=-");
    System.out.println("> java quik.Main { -option }*");
    System.out.println("   server          ... Startup Quik server");
    System.out.println("   sn | ns         ... Startup server without Window");
    System.out.println("   client          ... Startup Quik client");
    System.out.println("   cn | nc         ... Startup client without Window");
    System.out.println("   file [filename] ... Startup with open [filename]");
    System.out.println("   nowindow        ... Startup no window mode");
    System.out.println("   help | ?        ... view this help");

    System.exit(0);
  }
}
