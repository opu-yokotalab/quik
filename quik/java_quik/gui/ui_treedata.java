// User Interface
// Tree Data
// Programed By Sugihara
// 
// JDK1.1
// From 1998/01/25
// Last Update 1998/01/25

package java_quik.gui;
import java_quik.*;

import java.awt.*;

public class UI_TreeData {
  // コンストラクタ
  public UI_TreeData(UI_MediTree ui_tree, String str) {
    parent = ui_tree;
    name = str;
    up_num = 0;
    down_num = 0;
    next = null;
    down = null;
    var = 0;
    state = false;

    name_width = parent.bg.getFontMetrics().stringWidth(name);
  }

  // 葉の追加
  public UI_TreeData add(String str) {
    UI_TreeData new_td = new UI_TreeData(parent, str);
    new_td.up_num = up_num + 1;
    down_num++;

    // 最大横幅の更新
    parent.resetWidth(new_td);

    // くっつける
    UI_TreeData td = down;
    if (td == null) down = new_td;
    else {
      while (td.next != null) td = td.next;
      td.next = new_td;
    }

    parent.resetIndex();
    return new_td;
  }

  // インスタンス変数
  public UI_MediTree parent; // 親
  public String name; // 葉の名前
  public int up_num; // 深さ
  public int down_num; // 下につながっている葉の数
  public UI_TreeData next; // 次につながっている葉
  public UI_TreeData down; // 下につながっている葉の頭

  public int name_width; // 葉の名前の長さ
  public int var; // 葉の種類(variety)
  public boolean state; // 葉の状態

  // クラス変数
  // Nothing
}
