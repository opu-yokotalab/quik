package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;

import com.ibm.aglet.*;

//  �ץ��������Ѥ��Ԥ�����
class CM_Queue {
  String   Uid;          //  �桼��ID
  AG_Aglet aglets;       //  ��������Aglets
  int      CMDid;        //  ���ޥ��ID
  CM_Queue next;         //  ���Υ��塼

  //  ���󥹥ȥ饯��
  public CM_Queue() {};

  //  ���󥹥ȥ饯��
  public CM_Queue( String uid, AG_Aglet aglet, int cmid, CM_Queue ptr) {
    Uid = uid;
    aglets = aglet;
    CMDid = cmid;
    next = ptr;
  }

  //  ���󥹥ȥ饯��
  public CM_Queue( String uid, AG_Aglet aglet, int cmid) {
    this( uid, aglet, cmid, null);
  }

  //  ���˽������륭�塼���ɲ�
  public void chainQ( CM_Queue ptr ) {
    next = ptr;
  }
}
