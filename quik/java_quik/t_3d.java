package java_quik;
import java_quik.send_om.*;

public class T_3D extends T_Con {

  public T_3D(String url, T_Point point)
  {
    this.url = url;

    if(point == null)
    {
      T_Point p = new T_Point((float)0.0, (float)0.0, (float)0.0);   //デフォルト
      this.point = p;
    }

  }

}
