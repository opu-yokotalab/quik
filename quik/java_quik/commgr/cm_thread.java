package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;
import java_quik.QuikStatus;

//  ���塼��������륯�饹
public class CM_Thread extends Thread {
  CM_communicationManeger commgr;
  int Interval = 250;                //  ����åɤ���ߤ����Ƥ�������[msec]

  //  ���󥹥ȥ饯��
  public CM_Thread( CM_communicationManeger init) {
    //  �ƤӽФ���Ƥ��륯�饹����Ͽ
    commgr = init;
  }

  //  ����å���߻��֤��ѹ�
  public void setInterval( int milisec) {
    Interval = milisec;
  }

  //  ����åɤ�����
  public void run() {
    while( true) {
      //  ���塼�ν�����Ԥʤ�
      commgr.execQueue();
      try {
        //  ����åɤ���ߤ�����
        sleep(Interval);
      } catch ( InterruptedException ie ) {
        //  �㳰����������Ф����˽�
      }
    }
  }
}
