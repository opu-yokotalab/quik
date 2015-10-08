/* 
 *  2000.10.24 matono   
 *  クライアントからの問い合わせに対する処理
 *  シーングラフ配信, 開始(start), 一時停止(halt), 再開(continue), 終了(stop)
 */

package java_quik;
import java_quik.send_om.*;

/* 
 * シーングラフ配信  exec_scene_graph
 * デバグ用          exec_scene_graph
 *
 * 処理の割り振り    exec_process_allocation
 * 重み付け          exec_weight_node      
 * フラグの初期化    exec_init_flag 
 * フラグの最適化    exec_optimize_flag 
 * 順序決定(正常用)  exec_order_decision_normal
 * 順序決定(異常用)  exec_order_decision_error
 * AP配信時に生成    exec_creat_ap
 * デバグ用          exec_debag_ap
 * デバグ用          exec_debag_weight
 *
 */

public class Exec_Start{
  int count = 0;

  public void exec_debug_scene( Exec_Tree tree ){

    while( tree != null ){
      if( tree.child != null){
	for(int i = 0 ; i < count ; i++ )System.out.print("\t");
	System.out.println( "#" + tree.m_name + "/[" + tree.s_comment + "] <= " );
	count++;
	exec_debug_scene( tree.child );	//再起呼び出し
	count--;
      }else{
	for(int i = 0 ; i < count ; i++ )System.out.print("\t");
	System.out.println( "#" + tree.m_name + 
			    "/[" + tree.m_comment + ", " + tree.m_type + "]" +
			    "(@" + tree.a_name + 
			    "/[" + tree.a_comment + ", " + tree.a_type + "])" +
			    "/[" + tree.s_comment + ", " + tree.s_num + "]" );
      }
      
      if( tree.seq != null ){
	tree = tree.seq;
      }else if( tree.nonorder != null ){
	tree = tree.nonorder;
      }else{
	tree = null;
      }      
    }
  }

  public Exec_Tree exec_scene_graph( OM_Node node ){
    OM_Leaf   leaf   = node.body;
    Exec_Tree tree   = null;
    Exec_Tree top    = null;
    Exec_Tree pre    = null;
    boolean top_flag = true; 
    boolean seq_flag = true; 
    
    while( leaf != null ){

      if( leaf.ap != null ){
	tree = new Exec_Tree( leaf );
      }else{
	tree = new Exec_Tree( leaf );	
	node = leaf.media_atom.node;
	tree.child = exec_scene_graph( node );
	node = leaf.node;
      }
      
      if( top_flag ){
	top = tree;
	top_flag = false;
      }	

      if( pre != null )
	if( seq_flag ){
	  pre.seq = tree;
	}else if( !seq_flag ){
	  pre.nonorder = tree;
	}

      pre = tree;
      
      if( leaf.nonorder != null ){
	leaf = leaf.nonorder;
	seq_flag = false;
      }else if( leaf.seq_next != null ){
	leaf = leaf.seq_next;
	seq_flag = true;
      }else{
	leaf = null;
      }
    } 
    
    return( top );
  }

  public void exec_debug_ap( OM_ActionPointer ap ){
    System.out.println("#" + ap.media.media_atom.name + "@" + ap.action.action_atom.name);
    //System.out.println("ap:" + ap);
    //System.out.println("media:" + ap.media);
    //System.out.println("m_atom:" + ap.media.media_atom);
    //System.out.println("action:" + ap.action);
    //System.out.println("a_atom:" + ap.action.action_atom);
    //System.out.println();
    
  }
  /* アクションポインタを生成とコピー */
  public OM_ActionPointer exec_creat_ap( OM_ActionPointer ap ){
    
    OM_ActionPointer new_ap = new OM_ActionPointer();
    OM_Media      new_media = new OM_Media();    
    OM_Action    new_action = new OM_Action();
    OM_Atom      new_m_atom = new OM_Atom();
    OM_Atom      new_a_atom = new OM_Atom();

    new_ap.loop         = ap.loop;
    new_ap.comment      = ap.comment;
    new_ap.scenario_num = ap.scenario_num;
    new_ap.play_time    = ap.play_time;
    new_ap.synchro_time = ap.synchro_time;
    new_ap.media        = new_media;
    new_ap.action       = new_action;
    new_ap.next         = null;
    new_ap.next_bucket  = null;
    
    new_media.media_type  = ap.media.media_type;
    new_media.media_time  = ap.media.media_time;
    new_media.comment     = ap.media.comment;
    new_media.media_atom  = new_m_atom;
    new_media.picture     = ap.media.picture;
    new_media.sound       = ap.media.sound;
    new_media.text        = ap.media.text;
    new_media.three_d     = ap.media.three_d;
    new_media.other_media = ap.media.other_media;
    new_media.ap          = new_ap;
    new_media.next        = null;

    new_action.action_type = ap.action.action_type;
    new_action.comment     = ap.action.comment;
    new_action.action_atom = new_a_atom;
    new_action.out         = ap.action.out;
    new_action.move        = ap.action.move;
    new_action.speak       = ap.action.speak;
    new_action.light       = ap.action.light;
    new_action.del         = ap.action.del;
    new_action.next        = null;

    new_m_atom.name        = ap.media.media_atom.name;
    new_m_atom.next_bucket = null;
    new_m_atom.node        = null;
    new_m_atom.media       = new_media;
    new_m_atom.action      = null;

    new_a_atom.name        = ap.action.action_atom.name;
    new_a_atom.next_bucket = null;
    new_a_atom.node        = null;
    new_a_atom.media       = null;
    new_a_atom.action      = new_action;

    return( new_ap );
  }
  
