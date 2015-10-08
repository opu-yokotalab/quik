package java_quik.commgr;
import java_quik.*;
import java_quik.gui.*;
import java_quik.QuikStatus;

//  キューを処理するクラス
public class CM_Thread extends Thread {
  CM_communicationManeger commgr;
  int Interval = 250;                //  スレッドを停止させておく時間[msec]

  //  コンストラクタ
  public CM_Thread( CM_communicationManeger init) {
    //  呼び出されているクラスの登録
    commgr = init;
  }

  //  スレッド停止時間の変更
  public void setInterval( int milisec) {
    Interval = milisec;
  }

  //  スレッドの本体
  public void run() {
    while( true) {
      //  キューの処理を行なう
      commgr.execQueue();
      try {
        //  スレッドを停止させる
        sleep(Interval);
      } catch ( InterruptedException ie ) {
        //  例外処理があればここに書く
      }
    }
  }
}
