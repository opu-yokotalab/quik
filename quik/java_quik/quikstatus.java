package java_quik;
import java_quik.commgr.*;
import java_quik.gui.*;

public class QuikStatus {
  //  $B3F4IM}7O$N>uBV(B
  public static final int SUGIHARA = 1;
  public static final int SUGIMOTO = 2;
  public static final int NERIO    = 4;
  public static final int YOKOGAWA = 8;
  public static final int OGATA    = 16;

  //  $B%3%^%s%I7O$N>uBV(B
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

  //  Aglets $B$NM%@hEY$N>uBV(B
  public static final int NORMAL   = 0;
  public static final int HIGHEST  = 1;

  //  $B<+J,$,%5!<%P$+%/%i%$%"%s%H$+$NH=JLMQ(B
  public static final int SERVER   = 0;
  public static final int CLIENT   = 1;

  //  $B3F=hM}$,=*$C$?;~$KJV$9>uBV$N%Y!<%9(B
  public static final int SUCCESS  = 0x10000;
  public static final int ERROR    = 0x20000;
  public static final int WARNING  = 0x40000;
  public static final int RESUME   = 0x80000;
  public static final int MASK     = 0xf0000;
  public static final int ERRMASK  = 0x0ffff;

  //  $B%(%i!<HV9f(B($B2>(B)
  public static final int LATTICE_ERROR  = 1; //  ($B2>(B)
  public static final int SHOW_LATTICE   = 2; //  ($B2>(B)

  public static final int MEDINAME_NOT_FOUND = 3;

  //commgr.returnStatus(QuikStatus.WARNING | QuikStatus.LATTICE_ERROR, (Object)str);
}
