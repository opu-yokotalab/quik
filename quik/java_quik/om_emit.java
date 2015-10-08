package java_quik;

import java_quik.send_om.*;
//import java_quik.gui.*;
import java.io.*;
import java.util.*;

public class OM_emit {

  /* $B%^%k%A%a%G%#%"5!G=ItJ,(B ***1 ---> ***2 $B$O(B***1$B$+$i(B***2$B$K7R$0$3$H$r0UL#$9$k!#(B*/
  
  //          <<<Node$B$rFbItI=8=$KJQ$($k!#(B>>>
  public static int flag=0;

  public static void emit_node (T_Leaf t_head, T_LeafList t_body)
  {
    int len=1;//dummy
    OM_Leaf new_leaf;
    OM_Node new_node;

    OM_LeafList new_leaf_list = new OM_LeafList();
    
    new_node = new OM_Node();

    //new_leaf = new OM_Leaf(t_head);
    // 12/01/2000 ObjectManager    
    new_leaf = new OM_Leaf();
    new_leaf.leaf_flag = t_head.leaf_flag;  
    new_leaf.media_atom = t_head.media_atom; 
    new_leaf.leaf_flag = false;
    new_leaf.comment = null;
    new_leaf.weight = 0.0;     
    new_leaf.action_atom = null;
    new_leaf.ap = null;         
    new_leaf.seq_next = null;
    new_leaf.nonorder = null;
    new_leaf.node = null;
    //

    if(t_head.t_ap.t_con == null)
      new_leaf.comment = null;
    else
      new_leaf.comment = t_head.t_ap.t_con.comment;
    
    if(t_body.str_stream != null)  
      flag = 1;    //$B%3%^%s%I(Bstream$B$,5-=R$5$l$F$k>l9g(B 

    if(flag == 1)
      {
	//System.out.println("-Stream-");
	//$B%7!<%s<+F0@8@.!J(Bstream$B$,$"$k>l9g!K!#>lLL$N%X%C%I$r(Bseq_next$B$G7R$0!#%G!<%?%b%G%k;2>H(B
	if(Extern_h.node_pool == null)        //main$B$N(BOM_Leaf.seq_next ---> OM_Leaf
	  {
	    Extern_h.node_pool = new_node;            //Node$B$N@hF,(B
	    new_node.stream_str = t_body.leaf.media_atom.name; //stream$B$NJ8;zNs(B
	   
	    len = new_node.stream_str.length();      //stream$B$NJ8;zNs$ND9$5(B
	  }
	//  }2000/11/6 itadani
    
	if(Extern_h.node_pool.body == null) //$B:G=i$N>lLL$r(Bbody$B$K7R$0!#(B
	  {
	    
	    boolean matches = new_leaf.media_atom.name.regionMatches(0, new_node.stream_str, 0, len); 
	    //#stream()$B$NCf$NJ8;zNs$N%^%C%A%s%0(B
	    
	    if (matches == true){
	      //	      new_leaf.command = Media_Command.MC_Stream;
	  
	      Extern_h.node_pool.body = new_leaf;
	      
	      new_leaf.stream_current_leaf = new_leaf;
	    }
	  }
	else  //2$BHVL\0J9_$N>lLL$r7R$0!#(B
	  {
	    boolean matches = new_leaf.media_atom.name.regionMatches(0, new_node.stream_str, 0, len); 
	    // stream()$B$NCf$NJ8;zNs$N%^%C%A%s%0(B
	    if (matches == true){
	      new_leaf.stream_current_leaf.seq_next = new_leaf;        //OM_Leaf.seq_next ---> OM_Leaf
	      new_leaf.stream_current_leaf = new_leaf;
	    }
	  }
	
      } //2000/11/6 itadani
    if(Extern_h.node_pool == null)
      Extern_h.node_pool = new_node;            //stream$B$,$J$$>l9g$N(BNode$B$N@hF,(B
    
    new_node.head = t_head.media_atom;        //OM_Node.head ---> OM_Atom
    new_node.head.node = new_node;            //OM_Atom.node ---> OM_Node
    
    if(t_body.str_stream == null){ //stream$B$,$J$$>l9g(B
      //System.out.println("-NotStream-");
      new_leaf_list = emit_leaf_list(t_body);  //OM_Node.body ---> OM_Leaf
      
	new_node.body = new_leaf_list.leaf;
	if(Extern_h.node_pool == null)
	  new_node.body.media_atom.node = new_node;
    }
    
    //OM_Node.next ---> OM_Node
    if(Extern_h.node_pool != new_node){
      new_node.node_pool_last.next = new_node;
    }
    new_node.node_pool_last = new_node;  //1$B$DA0(B




    //System.out.println("-----OM_Node.head.name-----"+ new_node.head.name);
  

  }
  

  

