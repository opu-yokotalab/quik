package java_quik;

public class OM_Action_Type {    //��ǥ����Υ�����

  public OM_Action_Type(int integer)
    {
      type = integer;
    }

  public int type;

  public static final int AT_Out   = 1;    //�и�
  public static final int AT_Move  = 2;    //��ư
  public static final int AT_Speak = 3;    //ȯ��
  public static final int AT_Light = 4;    //�饤�ƥ���
  public static final int AT_Del   = 5;    //�õ�
}
