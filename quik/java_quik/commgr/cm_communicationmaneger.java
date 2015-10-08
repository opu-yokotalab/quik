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

  //  コンストラクタ
  public CM_communicationManeger(Object init) {
    parent = (Main)init;
    uiServer = parent.uiServer;
    uiClient = parent.uiClient;
  }

  //  コンストラクタ
  public CM_communicationManeger() {}

  /*****************************  共通メソッド  *****************************/

  //  初期化
  public void init(int port) {
    AServer.setPort(port);           //  Aglets サーバのポート指定
    AServer.BuildServer();           //  Aglets サーバの起動
    getAddressBook( url);            //  アドレスブックの読み込み

    thread.start();                  //  ジョブ処理スレッドをスタート
  }

  //  問い合わせ,プログラム等をファイルに保存する
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

  //  save のオーバーロード
  public void save( String message ) {
    save( message, "_program.quik");
  }

  //  MediName で指定されるメディエータ名を持ったメディエータを
  //  アドレスブックから探す
  public CM_AddressBook searchMediName( String MediName ) {
    CM_AddressBook ptr = null;

    for( ptr = adrbok; ptr != null; ptr = ptr.next)
      if ( ptr.searchMediName(MediName) )
        break;

    return ptr;
  }

  //  プログラムをロードする(URL指定で)
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

  //  アドレスブックから情報を取り出す
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

  //  getAddressBook のオーバーロード
  public void getAddressBook() {
    getAddressBook(url);
  }

  //  アドレスブックの情報をオブジェクトに格納する
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

  //  待ち行列に追加
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

  //  待ちジョブの実行
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
    /*****  クライアントからの処理  *****/
    //  startup
    if ( CMDid == QuikStatus.STARTUP ) {
      SvStartup(((AG_quikMessage)aglets).message);

    //  activate
    } else if ( CMDid == QuikStatus.ACTIVATE ) {
      SvActivate();

    //  open
    } else if ( CMDid == QuikStatus.OPEN ) {
      SvOpen();

    //  問合せ
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

    /*****  サーバからの処理  *****/
    //  メディアをクライアントに返す。 2000/02/11  Earsh
    } else if (CMDid >= QuikStatus.OM_ActionPointer &&
               CMDid <= QuikStatus.retTREE ) {
      DM_Message dmobj = makeDummyObj( aglets );
      parent.dmmain.sendObject(dmobj); /* send to dummy */

   //  解をクライアントに返す。
    } else if ( CMDid == QuikStatus.ANSWER ) {
      DM_Message dmobj = makeDummyObj(aglets);
      parent.dmmain.sendObject(dmobj); /* send to dummy */

    //  クライアントからのエラー復帰情報をサーバに返す
    } else if ( (CMDid & QuikStatus.MASK) == QuikStatus.RESUME ) {
      uiServer.getState( CMDid, (Object)((AG_quikStatus)aglets).obj);

    //  サーバから状態が返ってきたらクライアントに返す
    } else if ( CMDid >= QuikStatus.SUCCESS ) {
      DM_Message dmobj = makeDummyObj(aglets);
      parent.dmmain.sendObject(dmobj); /* send to dummy */

    }
  }

  //  トークンからメディエータ名の切りだし
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

  //  aglets to DummyObject 変換
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
  /*************************  クライアントメソッド  *************************/

  //  QUIK クライアントの登録
  public void setClient( UI_Client client) {
    uiClient = client;
  }

  //  クライアント(dummy server)からDummyObjectを受ける - client method
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

  //  クライアント側でのコマンド処理
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

  //  クライアントウィンドウからメッセージを受ける
  /*  dummyから ((DM_Message)dmobj).message 入れれば従来通り */
  public void sendMessage(String Message) {
    int cmd = -1;

    cmd = command.selCommand( Message);
    if ( cmd >= QuikStatus.STARTUP && cmd <= QuikStatus.ABORT)
      ClCommand( Message, cmd);
    else {
      AServer.sendMessage( Message, QuikStatus.QUERY, qServer);
    }
  }

  //  ClCommandのオーバーロード
  //  Hirano
  public void ClCommand(String Message, int state) {
    DM_Message dmobj = new DM_Message();

    dmobj.setMessage(Message);

    ClCommand(dmobj, state);
  }

  //  WARNINGからの復帰情報をサーバに返す
  /*  dummyから ((Message)dmobj).statobj 入れれば従来通り */
  public void resumeStatus( int Errid, Object obj) {
//    System.out.println("retAServer:" + retAServer);
    AServer.returnStatus( Errid, obj, retAServer);
  }

  /****************************  サーバメソッド  ****************************/

  //  QUIK サーバの登録
  public void setServer( UI_Server server) {
    uiServer = server;
  }

  //  継承関係によってメディエータ(プログラム)をとってくる
  //  ローカルファイル(URL指定で)から
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

  //  サーバからメッセージを返す
  public void returnMessage( String Message, int CMDid ) {
    AServer.sendMessage( Message, CMDid, retAServer);
  }

  //  サーバ側の startup
  public void SvStartup(String MediName) {
    String Program;
    CM_AddressBook abptr = null;

    STATUS = QuikStatus.STARTUP;

    abptr = searchMediName( MediName);
/**    クライアントで処理されているためここは意味なし
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

    /* デバッグ 2000/1/25 */
    /*OM_MediaPrint media_print = new OM_MediaPrint();
    media_print.Print_Rule();
    System.exit(1);*/

    returnStatus( QuikStatus.SUCCESS | QuikStatus.STARTUP,
                  "startup complete.");
  }

  public void SvActivate() {
    //  activate 時の処理はここで
    returnStatus( QuikStatus.SUCCESS | QuikStatus.ACTIVATE,
                  "activate complete.");
  }

  public void SvOpen() {
    //  open 時のしょりはここで
    returnStatus( QuikStatus.SUCCESS | QuikStatus.OPEN,
                  "open complete.");
  }

  //  query が送られてきた時の処理
  public void respondQuery( String query) {
    save( query, "Query");
    uiServer.inputProc("Query");
  }

  //  サーバーからの処理結果状態を返す
  public void returnStatus( int Errid, Object obj) {
    AServer.returnStatus( Errid, obj, retAServer);
  }

  //  サーバからObjectを返す
  //  Hirano
  public void sendObject( Object media, int type ) {
    //  2000/02/11  Earsh
    //  OM_Media から DM_Media へ変換を行う
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