  //          <<<LeafList$B$rFbItI=8=$KJQ$($k!#(B>>>
  
  public static OM_LeafList emit_leaf_list (T_LeafList t_body)
  {
    OM_LeafList new_leaf_list;
    OM_LeafList.leaf_list_p=null;
    OM_LeafList.leaf_list_last=null;

    while (t_body != null)
      {
	new_leaf_list = new OM_LeafList();
	new_leaf_list.leaf = emit_leaf(t_body.leaf);

	//OM_LeafList$B$r:n@.$7$=$l$r%j%9%H$K$D$J$0(B
	if(OM_LeafList.leaf_list_p == null){
	  OM_LeafList.leaf_list_p =  new_leaf_list;
	  OM_LeafList.leaf_list_last = new_leaf_list;
	}	
	else{
	  OM_LeafList.leaf_list_last.next =  new_leaf_list;
	  OM_LeafList.leaf_list_last = new_leaf_list;
	}

      t_body = t_body.next;
      }

    if(Extern_h.leaf_list_pool == null){
      Extern_h.leaf_list_pool = OM_LeafList.leaf_list_p;
      Extern_h.leaf_list_last = OM_LeafList.leaf_list_last;
    }
    else{
      Extern_h.leaf_list_last.next = OM_LeafList.leaf_list_p;
       Extern_h.leaf_list_last = OM_LeafList.leaf_list_last;
    }
    
    //System.out.println("-----OM_LeafList.leaf_list_p.leaf.media_atom.name-----"+ OM_LeafList.leaf_list_p.leaf.media_atom.name);
    //System.out.println("-----OM_LeafList.leaf_list_last.leaf.media_atom.name-----"+ OM_LeafList.leaf_list_last.leaf.media_atom.name);

    /*
    if(OM_LeafList.leaf_list_p.leaf.seq_next != null)
      System.out.println("-----OM_LeafList.leaf_list_p.leaf.seq_next.media_atom.name-----"+ OM_LeafList.leaf_list_p.leaf.seq_next.media_atom.name);
    else if(OM_LeafList.leaf_list_p.leaf.nonorder != null)
      System.out.println("-----OM_LeafList.leaf_list_p.leaf.nonorder.media_atom.name-----"+ OM_LeafList.leaf_list_p.leaf.nonorder.media_atom.name);
    else
      System.out.println("--Not--Leaf.next----");
      */
    return OM_LeafList.leaf_list_p;

  }
  
