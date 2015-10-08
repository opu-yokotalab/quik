package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Sound implements Serializable {

  public OM_Sound(String url, float volume)
  {
    this.url = url;
    this.volume = volume;
  }

  //  2000/02/14 Earsh
  public OM_Sound() {
    this( null, (float)1.0);  // 10/11/2000 ObjectManager
  }

  public String url;
  public float  volume; 
} 
