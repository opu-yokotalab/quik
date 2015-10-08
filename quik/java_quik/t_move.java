package java_quik;
import java_quik.send_om.*;

public class T_Move extends T_Con {

  public T_Move(int action_type, T_Point point)
  {
    this.action_type = action_type;
    //    this.obj = atom;
    this.point = point;

    if(point == null)
    {
      T_Point p = new T_Point((float)0.0, (float)0.0, (float)0.0);   //デフォルト
      this.point = p;
    }
  }

  /* rotationの場合 10/27/2000 ObjectManager*/
  public T_Move(int action_type, String rotation)
  {
    this.action_type = action_type;
    this.rotation = rotation;
  }
  
}