  //          <<<Leaf$B$rFbItI=8=$KJQ$($k!#(B>>>
  // T_Leaf$B$+$i(BOM_Leaf$B$KJQ49(B
  public static OM_Leaf emit_leaf (T_Leaf t_leaf)
  {
    OM_Leaf new_leaf;
    OM_Media media;
    
    //System.out.println("t_leaf.seq_next, t_leaf.nonorder" + t_leaf.seq_next +","+ t_leaf.nonorder);

    //    new_leaf = new OM_Leaf(t_leaf.seq_next, t_leaf.nonorder);
    // 12/01/2000 ObjectManager   
    new_leaf = new OM_Leaf();    
    OM_Leaf dummy_leaf = new OM_Leaf();
    new_leaf.leaf_flag = false;  
    new_leaf.comment = null;     
    new_leaf.weight = 0.0;       
    new_leaf.media_atom = null;  
    new_leaf.action_atom = null; 
    new_leaf.ap = null;          

    if(t_leaf.seq_next == 1)
      new_leaf.seq_next = dummy_leaf;
    else
      new_leaf.seq_next = null;
    if(t_leaf.nonorder == 1)
      new_leaf.nonorder = dummy_leaf;
    else
      new_leaf.nonorder = null;

    new_leaf.node = null;
    //


    if(OM_Leaf.ex_leaf != null){
      if(OM_Leaf.ex_leaf.seq_next != null){
	OM_Leaf.ex_leaf.seq_next = new_leaf;
	OM_Leaf.ex_leaf.nonorder = null;
      }
      else if(OM_Leaf.ex_leaf.nonorder != null){
	OM_Leaf.ex_leaf.nonorder = new_leaf;
	OM_Leaf.ex_leaf.seq_next = null;
      }
      else{
	OM_Leaf.ex_leaf.seq_next = null;
	OM_Leaf.ex_leaf.nonorder = null;
      }
    }
    
    OM_Leaf.ex_leaf = new_leaf;

    

    if(t_leaf.t_ap.t_con == null)
      new_leaf.comment = null;
    else
      new_leaf.comment = t_leaf.t_ap.t_con.comment;
            
    /*
      //command$B$O:G8e$N(BT_Leaf$B$K$7$+J]B8$5$l$F$J$$$?$a(B
      //command$B$N:n@.(B
      if(new_leaf.command != -1)  
      new_leaf.stack_command = t_leaf.command;
    else
    new_leaf.command = new_leaf.stack_command;
    */

    new_leaf.media_atom = t_leaf.media_atom;     //OM_Leaf.media_atom ---> OM_Atom
    new_leaf.action_atom = t_leaf.action_atom;  // 10/25/2000 ObjectManager
  
    //System.out.println("--OM_Leaf.media_atom--"+new_leaf.media_atom.name);
    
    /*
    if(new_leaf.action_atom!=null)  //2000/11/6 itadani
      System.out.println("---------------------------------OM_Leaf.action_atom--"+new_leaf.action_atom.name);
      */
    return new_leaf;    
    
  }
  
  //          <<<Media$B$rFbItI=8=$KJQ$($k!#(B>>>
  //  OM_Media$B$N@8@.(B
  public static void emit_media (T_Leaf t_leaf)
  {
    
    OM_Media new_media = new OM_Media();

    new_media.media_type = t_leaf.t_ap.t_con.media_type;
    new_media.comment = t_leaf.t_ap.t_con.comment;
    new_media.media_time = t_leaf.t_ap.t_con.media_time;
    new_media.media_atom = t_leaf.t_ap.media_atom;
    new_media.picture = null;
    new_media.sound = null;
    new_media.text = null;
    new_media.three_d = null;
    new_media.other_media = null;
    new_media.ap = null;
    new_media.next = null;    
    
    if(Extern_h.media_pool == null)
      Extern_h.media_pool = new_media;            //Node$B$N@hF,(B
    
    new_media.media_atom.media = new_media;    //OM_Atom.media ---> OM_Media
    
    //OM_Media.* ---> OM_*
    switch (new_media.media_type){
    case OM_Media_Type.MT_Pic:        //$B@E;_2h(B
    case OM_Media_Type.MT_Ani:        //$BF02h(B
      OM_Picture p = new OM_Picture(t_leaf.t_ap.t_con.url, t_leaf.t_ap.t_con.scale);
      new_media.picture = p;            //OM_Media.picture ---> OM_Picture
      break;
    case OM_Media_Type.MT_3D:       //3D$B%*%V%8%'%/%H(B
      OM_3D three_d = new OM_3D(t_leaf.t_ap.t_con.url, t_leaf.t_ap.t_con.rotation);
      new_media.three_d = three_d;          
      break;
    case OM_Media_Type.MT_Text:      //$B%F%-%9%H(B
      OM_Text text = new OM_Text(t_leaf.t_ap.t_con.url, t_leaf.t_ap.t_con.str, t_leaf.t_ap.t_con.font, t_leaf.t_ap.t_con.scale, t_leaf.t_ap.t_con.r, t_leaf.t_ap.t_con.g, t_leaf.t_ap.t_con.b);
      new_media.text = text;
      break;
    case OM_Media_Type.MT_Sound:     //$B%5%&%s%I%U%!%$%k(B
      OM_Sound sound = new OM_Sound(t_leaf.t_ap.t_con.url, t_leaf.t_ap.t_con.volume);
      new_media.sound = sound;
      break;
    case OM_Media_Type.MT_OtherM:     //$B30It%a%G%#%"(B
      OM_OtherM  other_media = 	new OM_OtherM(t_leaf.t_ap.t_con.url, t_leaf.t_ap.t_con.export_type, t_leaf.t_ap.t_con.scale_x, t_leaf.t_ap.t_con.scale_y);
      new_media.other_media = other_media;
      break;
    } 
    
    //OM_Media.next ---> OM_Media
    if(Extern_h.media_pool != new_media)  //new_media$B$,@hF,$G$J$$$J$i(B
      new_media.media_pool_last.next = new_media;
    new_media.media_pool_last = new_media;  //1$B$DA0(B

    //    emit_debug_media(new_media);    
  }

