package java_quik;
import java_quik.send_om.*;

public class T_Con {

  public T_Con()
  {
    //デフォルト
    this.comment = null;
    this.scenario_num = (float)0.0;
    this.play_time = -1;
    this.synchro_time = -1;
    this.media_time = -1;
    this.scale = (float)0.5;
    this.intonation = (float)0.5;
    this.pitch = (float)0.5;
    this.volume = (float)0.5;
    this.scale_x = 1;
    this.scale_y = 1;
    this.r = (float)1.0;
    this.g = (float)1.0;
    this.b = (float)1.0;
  }

  public String         name;
  public int            media_type;
  public int            action_type;
  public String         comment;  //  10/27/2000 ObjectManager
  public OM_Atom        media_atom;
  public OM_Atom        action_atom;
  public float          scenario_num;
  public int            play_time;
  public int            synchro_time;
  public float          media_time;
  public T_Picture      picture;
  public T_Sound        sound;
  public T_Speak        speak;
  public T_Text         text;
  public T_3D           three_d;
  public T_Move         move;

  public T_Point        point = null;
  //public T_PointList    next_point;       //pointをつなぐため
  public float          scale;

  public String         url;
  public String         rotation;

  public String         str;       //発話
  public String         font;
  public float          intonation;
  public float          pitch;
  public float          volume;
  public float          speed;

  public float          r;
  public float          g;
  public float          b;

  public float          brightness;  //ライティング
  public float          steradian;

  public int            export_type;
  public int            scale_x;
  public int            scale_y;

  public String         con_name;
  public T_ConList      flag;
}
