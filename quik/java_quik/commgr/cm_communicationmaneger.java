package java_quik.commgr;
import java_quik.*;
import java_quik.send_om.*;
import java_quik.gui.*;
import java_quik.commgr.common.*;
import java_quik.QuikStatus;

// import DM_Message;

import java.io.*;
import java.net.*;
import java.util.*;

public class CM_communicationManeger {

  String url = "file:/project/quik/Quik/java_quik/commgr/AddressBook.txt";

  CM_AddressBook  adrbok  = new CM_AddressBook();
  CM_agletsServer AServer = new CM_agletsServer(this);
  CM_command      command = new CM_command();
  CM_Thread       thread  = new CM_Thread(this);
  CM_Queue        queue   = null;

  int STATUS = 0;

  public Main parent = null;
  public UI_Server uiServer = null;
  public UI_Client uiClient = null;

  final String SEPARATOR = "\t";

  String qServer,
         qClient,
         retAServer;

  //  ���󥹥ȥ饯��
  public CM_communicationManeger(Object init) {
    parent = (Main)init;
    uiServer = parent.uiServer;
    uiClient = parent.uiClient;
  }

  //  ���󥹥ȥ饯��
  public CM_communicationManeger() {}

  /*****************************  ���̥᥽�å�  *****************************/

  //  �����
  public void init(int port) {
    AServer.setPort(port);           //  Aglets �����ФΥݡ��Ȼ���
    AServer.BuildServer();           //  Aglets �����Фε�ư
    getAddressBook( url);            //  ���ɥ쥹�֥å����ɤ߹���

    thread.start();                  //  ����ֽ�������åɤ򥹥�����
  }

  //  �䤤��碌,�ץ��������ե��������¸����
  public void save( String message, String filename) {
    BufferedWriter bw = null;

    try {
      bw = new BufferedWriter( new FileWriter(filename) );
      bw.write( message );
      bw.close();
    } catch ( IOException ie ) {
      System.out.println("IO Error!!");
      System.out.println("" + ie);
    }
  }

  //  save �Υ����С�����
  public void save( String message ) {
    save( message, "_program.quik");
  }

  //  MediName �ǻ��ꤵ����ǥ�������̾����ä���ǥ���������
  //  ���ɥ쥹�֥å�����õ��
  public CM_AddressBook searchMediName( String MediName ) {
    CM_AddressBook ptr = null;

    for( ptr = adrbok; ptr != null; ptr = ptr.next)
      if ( ptr.searchMediName(MediName) )
        break;

    return ptr;
  }

  //  �ץ�������ɤ���(URL�����)
  public String loadProgram( CM_AddressBook ptr ) {
    URL url;
    URLConnection urlc;
    BufferedReader br;
    String program = "", line;

    try {
      System.out.println("file:" + ptr.getFile() );
      url = new URL(ptr.getFile());
      urlc = url.openConnection();
      br = new BufferedReader( new InputStreamReader( urlc.getInputStream()));

      while( (line = br.readLine()) != null ) {
        program += line;
        if ( line.indexOf("\n") < 0 )
          program += "\n";
      }
    } catch ( Exception e ) {
      System.out.println("Program Load Error !!");
      System.out.println("" + e );
      e.printStackTrace();
    }
    return program;
  }

  //  ���ɥ쥹�֥å�����������Ф�
  public void getAddressBook( String Url ) {
    URL url;
    URLConnection urlc;
    BufferedReader br;
    String line;
    CM_AddressBook tmp, now = adrbok;

    try {
      url = new URL(Url);
      urlc = url.openConnection();
      br = new BufferedReader( new InputStreamReader(urlc.getInputStream()));

      while( (line = br.readLine()) != null ) {
        if ( !line.startsWith("#") ) {
          tmp = addAddressBook(line);
          if ( tmp != null ) {
            adrbok.connect(tmp);
            adrbok = adrbok.next;
          }
        }
      }
    } catch( Exception e ) {
      System.out.println("Addressbook lookup Error!!");
      System.out.println("" + e);
      e.printStackTrace();
    }
    adrbok = now.next;
  }

  //  getAddressBook �Υ����С�����
  public void getAddressBook() {
    getAddressBook(url);
  }

