// User Interface
// Server Main Window
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/13
// Last Update 1998/01/30

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class UI_Server extends UI_Base
implements ActionListener, WindowListener {
  static TextArea taCommand, taAnswer;
  String rule, query;
  Label lb1, lb2, lb3;
  TextField tfFileName;
  Button btCommandClear, btAnswerClear;
  MenuItem miFileOpen, miFileExit, miLatticeOpen, miDebugOpen;

//  LAT_Main lataf;
  int lat = 0;
  public static String lattice = " ";
  public String filename = "";    /* 1998/07/10 Sugimoto */
  public static String thesa_mess = "";

  Main parent;

  public Exec_Start exec_start = new Exec_Start(); /* 2000/02/11 ogata */

  public UI_Server(Main parent) {
    super("QUIK Server");
    this.parent = parent;

    // メニューバー
    MenuBar mb = new MenuBar();
    setMenuBar(mb);

    // ファイル
    Menu meFile = new Menu("File");
    mb.add(meFile);

    miFileOpen = addMenuItem("Open", miFileOpen, meFile);
    miFileExit = addMenuItem("Exit", miFileExit, meFile);

/**
    // ラティス
    Menu meLattice  = new Menu("Lattice");
    mb.add(meLattice);

    miLatticeOpen = addMenuItem("Open Lattice", miLatticeOpen, meLattice);
*/

    // ウィンドウの作成

    // クイックラベル
    QuikLabel lab = new QuikLabel();
    lab.setSize(500,50);

    // ボタンの設置
    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1, 2));
    btCommandClear = addButton("Clear", btCommandClear, pnButton);
    btAnswerClear = addButton("Clear", btAnswerClear, pnButton);

    // テキストエリアのラベル
    Panel pnLabel = new Panel();
    pnLabel.setLayout(new GridLayout(1, 2));

    lb1 = new Label("PROGRAM WINDOW");
    lb2 = new Label("MESSAGE WINDOW");

    pnLabel.add(lb1);
    pnLabel.add(lb2);

    // テキストエリア
    Panel pnText = new Panel();
    pnText.setLayout(new GridLayout(1, 2));

    taCommand = new TextArea(20,20);
    taAnswer = new TextArea(20,20);
    taAnswer.setEditable(false);

    pnText.add(taCommand);
    pnText.add(taAnswer);

    // テキストエリア
    Panel pnFileName = new Panel();
    pnFileName.setLayout(new BorderLayout(0, 0));

    lb3 = new Label("File Name");
    tfFileName = new TextField(30);

    pnFileName.add("West", lb3);
    pnFileName.add("Center", tfFileName);

    // 全部を合体
    Panel pn1 = new Panel();
    pn1.setLayout(new BorderLayout(0, 0));
    pn1.add("Center", lab);

    Panel pn3 = new Panel();
    pn3.setLayout(new BorderLayout(0, 0));
    pn3.add("North", pn1);
    pn3.add("Center", pnFileName);

    Panel pn2 = new Panel();
    pn2.setLayout(new BorderLayout(0, 0));
    pn2.add("North", pnLabel);
    pn2.add("Center", pnText);
    pn2.add("South", pnButton);

    add("North", pn3);
    add("Center", pn2);

    addWindowListener(this);
  }

  public void start( boolean flag ) {
    /* 1998/07/10 Sugimoto */
    if( !filename.equals("") ) {
      tfFileName.setText( filename );
      Filereader( filename );
    }
    /* end of append */
    setSize(600,500);
    if ( !flag ) {
      show();
    } else {
      System.out.println("\nQuik Server is Standing by !!\n");
    }
  }

  //  コンストラクタ
  public void start() {
    start( false );
  }

  public void actionPerformed(ActionEvent ev) {
    Object obj;

    //  MenuItem Objects
    if( (obj = ev.getSource()) == miFileOpen ) {          //  Open
      //System.out.println("Open...");
      if (tfFileName.getText().equals(""))
	Filereader();
      else
	Filereader(tfFileName.getText());
    } else if( (obj = ev.getSource()) == miFileExit ) {    //  Quit
      //System.out.println("Exit...");
      dispose();
      System.exit(0);
/**
    } else if ( obj == miLatticeOpen ) {                     //  Open Lattice
      if(lat == 0) {
        disp_lattice();
        lat = 1;
      } else {
        if( lataf.isShowing() )
          lataf.setVisible(false);
        else
          lataf.show();
      }
*/

    //  TextField Objects
    } else if ( obj == tfFileName ) {                    //  TextField
      Filereader(tfFileName.getText().trim());

    //  Button Objects
    } else if ( obj == btCommandClear ) {                     //  CLEAR
      taCommand.setText("");
    } else if ( obj == btAnswerClear ) {                     //  CLEAR
      taAnswer.setText("");
    }
  }

