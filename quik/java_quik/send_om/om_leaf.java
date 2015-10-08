package java_quik.send_om;

import java_quik.*;

import java.io.*;

public class OM_Leaf implements Serializable {

  // 初期化
  public OM_Leaf() 
  {
    this.leaf_flag = false;   // 10/10/2000 ObjectManager
    this.comment = null;
    this.weight = 0.0;         // 10/10/2000 ObjectManager
    this.media_atom = null;  // 10/10/2000 ObjectManager
    this.action_atom = null; // 10/10/2000 ObjectManager
    this.ap = null;          // 10/10/2000 ObjectManager
    this.seq_next = null;
    this.nonorder = null;
    this.node = null;
  }
  
  //コンストラクタを追加 2000/12/7 fujino
  public OM_Leaf(OM_Atom media_atom, OM_Atom action_atom){
    this.leaf_flag   = false;
    this.comment     = null;
    this.weight      = 0.0;
    this.media_atom  = media_atom;
    this.action_atom = action_atom;
    this.ap          = null;
    this.seq_next    = null;
    this.nonorder    = null;
    this.node        = null;
  }

  public boolean   leaf_flag;   /* 10/10/2000 ObjectManager 送信済:true 送信前:false */
  public String    comment;
  public double    weight;      // 10/10/2000 ObjectManager
  public OM_Atom   media_atom;  // data -> media_atom 10/10/2000 ObjectManager
  public OM_Atom   action_atom; // 10/10/2000 ObjectManager
  public OM_ActionPointer   ap;  //  10/10/2000 ObjectManager
  public OM_Leaf   seq_next;
  public OM_Leaf   nonorder;
  public OM_Node   node;

  public static OM_Leaf stream_current_leaf; //#stream()の現在つながれているところまでのLeaf
  public static int   stack_command;
  public static OM_Leaf ex_leaf=null;
  
} 
