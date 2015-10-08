package java_quik;
import java_quik.send_om.*;

import java.io.*;

public class Exec_Tree implements Serializable {
  
  public String    m_name    = "";   
  public String    a_name    = "";   
  public String    m_comment = "";
  public String    a_comment = "";
  public String    s_comment = "";
  public int       m_type    = 0;   
  public int       a_type    = 0;   
  public float     s_num     = -1;    
  public Exec_Tree child     = null;   
  public Exec_Tree seq       = null;     
  public Exec_Tree nonorder   = null;  

  public Exec_Tree() {}

  public Exec_Tree( OM_Leaf leaf ){
    if( leaf.ap != null ){
      this.m_name    = leaf.media_atom.name;  
      this.a_name    = leaf.action_atom.name;  
      this.m_comment = leaf.ap.media.comment;
      this.a_comment = leaf.ap.action.comment;
      this.s_comment = leaf.ap.comment;
      this.m_type    = leaf.ap.media.media_type;
      this.a_type    = leaf.ap.action.action_type;  
      this.s_num     = leaf.ap.scenario_num;    
    }else{
      this.m_name    = leaf.media_atom.name;  
      this.s_comment = leaf.comment;
    }
  }

}
