// QuikLabel
// 1998/01/14 Sugihara

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;

// Label $B@_Dj(B
public class QuikLabel extends Canvas {
  public void paint(Graphics g) {
    drawStringCenter(g, "QUIK version Ver.1.0.  CopyRight (C) 1998 Yokota Lab.", 40);
    g.setFont(new Font("Dialog", Font.ITALIC, 21));
    drawStringCenter(g, "QUIK", 20);
/*
    // $B0J2<L5BLItJ,(B(^^;
    int x = getSize().width / 2 - g.getFontMetrics().stringWidth("QUIK") / 2;
    g.setColor(new Color(0, 0, 0x60));
    g.drawString("QUI", x - 1, 20);
    g.setColor(new Color(0, 0, 0xb0));
    g.drawString("QU", x - 2, 20);
    g.setColor(new Color(0, 0, 0xff));
    g.drawString("Q", x - 3, 20);
*/
  }

  // $BCf1{$KG[CV$5$l$k$h$&$KJ8;z$rIA2h(B
  public void drawStringCenter(Graphics g, String s, int h) {
    g.drawString(s, getSize().width / 2 -
		 g.getFontMetrics().stringWidth(s) / 2, h);
  }
}