   //          <<<Action$B$rFbItI=8=$KJQ$($k!#(B>>>
  //  OM_Action$B$N@8@.(B
  public static void emit_action (T_Leaf t_leaf)
  {
    OM_Action new_action = new OM_Action();

    new_action.action_type = t_leaf.t_ap.t_con.action_type;
    new_action.comment = t_leaf.t_ap.t_con.comment;
    new_action.action_atom =  t_leaf.t_ap.action_atom;
    new_action.out = null;
    new_action.del = null;
    new_action.move = null;
    new_action.speak = null;
    new_action.light = null;
    new_action.next = null;


    if(Extern_h.action_pool == null)
      Extern_h.action_pool = new_action;            //Node$B$N@hF,(B

    new_action.action_atom.action = new_action;    //OM_Atom.action ---> OM_Action
    
    //OM_Action.* ---> OM_*
    switch (new_action.action_type){
    
    case OM_Action_Type.AT_Out:     //$B=P8=(B
      OM_Out out = new OM_Out();
      out.point = null;
      out.font = t_leaf.t_ap.t_con.font;
      out.r = t_leaf.t_ap.t_con.r;
      out.g = t_leaf.t_ap.t_con.g;
      out.b = t_leaf.t_ap.t_con.b;
      out.volume = t_leaf.t_ap.t_con.volume;
      out.scale = t_leaf.t_ap.t_con.scale;
      out.point = emit_point(t_leaf.t_ap.t_con.point);
      new_action.out = out;
      break;
    case OM_Action_Type.AT_Move:      //$B0\F0(B
      OM_Move move = new OM_Move();
      move.point = null;
      move.rotation = t_leaf.t_ap.t_con.rotation;
      
      move.point = emit_point(t_leaf.t_ap.t_con.point);
      //System.out.println("emit_point"+move.point.point_x);


      new_action.move = move;
      break;
    case OM_Action_Type.AT_Speak:     //$BH/OC(B

      OM_Speak speak = new OM_Speak();
      speak.font = t_leaf.t_ap.t_con.font;
      speak.intonation = t_leaf.t_ap.t_con.intonation;
      speak.pitch = t_leaf.t_ap.t_con.pitch;
      speak.volume = t_leaf.t_ap.t_con.volume;
      speak.speed = t_leaf.t_ap.t_con.speed;

      new_action.speak = speak;
    case OM_Action_Type.AT_Light:     //$B%i%$%F%#%s%0(B
      OM_Light light = new OM_Light();
      light.point = null;
      light.brightness = t_leaf.t_ap.t_con.brightness;
      light.steradian = t_leaf.t_ap.t_con.steradian;
      light.r = t_leaf.t_ap.t_con.r;
      light.g = t_leaf.t_ap.t_con.g;
      light.b = t_leaf.t_ap.t_con.b;
      light.point = emit_point(t_leaf.t_ap.t_con.point);
      new_action.light = light;
    }
 
  
    //OM_Action.next ---> OM_Action
    if(Extern_h.action_pool != new_action)  //new_action$B$,@hF,$G$J$$$J$i(B
      new_action.action_pool_last.next = new_action;
    new_action.action_pool_last = new_action;  //1$B$DA0(B

    //    emit_debug_action(new_action);    
  }


  
  //          <<<Point$B$rFbItI=8=$KJQ$($k!#(B>>>
  //  T_Point$B$+$i(BOM_Point$B$KJQ49(B,T_Point$B$O:G8e$+$i(Bnext$B$G$?$I$C$F$$$k$,(B,$B$=$l$r:G=i(B
  //$B$+$i$?$I$k$h$&$KJQ49(B
  public static OM_Point emit_point (T_Point t_point)
  {
    OM_Point po = null;
   
    


    if (t_point == null) //$B%G%U%)%k%H(B
      {
      po = new OM_Point();
      return po;
      }

    //2000/11/6  itadani begin  
    Stack pointliststack = new Stack();
    while (t_point != null)//$B%]%$%s%H$N@hF,$r5UE>(B
      {
	
	pointliststack.push(t_point);
	t_point = t_point.next;
      }
    OM_Point.point_pool = null;
    while(pointliststack.empty()!=true){//$B%]%$%s%H$N@hF,$r:F$S5UE>(B
      
      T_Point t_point_tmp = (T_Point)pointliststack.pop();
      OM_Point new_point = new OM_Point();
      new_point.point_x = t_point_tmp.point_x;
      new_point.point_y = t_point_tmp.point_y;
      new_point.point_z = t_point_tmp.point_z;
      new_point.next = OM_Point.point_pool;
      OM_Point.point_pool = new_point;
    }
    return OM_Point.point_pool;
    
    //2000/11/6 itadani  end
    /*
      
    OM_Point.point_pool = null;
    while (t_point != null)
      {
	OM_Point.point_pool = new OM_Point( t_point, OM_Point.point_pool );
	t_point = t_point.next;
      }
    return OM_Point.point_pool;
    */

}
  
  
  //         <<<OM_Node.parent ---> OM_Leaf>>>
  public static void emit_parent()
  {
    OM_Node tmp_node;
    OM_Leaf tmp_leaf;
    
    tmp_node = Extern_h.node_pool;  //OM_Node$B$N@hF,(B
    
    while( tmp_node != null ){  
      tmp_leaf = tmp_node.body;
      while( tmp_leaf != null ){
	tmp_leaf.node = tmp_node;
	if( tmp_leaf.media_atom.node != null )
	  tmp_leaf.media_atom.node.parent = tmp_leaf;  //$B?F>pJs$r;}$?$;$k!#(B
	if( tmp_leaf.seq_next != null )
	  tmp_leaf = tmp_leaf.seq_next;
	else if( tmp_leaf.nonorder != null )
	  tmp_leaf = tmp_leaf.nonorder;
	else
	  tmp_leaf = null;
      }
      /*
      if(tmp_node.head.name!=null)
	System.out.println("-----OM_Node.parent.media_atom.name-----"+ tmp_node.head.name);	 
	*/
      tmp_node = tmp_node.next;
    }
  }

