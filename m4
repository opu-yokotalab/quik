&program;;
&rule;;
#main<=stream(#scene);;
#scene1/[comment=hogehoge] <= #background1(@outbg);
	#�������1(@����1);
	#���֥�������(@tojo1)/[play_time=5];
	#��������(@tojo2)/[play_time=5];
	#���֥�������(@��ư1)/[play_time=10];
	#�������2(@����2);
	#��������(@��ư2)/[play_time=10];
	#����(@ȯ��)/[play_time=6];
	#��������(@�õ�);
	#���֥�������(@�õ�);;
#scene2 <= #����(@outbg);
	#��������(@tojo2)/[play_time=5];
	#����(@tojo2),
	#�Ҳ�(@ȯ��)/[play_time=6];
	#��������(@�õ�);;
#background1/[type=3, 
url=file:C:/jdk122/Prog/QUIK/java_quik/viewer/Stage1.class,
comment = hogehoge2];;
#����/[type=3, url=file:C:/jdk122/Prog/QUIK/java_quik/Media/nisimon2.wrl];;
#��������/[type=1, url=file:C:/jdk122/Prog/QUIK/java_quik/Media/isaseri.gif];;
#����/[type=1, url=file:C:/jdk122/Prog/QUIK/java_quik/Media/ura.gif];;
#����/[type=4, str=�����˭���ʹ�Ť���򤷤Ƥ��벹��Τ��Ȥ򡢤��Ҥ�������������];;
#�Ҳ�/[type=4, str=��������������3D�Ǥ���];;
#���֥�������/[type=3, url=file:C:/jdk122/Prog/QUIK/java_quik/Media/SimpleSphere.class];;
#�������1/[type=5, url=file:C:/jdk122/Prog/QUIK/java_quik/Media/walk.wav];;
#�������2/[type=5, url=file:C:/jdk122/Prog/QUIK/java_quik/Media/drip.wav];;
@outbg/[action_type=1];;
@tojo1/[action_type=1, point={(0.0, 0.0, 0.0)}, comment=hogehoge3, scale=8.0];;
@tojo2/[action_type=1, point={(2.0, 0.0, 10.0)}, scale=8.0];;
@�о�3/[action_type=1, point={(0.0, 11.5, 1.0)}, scale=8.0];;
@�о�4/[action_type=1, point={(3.0, 11.5, 1.0)}, scale=4.0];;
@ȯ��/[action_type=3, pitch=3.0, font=male, speed=3.0];;
@�õ�/[action_type=5];;
@����/[action_type=1];;
@����1/[action_type=1, volume=1.0];;
@����2/[action_type=1, volume=2.0];;
@��ư1/[action_type=2, point={(0.0,0.0,0.0),(0.5,0.5,0.0),(0.0,1.0,0.5),(-0.5,1.5,0.0),(0.0,2.0,-0.5),(0.5,3.0,0.0),(0.0,3.5,0.5),(-0.5,4.0,0.0),(0.0,4.5,-0.5),(0.5,5.0,0.0),(0.0,5.5,0.5),(-0.5,6.0,0.0),(0.0,6