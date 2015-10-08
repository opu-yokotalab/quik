/* ARRANGE THESA.JAVA  --1998-- yokogawa */
package java_quik.gui;

import java_quik.*;
import java.awt.*;

public class LAT_Thesa extends Frame{
  public int NODENUM;

  public String nodename[];
  public boolean nodeflag[];
  int edge[][];
  int input[][];
  int check[][];
  int edge_box[][];
  int start_point[];
  int end_point[];
  int result_data[][];
  int lat_box[][];

  private int serial_number;
  int check_cnt = 0;
  int reach = 0;
  public static String dia_num = "0";
  /* 99/01/29 yokogawa */
  public static String mess = "null";
  boolean dia_flag = false;
  int num_rimit = 0;
  int weak_num = 0;
  int start = 0;
  boolean select_flag = true;

  public LAT_Thesa(int num) {
    int i, j;

    NODENUM = num;
    nodename = new String[NODENUM];
    nodeflag = new boolean[NODENUM];
    edge = new int[NODENUM][NODENUM];
    input = new int[NODENUM][NODENUM];
    check = new int[NODENUM][NODENUM];
    edge_box = new int[NODENUM][NODENUM];
    lat_box = new int[NODENUM][NODENUM];
    start_point = new int[NODENUM];
    end_point = new int[NODENUM];
    result_data = new int[NODENUM][3];

    /* nodeflag の初期化 */
    for (i = 0; i < NODENUM; ++i) {
      nodeflag[i] = false;
      for (j = 0; j < NODENUM; ++j) {
	edge[i][j] = 0;
	input[i][j] = 0;
	lat_box[i][j] = 0;
      }
    }
    serial_number = 0;
  }

  /* search empty nodename */
  int find_empty() {
    int i;

    for (i = 0; i < NODENUM; ++i) if (false == nodeflag[i]) return i;
    return -1;
  }

  /* make new nodename */
  public int node_number(String name) {
    int i;

    for (i = 0; i < NODENUM; ++i) {
      if (!nodeflag[i]) continue;
      if (0 == name.compareTo(nodename[i])) return i;
    }
    return -1;
  }

  /* input node in nodename[] and make edge */
  public void add(String n1, String n2) {
    int	i, t1, t2;

    if (0 == n1.compareTo(n2)) return;
    t1 = node_number(n1);
    t2 = node_number(n2);

    if (-1 == t1) {
      if (-1 == (t1 = find_empty())) {
	mess = "node overflow: " + n1 + "<" + n2 + "\n" + "Please Node within 16";
	returnStatus(QuikStatus.ERROR,(Object)mess);
	return;
      }
      nodename[t1] = n1;
      nodeflag[t1] = true;
    }

    if (-1 == t2) {
      if (-1 == (t2 = find_empty())) {
	mess = "node overflow: " + n1 + "<" + n2 + "\n" + "Please Node within 16";
	returnStatus(QuikStatus.ERROR,(Object)mess);
	return;
      }
      nodename[t2] = n2;
      nodeflag[t2] = true;
    }
    edge[t2][t1] = 1;
    input[t2][t1] = 1;
  }

  /* make new edge */
  void thin_out_edges() {
    boolean flag = true;

    while (flag) {
      int i, j, k;
      flag = false;

      for (i = 0; i < NODENUM; ++i) {
	if (!nodeflag[i]) continue;
	for (j = 0; j < NODENUM; ++j) {
	  if (0 == edge[i][j]) continue;
	  for (k = 0; k < NODENUM; ++k) {
	    if (0 == edge[j][k]) continue;
	    if (edge[i][k] < edge[i][j] + edge[j][k]) {
	      edge[i][k] = edge[i][j] + edge[j][k];
	      flag = true;
	    }
	  }
	}
      }
    }
  }

