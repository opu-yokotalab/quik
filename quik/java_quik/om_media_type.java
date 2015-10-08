package java_quik;

public class OM_Media_Type {    //メディアのタイプ

  public OM_Media_Type(int integer)
    {
      type = integer;
    }

  public int type;

  public static final int MT_Pic = 1;     //静止画
  public static final int MT_Ani = 2;     //動画
  public static final int MT_3D = 3;     //オブジェクト
  public static final int MT_Text = 4;    //テキスト
  public static final int MT_Sound = 5;   //サウンドファイル
  public static final int MT_OtherM = 6;   //サウンドファイル
}
