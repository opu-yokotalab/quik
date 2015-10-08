package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Media implements Serializable {

  public OM_Media(){
    this.media_type = 0;
    this.comment = null;
    this.media_time = (float)0.0;
    this.media_atom = null;
    this.picture = null;
    this.sound = null;
    this.text = null;
    this.three_d = null;
    this.other_media = null;
    this.ap = null;
    this.next = null;
  }

  //コンストラクタを追加 2000/12/7 fujino
  public OM_Media( int type, OM_Atom atom ){
    this.media_type  = type;
    this.media_time  = -1;
    this.comment     = null;
    this.media_atom  = atom;
    this.picture     = null;
    this.sound       = null;
    this.text        = null;
    this.three_d     = null;
    this.other_media = null;
    this.ap          = null;
    this.next        = null;

    atom.media = this;
  }
  public OM_Media( OM_Atom atom ){
    this( 0, atom );
  }

  public int           media_type;            // 10/10/2000 ObjectManager
  public String        comment;               // 10/10/2000 ObjectManager
  public float         media_time;            // 10/10/2000 ObjectManager
  public OM_Atom       media_atom;
  public OM_Picture    picture;
  public OM_Sound      sound;
  public OM_Text       text;
  public OM_3D        three_d;               // 10/10/2000 ObjectManager
  public OM_OtherM        other_media;          // 10/10/2000 ObjectManager
  public OM_ActionPointer ap;                // 10/10/2000 ObjectManager
  public OM_Media      next;

  public static OM_Media media_pool_last = null;
} 