  /* search bad edge */
  void fill_edges() {
    boolean flag = true;

    search_lat_edge();

    while (flag) {
      int	i, j, k;

      flag = false;
      for (i = 0; i < NODENUM; ++i) {
	if (!nodeflag[i]) continue;
	for (j = 0; j < NODENUM; ++j) {
	  if (0 == edge[i][j]) continue;
	  for (k = 0; k < NODENUM; ++k) {
	    if (0 == edge[j][k] || 0 != edge[i][k]) continue;
	    edge[i][k] = edge[i][j] + edge[j][k];
	    flag = true;
	  }
	}
      }
    }

    for(int x=0 ; x<NODENUM ; x++) {
      if(edge[x][x] == 0) continue;
      clear_edge_box();
      edge_break(x, 0, x);
    }

    for(int x=0 ; x<NODENUM ; x++) {
      if(edge[x][x] == 0) continue;
      clear_check();
      fill_tree(x, 0, 0, x);
    }
  }

  /* make lattice */
  void every_meet() {
    int	n1, n2;

    for (n1 = 0; n1 < NODENUM - 1; ++n1) {
      if (!nodeflag[n1]) continue;
      for (n2 = n1 + 1; n2 < NODENUM; ++n2) {
	boolean flag = true;

	if (!nodeflag[n2]) continue;
	while (flag) {
	  int i, meet_n = NODENUM;

	  flag = false;
	  for (i = 0; i < NODENUM; ++i) {
	    if ( 0 != edge[n1][i] && 0 != edge[n2][i] && (i != n1 || i != n2)) {
	      if (NODENUM == meet_n) {
		meet_n = i;
	      } else {
		if (0 != edge[i][meet_n]) {
		  meet_n = i;
		} else if (0 == edge[meet_n][i]) {
		  int j;

		  for (j = 0; j < NODENUM; ++j) {
		    if ((0 != edge[n1][j]|| 0 != edge[n2][j]) && 0 != edge[j][i] && 0 != edge[j][meet_n]) break;
		  }
		  if (NODENUM !=  j) continue;

		  String insert_node = "ins[" + serial_number++ + "]";

		  add(insert_node, nodename[n1]);
		  add(insert_node, nodename[n2]);
		  add(nodename[meet_n], insert_node);
		  add(nodename[i], insert_node);
		  fill_edges();
		  flag = true;
		  break;
		}
	      }
	    }
	  }
	}
      }
    }
  }

  /* search lattice edge*/
  void search_lat_edge() {
    int	n1, n2;
	
    for (n1 = 0; n1 < NODENUM - 1; ++n1) {
      if (!nodeflag[n1]) continue;
      for (n2 = n1 + 1; n2 < NODENUM; ++n2) {
	boolean flag = true;

	if (!nodeflag[n2]) continue;
	while (flag) {
	  int i, meet_n = NODENUM;

	  flag = false;
	  for (i = 0; i < NODENUM; ++i) {
	    if ( 0 != edge[n1][i] && 0 != edge[n2][i] && (i != n1 || i != n2)) {
	      if (NODENUM == meet_n) {
		meet_n = i;
	      } else {
		if (0 != edge[i][meet_n]) {
		  meet_n = i;
		} else if (0 == edge[meet_n][i]) {
		  int j;

		  for (j = 0; j < NODENUM; ++j) {
		    if ((0 != edge[n1][j]|| 0 != edge[n2][j]) && 0 != edge[j][i] && 0 != edge[j][meet_n]) break;
		  }
		  if (NODENUM !=  j) continue;
		  if (lat_box[n1][meet_n]==1 && lat_box[n1][i]==1 && lat_box[n2][meet_n]==1 && lat_box[n2][i]==1) continue;
		  lat_box[n1][meet_n] = 1;
		  lat_box[n1][i] = 1;
		  lat_box[n2][meet_n] = 1;
		  lat_box[n2][i] = 1;
		  flag = true;
		  break;
		}
	      }
	    }
	  }
	}
      }
    }
  }

/**     // 1998/07/13 Sugimoto
  // this method is not used
  public void show() {
  int i, j;

  for (i = 0; i < NODENUM; ++i) {
  if (!nodeflag[i]) continue;
  System.out.print(nodename[i] + " ");
  for (j = 0; j < NODENUM; ++j) {
  if (!nodeflag[j]) continue;
  System.out.print(edge[i][j] + " ");
  }
  System.out.println("");
  }
  }
*/