  /* 処理の割り振り */
  public int exec_process_allocation( OM_Atom atom, int flag ){
    int exec_Flag = 0;
    OM_Leaf exec_leaf = null;

    System.out.println("Flag:" + flag);

    switch( flag ){
    case 1:			// 終了( stop )
      exec_init_flag( atom.node );
      break;      
    case 2:			// 一時停止 ( halt )
      break;
    case -1:			// 異常( error )
    case 3:			// 再開( continue )
      System.out.println( "Continue" );
      exec_optimize_flag( atom.node, false );
      exec_debag_weight( atom.node );
      exec_leaf = exec_order_decision_error( atom.node );
      if(exec_leaf == null){
	System.out.println( "Continue Error!" );
	break;
      }else{
	exec_debug_ap( exec_creat_ap( exec_leaf.ap ) );
	//	exec_creat_ap( exec_leaf.ap );
        Main.mine.commgr.sendObject( exec_creat_ap( exec_leaf.ap ),
                                     QuikStatus.OM_ActionPointer );
	break;
      }
    case 0:			// 正常( normal )
      exec_optimize_flag( atom.node, true );
      exec_leaf = exec_order_decision_normal( atom.node );
      if(exec_leaf == null){
	System.out.println( "Normal Error!" );
        Main.mine.commgr.sendObject( null, -1 );
	break;
      }else{
	exec_leaf.leaf_flag = true;
	exec_debug_ap( exec_creat_ap( exec_leaf.ap ) );
	//	exec_debug_ap( exec_leaf.ap );
	//	exec_creat_ap( exec_leaf.ap );
        Main.mine.commgr.sendObject( exec_creat_ap( exec_leaf.ap ),
                                     QuikStatus.OM_ActionPointer );
	break;
      }
    case 4:			// 開始( start )
      System.out.println( "Start" );
      exec_weight_node( atom.node );  
      exec_leaf = exec_order_decision_normal( atom.node );
      if(exec_leaf == null){
	System.out.println( "Start Error!" );
	break;
      }else{
	exec_leaf.leaf_flag = true;
	exec_debug_ap( exec_creat_ap( exec_leaf.ap ) );
	//	exec_creat_ap( exec_leaf.ap );
        Main.mine.commgr.sendObject( exec_creat_ap( exec_leaf.ap ),
                                     QuikStatus.OM_ActionPointer );
	break;
      }
    default:
      exec_Flag = -1;
      System.out.println("Error" );
      break;
    }
    
    return( exec_Flag );

  }

  // 2000.10.24 matono
  // フラグが立っていなくて,且つ重みの小さな順にクライアントに渡す  
  public OM_Leaf exec_order_decision_normal( OM_Node node ){
    
    OM_Leaf leaf = node.body;
    OM_Leaf mini_leaf = null;
    OM_Leaf return_leaf = null;
        
    while( leaf != null ){
      while( true ){	
	if( !leaf.leaf_flag )
	  if( mini_leaf == null || mini_leaf.weight > leaf.weight ){
	    mini_leaf = leaf;
	  }
	if( leaf.nonorder != null )
	  leaf = leaf.nonorder;
	else
	  break;
      }

      if( mini_leaf != null ){
	if(mini_leaf.ap != null){
	  return( mini_leaf );
	}else{
	  node = mini_leaf.media_atom.node;
	  return_leaf = exec_order_decision_normal( node );	  
	  if( return_leaf != null )
	    return( return_leaf );
	  //	  mini_leaf.leaf_flag = true;
	  node = mini_leaf.node;
	}
      }

      if( leaf.seq_next != null ){
	leaf = leaf.seq_next;
      }else{
	leaf = null;
      }
    }
    return( leaf );
  }