  //       <<OM_ActionPointer$B$N@8@.(B>>
  public static void emit_action_pointer()
  {

    OM_Media media_p=null;
    OM_Action action_p=null;
    T_ActionPointer t_ap;
    OM_ActionPointer ap;

    t_ap = T_ActionPointer.t_ap_pool;

    while(t_ap != null){

      media_p = Extern_h.media_pool;
      action_p = Extern_h.action_pool;

      if(t_ap.media_atom != null && t_ap.action_atom != null){
	if(t_ap.t_con != null){
	  ap = new OM_ActionPointer();
	  ap.loop = t_ap.loop;
	  ap.scenario_num = t_ap.t_con.scenario_num;
	  ap.play_time = t_ap.t_con.play_time;
	  ap.synchro_time = t_ap.t_con.synchro_time;
	  ap.comment = t_ap.t_con.comment;
	  ap.media = null;
	  ap.action = null;
	  ap.next = null;
	}
	else
	  ap = new OM_ActionPointer();

	while(media_p != null){
	  if(media_p.media_atom == t_ap.media_atom){
	    //Media$B$N(BActionPointer$B$,(Bnull$B$J$iF~$l$k(B
	    if(media_p.ap == null)
	      media_p.ap = ap;
	    //
	    ap.media = media_p;
	    media_p = media_p.next;
	  }
	  else
	    media_p = media_p.next;
	}
    
	while(action_p != null){
	  if(action_p.action_atom == t_ap.action_atom){
	    ap.action = action_p;
	    action_p = action_p.next;
	  }
	  else
	    action_p = action_p.next;
	}

	if(OM_ActionPointer.ap_pool == null){
	  OM_ActionPointer.ap_pool = ap;
	  Extern_h.ap_pool = ap;
	}
	else{
	  OM_ActionPointer.ap_pool.next_bucket = ap;
	  OM_ActionPointer.ap_pool = ap;
	}
	t_ap = t_ap.next;
      }
      else
	t_ap = t_ap.next;

    } 

    OM_ActionPointer.ap_pool = Extern_h.ap_pool;
    
    while(OM_ActionPointer.ap_pool != null){
      OM_ActionPointer tmp_ap1, tmp_ap2;
      tmp_ap1 = OM_ActionPointer.ap_pool;
      if(OM_ActionPointer.ap_pool.next_bucket != null){
	tmp_ap2 = OM_ActionPointer.ap_pool.next_bucket;
      }
      else break;
      while(tmp_ap2 != null){
	if(tmp_ap1.media.media_atom.name.equals(tmp_ap2.media.media_atom.name))
	  {
	    tmp_ap1.next = tmp_ap2;
	   //System.out.println("---------OM_ActionPointer.next-----");
	   //System.out.println(tmp_ap1.media.media_atom.name);
	   //System.out.println(tmp_ap1.action.action_atom.name);
	   //System.out.println(tmp_ap2.media.media_atom.name);
	   //System.out.println(tmp_ap2.action.action_atom.name);

	    tmp_ap1 = tmp_ap2;
	    tmp_ap2 = tmp_ap2.next_bucket;
	  }
	else{
	  tmp_ap2 = tmp_ap2.next_bucket;
	}
      }
      //System.out.println("---------OM_ActionPointer-------------------");
      //System.out.println(OM_ActionPointer.ap_pool.media.media_atom.name);
      //System.out.println(OM_ActionPointer.ap_pool.action.action_atom.name);

      OM_ActionPointer.ap_pool  = OM_ActionPointer.ap_pool.next_bucket;
    }
  }

