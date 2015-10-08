package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;

import java.io.*;

public class OM_Atom implements Serializable {

  /* 10/17/2000 ObjectManager */
  public OM_Atom()
  {
    this.name = null;
    this.next_bucket = null;
    this.node = null;
    this.media = null;
    this.action = null;
    //    this.rule_list;
    //    this.lattice_index;
    //    this.merge_index; 
  }

  //コンストラクタを追加 2000/12/7 fujino
  public OM_Atom(String name){
    this.name = name;
    this.node = null;
    this.media = null;
    this.action = null;
    this.next_bucket = null;
  }
  
  public static OM_Atom make_ATOM(String string)
    {
      OM_Atom new_obj,p;

      new_obj = new OM_Atom();
      new_obj.name = string;
      new_obj.next_bucket = null;
      new_obj.node = null;  //マルチメディア
      new_obj.media = null;  //マルチメディア
      new_obj.action = null;  // 10/10/2000 ObjectManager

      new_obj.next_bucket = null;
      //      new_obj.rule_list   = null;
      //      new_obj.lattice_index = -1;   

      for (p = atom_pool; p!=null ; p = p.next_bucket)
	if (string.equals(p.name))     //等しい
	  return p;                    //プールにつながない
      
      new_obj.next_bucket = atom_pool; //プールにつなぐ
      atom_pool = new_obj;
      return new_obj;
    }

  public String       name;  
  public OM_Atom      next_bucket;
  public OM_Node      node;
  public OM_Media     media;  // data -> media 10/10/2000 ObjectManager
  public OM_Action    action; // 10/10/2000 ObjectManager

  public static OM_Atom atom_pool;

}


