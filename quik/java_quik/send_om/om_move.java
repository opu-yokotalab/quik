package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Move implements Serializable {

  public OM_Move()//�����
  {
    this.point = null;
    this.rotation = null;
  }

  //���󥹥ȥ饯�����ɲ� 2000/12/7 fujino
  public OM_Move( OM_Point p, String rotation ){
    this.point = p; 
    this.rotation = rotation;
 }

  public OM_Point  point;
  public String rotation;
} 
