package java_quik;

public class OM_Action_Type {    //メディアのタイプ

  public OM_Action_Type(int integer)
    {
      type = integer;
    }

  public int type;

  public static final int AT_Out   = 1;    //出現
  public static final int AT_Move  = 2;    //移動
  public static final int AT_Speak = 3;    //発話
  public static final int AT_Light = 4;    //ライティング
  public static final int AT_Del   = 5;    //消去
}
