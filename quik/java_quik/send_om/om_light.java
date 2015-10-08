/* アクションの一つ ライティング とりあえず用意 10/12/2000 ObjectManager */
package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Light implements Serializable {

  // 初期化
  public OM_Light()
  {
    this.point = null;
    this.brightness = 0;
    this.steradian = 0;
    this.r = (float)255;
    this.g = (float)255;
    this.b = (float)255;
  }

  //コンストラクタを追加 2000/12/7 fujino
  public OM_Light( OM_Point bf_point, float bf_brightness, float bf_steradian, float bf_r, float bf_g, float bf_b ){
    point      = bf_point;
    brightness = bf_brightness;
    steradian  = bf_steradian;
    r          = bf_r;
    g          = bf_g;
    b          = bf_b;
  }

  public OM_Point point;
  public float brightness;
  public float steradian;
  public float r;
  public float g;
  public float b;
} 