  /* Data edge is fill */
  public void fill_tree(int front, int check_one, int count, int start) {
    boolean tree_flag = true;
    int check_two = 0;
    int front_box = 0;
    int next_box = 0;

    count++;
    for(int i=0 ; i<NODENUM ; i++) {
      if(edge[front][i] == 1 && edge[i][front] != 0) {
	if(check[check_one][i] == 0) {
	  if(edge[start][i] < count && lat_box[start][i]==0) edge[start][i] = count;
	  if(tree_flag) {
	    tree_flag = false;
	  } else {
	    check_cnt++;
	    check_one = check_cnt;
	    for(int j=0 ; j<NODENUM ; j++) {
	      check[check_one][j] = check[check_two][j];
	    }
	    check[check_two][next_box] = 1;
	    if(next_box != start) fill_tree(next_box, check_two, count, start);
	  }
	  front_box = front;
	  check_two = check_one;
	  next_box = i;
	}
      }
    }
    if(!tree_flag) {
      check[check_two][next_box] = 1;
      if(next_box != start) fill_tree(next_box, check_two, count, start);
    }
  }

  /* Data simple edge is Break */
  public void edge_break(int front, int count, int start) {
    count++;
    for(int i=0 ; i<NODENUM ; i++) {
      if(edge[front][i]==1 && edge[i][front]!=0 && edge_box[front][i]==0) {
	edge_box[front][i] = 1;
	if(edge[start][i] == 1 && count>1 && count<edge[start][start] && lat_box[start][i]==0) {
	  edge[start][i] = count;
	  mess = "BREAK "+nodename[start]+"_"+nodename[i];
	  returnStatus(QuikStatus.WARNING,mess);
	  input[start][i] = 0;
	}
	if(i != start) edge_break(i, count, start);
      }
    }
  }

  /* 矛盾解消 */
  public void unite() {
    for(int i=0 ; i<NODENUM ; i++){
      if( edge[i][i] == 0 ) continue;
      search_edge(i);
    }
  }

  /* Search Most Weak Edge */
  public void search_edge(int i) {
    boolean select_flag = false;
    dia_flag = false;
    start = 0;
    num_rimit = 0;
    weak_num = 0;

    int most_short = short_edge();
    reach = edge[most_short][most_short];
    clear_edge_box();
    clear_start_point();
    search_start(most_short);
    clear_edge_box();
    clear_end_point();
    search_end(most_short);
    start = i;
    start = unite_start_point(i);
    clear_edge_box();
    try {
      next_search(start, 1, start);
    } catch( StackOverflowError w ) {
      error_Mes(start);
    }
    clear_result_data();
    num_rimit = 0;
    weak_num = 0;
    select_flag = true;
    dia_flag = false;
    while(select_flag) {
      weak_num++;
      if(weak_num > 1) select_flag = false;
      for(int j=0 ; j<NODENUM ; j++) {
	for(int k=0 ; k<NODENUM ; k++) {
	  if(edge_box[j][k] != 0) {
	    if(edge_box[j][k] == weak_num) {
	      num_rimit++;
	      select_flag = false;
	      dia_flag = true;
	      result_data[num_rimit][0] = num_rimit;
	      result_data[num_rimit][1] = j;
	      result_data[num_rimit][2] = k;
	    }
	  }
	}
      }
    }
    if (dia_flag) {
      lattice_warning();
      remake();
      if(weak_num == 2) {
	search_edge(start);
	weak_num--;
      }
    } else {
      error_Mes(start);
    }
  }