  // 2000.10.25 matono
  // エラー発生時に使うメソッドで, 配信したオブジェクトを再び配信する
  public OM_Leaf exec_order_decision_error( OM_Node node ){
    
    OM_Leaf leaf = node.body;
    OM_Leaf mini_leaf = null;
    OM_Leaf return_leaf = null;
    boolean seq_flag = true;


    while( leaf != null ){
      while( true ){	
 	if( leaf.leaf_flag )
	  if( seq_flag || mini_leaf.weight <= leaf.weight ){
	    mini_leaf = leaf;
	    seq_flag = false;
	  }
	
	if( leaf.nonorder != null )
	  leaf = leaf.nonorder;
	else
	  break;
      }

      if( leaf.seq_next != null ){
	leaf = leaf.seq_next;
	seq_flag = true;
      }else{
	leaf = null;
      }
    }

    if( mini_leaf != null ){
      if(mini_leaf.ap != null){
	return( mini_leaf );
      }else{
	node = mini_leaf.media_atom.node;
	return_leaf = exec_order_decision_error( node );	  
	if( return_leaf != null )
	  return( return_leaf );
	node = mini_leaf.node;
      }
    }
    
    return( leaf );
  }


  // 重み付けを行い, フラグの初期化を行う 2000.10.24 matono
  public double exec_weight_node( OM_Node node ){
    int    objects = 0;		// 同階層内のオブジェクト数
    double total   = 0;		// 同階層内の重みの合計
    double average = 0;		// 同階層内の重みの平均
    
    OM_Leaf leaf = node.body;

    //    System.out.println( "exec_weight_node!!");        
    //    System.out.println( );
    
    while( leaf != null ){
      
      if( leaf.ap != null ){
	leaf.leaf_flag = false;
	// メディアの場合 type を重みとし利用。
	leaf.weight = (double)(leaf.ap.media.media_type);
	leaf.weight += (double)(leaf.ap.action.action_type)/10;
	//	System.out.print( leaf.media_atom.name + "["+ leaf.weight + "], ");
	// 同時に合計を計算。
	total += leaf.weight;
      }

      // 子供が存在するか？
      else if( leaf.ap == null ){
	node = leaf.media_atom.node;
	// 再起呼出。averageに下位層の平均が帰ってくる。
	average = exec_weight_node( node );
	node = leaf.node;
       // averageはこのノードの重みになる。
	leaf.leaf_flag = false;
	leaf.weight = average;
	total += average;
	average = 0;
      }

      // 弟に交代。
      if( leaf.nonorder != null )
	leaf = leaf.nonorder;
      else if( leaf.seq_next != null )
	leaf = leaf.seq_next;
      else 
	leaf = null;
      
      // 同階層のオブジェクト数
      objects++;
    }
 
    // 同階層の平均値を返す。
    return( (double)total/objects );
  }



  // フラグを最適化する 2000.10.14 matono
  public boolean exec_optimize_flag( OM_Node node ,boolean plus_times ){
    
    OM_Leaf leaf = node.body;
    boolean flagflag = plus_times;

    //  plus_times = true の時はひとつでも未送信があれば親は未送信のままで、すべて送信済であれば親は必ず送信済
    //  plus_times = false の時はひとつでも送信済があれば親は送信済にし、すべて送信済であれば親は必ず送信済

    while( leaf != null ){

      if(plus_times)
	flagflag &= leaf.leaf_flag;
      else	
	flagflag |= leaf.leaf_flag;

      if( leaf.ap == null ){
	node = leaf.media_atom.node;
	flagflag = exec_optimize_flag( node, plus_times );
	leaf.leaf_flag = flagflag;
	node = leaf.node;
      }

      if( leaf.nonorder != null )
	leaf = leaf.nonorder;
      else if( leaf.seq_next != null )
	leaf = leaf.seq_next;
      else 
	leaf = null;
      
    }
    return( flagflag );
 
  }

  // フラグを初期化する 2000.10.14 matono
  public void exec_init_flag( OM_Node node ){
    
    OM_Leaf leaf = node.body;
    
    while( leaf != null ){
      leaf.leaf_flag = false;

      if( leaf.ap == null ){
	node = leaf.media_atom.node;
	exec_init_flag( node );
	node = leaf.node;
      }

      if( leaf.nonorder != null )
	leaf = leaf.nonorder;
      else if( leaf.seq_next != null )
	leaf = leaf.seq_next;
      else 
	leaf = null;
      
    }
 
  }


  // 巡回して重みやフラグを出力する( デバグ用 ) 2000.10.24 matono
  public void exec_debag_weight( OM_Node node ){
    
    OM_Leaf leaf = node.body;

    //    System.out.println( "exec_weight_node!!");        
    
    while( leaf != null ){

      if(leaf.ap != null){
	System.out.print( "#" + leaf.media_atom.name + 
			  "@" + leaf.action_atom.name + 
			  "("+ leaf.weight + "/" +leaf.leaf_flag + ")" );
      }else if( leaf.ap == null ){
	node = leaf.media_atom.node;
	System.out.print( "#" + leaf.media_atom.name + 
			  "("+ leaf.weight + "/" +leaf.leaf_flag + ") <= " );
	exec_debag_weight( node );
	node = leaf.node;
      }

      if( leaf.nonorder != null ){
	System.out.print( ", " );
	leaf = leaf.nonorder;
      }else if( leaf.seq_next != null ){
	System.out.print( "; " );
	leaf = leaf.seq_next;
      }else{
	System.out.println( ";; " );
	leaf = null;
      }
    }
  }

}
