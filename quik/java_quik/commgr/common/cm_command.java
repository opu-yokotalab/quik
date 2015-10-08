package java_quik.commgr.common;
import java_quik.*;
import java_quik.gui.*;
import java_quik.commgr.*;

import java.util.*;

//  コマンドの処理
public class CM_command {
  String CMD1[] = { "startup", "activate", "open",
                   "close", "shutdown", "abort"},
         CMD2[] = { "start", "continue", "save", "gettree" };
  String token[];

  //  入力された文字列がコマンドであるかどうか判定する
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

  //  入力された文字列をトークンに切り分ける
  public String[] divToken( String text) {
    StringTokenizer st = new StringTokenizer( text, " " );
    String tokens[];
    int i = 0;

    tokens = new String[st.countTokens()];
    while( st.hasMoreTokens())
      tokens[i++] = st.nextToken();

    return tokens;
  }

  // トークンのどこにコマンドがあるか調べる
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
