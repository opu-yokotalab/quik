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
  // ���󥹥ȥ饯��
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

  // �դ��ɲ�
  public UI_TreeData add(String str) {
    UI_TreeData new_td = new UI_TreeData(parent, str);
    new_td.up_num = up_num + 1;
    down_num++;

    // ���粣���ι���
    parent.resetWidth(new_td);

    // ���äĤ���
    UI_TreeData td = down;
    if (td == null) down = new_td;
    else {
      while (td.next != null) td = td.next;
      td.next = new_td;
    }

    parent.resetIndex();
    return new_td;
  }

  // ���󥹥����ѿ�
  public UI_MediTree parent; // ��
  public String name; // �դ�̾��
  public int up_num; // ����
  public int down_num; // ���ˤĤʤ��äƤ����դο�
  public UI_TreeData next; // ���ˤĤʤ��äƤ�����
  public UI_TreeData down; // ���ˤĤʤ��äƤ����դ�Ƭ

  public int name_width; // �դ�̾����Ĺ��
  public int var; // �դμ���(variety)
  public boolean state; // �դξ���

  // ���饹�ѿ�
  // Nothing
}
