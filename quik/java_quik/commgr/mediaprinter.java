package java_quik.commgr;
import  java_quik.*;
import  java_quik.send_om.*;

//  オブジェクトの情報を表示
public class MediaPrinter {
  //  オブジェクトの情報表示
  public void printObject( OM_ActionPointer ap ) {
    printAPointer( ap );
    printMedia( ap.media );
    printAction( ap.action );
  }

  //  ActionPointer の情報を表示
  public void printAPointer( OM_ActionPointer ap ) {
    System.out.println("ActionPointer##########");
    System.out.println("(" + ap + ")");
    if ( ap == null ) {
      System.out.println("data is nothing.");
      return;
    }
    System.out.println("loop        :" + ap.loop);
    System.out.println("scenario_num:" + ap.scenario_num);
    System.out.println("play_time   :" + ap.play_time);
    System.out.println("synchro_time:" + ap.synchro_time);
    System.out.println("media       :" + ap.media);
    System.out.println("action      :" + ap.action);
    System.out.println("next        :" + ap.next);
    System.out.println("##########");
  }

  //  メディアの情報表示
  public void printMedia( OM_Media media ) {
    System.out.println("Media----------");
    System.out.println("(" + media +")");
    if ( media == null ) {
      System.out.println("Media is nothing.");
      return;
    }
    System.out.println("media_type:" + media.media_type);
    System.out.println("comment   :" + media.comment);
    System.out.println("media_atom:" + media.media_atom.name);

    printMedias( media );

    System.out.println("ap        :" + media.ap);
    System.out.println("next      :" + media.next);
    System.out.println("----------");
  }

  //  各種メディアの詳細情報の表示
  public void printMedias( OM_Media media ) {
    switch( media.media_type ) {
      case 1:  //  静止画
      case 2:  //  動画
        System.out.println("  OM_Pictire=====");
        System.out.println("  url  :" + media.picture.url);
        System.out.println("  scake:" + media.picture.scale);
        break;
      case 3:  //  3Dオブジェクト
        System.out.println("  OM_3D=====");
        System.out.println("  url:" + media.three_d.url);
        break;
      case 4:  //  テキスト
        System.out.println("  OM_Text=====");
        System.out.println("  url    :" + media.text.url);
        System.out.println("  font   :" + media.text.font);
        System.out.println("  scale  :" + media.text.scale);
        System.out.println("  (R,G,B)=( " + media.text.r + ", " +
                            media.text.g + ", " + media.text.r +")");
        break;
      case 5:  //  サウンド
        System.out.println("  OM_Sound=====");
        System.out.println("  url   :" + media.sound.url);
        System.out.println("  volume:" + media.sound.volume);
        break;
      case 6:  //  その他メディア
        System.out.println("  OM_OtherM=====");
        System.out.println("  url        :" + media.other_media.url);
        System.out.println("  export_type:" + media.other_media.export_type);
        System.out.println("  scale(x, y)=( " + media.other_media.scale_x +
                            ", " + media.other_media.scale_y + ")");
        break;
    }
    System.out.println("  =========");
  }

  //  アクションの情報の表示
  public void printAction( OM_Action action ) {
    System.out.println("OM_Action//////////");
    System.out.println("(" + action + ")");
    if ( action == null ) {
      System.out.println("Action is nothing.");
      return;
    }
    System.out.println("action_type:" + action.action_type);
    System.out.println("comment    :" + action.comment);
    System.out.println("atom       :" + action.action_atom.name);

    printActions( action );

    System.out.println("next       :" + action.next);
    System.out.println("//////////");
  }

  //  各種アクションの詳細情報表示
  public void printActions( OM_Action action ) {
    switch( action.action_type ) {
      case 1:  //  Out
        System.out.println("  Out+++++");
        System.out.print("  Point:");
        printPoints( action.out.point );
        System.out.println("  font   :" + action.out.font);
        System.out.println("  (R,G,B)=( " + action.out.r + ", " +
                            action.out.g + ", " + action.out.b + ")");
        System.out.println("  volume :" + action.out.volume);
        System.out.println("  scale  :" + action.out.scale);
        break;
      case 2:  //  Move
        System.out.println("  Move+++++");
        System.out.print("  Point:");
        printPoints( action.move.point );
        break;
      case 3:  //  Speak
        System.out.println("  Speak+++++");
        System.out.println("  font      :" + action.speak.font);
        System.out.println("  intonation:" + action.speak.intonation);
        System.out.println("  pitch     :" + action.speak.pitch);
        System.out.println("  volume    :" + action.speak.volume);
        System.out.println("  speed     :" + action.speak.speed);
        break;
      case 4:  //  Lighting
        System.out.println("  Lighting+++++");
        System.out.print("  Point:");
        printPoints( action.light.point );
        System.out.println("  brightness:" + action.light.brightness);
        System.out.println("  steradian :" + action.light.steradian);
        System.out.println("  (R,G,B)=( " + action.light.r + ", " +
                            action.light.g + ", " + action.light.b + ")");
        
        break;
      case 5:  //  Del
        System.out.println("  Del+++++");
        break;
    }
    System.out.println("  ++++++++++");
  }

  //  位置情報の表示(再帰的)
  public void printPoints( OM_Point point ) {
    if ( point == null ) {
      System.out.println();
      return;
    }

    System.out.print("( " + point.point_x);
    System.out.print(", " + point.point_y);
    System.out.print(", " + point.point_z + ")");
    if ( point.next != null )
      System.out.print("->");
    printPoints( point.next );
  }
}
