package java_quik;
import java_quik.send_om.*;

public class Exec_PrettyPrint{
  //  Extern_h Extern_h;
  
  /*
  public Exec_PrettyPrint( Extern_h ext ){
    Extern_h = ext;
  }
  */

  public void exec_printrule(){
    exec_print_node();
    exec_print_media();
    exec_print_action();
  }

  /* 全ての Node と Leaf を出力 */
  public void exec_print_node(){
    OM_Node tmp_node;
    OM_Leaf tmp_leaf;

    System.out.println( "/* scene-list */");
    tmp_node = Extern_h.node_pool;
    while( tmp_node != null ){ /* ヘッドの出力 */
      System.out.println( "#" + tmp_node.head.name + 
			"/[comment=" + tmp_node.body.comment +
			"]" +
			" <= " );
      
      exec_print_leaf( tmp_node ); /* ボディーの出力 */
      
      System.out.print( ";;" );
      tmp_node = tmp_node.next;
      System.out.println();
    }
    System.out.println();
  }

  public void exec_print_leaf( OM_Node tmp_node ){
    OM_Leaf tmp_leaf = null;

    tmp_leaf = tmp_node.body; 
    while( tmp_leaf != null ){
      if( tmp_leaf.media_atom != null)
	System.out.print( "\t#" + tmp_leaf.media_atom.name );    

      if( tmp_leaf.action_atom != null )
	System.out.print( "(@" + tmp_leaf.action_atom.name + ")" );    

      if( tmp_leaf.ap != null )
	System.out.print( "/[" + 
			  "play_time=" + tmp_leaf.ap.play_time +
			  ", synchro_time=" + tmp_leaf.ap.synchro_time +
			  ", scenario_num=" + tmp_leaf.ap.scenario_num +
			  ", comment=" + tmp_leaf.ap.comment +
			  "]" );

      if( tmp_leaf.nonorder != null){
	tmp_leaf = tmp_leaf.nonorder;    
	System.out.println( ", " );    
      }else if( tmp_leaf.seq_next != null){
	tmp_leaf = tmp_leaf.seq_next;    
	System.out.println( "; " );    
      }else
	tmp_leaf = null;    	
	
    }
  }

  public void exec_print_media( ){
    OM_Media media = Extern_h.media_pool;
    
    System.out.println( "/* media_list */");    
    while( media != null ){
      //      media.print();
      System.out.print( "#" + media.media_atom.name  + 
			"/[media_type=" + media.media_type + 
			", media_time=" + media.media_time +
			", comment=" + media.comment      
			);   
      switch( media.media_type ){
      case 1:
      case 2:
	System.out.print( ", url=" + media.picture.url + 
			  ", scale=" + media.picture.scale  );
	break;
      case 3:
	System.out.print( ", url=" + media.three_d.url + 
			  ", rotation=" + media.three_d.rotation );
	break;
      case 4:
	System.out.print( ", url=" + media.text.url + 
			  ", font=" + media.text.font + 
			  ", scale=" + media.text.scale + 
			  ", str=" + media.text.str + 
			  ", color={r=" + media.text.r + 
			  ", g=" + media.text.g +  
			  ", b=" + media.text.b + "}" );
	break;
      case 5:
	System.out.print( ", url=" + media.sound.url + 
			  ", volume=" + media.sound.volume  );
	break;
      case 6:
	System.out.print( ", url=" + media.other_media.url + 
			  ", export_type" + media.other_media.export_type + 
			  ", scale_x=" + media.other_media.scale_x + 
			  ", scale_y=" + media.other_media.scale_y );	
	break;
      default:
	System.out.print( "  Media_type Error" );
      }
      System.out.println( " ];;" );    

      media = media.next;    
    }
    System.out.println(); 
  }
  
  public void exec_print_action(){
    OM_Action action = Extern_h.action_pool;
    
    System.out.println( "/* action_list */");    
    while( action != null ){
      //      action.print();

      System.out.print( "@" + action.action_atom.name  + 
		      "/[action_type=" + action.action_type +
		      ", comment=" + action.comment
		      );    
      
      switch( action.action_type ){
      case 1:
	System.out.print( ", point={" );
	exec_print_point( action.out.point );
	System.out.print( "}" +
			  ", font=" + action.out.font +
			  ", color={r=" + action.out.r +
			  ", g=" + action.out.g +
			  ", b=" + action.out.b +
			  "}, volume=" + action.out.volume +
			  ", scale=" + action.out.scale 
			  );
	
	break;
      case 2:
	System.out.print( ", point={");
	exec_print_point( action.move.point);
	System.out.print( "}" +
			  ", rotation=" + action.move.rotation );
	break;
      case 3:
	System.out.print( ", font=" + action.speak.font + 
			  ", intonation=" + action.speak.intonation + 
			  ", pitch=" + action.speak.pitch + 
			  ", volume=" + action.speak.volume + 
			  ", speed=" + action.speak.speed );
	break;
      case 4:
	System.out.print( ", point={");
	exec_print_point( action.light.point);
	System.out.print( "}" +
			  ", brightness=" + action.light.brightness +
			  ", steradian=" + action.light.steradian +
			  ", color={r=" + action.light.r +
			  ", g=" + action.light.g +
			  ", b=" + action.light.b + "}");
	break;
      case 5:
	break;
      default:
	System.out.print( "  Action_type Error" );
      }
      System.out.println( " ];;" );    
      

      action = action.next;    
    }
    System.out.println();    
  }
  
  public void exec_print_point( OM_Point point ){
    System.out.print( "(" + point.point_x  + ", " + point.point_y  + ", " + point.point_z  + ")");
    if( point.next != null ){
      System.out.print(", ");
      exec_print_point( point.next );
    }
  }
 
  public OM_Atom Search_Atom( String atom ){
    OM_Atom tmp_atom;

    tmp_atom = Extern_h.atom_pool;
    while( tmp_atom != null )
      if( atom.equals( tmp_atom.name ) )
	break;
      else
	tmp_atom = tmp_atom.next_bucket;
    
    return tmp_atom;
  }

  public void Print_Atom(){
    OM_Atom tmp_atom = Extern_h.atom_pool;
    
    System.out.println( "----------atom_pool----------");    
    while( tmp_atom != null ){
      System.out.print( tmp_atom.name + ", ");    
      tmp_atom = tmp_atom.next_bucket;    
    }
    System.out.println("\n");    
  }

  public void Print_Node( ){
    OM_Node tmp_node = Extern_h.node_pool;
    
    System.out.println( "----------node_pool----------");    
    while( tmp_node != null ){
      System.out.print( tmp_node.head.name  + ", " );    
      tmp_node = tmp_node.next;    
    }
    System.out.println("\n");    
  }

}