  //  ���ɥ쥹�֥å��ξ���򥪥֥������Ȥ˳�Ǽ����
  public CM_AddressBook addAddressBook( String Field ) {
    String tk1 = null,
           tk2 = null,
           tk3 = null;
    int    sl1 = -1,
           sl2 = -1;

    sl1 = Field.indexOf(SEPARATOR);
    sl2 = Field.lastIndexOf(SEPARATOR);

    if ( sl1 == -1 || sl2 == -1 )
      return null;

    tk1 = Field.substring( 0, sl1);
    tk2 = Field.substring( sl1 + 1, sl2);
    tk3 = Field.substring( sl2 + 1, Field.length());

    return new CM_AddressBook( tk1, tk2, tk3);
  }

  //  �Ԥ�������ɲ�
  public void addQueue( AG_Aglet aglets) {
    String Uid;
    int    CMDid;
    CM_Queue ptr = queue;

    Uid = aglets.getUid();
    CMDid = aglets.getCMDid();

    if ( aglets.getPriority() == QuikStatus.HIGHEST) {
      queue = new CM_Queue( Uid, aglets, CMDid);
      queue.chainQ( ptr);
      return;
    }

    if ( queue == null ) {
      queue = new CM_Queue( Uid, aglets, CMDid);
      return;
    }

    for ( ; queue.next != null; queue = queue.next);
    queue.chainQ( new CM_Queue( Uid, aglets, CMDid));
    queue = ptr;
  }

  //  �Ԥ�����֤μ¹�
  public void execQueue() {
    int      CMDid;
    AG_Aglet aglets;

//    System.out.println("queue:" + queue);
    if ( uiClient != null ) {
      uiClient.waiting();
    }
    if ( queue == null ) {
      return;
    }

    CMDid  = queue.CMDid;
    aglets = queue.aglets;
    queue  = queue.next;
    retAServer = aglets.myHost;

    //  Hirano
    /*****  ���饤����Ȥ���ν���  *****/
    //  startup
    if ( CMDid == QuikStatus.STARTUP ) {
      SvStartup(((AG_quikMessage)aglets).message);

    //  activate
    } else if ( CMDid == QuikStatus.ACTIVATE ) {
      SvActivate();

    //  open
    } else if ( CMDid == QuikStatus.OPEN ) {
      SvOpen();

    //  ��礻
    } else if ( CMDid == QuikStatus.QUERY ) {
      respondQuery(((AG_quikMessage)aglets).message);

    //  start
    } else if ( CMDid == QuikStatus.START ) {
      uiServer.m_Start( CMDid );

    //  continue
    } else if ( CMDid == QuikStatus.CONTINUE ) {
      uiServer.m_Continue( CMDid );

    //  save
    } else if ( CMDid == QuikStatus.SAVE ) {
      uiServer.m_Save(CMDid, (OM_Media)(aglets.object));

    //  getTree
    } else if ( CMDid == QuikStatus.getTREE ) {
      uiServer.m_getTREE( CMDid );

    //  returnStatus
    } else if ( CMDid == QuikStatus.rSTATUS ) {
      int retFlag = ((Integer)((AG_quikStatus)aglets).obj).intValue();
      uiServer.receive_state( retFlag );

    /*****  �����Ф���ν���  *****/
    //  ��ǥ����򥯥饤����Ȥ��֤��� 2000/02/11  Earsh
    } else if (CMDid >= QuikStatus.OM_ActionPointer &&
               CMDid <= QuikStatus.retTREE ) {
      DM_Message dmobj = makeDummyObj( aglets );
      parent.dmmain.sendObject(dmobj); /* send to dummy */

   //  ��򥯥饤����Ȥ��֤���
    } else if ( CMDid == QuikStatus.ANSWER ) {
      DM_Message dmobj = makeDummyObj(aglets);
      parent.dmmain.sendObject(dmobj); /* send to dummy */

    //  ���饤����Ȥ���Υ��顼��������򥵡��Ф��֤�
    } else if ( (CMDid & QuikStatus.MASK) == QuikStatus.RESUME ) {
      uiServer.getState( CMDid, (Object)((AG_quikStatus)aglets).obj);

    //  �����Ф�����֤��֤äƤ����饯�饤����Ȥ��֤�
    } else if ( CMDid >= QuikStatus.SUCCESS ) {
      DM_Message dmobj = makeDummyObj(aglets);
      parent.dmmain.sendObject(dmobj); /* send to dummy */

    }
  }

