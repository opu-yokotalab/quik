package java_quik;
import java_quik.send_om.*;
import java.io.*;

public class OM_LeafList implements Serializable{

  public OM_LeafList(){
    this.leaf = null;
    this.next = null;
  }

  /* public OM_LeafList(OM_LeafList next){
    this.leaf = null;
    this.next = next;
  }
  */
  public OM_Leaf       leaf;
  public OM_LeafList   next;

  public static OM_LeafList leaf_list_p;  //OM_Leafの先頭を格納する変数
  public static OM_LeafList leaf_list_last;
} 
