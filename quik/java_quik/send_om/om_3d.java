/* OM_Object -> OM_3D    10/11/2000 ObjectManager*/
/* 11/30/2000 ObjectManager*/
package java_quik.send_om; 

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_3D implements Serializable {

  public OM_3D()
  {
    this.url = null;
    this.rotation = null;
    //    this.point = null;
  }

  public OM_3D(String url, String rotation)
  {
    this.url = url;  
    //    this.point = null;
    this.rotation = rotation;
  }

  public String    url;
  public String    rotation;
  //  public OM_Point  point;

} 