  /* lattice_warning 追加 99/02/12 yokogawa */
  public void lattice_warning(){
    /* エラー表示を何度も表示するように変更。エラーを表示→ダイアログを開く */

    /* 新仕様 メッセージを通信系へ */
    show_data();

    /* 旧仕様 ダイアログの表示 杉原へ */
    //Que_Dialog dia = new Que_Dialog(this);
    //dia.show();

    int num = 0;
    dia_num = "";

    while (dia_num.equals("")) {
      System.out.println("." + dia_num);
      Thread.yield();
      try {
	Thread.sleep(1000);
      } catch(InterruptedException ie) {}
    }

    /*
    UI_Server.thesa_mess = "";
    try {
      UI_Server.thesa_mess.wait();
    } catch(InterruptedException ie) {}
    */

    num = Integer.parseInt(dia_num);

    /* 新仕様 */
    //int num = sellkilllattice(mess);
    /* 旧仕様 */
    //int num = Integer.parseInt(dia_num);

    correct_lattice(num);
  }
  
  public void getSelectnumber(String num) {
    dia_num = num;
    select_flag = false;
  }

  /* Show a Dialog Data 変更 99/01/29 yokogawa */
  public void show_data() {
    //MainWin.Message("You Select Killing Edge Number. ");
    mess = "";
    for(int i=0 ; i<NODENUM ; i++) {
      if(result_data[i][0] == 0) continue;
      mess += i + ". "+ nodename[result_data[i][1]] + " >= " + nodename[result_data[i][2]] + "\n";
    }
    //for( OM_SubRel tmp = OM_SubRel.subrel_list; tmp != null; tmp = tmp.next )
    //  mess += tmp.a1.name + " >= " + tmp.a2.name + "\n";
    
    returnStatus(QuikStatus.WARNING | QuikStatus.LATTICE_ERROR ,(Object)mess);
  }

  /* latticeの修正 99/01/30 yokogawa */
  public void correct_lattice(int num) {

    int input_data = num;
    if(0 < input_data && input_data <= num_rimit) {
      dia_flag = false;
      input[result_data[input_data][1]][result_data[input_data][2]] = 0;
      mess = "Killing " + nodename[result_data[input_data][1]] + " >= " + nodename[result_data[input_data][2]];
      returnStatus(QuikStatus.SUCCESS,(Object)mess);
    }
  }

  /* Search Most Short Edge */
  public int short_edge() {
    int short_box[] = new int[2];

    short_box[0] = 9999;
    short_box[1] = 9999;

    for(int i=0 ; i<NODENUM ; i++) {
      if(edge[i][i] == 0 || short_box[1] < edge[i][i]) continue;
      short_box[0] = i;
      short_box[1] = edge[i][i];
    }
    return short_box[0];
  }

  /* Search Start Point */
  public void search_start(int front) {
    for(int i=0 ; i<NODENUM ; i++) {
      if(edge[front][i]==1 && edge[i][front]!=0 && front!=i && edge[front][i]+edge[i][front] <= reach) {
	if(edge_box[front][i] == 0) {
	  edge_box[front][i] = 1;
	  start_point[front]++;
	  search_start(i);
	}
      }
    }
  }

  /* Search End Point */
  public void search_end(int front) {
    for(int i=0 ; i<NODENUM ; i++) {
      if(edge[i][front]==1 && edge[front][i]!=0 && front!=i && edge[front][i]+edge[i][front] <= reach) {
	if(edge_box[i][front] == 0) {
	  edge_box[i][front] = 1;
	  end_point[front]++;
	  search_end(i);
	}
      }
    }
  }

  /* Search unite_start_point */
  public int unite_start_point(int fast) {
    int unite_start[] = new int[2];
    unite_start[0] = 0;
    unite_start[1] = fast;

    for(int i=0 ; i<NODENUM ; i++) {
      if(start_point[i] <= 1) continue;
      for(int j=0 ; j<NODENUM ; j++) {
	if(end_point[j] <= 1) continue;
	if(unite_start[0] < edge[i][j] && edge[i][j] <= reach) {
	  unite_start[0] = edge[i][j];
	  unite_start[1] = i;
	}
      }
    }
    return unite_start[1];
  }

