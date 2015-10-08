// User Interface SystemWindow
// Programed By Shiraki
// 
// JDK1.1
// From 1998/10/09
// Last Update 1999/02/02

/* SystemWindow.java */
/* Heterogenious documents integrated management system�Υ��󥿥ե������� */

import java.awt.*;
import java.awt.event.*;

public class SystemWindow extends Frame implements ActionListener{

  TextArea ta_caution;
  Button bt_xml, bt_link, bt_search;
  MenuBar menubar;
  Menu window, help;
  MenuItem mi_close, mi_quit, mh_ver, mh_man;

  // �̥��饹�λ���
  XmlWindow    xml = null;
  LinkEdit     link = null;
  XmlSearch    search = null;
  Version      ver = null;
  ManFrame     man = null;

  String staff;

  // init�᥽�å�
  public void init() {

    staff  = "Faculty of Computer Science and System Engineering Okayama Prefectural University\n";
    staff += "--- STAFF ---\n";
    staff += "Professor Dr.Kazumasa Yokota\n";
    staff += "Associate Professor Dr.Takeo Kunishima\n";
    staff += "Associate Professor Dr.Bojiang Liu (Faculty of Informatics Okayama University of Science)\n";
    staff += "--- '97 Labo Deiredire Application Team Staff ---\n";
    staff += "Noritaka Ikeguchi (B4), Takamasa Nose (B4), Hiroaki Hongyo (B4)\n";
    staff += "--- '98 Labo Deiredire Application Team Staff ---\n";
    staff += "Hiroaki Hongyo (M1), Yoshitaka Shiraki (B4), Akiko Tamaki (B4)\n";

    // �ƥ����ȥ��ꥢ
    ta_caution = new TextArea(staff, 30, 34, ta_caution.SCROLLBARS_NONE);
    ta_caution.setEditable(false);
    add(ta_caution);

    // ��˥塼�С�����
    menubar = new MenuBar();
    setMenuBar(menubar);

    // ��˥塼
    window = new Menu( "Window" );
    menubar.add(window);
    mi_close = new MenuItem("Close window ...");
    window.add(mi_close);
    mi_close.addActionListener(this);
    mi_quit = new MenuItem("Quit system ...");
    window.add(mi_quit);
    mi_quit.addActionListener(this);

    // �إ�ץ�˥塼
    help = new Menu( "Help" );
    menubar.add(help);
    menubar.setHelpMenu(help);
    mh_ver = new MenuItem( "Version info.." );
    help.add(mh_ver);
    mh_ver.addActionListener(this);
    mh_man = new MenuItem( "Manual info.." );
    help.add(mh_man);
    mh_man.addActionListener(this);

    // �ܥ������
    bt_xml = new Button( "XmlWindowSystem" );
    bt_xml.addActionListener(this);
    bt_link = new Button( "XmlLinkEditor" );
    bt_link.addActionListener(this);
    bt_search = new Button( "XmlSearchEngin" );
    bt_search.addActionListener(this);

    SystemLabel lab = new SystemLabel();
    lab.setSize(500,50);

    // �쥤�����Ⱦ���
    Panel pnTitle = new Panel();
    pnTitle.setLayout(new BorderLayout());
    pnTitle.add(lab);
    add("North",pnTitle);

    Panel pnInfor = new Panel();
    pnInfor.setLayout(new BorderLayout());
    pnInfor.add(ta_caution);
    add("Center",pnInfor);

    Panel pnButton = new Panel();
    pnButton.setLayout(new GridLayout(1,3));
    pnButton.add(bt_xml);  
    pnButton.add(bt_link);  
    pnButton.add(bt_search);  
    add("South",pnButton);

    addWindowListener(new WindowControl());

  }

  //  �ᥤ��᥽�å�
  public static void main(String args[]){
    SystemWindow window = new SystemWindow();
    window.setTitle("Documents Integrated Management System ver." + Version.version_xml);
    window.init();
    //window.pack();
    window.setResizable(false);
    window.setSize(600,300);
    window.show();
  }

  public void actionPerformed( ActionEvent e ) {
    Object obj = e.getSource();
 
    if ( obj == mi_close) {
      // ���Υ�����ɥ�������λ
      if ( xml != null && xml.isVisible() == true || link != null && link.isVisible() == true || search != null && search.isVisible() == true ){
	dispose();
      }else{
	System.exit(0);
	dispose();
      }

    } else if ( obj == mi_quit) {
      // �����ƥऴ�Ƚ�λ
      dispose();
      System.exit(0);

    } else if( obj == bt_xml ) {
      // ��󥯥��ǥ�����ư
      if ( xml == null )
	ShowXmlWindow();
      if ( !xml.isVisible() )
	xml.setVisible(true);

    } else if( obj == bt_link ) {
      // ��󥯥��ǥ�����ư
      if ( link == null )
	ShowLinkEdit();
      if ( !link.isVisible() )
	link.setVisible(true);

    } else if( obj == bt_search ) {
      // XmlSearch��ư
      if ( search == null )
	ShowXmlSearch();
      if ( !search.isVisible() )
	search.setVisible(true);

    } else if( obj == mh_ver ) {
      // �С���������򳫤�
      ShowVersion();

    } else if( obj == mh_man ) {
      // �ޥ˥奢��򳫤�
      ShowManFrame();

    }
  }

  //  Xml Window�ε�ư
  public void ShowXmlWindow() {
    if ( xml != null )
      if ( xml.isShowing() ) {
        xml.show();
        return;
      }
    xml = new XmlWindow();
    xml.setTitle("Xml Window ver." + Version.version_link);
    xml.init();
    xml.start();
    xml.pack();
    xml.setResizable(false);
    xml.show();
  }

  //  Link Editor�ε�ư
  public void ShowLinkEdit() {
    if ( link != null )
      if ( link.isShowing() ) {
        link.show();
        return;
      }
    link = new LinkEdit();
    link.setTitle("Link Editor ver." + Version.version_link);
    link.init();
    link.pack();
    link.setResizable(false);
    link.show();
  }

  //  Xml Search�ε�ư
  public void ShowXmlSearch() {
    if ( search != null )
      if ( search.isShowing() ) {
        search.show();
        return;
      }
    search = new XmlSearch();
    search.setTitle("Xml Search ver." + Version.version_search);
    search.init();
    search.pack();
    search.setResizable(false);
    search.show();
  }

  //  Version Window��ɽ��
  public void ShowVersion() {
    if ( ver != null )
      if ( ver.isShowing() ) {
        ver.show();
        return;
      }
    ver = new Version();
    ver.setTitle("Version Information");
    ver.init(3);
    ver.setSize( 250, 200);
    ver.setResizable(false);
    ver.show();
  }

  //  Manual Window��ɽ��
  public void ShowManFrame() {
    if ( man != null )
      if ( man.isShowing() ) {
        man.show();
        return;
      }
    man = new ManFrame();
    man.init(3);
    man.setTitle("Manual Information");
    man.pack();
    man.show();
  }

  //  Window�ν�λ����
  class WindowControl extends WindowAdapter {
    public void windowClosing(WindowEvent e){

	System.exit(0);
	dispose();
      }
    }
  }


