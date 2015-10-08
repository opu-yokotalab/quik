package java_quik.send_om;

import java_quik.*;

import java.io.*;
public class OM_Node implements Serializable{

  public OM_Node(){
    this.head = null;
    this.body = null;
    this.parent = null;
    this.next = null;

    //this.head = head;
    //this.body = body;
    //this.next = next;
    //this.parent = parent;
  }

  //コンストラクタを追加 2000/12/7 fujino
  public OM_Node( OM_Atom atom ){
    this.head   = atom;
    this.body   = null;
    this.parent = null;
    this.next   = null;

    atom.node = this;
  }
  
  public OM_Atom   head;
  public OM_Leaf   body;
  public OM_Leaf   parent;
  public OM_Node   next;

  //public static OM_Leaf   leaf_list;  //OM_Leaf.node ---> OM_Nodeのとき必要 

  //public static int  stream_str_int;  //#stream()の中の文字列をintで保持
  public static String  stream_str;
  public static OM_Node node_pool = null;
  public static OM_Node node_pool_last = null;
} 