  //         <<<OM_Leaf.ap ---> OM_ActionPointer>>>
  public static void emit_leaf_ap(){
    OM_LeafList tmp_leaf_list;
    OM_ActionPointer tmp_ap;

    tmp_leaf_list = Extern_h.leaf_list_pool;
    tmp_ap = Extern_h.ap_pool;

    while(tmp_leaf_list != null){
      if(tmp_leaf_list.leaf.media_atom != null && tmp_leaf_list.leaf.action_atom != null){
	//System.out.println("LeafList.Leaf.media_atom --" + tmp_leaf_list.leaf.media_atom.name);
	tmp_leaf_list.leaf.ap = tmp_ap;
	tmp_leaf_list = tmp_leaf_list.next;
	//System.out.println("tmp_ap-- " + tmp_ap.media.media_atom.name);
	tmp_ap = tmp_ap.next_bucket;
      }
      else 
	tmp_leaf_list = tmp_leaf_list.next;
    }
  }


  // $B%G%P%C%0MQ(B

  public static void emit_debug_media(OM_Media new_media){
    
    System.out.println("--------OM_Media = #"+ new_media.media_atom.name);
    System.out.println("media_type = "+ new_media.media_type);
    System.out.println("media_time = "+ new_media.media_time);
    System.out.println("comment = "+ new_media.comment);

    if(new_media.picture != null){
      System.out.println("OM_Picture.url = "+new_media.picture.url);
      System.out.println("OM_Picture.scale = "+new_media.picture.scale);
    }
    else if(new_media.sound != null){
      System.out.println("OM_Sound.url = "+new_media.sound.url);
      System.out.println("OM_Sound.volume = "+new_media.sound.volume);
    }
    else if(new_media.text != null){
      System.out.println("OM_Text.url = "+ new_media.text.url);
      System.out.println("OM_Text.font = " + new_media.text.font);
      System.out.println("OM_Text.str = " + new_media.text.str);
      System.out.println("OM_Text.scale = " + new_media.text.scale);
      System.out.println("OM_Text.color(r,g,b) = " + "(" + new_media.text.r + 
			 "," + new_media.text.g + "," + new_media.text.b + ")");
    }
    else if(new_media.three_d != null){
      System.out.println("OM_3D.url = "+ new_media.three_d.url);
      System.out.println("OM_3D.rotation = "+ new_media.three_d.rotation);
    }
    else if(new_media.other_media != null){
      System.out.println("OM_OtherM.url = "+ new_media.other_media.url);
      System.out.println("OM_OtherM.export_type = "+ new_media.other_media.export_type);
      System.out.println("OM_OtherM.scale_x = "+ new_media.other_media.scale_x);
      System.out.println("OM_OtherM.scale_y = "+ new_media.other_media.scale_y);
    }
    /*
    if(new_media.ap != null){
      emit_debug_ap(new_media.ap);
    }
    */
    System.out.println("-------------------------------------------");
  }
  
  
  public static void emit_debug_action(OM_Action new_action){
    
    System.out.println("--------OM_Action = @"+ new_action.action_atom.name);
    System.out.println("action_type = "+ new_action.action_type);
    System.out.println("comment = "+ new_action.comment);

    if(new_action.out != null){
      System.out.println("OM_Out.point(x,y,z) = " + "(" + new_action.out.point.point_x +  "," + new_action.out.point.point_y + "," + new_action.out.point.point_z + ")");
      System.out.println("OM_Out.font = " + new_action.out.font);
      System.out.println("OM_Out.color(r,g,b) = " + "(" + new_action.out.r + 
			 "," + new_action.out.g + "," + new_action.out.b + ")");
      System.out.println("OM_Out.scale = " + new_action.out.scale);
      System.out.println("OM_Out.volume = " + new_action.out.volume);
    }

    else if(new_action.del != null){
      System.out.println("OM_Del = "+new_action.del);
    }
    
    else if(new_action.move != null){
      OM_Point tmp_p = new_action.move.point;
      while(tmp_p != null){
	System.out.println("OM_Move.point(x,y,z) = " + "(" + tmp_p.point_x +  "," + tmp_p.point_y + "," + tmp_p.point_z + ")");
	tmp_p = tmp_p.next;
      }
      System.out.println("OM_Move.rotation = " + new_action.move.rotation);
    }
    
    else if(new_action.speak != null){
      System.out.println("OM_Speak.font = "+new_action.speak.font);
      System.out.println("OM_Speak.intonation = "+new_action.speak.intonation);
      System.out.println("OM_Speak.pitch = "+new_action.speak.pitch);
      System.out.println("OM_Speak.volume = "+new_action.speak.volume);
      System.out.println("OM_Speak.speed = "+new_action.speak.speed);
    }
    
    else if(new_action.light != null){
      System.out.println("OM_Light.point(x,y,z) = " + "(" + new_action.light.point.point_x +  "," + new_action.light.point.point_y + "," + new_action.light.point.point_z + ")");
      System.out.println("OM_Light.brightness = " + new_action.light.brightness);
      System.out.println("OM_Light.steradian = " + new_action.light.steradian);
      System.out.println("OM_Light.color(r,g,b) = " + "(" + new_action.light.r + "," + new_action.light.g + "," + new_action.light.b + ")");
    }

    System.out.println("-------------------------------------------");
  }

