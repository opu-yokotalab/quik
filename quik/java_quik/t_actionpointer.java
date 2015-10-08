/* 10/27/2000 ObjectManager */
package java_quik;
import java_quik.send_om.*;

public class T_ActionPointer {

  //  初期化
  public T_ActionPointer()
  {
    this.loop = 0;
    this.media_atom = null;
    this.action_atom = null;
    this.t_con = null;
  }

  // #mediaだけ
  public T_ActionPointer(OM_Atom media_atom)
  {
    this.loop = 0;
    this.media_atom = media_atom;
    this.action_atom = null;
    this.t_con = null;
  }

  // #media/[   ]の場合
  public T_ActionPointer(OM_Atom media_atom, T_Con t_con)
  {
    this.loop = 0;
    this.media_atom = media_atom;
    this.action_atom = null;
    this.t_con = t_con;
  }
  
  // @action/[  ]の場合
  public static T_ActionPointer make_Action(OM_Atom action_atom, T_Con t_con)
  {
    T_ActionPointer new_t_ap = new T_ActionPointer();
    new_t_ap.action_atom = action_atom;
    new_t_ap.t_con = t_con;
    return new_t_ap;
  }
  // #media(@action)の場合
  public T_ActionPointer(OM_Atom media_atom, OM_Atom action_atom)
  {
    this.loop = 0;
    this.media_atom = media_atom;
    this.action_atom = action_atom;
    this.t_con = null;
  }

  // #media(@action)/[  ] の場合
  public T_ActionPointer(OM_Atom media_atom, OM_Atom action_atom, T_Con t_con)
  {
    this.loop = 0;
    this.media_atom = media_atom;
    this.action_atom = action_atom;
    this.t_con = t_con;
  }
  
  public int loop;
  public OM_Atom media_atom;
  public OM_Atom action_atom;
  public T_Con t_con;
  public T_ActionPointer next;

  public static T_ActionPointer t_ap_pool=null;
  public static T_ActionPointer t_ap_pool_last=null;
}

