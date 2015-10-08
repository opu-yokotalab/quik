package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;

import com.ibm.aglet.*;

//  プロセス処理用の待ち行列
class CM_Queue {
  String   Uid;          //  ユーザID
  AG_Aglet aglets;       //  処理するAglets
  int      CMDid;        //  コマンドID
  CM_Queue next;         //  次のキュー

  //  コンストラクタ
  public CM_Queue() {};

  //  コンストラクタ
  public CM_Queue( String uid, AG_Aglet aglet, int cmid, CM_Queue ptr) {
    Uid = uid;
    aglets = aglet;
    CMDid = cmid;
    next = ptr;
  }

  //  コンストラクタ
  public CM_Queue( String uid, AG_Aglet aglet, int cmid) {
    this( uid, aglet, cmid, null);
  }

  //  次に処理するキューの追加
  public void chainQ( CM_Queue ptr ) {
    next = ptr;
  }
}
