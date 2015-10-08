package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Text implements Serializable {

  public OM_Text()
  {
    this.url = null;
    this.str = null;  
    this.font = "TimesRoman";
    this.scale = (float)0.5;
    this.r = (float)0.0;
    this.g = (float)0.0;
    this.b = (float)0.0;
  }

  public OM_Text(String url, String str, String font, float scale, float r, float g, float b)
  {
    // 10/11/2000 ObjectManager
    if(url == null){
      this.url = null;
      this.str = str;
    }
    if(str == null){
      this.url = url;
      this.str = null;
    }
    this.font = font;
    this.scale = scale;
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public String    url;
  public String    str;
  public String    font;
  public float     scale;
  public float     r;
  public float     g;
  public float     b;
} 
