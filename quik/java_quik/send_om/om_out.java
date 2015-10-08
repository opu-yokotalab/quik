/* アクションの一つ メディアの出現 10/12/2000 ObjectManager */
package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Out implements Serializable {

  public OM_Out()//初期化
  {
    this.point = null;
    this.font = null;
    this.r = (float)0.0;
    this.g = (float)0.0;
    this.b = (float)0.0;
    this.volume = (float)0.0; 
    this.scale =  (float)0.0;
  }

  //コンストラクタを追加 2000/12/7 fujino
  public OM_Out( OM_Point p, String bf_font, 
		 float bf_r, float bf_g, float bf_b, float bf_volume, float bf_scale){
    point  = p;
    font   = bf_font;
    r      = bf_r;
    g      = bf_g;
    b      = bf_b;
    volume = bf_volume;
    scale  = bf_scale;
  }

  public OM_Point point;
  public String font;
  public float r;
  public float g;
  public float b;
  public float volume;
  public float scale;
} 
