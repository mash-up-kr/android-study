```
스터디 진행 : 2016년 2월 13일, 김동희
최조 작성자 : 김동희
최초 작성일 : 2016년 2월 11일
```

#  1강 - 처음 만드는 안드로이드 애플리케이션

## 들어가면서

**수정 요망**
안드로이드 개발팀의 두번재 스터디인 이번 시간에는 안드로이드 OS의 구성과(이걸 해야하나..... 고민 중, 그림그리기가 ㄷㄷ;) .......
프로젝트 구조 설명(...이것도 해야하는가... 심히 고민이 된다.)
기본적인 View, ViewGroup 소개, 종류, 속성
리스너를 구현하는 법, 종류(?)
에 대해 다룬다.


## 앱 기본사항
앱은 하나의 Activity와 하나의 layout으로 구성된다.

Activity는 안드로이드 SDK의 Activity의 인스턴스(객체)이다. Activity는 화면을 통해 사용자가 작업할 수 있게 해준다.

layout은 사용자 인터페이스 객체들과 그것들의 화면 위치를 정의한다. layout은 XML로 작성된 정의들로 구성된다.
각 정의는 버튼이나 텍스트처럼 화면에 나타나는 하나의 객체를 생성하는데 사용된다.

Activity --> 화면 <-- layout.xml
<위에꺼 그림으로 표현>


## 안드로이드 프로젝트 생성하기
**지난 시간 내용이므로 나중에 뺀다.**
domain에 `mash-up.co.kr` 작성

패키지이름은 다른 장치나 Google Play 등에서 애플리케이션을 식별값으로 사용하므로 고유값 정의.


## 사용자 인터페이스의 레이아웃 만들기

### 뷰 계층 구조

<뷰 계층 구조 그림 들어감>

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    tools:context="kr.co.mash_up.firstapp.MainActivity">

     <Button
        android:id="@+id/button_show_toast"
        android:text="Toast 출력"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</LinearLayout>
```

widget: 텍스트나 그래픽을 보여줄 수 있고, 사용자와 상호 동작할 수 있으며, 화면에 다른 위젯을 배열할 수도 있다.
ex) 버튼, 텍스트 입력 컨트롤, 체크 받스 등

안드로이드 SDK에는 많은 위젯들이 포함되어 있으며, 우리가 원하는 사용자 인터페이스(화면에 보여지는 모습과 앱과의 상호 동작)를 만들기 위해 그것들을 구성할 수 있다.
안드로이드에서 모든 위젯은 View 클래스의 인스턴스이거나, View의 서브 클래스(ex. Button, TextView 등) 중 하나이다.

LinearLayout은 ViewGroup이라는 이름의 View 서브 클래스로부터 상속 받는다.
ViewGroup은 다른 위젯들을 포함하고 배열하는 위젯


LinearLayout은 위젯들을 수직(vertical), 수평(horizontal)으로 배열하고 싶을 때 사용하는 레이아웃
RelativeLayout은 위젯들을 상대적으로 배치
FrameLayout은 ....?

위젯이 ViewGroup에 포함될 때 그 위젯은 그 ViewGroup의 자식(child)이라고 한다.

여기서는 루트 LinearLayout에 1개의 Button을 자식으로 가진다.




### 위젯 속성

#### android:id
해당 위젯을 식별할 수 있는 id
모든 위젯이 필요로 하지않는다. -> 코드와 상호동작할 것만 필요

#####@+id와 @id의 차이

* @+id - 리소스 **생성**
* @id -  기존에 생성된 리소스 **참조**

#### android:layout_width와 android:layout_height
모든 타입의 위젯에서 필요로 한다.
wrap_content - 자신이 갖는 콘텐츠에 필요한 크기로 보여질 수 있다.
match_parent - 자신의 부모만큼 크게 보여질 수 있다.
수치값 - 해당 수치 만큼의 크기로 보여진다. ex. 200dp

#### android:orientation
LinearLayout의 자식들이 어느 방향으로 배치될지 결정
vertical - 수직
horizontal - 수평

#### android:text
보여줄 텍스트를 나타낸다.
문자열 리터럴이나 리소스에 대한 참조(reference) 사용 가능

*문자열 리소스(string resource) - strings.xml이라는 별도의 XML파일에 정의된 문자열

```xml
<resources>
    <string name="app_name">FirstApp</string>
    <string name="hello">Hello!!</string>
</resources>
```
보통 strings.xml이지만 원하는 이름으로 만들 수 있다.
res/values/ 아래에 위치해 있고, <resoruces>라는 루트 요소를 가지며, 자식들로 문자열 요소가 포함되어 있다면 가능.
*문자열 리소스를 사용하는 것을 권장 -> 지역화(localization) 때문에





<추가>
**padding, margin 등 뷰의 기본 속성을 좀 더 다룬다.**





<추가>
**자주쓰는 기본 위젯 TextView ,Button, ImageView 등에 대해 좀더 소개**
**자주쓰는 ViewGroup. Frame, Relative, Linear에 대해 좀더 다룬다.**








## 레이아웃 XML에서 뷰 객체로
layout.xml에 정의된 XML 요소들이 어떻게 View객체로 되는 것일까?

```Java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

