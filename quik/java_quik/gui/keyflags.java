// 1998/10/11 Sugihara
// Key Flags

package java_quik.gui;
import java_quik.*;

import java.awt.event.KeyEvent;

public class KeyFlags {
  // 初期化
  public static void init() {
    left = right = up = down = enter = space = false;
  }

  // キーが押された時のフラグ立て
  public static void keyPressedCheck(int key) {
    switch (key) {
    case KeyEvent.VK_LEFT:
      left = true;
      break;
    case KeyEvent.VK_RIGHT:
      right = true;
      break;
    case KeyEvent.VK_UP:
      up = true;
      break;
    case KeyEvent.VK_DOWN:
      down = true;
      break;
    case KeyEvent.VK_ENTER:
      enter = true;
      break;
    case KeyEvent.VK_SPACE:
      space = true;
      break;
    default:
      break;
    }
  }

  // キーが離された時のフラグ下げ
  public static void keyReleasedCheck(int key) {
    switch (key) {
    case KeyEvent.VK_LEFT:
      left = false;
      break;
    case KeyEvent.VK_RIGHT:
      right = false;
      break;
    case KeyEvent.VK_UP:
      up = false;
      break;
    case KeyEvent.VK_DOWN:
      down = false;
      break;
    case KeyEvent.VK_ENTER:
      enter = false;
      break;
    case KeyEvent.VK_SPACE:
      space = false;
      break;
    default:
      break;
    }
  }

  // それぞれキーの状態フラグ
  public static boolean left;
  public static boolean right;
  public static boolean up;
  public static boolean down;
  public static boolean enter;
  public static boolean space;
}
