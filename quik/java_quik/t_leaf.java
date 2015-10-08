package java_quik;
import java_quik.send_om.*;

public class T_Leaf {

   public T_Leaf(T_ActionPointer t_ap)
  {
    this.leaf_flag = false;
    this.seq_next = 0;
    this.nonorder = 0;
    this.media_atom = t_ap.media_atom;
    this.action_atom = t_ap.action_atom;
    this.t_ap = t_ap;
  }

  public boolean    leaf_flag;
  public int        seq_next;
  public int        nonorder;
  public OM_Atom    media_atom;
  public OM_Atom    action_atom;
  public T_ActionPointer t_ap;
}