/**
  void disp_lattice(){
    //Commands.show_subsumption_lattice();
    lataf = new LAT_Main();
    lataf.start();
  }
*/

  // 返ってきたステータスを処理
  public void getState(int state, Object obj) {
    int err_num = state & QuikStatus.ERRMASK;
    switch (state & QuikStatus.MASK) {
    case QuikStatus.SUCCESS:
      messagePrint("SUCCESS: " + obj);
      break;
    case QuikStatus.ERROR:
      messagePrint("ERROR: " + obj);
      break;
    case QuikStatus.WARNING:
      messagePrint("WARNING: " + obj);
      break;
    case QuikStatus.RESUME:
      //messagePrint("RESUME: " + obj);
      getReturn(err_num, obj);
      break;
    default:
      break;
    }
  }

  // 返ってきたステータスを処理
  public void getReturn(int state, Object obj) {
    switch (state) {
/**
    case QuikStatus.LATTICE_ERROR:
      messagePrint("LATTICE_ERROR: Return Selected Number");
      System.out.println(" "+ obj);
      LAT_Thesa.dia_num = (String)obj;
      break;
    case QuikStatus.SHOW_LATTICE:
//      Subsump.show_lattice();
      break;
*/
    default:
      messagePrint("RESUME: " + obj);
      break;
    }
  }

  public static void messageLattice(String mess) {
    lattice = lattice + mess;
  }

  void Filereader(){
    FileDialog fd = new FileDialog(this, "Open File", FileDialog.LOAD);
    fd.show();

    String dir = fd.getDirectory();
    String file = fd.getFile();
    if (dir.equals("") || file.equals("")) {
      taAnswer.append("File Open Cancel.\n");
      return;
    }
    Filereader(dir + file);
    fd.dispose();
  }

  void Filereader(String file){
    String line = "";
    BufferedReader bfin;

    try{
      bfin = new BufferedReader(new FileReader(file));

      while( (line = bfin.readLine()) != null )
        appendCommand(line);

    } catch(FileNotFoundException e){
      System.out.println("FileNotFoundException:" + e);
    } catch(IOException e){
      System.out.println("IOException:" + e);
    }

    tfFileName.setText(file);

    inputProc( file );
  }

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

  public void m_Start( int cmdid ){
    int flag;

    flag = exec_start.exec_process_allocation( Extern_h.atom_pool, 4 );

  }

  public void m_Continue( int cmdid ){
    int flag;

    flag = exec_start.exec_process_allocation( Extern_h.atom_pool, 3 );
  }

  public void m_Save( int cmdid, Object obj ){

  }

  public void m_getTREE( int cmdid ) {
    Exec_Tree tree = null;
    int       flag;

    tree = exec_start.exec_scene_graph( Extern_h.node_pool );
    Main.mine.commgr.sendObject( tree, QuikStatus.retTREE );
  }


  /* メディアをクライアントに送信した返り値を受けとる */
  public void receive_state( int cmdid ){
    int flag;

    flag = exec_start.exec_process_allocation( Extern_h.atom_pool, cmdid );
  }

  public void inputProc(String Filename) {
    UI_RunParser parse_obj = new UI_RunParser(this);
    parse_obj.start( Filename );
  }

  public void appendCommand(String str) {
    taCommand.append( str + "\n");
  }

  public void setProgram( String Program) {
    taCommand.append( Program);
  }

  public static void messagePrint(String mess) {
    taAnswer.append( mess );
    System.out.print ( mess );
  }

  public void progprint(String prog) {
    taCommand.append(prog);
  }

  public void windowClosing( WindowEvent we ) {
    dispose();
    System.exit(0);
  }
  public  void windowClosed( WindowEvent we ) {}
  public void windowOpened( WindowEvent we ) {}
  public void windowIconified( WindowEvent we ) {}
  public void windowDeiconified( WindowEvent we ) {}
  public void windowActivated( WindowEvent we ) {}
  public void windowDeactivated( WindowEvent we ) {}
}
