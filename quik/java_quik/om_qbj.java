/* 11/17/2000 オブジェクト管理 */
/* QUIKの内部データを中間コードでファイルに保存。
保存したファイルからデータを戻す。
入れ子トランザクション管理
*/

package java_quik;

import java_quik.send_om.*;
import java.io.*;

public class OM_qbj {

  public static int tr_num=0;
  static int max_tr_num=0;
  static int del_qbj_num=0;

  //トランザクション命令の判別
  public static void nested_transaction(String state){

    // beginの処理
    if(state.equals("begin")){
      object_create(tr_num);
      tr_num++;
      object_create(tr_num);
    }
    // endの処理
    else if(state.equals("end")){
      object_create(tr_num);
      tr_num--;
      max_tr_num = tr_num;
    }
    // abortの処理
    else if(state.equals("abort")){
      del_qbj_num = tr_num;
      while(del_qbj_num <= max_tr_num)
	{
	  del_qbj(del_qbj_num);
	  del_qbj_num++;
	}
      tr_num--;
      OM_Node.node_pool = object_restore(tr_num);
    }
    // error
    else{
      System.out.println("No such a command");
      System.exit(0);
    }
  }

  /*
    // トランザクション命令の判別(状態を変数にした時用)
  public static boolean nested_transaction(int state){
    boolean tr_flag = true;
    
    if(state == 1){
      object_create(tr_num);
      tr_num++;
      object_create(tr_num);
      return tr_flag;
    }
    else if(state == 2){
      object_create(tr_num);
      tr_num--;
      max_tr_num = tr_num;
      return tr_flag;
    }
    else if(state == 3){
      del_qbj_num = tr_num;
      while(del_qbj_num <= max_tr_num)
	{
	  del_qbj(del_qbj_num);
	  del_qbj_num++;
	}
      tr_num--;
      OM_Node.node_pool = object_restore(tr_num);
      return tr_flag;
    }
    else{
      tr_flag = false;
      return tr_flag;
    }
  }
  */

  // QUIKオブジェクトファイル(qbj/temp-*.qbj)の生成 
  public static void object_create(int ts_id){
  
    //    OM_Media target = Extern_h.media_pool;
    //    OM_Media cp_media = media_copy(target);
    OM_Node target = Extern_h.node_pool;
    OM_Node cp_node = node_copy(target);    // Nodeから繋がるオブジェクト

    try {
      FileOutputStream fount = new FileOutputStream("qbj/temp-"+ts_id+".qbj");
      ObjectOutputStream out = new ObjectOutputStream(fount);
      //out.writeObject(cp_media);
      out.writeObject(cp_node);
    } catch(IOException e){
    }
  }

  /* オブジェクトファイルからの復元(OM_Mediaの時用)
    public static OM_Media object_restore(int ct_id){

    OM_Media media_p = null;
    
    try {
    FileInputStream fis = new FileInputStream("qbj/temp-"+ct_id+".qbj");
    ObjectInputStream ois = new ObjectInputStream(fis);
    media_p=(OM_Media)ois.readObject();
    } catch(IOException e){
    } catch(ClassNotFoundException cnfe){
    }
    
    return  media_p;
    }
    */

  // オブジェクトファイルからの復元(OM_Nodeから繋がる全てのオブジェクト)
  public static OM_Node object_restore(int ct_id){

    OM_Node node_p = null;
    
    try {
      FileInputStream fis = new FileInputStream("qbj/temp-"+ct_id+".qbj");
      ObjectInputStream ois = new ObjectInputStream(fis);
      node_p=(OM_Node)ois.readObject();
    } catch(IOException e){
    } catch(ClassNotFoundException cnfe){
    }
    
    return  node_p;
  }

  /*
    public static OM_Media media_copy(OM_Media target){
    OM_Media source=target;
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    try{
    ObjectOutputStream out = new ObjectOutputStream(bout);
    out.writeObject(source);
    ByteArrayInputStream bin =  new ByteArrayInputStream(bout.toByteArray());
    ObjectInputStream in = new ObjectInputStream(bin);
    return (OM_Media)in.readObject();
    } catch (IOException ioe) {
    } catch (ClassNotFoundException cnfe){
    }
    return source;
    }
    */


  // OM_Nodeから繋がる全てのオブジェクトをコピーする
  public static OM_Node node_copy(OM_Node target){
    OM_Node source=target;
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    try{
      ObjectOutputStream out = new ObjectOutputStream(bout);
      out.writeObject(source);
      ByteArrayInputStream bin =  new ByteArrayInputStream(bout.toByteArray());
      ObjectInputStream in = new ObjectInputStream(bin);
      return (OM_Node)in.readObject();
    } catch (IOException ioe) {
    } catch (ClassNotFoundException cnfe){
    }
    return source;
  }

  // QUIKオブジェクトファイル(qbj/temp-*.qbj)の削除
  public static void del_qbj(int at_id){

    try{
      File file = new File("qbj/temp-"+at_id+".qbj");
      file.delete();
    }
    catch(SecurityException se){
    }
  }

} 
