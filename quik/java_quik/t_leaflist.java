package java_quik;

public class T_LeafList {

  public T_LeafList(T_Leaf leaf,T_LeafList next)
  {
    this.next = next;
    this.leaf = leaf;
  }

  public T_LeafList   next;
  public T_Leaf       leaf;
  public String       str_stream=null;
}
