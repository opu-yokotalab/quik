/* 10/11/2000 ObjectManager*/
package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_OtherM implements Serializable {

  public OM_OtherM()
  {
    this.url = null;
    this.export_type = 0;
    this.scale_x = 1;
    this.scale_y = 1;
  }

  public OM_OtherM(String url, int type, int x, int y)
  {
    this.url = url;
    this.export_type = type;
    this.scale_x = x;
    this.scale_y = y;
  }

  //コンストラクタの追加 2000/12/7 fujino
  public OM_OtherM( int bf_type ){
    this( null,bf_type, 5, 5 );
  }
  
  public String    url;
  public int       export_type;  /* SMIL:1 TVML:2 その他:3 */
  public int       scale_x;
  public int       scale_y;
} 
