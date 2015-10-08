// JDK1.1 version update   1998/07/15  Sugimoto
/* COPY PAINTTH.JAVA  --1997-- */

package java_quik.gui;
import java_quik.*;

import java.awt.*;
import java.awt.event.*;

class LAT_Node {
  double x;
  double y;

  double dx;
  double dy;

  boolean fixed;
  String name;
}

class LAT_Edge {
  int from;
  int to;

  double len;
}

public class LAT_Paintth extends Canvas
implements Runnable, MouseListener, MouseMotionListener  {
  LAT_Thesa th;
  Dimension minSize;
  int width, height;

  int nnodes, nedges;
  LAT_Node nodes[] = new LAT_Node[100];
  LAT_Edge edges[] = new LAT_Edge[200];
  final int EDGELEN = 48;

  Thread relaxer;

  LAT_Node pick;

  public LAT_Paintth(int w, int h) {
    width = w;
    height = h;
    minSize = new Dimension(w, h);
    th = null;

    nnodes = nedges = 0;
    pick = null;
  }

  public Dimension getPreferredSize() {
    return getMinimumSize();
  }

  public synchronized Dimension getMinimumSize() {
    return minSize;
  }

  public int node_number(String name) {
    int i;

    for (i = 2; i < nnodes; ++i) {
      if (0 == name.compareTo(nodes[i].name)) return i;
    }
    return -1;
  }

public void setth(LAT_Thesa thesaurus) {
  th = thesaurus;	

  nnodes = 2;
  nedges = 0;

  LAT_Node top = new LAT_Node();
  top.name = "TOP";
  top.x = width/2-15;  /* put TOP */
  top.y = 15;
  nodes[0] = top;

  LAT_Node bot = new LAT_Node();
  bot.name = "BOTTOM";
  bot.x = width/2-15;         /* put BOTTOM */
  bot.y = height-15;
  nodes[1] = bot;

  for (int i = 0; i < th.NODENUM; ++i) {
    if ( th.nodeflag[i] ) {
      LAT_Node n = new LAT_Node();

      n.name = th.nodename[i];
      n.x = (width-2) * Math.random();
      n.y = (height-2) * Math.random();
      n.dx = n.dy = 0;
      n.fixed = false;
      nodes[nnodes++] = n;
    }
  }

  for (int i = 0; i < th.NODENUM; ++i) {
    boolean flag = false;
    if ( !th.nodeflag[i] )
      continue;
    for (int j = 0; j < th.NODENUM; ++j) {
      if (!th.nodeflag[j])
        continue;
      if ( 1 == th.edge[i][j] ) {
        LAT_Edge e = new LAT_Edge();

        e.from = node_number(th.nodename[j]);
        e.to = node_number(th.nodename[i]);
        e.len = EDGELEN;
        edges[nedges++] = e;
        flag = true;
      }
    }
    if (!flag) {
      LAT_Edge e = new LAT_Edge();

      e.from = 1;
      e.to = node_number(th.nodename[i]);
      e.len = EDGELEN;
      edges[nedges++] = e;
    }
  }

  for (int i = 0; i < th.NODENUM; ++i) {
    boolean flag = false;
    if ( !th.nodeflag[i] )
      continue;
    for (int j = 0; j < th.NODENUM; ++j) {
      if ( !th.nodeflag[j] )
        continue;
      if ( 1 == th.edge[j][i] ) {
        flag = true;
      }
    }
    if ( !flag ) {
      LAT_Edge e = new LAT_Edge();

      e.from = node_number(th.nodename[i]);
      e.to = 0;
      e.len = EDGELEN;
      edges[nedges++] = e;
    }
  }
}

  public void start() {
    relaxer = new Thread(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    relaxer.start();
  }

  public void stop() {
    relaxer.stop();
    removeMouseListener(this);
    removeMouseMotionListener(this);
  }

  public void run() {
    while (true) {
      relax();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  synchronized void relax() {
    for (int i = 0 ; i < nedges ; i++) {
      LAT_Edge e = edges[i];
      double vx = nodes[e.to].x - nodes[e.from].x;
      double vy = nodes[e.to].y - nodes[e.from].y;
      double len = Math.sqrt(vx * vx + vy * vy);
      double f = (edges[i].len - len) / (len * 3);
      double dx = f * vx;
      double dy = f * vy;

      nodes[e.to].dx += dx;
      nodes[e.to].dy += dy;
      nodes[e.from].dx += -dx;
      nodes[e.from].dy += -dy;
    }

    for (int i = 0 ; i < nnodes ; i++) {
      LAT_Node n1 = nodes[i];
      double dx = 0;
      double dy = 0;

      for (int j = 0 ; j < nnodes ; j++) {
        if (i == j)
          continue;

        LAT_Node n2 = nodes[j];
        double vx = n1.x - n2.x;
        double vy = n1.y - n2.y;
        double len = vx * vx + vy * vy;

        if (len < 100*100) {
          dx += vx / len;
          dy += vy / len;
        }
      }
      double dlen = dx * dx + dy * dy;

      if (dlen > 0) {
//        dlen = Math.sqrt(dlen) / 2;
        dlen = Math.sqrt(dlen) / 8;
        n1.dx += dx / dlen;
        n1.dy += dy / dlen;
      }
    }

    for (int i = 2 ; i < nnodes ; i++) {
      LAT_Node n = nodes[i];

      if ( !n.fixed ) {
        n.x += Math.max(-5, Math.min(5, n.dx));
        n.y += Math.max(-5, Math.min(5, n.dy));

        if (n.x < 0) {
          n.x = 0;
        } else if (n.x > width) {
          n.x = width;
        }

        if (n.y < 0) {
          n.y = 0;
        } else if (n.y > height) {
          n.y = height;
        }
      }
      n.dx /= 2;
      n.dy /= 2;
    }
    repaint();
  }

  public synchronized void mousePressed(MouseEvent evt) {
    int x = evt.getX(),
        y = evt.getY();
    double bestdist = -1;

    for (int i = 2 ; i < nnodes ; i++) {
      LAT_Node n = nodes[i];
      double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);

      if (dist < bestdist || -1 == bestdist) {
        pick = n;
        bestdist = dist;
      }
    }

    if (null == pick)
      return;

    pick.fixed = true;
    pick.x = x;
    pick.y = y;
    repaint();
  }

  public synchronized void mouseDragged(MouseEvent evt) {
    int x = evt.getX(),
        y = evt.getY();

    if (null == pick) 
      return;

    pick.x = x;
    pick.y = y;
    repaint();
  }

  public synchronized void mouseReleased(MouseEvent evt) {
    int x = evt.getX(),
        y = evt.getY();

    if (null == pick)
      return;

    pick.x = x;
    pick.y = y;
    pick.fixed = false;
    pick = null;

    repaint();
  }

  public synchronized void mouseClicked(MouseEvent evt) {}
  public synchronized void mouseEntered(MouseEvent evt) {}
  public synchronized void mouseExited(MouseEvent evt) {}
  public synchronized void mouseMoved(MouseEvent evt) {}

  public void paint(Graphics g) {
    g.drawRect(0, 0, width - 1, height - 1);

    for (int i = 0; i < nedges; ++i) {
      g.setColor(Color.white);
      g.drawLine((int)nodes[edges[i].from].x, (int)nodes[edges[i].from].y,
                 (int)nodes[edges[i].to].x, (int)nodes[edges[i].to].y);
    }

    for (int i = 0; i < nnodes; ++i) {
      g.setColor(Color.black);
      g.drawString(nodes[i].name, (int)nodes[i].x, (int)nodes[i].y);
    }
  }
}
