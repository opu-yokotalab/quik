/* 10/10/2000 ObjectManager */
package java_quik.send_om;

import java_quik.*;
//import java_quik.viewer.*;
import java.io.*;

public class OM_Action implements Serializable {

  public OM_Action(){
    this.action_type = 0;
    this.comment = null;
    this.action_atom =  null;
    this.out = null;
    this.del = null;
    this.move = null;
    this.speak = null;
    this.light = null;
    this.next = null;
  }

  //コンストラクタを追加 2000/12/7 fujino
  public OM_Action( int type, OM_Atom atom ){
    this.action_type = type;    
    this.comment = null;
    this.action_atom = atom;       
    this.out = null;        
    this.del = null;        
    this.move = null;       
    this.speak = null;      
    this.light = null;
    this.next = null;

    atom.action = this;
  }
  public OM_Action( OM_Atom atom ){
    this( 0, atom );
  }       

  public int           action_type;
  public String        comment;
  public OM_Atom       action_atom;
  public OM_Out        out;
  public OM_Del        del;
  public OM_Move       move;
  public OM_Speak      speak;
  public OM_Light      light;
  public OM_Action     next;

  public static OM_Action action_pool_last = null;
} 
