package java_quik;
import java_quik.send_om.*;

public class T_Picture extends T_Con {

  public T_Picture(String url, T_Point point, float scale)
  {
    this.url = url;
    this.point = point;
    this.scale = scale;
    
    if(point == null)
    {
      T_Point p = new T_Point((float)0.0, (float)0.0, (float)0.0);   //デフォルト
      this.point = p;
    }

  }

}
