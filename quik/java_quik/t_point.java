package java_quik;
import java_quik.send_om.*;

public class T_Point {

  public T_Point()
  {
    this.point_x = (float)0.0;
    this.point_y = (float)0.0;
    this.point_z = (float)0.0;
    this.next = null;
  }
  
  public T_Point(float x, float y, float z)
  {
    this.point_x = x;
    this.point_y = y;
    this.point_z = z;
    this.next = null;
  }

  public float     point_x;
  public float     point_y;
  public float     point_z;
  public T_Point   next;

}
