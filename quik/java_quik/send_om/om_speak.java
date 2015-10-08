/* OM_Utterance -> OM_Speak 10/12/2000 ObjectManager */
package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Speak implements Serializable {

  public OM_Speak()
  {
    this.font = null;
    this.intonation = (float)0.5;
    this.pitch = (float)0.5;
    this.volume = (float)0.5;
    this.speed = (float)1.0;
  }

  //コンストラクタの追加 2000/12/7 fujino
  public OM_Speak( String bf_font, float bf_intonation, float bf_pitch, float bf_volume, float bf_speed ){
    font      = bf_font;
    intonation = bf_intonation;
    pitch     = bf_pitch;
    volume    = bf_volume;
    speed     = bf_speed;
  }

  public String  font;
  public float   intonation;
  public float   pitch;
  public float   volume;
  public float   speed;
} 