  //  �ȡ����󤫤��ǥ�������̾���ڤ����
  public String checkMediName( String token) {
    int sl[], min = token.length();
    String ss[] = {".", ";;", " ", "\t", "\r", "\n"};

    sl = new int[ss.length];
    for ( int i = 0; i < ss.length; i++)
      sl[i] = token.indexOf( ss[i]);

    for ( int i = 0; i < sl.length; i++ )
      if ( sl[i] != -1 && min > sl[i] )
        min = sl[i];

    return token.substring( 0, min);
  }

  //  aglets to DummyObject �Ѵ�
  //  Hirano
  public DM_Message makeDummyObj(AG_Aglet aglets) {
    DM_Message dmobj = new DM_Message();

    dmobj.setUid(aglets.getUid());
    dmobj.setCMDid(aglets.getCMDid());
    dmobj.setPriority(aglets.getPriority());
    dmobj.setObject(aglets.getObject());
    dmobj.setHost(aglets.getHost());
    dmobj.setAgletsID(aglets.getAgletsID());

    if( aglets.getCMDid() <= QuikStatus.retTREE ) {
      dmobj.setMessage(((AG_quikMessage)aglets).message);
      dmobj.setID(((AG_quikMessage)aglets).id);
    } else if( aglets.getCMDid() >= QuikStatus.SUCCESS ){
      dmobj.setMedi(((AG_quikStatus)aglets).medi);
      dmobj.setStatusObj((Object)((AG_quikStatus)aglets).obj);
    }

    /* dmobj.setClient(retDClient); */

    return dmobj;
  }
  /*************************  ���饤����ȥ᥽�å�  *************************/

  //  QUIK ���饤����Ȥ���Ͽ
  public void setClient( UI_Client client) {
    uiClient = client;
  }

  //  ���饤�����(dummy server)����DummyObject������� - client method
  //  Hirano
  public void sendCSObject(Object dmobj) {
    int cmd = -1;

    // getMessage
    String Message = ((DM_Message)dmobj).message;

    cmd = command.selCommand( Message);
    if ( cmd >= QuikStatus.STARTUP && cmd <= QuikStatus.ABORT ) {
      ClCommand( dmobj, cmd);
    }else if ( cmd >= QuikStatus.START && cmd <= QuikStatus.getTREE ) {
      AServer.sendCSObject( dmobj, cmd, qServer);
    }else {
      AServer.sendCSObject( dmobj, QuikStatus.QUERY, qServer);
    }
  }

  //  ���饤�����¦�ǤΥ��ޥ�ɽ���
  //  Hirano
  public void ClCommand(Object dmobj, int state) {
    String token[], MediName,
           commands[] = {"&startup", "&activate", "&open",
                         "&close", "&shutdown", "&abort" };
    CM_AddressBook abptr;
    int i, cl;

    String Message = ((DM_Message)dmobj).message;

    token = command.divToken( Message);
    System.out.println("token.length:" + token.length);
    cl = command.findCommand( commands[state - 1], token);
    System.out.println("cl:" + cl);
    MediName = checkMediName( token[cl + 1]);

    abptr = searchMediName( MediName);
    if ( abptr == null ) {
      uiClient.getState( QuikStatus.ERROR | QuikStatus.MEDINAME_NOT_FOUND,
                        (Object)("Mediator name is not Found:" + MediName));
      System.out.println("Not Find such Mediator name!!");
      return;
    }

    qServer = abptr.getATP();
    ((DM_Message)dmobj).setMessage(MediName);

    AServer.sendCSObject( dmobj, state, abptr);
  }

  //  ���饤����ȥ�����ɥ������å������������
  /*  dummy���� ((DM_Message)dmobj).message �����н����̤� */
  public void sendMessage(String Message) {
    int cmd = -1;

    cmd = command.selCommand( Message);
    if ( cmd >= QuikStatus.STARTUP && cmd <= QuikStatus.ABORT)
      ClCommand( Message, cmd);
    else {
      AServer.sendMessage( Message, QuikStatus.QUERY, qServer);
    }
  }

