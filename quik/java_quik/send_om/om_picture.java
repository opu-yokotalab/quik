package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Picture implements Serializable {

  public OM_Picture(String url, float scale)
  {
    this.url = url;  
    //    this.point = null;   // 10/11/2000 ObjectManager
    this.scale = scale;
  }

  public OM_Picture()
  {
    this.url = null;  
    //    this.point = null;   // 10/11/2000 ObjectManager
    this.scale = (float)0.5;
  }

  public String    url;
  //  public OM_Point  point;  // 10/11/2000 ObjectManager
  public float     scale;

} 
