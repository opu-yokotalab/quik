package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Point implements Serializable {


  public OM_Point()
  {
    this.point_x = (float)0.0;
    this.point_y = (float)0.0;
    this.point_z = (float)0.0;
    this.next = null;
  }

 //コンストラクタを追加 2000/12/7 fujino
  public OM_Point( float x, float y, float z ){
    point_x = x;
    point_y = y;
    point_z = z;
    next = null;
  }
  
  public float            point_x;
  public float            point_y;
  public float            point_z;
  public OM_Point         next;
  public static OM_Point  point_pool;

}
