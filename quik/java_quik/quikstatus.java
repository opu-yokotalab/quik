package java_quik;
import java_quik.commgr.*;
import java_quik.gui.*;

public class QuikStatus {
  //  各管理系の状態
  public static final int SUGIHARA = 1;
  public static final int SUGIMOTO = 2;
  public static final int NERIO    = 4;
  public static final int YOKOGAWA = 8;
  public static final int OGATA    = 16;

  //  コマンド系の状態
  public static final int STARTUP  = 1;
  public static final int ACTIVATE = 2;
  public static final int OPEN     = 3;
  public static final int CLOSE    = 4;
  public static final int SHUTDOWN = 5;
  public static final int ABORT    = 6;
  public static final int START    = 7;
  public static final int CONTINUE = 8;
  public static final int SAVE     = 9;
  public static final int getTREE  = 10;
  public static final int QUERY    = 11;
  public static final int ANSWER   = 12;
  public static final int rSTATUS  = 13;

  public static final int OM_ActionPointer = 33;
  public static final int retTREE  = 35;
//  public static final int OM_Media = 33;
//  public static final int delMedia = 34;

  //  Aglets の優先度の状態
  public static final int NORMAL   = 0;
  public static final int HIGHEST  = 1;

  //  自分がサーバかクライアントかの判別用
  public static final int SERVER   = 0;
  public static final int CLIENT   = 1;

  //  各処理が終った時に返す状態のベース
  public static final int SUCCESS  = 0x10000;
  public static final int ERROR    = 0x20000;
  public static final int WARNING  = 0x40000;
  public static final int RESUME   = 0x80000;
  public static final int MASK     = 0xf0000;
  public static final int ERRMASK  = 0x0ffff;

  //  エラー番号(仮)
  public static final int LATTICE_ERROR  = 1; //  (仮)
  public static final int SHOW_LATTICE   = 2; //  (仮)

  public static final int MEDINAME_NOT_FOUND = 3;

  //commgr.returnStatus(QuikStatus.WARNING | QuikStatus.LATTICE_ERROR, (Object)str);
}
