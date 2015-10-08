package java_quik.commgr.common;
import java_quik.*;
import java_quik.gui.*;
import java_quik.commgr.*;

import java.util.*;

//  ���ޥ�ɤν���
public class CM_command {
  String CMD1[] = { "startup", "activate", "open",
                   "close", "shutdown", "abort"},
         CMD2[] = { "start", "continue", "save", "gettree" };
  String token[];

  //  ���Ϥ��줿ʸ���󤬥��ޥ�ɤǤ��뤫�ɤ���Ƚ�ꤹ��
  public int selCommand( String text ) {
    int i;

    if ( text.indexOf("&") != -1 ) {
      for( i = 0; i < CMD1.length; i++)
        if ( text.indexOf("&" + CMD1[i]) != -1)
          break;

      token = divToken(text);
      if ( i >= CMD1.length )
        return -1;
      else
        return i + 1;
    } else {
      for ( i = 0; i < CMD2.length; i++)
        if ( text.toLowerCase().indexOf("m_" + CMD2[i]) != -1)
          break;
      if ( i >= CMD2.length )
        return -1;
      else
        return i + 7;
    }
  }

  //  ���Ϥ��줿ʸ�����ȡ�������ڤ�ʬ����
  public String[] divToken( String text) {
    StringTokenizer st = new StringTokenizer( text, " " );
    String tokens[];
    int i = 0;

    tokens = new String[st.countTokens()];
    while( st.hasMoreTokens())
      tokens[i++] = st.nextToken();

    return tokens;
  }

  // �ȡ�����Τɤ��˥��ޥ�ɤ����뤫Ĵ�٤�
  public int findCommand( String command, String token[]) {
    int i = 0;

    for ( i = 0; i < token.length; i++)
      if ( command.equals(token[i]))
        break;

    if ( i == token.length )
      return -1;
    else
      return i;
  }
}