현재 MainActivity의 onCreate(Bundle)는 액티비티의 인스턴스가 생성될 때 자동으로 호출된다.
그리고 액티비티가 생성되면 사용자 인터페이스를 처리할 필요가 있다.
액티비티가 사용자 인터페이스를 처리하려면 다음의 메소드를 호출해야 한다.

```Java
setContentView(Rayout resId);
ex) setContentView(R.layout.activity_main);
```

이 메소드는 레이아웃을 뷰 객체로 바꾸어 화면에 나타낸다. -> inflate라고 한다.
이때 레이아웃 파일에 있는 각 위젯은 자신의 속성들이 정의된 대로 인스턴스로 생성된다.
이 메소드를 호출할 때 레이아웃의 리소스ID를 인자로 전달하여 처리할 레이아웃을 지정



<추가> 
inflate가 뭔지에 대해 추가







###리소스와 리소스 ID
레이아웃은 리소스(resource)다.
리소스는 애플리케이션의 일부이며, 코드가 아닌 이미지 파일이나 오디오 파일 및 XML 파일과 같은 것들

프로젝트의 리소스들은 res 디렉토리 아래에 존재한다.
레이아웃 리소스의 위치 - res/layout/
이미지 리소스의 위치 - res/drawable/
문자열 리소스의 위치 - res/values/

코드에서 리소스를 액세스하려면 그것의 리소스 ID를 사용
ex) 레이아웃 리소스 ID - R.layout.activity_main

리소스 ID는 안드로이드 빌드 프로세스에서 자동으로 생성되어 R.java파일에 정의 -> **임의로 수정하면 안된다.**
module명/build/generated/source/r/debug/package명/ 아래에 존재
<그림 들어감>

## 위젯을 코드와 연결하기

### 연결하기위한 단계
1. 생성되는 View객체들에 대한 참조를 얻는다.
2. 사용자의 액션에 응답하기 위해 그 객체들에 대한 리스너(listener)를 설정한다.

#### 위젯의 참조 얻기

다음의 Activity 메소드를 이용해 View 객체로 inflate되는 위젯의 참조를 얻을 수 있다.

```Java
public View findViewById(int id)
```

위젯의 리소스 ID를 인자로 받아 그 위젯의 View객체를 반환

```Java
public class MainActivity extends AppCompatActivity {

    Button btnShowToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		//View 객체가 반환되기 때문에 Button으로 casting
        btnShowToast = (Button)findViewById(R.id.button_show_toast);
    }
}
```

#### 리스너 설정하기
안드로이드 애플리케이션은 이벤트 기반으로 구동(event-driven).
명령행에서 실행된는 프로그램이나 스크립트와 달리 이벤트 기반으로 구동되는 애플리케이션들은 시작된 후 이벤트의 발생을(user, os, 다른 application) 기다린다.
ex) 사용자가 버튼을 누르는 경우

애플리케이션에서 이벤트를 기다리는 것을 가리켜 이벤트를 **"리스닝한다"**고 한다.
이벤트에 응답하기 위해 생성하는 객체를 리스너(listener)라 한다.
리스너는 해당 이벤트의 **리스너 인터페이스**를 구현하는 객체

안드로이드 SDK에는 다양한 이벤트들의 리스너가 존재
* OnClickListener
* OnLongClickListener
* OnTouchListener
* OnScrollChangeListener 등...

*직접 인터페이스를 만들어 구현해도 된다.

```Java
btnShowToast = (Button)findViewById(R.id.button_show_toast);
btnShowToast.setOnClickListener(new View.OnClickListener() {
    @Override
	public void onClick(View v) {
    //이벤트를 받았을 때 하고 싶은 동작 구현
    }
});
```


<추가>
리스너를 설정하는 방법에 대해, Activity에 implements, 내부 클래스 만들어서 구현 등등 자주쓰는 3가지? 정도



##### 익명의 내부 클래스 사용하기
이 리스너는 익명의 내부 클래스(anonymous inner class)로 구현

#### 토스트 만들기
버튼을 누르면 토스트(Toast)라고 하는 팝업 메시지를 출력

Toast - 사용자에게 뭔가를 알려주지만 사용자의 어떤 입력이나 액션도 요구하지 않는 짧은 메시지

```Java
public static Toast makeText(Context context, int resId, int duration)
public static Toast makeText(Context context, CharSequence text, int duration)
```
context - 일반적으로 Activity의 인스턴스(Activity는 Context의 서브 클래스)
text - 토스트가 보여주는 문자열
resId - 토스트가 보여주는 문자열 리소스 ID
duration - 얼마나 보여줄지 결정하는 상수(LENGTH_LONG, LENGTH_SHORT)

```Java
btnShowToast.setOnClickListener(new View.OnClickListener() {
	@Override
    public void onClick(View v) {
   		Toast.makeText(MainActivity.this, "Hello Android", Toast.LENGTH_LONG).show();
    }
});
```


## 에뮬레이터에서 실행시키기
**이전 시간에 포함되는 내용이면 삭제**


## 안드로이드 앱 빌드 절차
**이전 시간에 포함되는 내용이면 삭제**





<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="크리에이티브 커먼즈 라이선스" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />이 저작물은 <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">크리에이티브 커먼즈 저작자표시 4.0 국제 라이선스</a>에 따라 이용할 수 있습니다.