   public static void emit_debug_ap(OM_ActionPointer new_ap){

     System.out.println("---OM_ActionPointer---" + "#" + new_ap.media.media_atom.name + "(@" + new_ap.action.action_atom.name + ")");
     System.out.println("loop = "+ new_ap.loop);
     System.out.println("comment = "+ new_ap.comment);
     System.out.println("scenario_num = "+ new_ap.scenario_num);
     System.out.println("play_time = "+ new_ap.play_time);
     
     emit_debug_media(new_ap.media);
     emit_debug_action(new_ap.action);

     if(new_ap.next != null){
       OM_ActionPointer next_ap = new_ap.next;
       emit_debug_ap(next_ap);
     }
   }

  public static void emit_debug_node(OM_Node new_node){

     System.out.println("---OM_Node---" + "#" + new_node.head.name);
     if(new_node.body != null){
       System.out.println("---Node.body--");
       emit_debug_leaf(new_node.body);
     }
     if(new_node.parent != null){
       System.out.println("---Node.parent--");
       emit_debug_leaf(new_node.parent);
     }
  }

  public static void emit_debug_leaf(OM_Leaf new_leaf){

    OM_Leaf next_leaf = null;

    if(new_leaf.action_atom != null){
      System.out.print("---OM_Leaf--#"+new_leaf.media_atom.name);
      System.out.println("(@"+new_leaf.action_atom.name+")");
    }
    else
      System.out.println("---OM_Leaf--#"+new_leaf.media_atom.name);

     System.out.println("leaf_flag = " + new_leaf.leaf_flag);
     if(new_leaf.comment != null)
       System.out.println("comment = " + new_leaf.comment);
     System.out.println("weight = " + new_leaf.weight);
     
     
     if(new_leaf.ap != null)
       emit_debug_ap(new_leaf.ap);

     if(new_leaf.seq_next != null){
       next_leaf = new_leaf.seq_next;
       emit_debug_leaf(next_leaf);
     }
     else if(new_leaf.nonorder != null){
       next_leaf = new_leaf.nonorder;
       emit_debug_leaf(next_leaf);
     }
  }
  
}