  /* Search Weak Edge Point */
  public void next_search(int front, int heavy, int banpei) {
    heavy = heavy + start_point[front] -1;

    for(int i=0 ; i<NODENUM ; i++) {
      if(edge[front][i]==1 && edge[i][front]!=0 && front!=i && edge[front][i]+edge[i][front] <= reach) {
	if(edge_box[front][i] < heavy) {
	  edge_box[front][i] = heavy;
	}
	if(i != banpei) {
	  next_search(i, heavy-end_point[i]+1, banpei);
	}
      }
    }
  }

  /* Remake fill_edges again */
  public void remake() {
    for(int i=0 ; i<NODENUM ; i++) {
      for(int j=0 ; j<NODENUM ; j++) {
	if(input[i][j] == 1) edge[i][j] = 1;
	else edge[i][j] = 0;
      }
    }
    fill_edges();
  }

  /* Output Error Message to MainWindow */
  public void error_Mes(int NG_data) {
    mess = "---WARNING---\n";
    mess += "So Many Bad Data!! Please Confirm Again!!\n";
    mess += nodename[NG_data] + " is a SPELL MISS ?";
    for(int i=0 ; i<NODENUM ; i++) {
      if(input[NG_data][i] != 0) mess += "? > " + nodename[NG_data] + " - " + nodename[i];
      if(input[i][NG_data] != 0) mess += "? > " + nodename[i] + " - " + nodename[NG_data];
      returnStatus(QuikStatus.ERROR,(Object)mess);
    }
    clear_all();
  }

  /* Data check clear */
  public void clear_check() {
    check_cnt = 0;
    for(int i=0 ; i<NODENUM ; i++) {
      for(int j=0 ; j<NODENUM ; j++) {
	check[i][j] = 0;
      }
    }
  }

  /* Data edge_box clear */
  public void clear_edge_box() {
    for(int i=0 ; i<NODENUM ; i++) {
      for(int j=0 ; j<NODENUM ; j++) {
	edge_box[i][j] = 0;
      }
    }
  }

  /* Data start_point clear */
  public void clear_start_point() {
    for(int i=0 ; i<NODENUM ; i++) start_point[i] = 0;
  }

  /* Data end_point clear */
  public void clear_end_point() {
    for(int i=0 ; i<NODENUM ; i++) end_point[i] = 0;
  }

  /* Data edge and input and nodename clear */
  public void clear_all() {
    for(int i=0 ; i<NODENUM ; i++) {
      nodeflag[i] = false;
      for(int j=0 ; j<NODENUM ; j++) {
	edge[i][j] = 0;
	input[i][j] = 0;
      }
    }
  }

  /* Data result_data clear */
  public void clear_result_data() {
    for(int i=0 ; i<NODENUM ; i++) {
      for(int j=0 ; j<3 ; j++) {
	result_data[i][j] = 0;
      }
    }
  }

  /* Debugga */
  public void debug() {
    System.out.println("=== DEBUG RESULT ===");
    for(int i=0 ; i<NODENUM ; i++) {
      for(int j=0 ; j<NODENUM ; j++) {
	if( edge[i][j] != 0 ) {
	  System.out.println(">> " + nodename[i] + " - " + nodename[j] + " > " + edge[i][j]);
	}
      }
    }
  }

  public void debug2() {
    System.out.println("=== DEBUG2 RESULT ===");
    for(int i=0 ; i<NODENUM ; i++) {
      if( end_point[i] != 0 ) {
	System.out.println(">> " + nodename[i] + " > " + end_point[i]);
      }
    }
  }

  public void solve() {
    fill_edges();
    unite();
    mess = "Edge operation complete.";
    //returnStatus(QuikStatus.SUCCESS,(Object)mess);
    System.out.println(mess);
    every_meet();
    thin_out_edges();
    mess = "Taking all meets complete.";
    //returnStatus(QuikStatus.SUCCESS,(Object)mess);
    System.out.println(mess);
  }     

  public void returnStatus(int id , Object obj){
    System.out.println(obj);
    Main.mine.commgr.returnStatus(id, obj);
  }

  public int get_edge(int y, int x){
    return edge[y][x];
  }
}
