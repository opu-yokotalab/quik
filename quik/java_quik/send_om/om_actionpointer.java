/* メディアとアクションの介在をするもの 10/10/2000 ObjectManager */
package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_ActionPointer implements Serializable{

  //値を変更 2000/12/7 fujino
  public OM_ActionPointer(){
    this.loop = 0;
    this.scenario_num = (float)0.0;
    this.play_time = -1;
    this.synchro_time = -1;
    this.comment = null;
    this.media = null;
    this.action = null;
    this.next = null;
  }

  public int       loop;
  public float     scenario_num;
  public int       play_time;
  public int       synchro_time;
  public String    comment;
  public OM_Media  media;
  public OM_Action action;
  public OM_ActionPointer next;
  public OM_ActionPointer next_bucket;

  public static OM_ActionPointer ap_pool = null;
} 