  //  ClCommand�Υ����С�����
  //  Hirano
  public void ClCommand(String Message, int state) {
    DM_Message dmobj = new DM_Message();

    dmobj.setMessage(Message);

    ClCommand(dmobj, state);
  }

  //  WARNING�������������򥵡��Ф��֤�
  /*  dummy���� ((Message)dmobj).statobj �����н����̤� */
  public void resumeStatus( int Errid, Object obj) {
//    System.out.println("retAServer:" + retAServer);
    AServer.returnStatus( Errid, obj, retAServer);
  }

  /****************************  �����Х᥽�å�  ****************************/

  //  QUIK �����Ф���Ͽ
  public void setServer( UI_Server server) {
    uiServer = server;
  }

  //  �Ѿ��ط��ˤ�äƥ�ǥ�������(�ץ����)��ȤäƤ���
  //  ������ե�����(URL�����)����
  public String get_Mediators( String MediName ) {
    CM_AddressBook ptr = null;
    String Program;

    ptr = searchMediName( MediName );
    if ( ptr == null ) {
      uiServer.getState( QuikStatus.ERROR | QuikStatus.MEDINAME_NOT_FOUND,
                        (Object)("Mediator name is not Found:" + MediName));
      System.out.println("Not Find such Mediator name!!");
      return null;
    }

    Program = loadProgram( ptr );
    save( Program, MediName);
    return MediName;
  }

  //  �����Ф����å��������֤�
  public void returnMessage( String Message, int CMDid ) {
    AServer.sendMessage( Message, CMDid, retAServer);
  }

  //  ������¦�� startup
  public void SvStartup(String MediName) {
    String Program;
    CM_AddressBook abptr = null;

    STATUS = QuikStatus.STARTUP;

    abptr = searchMediName( MediName);
/**    ���饤����Ȥǽ�������Ƥ��뤿�ᤳ���ϰ�̣�ʤ�
    if ( abptr == null ) {
      System.out.println("Not Find such Mediator name!!");
      return;
    }
*/
    Program = loadProgram( abptr );
    System.out.println("Program");

    uiServer.setProgram( Program );

    Program += "\n";

    save( Program, MediName);
    System.out.println("MediName:" + MediName);
    System.out.println("parent.uiServer:" + parent.uiServer);
    uiServer.inputProc( MediName);
    System.out.println("I'm back!!");

    /* �ǥХå� 2000/1/25 */
    /*OM_MediaPrint media_print = new OM_MediaPrint();
    media_print.Print_Rule();
    System.exit(1);*/

    returnStatus( QuikStatus.SUCCESS | QuikStatus.STARTUP,
                  "startup complete.");
  }

  public void SvActivate() {
    //  activate ���ν����Ϥ�����
    returnStatus( QuikStatus.SUCCESS | QuikStatus.ACTIVATE,
                  "activate complete.");
  }

  public void SvOpen() {
    //  open ���Τ����Ϥ�����
    returnStatus( QuikStatus.SUCCESS | QuikStatus.OPEN,
                  "open complete.");
  }

  //  query �������Ƥ������ν���
  public void respondQuery( String query) {
    save( query, "Query");
    uiServer.inputProc("Query");
  }

  //  �����С�����ν�����̾��֤��֤�
  public void returnStatus( int Errid, Object obj) {
    AServer.returnStatus( Errid, obj, retAServer);
  }

  //  �����Ф���Object���֤�
  //  Hirano
  public void sendObject( Object media, int type ) {
    //  2000/02/11  Earsh
    //  OM_Media ���� DM_Media ���Ѵ���Ԥ�
    if ( media == null && type == -1 ) {
      returnStatus( (QuikStatus.SUCCESS | QuikStatus.OM_ActionPointer), null);
    } else {
      if ( type == QuikStatus.OM_ActionPointer ) {
//        DM_Media dmedia = new DM_Media( (OM_Media)media );
        AServer.sendObject( media, type, retAServer);
      } else if ( type == QuikStatus.retTREE ) {
        AServer.sendObject( media, type, retAServer);
      }
    }
  }
}
