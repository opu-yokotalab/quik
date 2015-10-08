package java_quik;
import java_quik.send_om.*;

public class T_ConList {

  public T_ConList(T_Con con,T_ConList next)
  {
    this.next = next;
    this.con = con;
  }
  
  public T_ConList    next;
  public T_Con        con;

  //public String       con_name;
}
