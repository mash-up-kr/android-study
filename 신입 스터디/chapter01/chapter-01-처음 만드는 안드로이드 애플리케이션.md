```
스터디 진행 : 2016년 2월 13일, 김동희
최조 작성자 : 김동희
최초 작성일 : 2016년 2월 11일
마지막 수정 : 2016년 2월 13일, 김동희
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

Activity는 안드로이드 SDK의 Activity의 인스턴스(객체)며, 화면을 통해 사용자와 상호작용할 수 있게 해준다.

Layout은 사용자 인터페이스(User Interface: UI) 객체들과 그것들의 화면 위치를 정의한다. Layout은 XML로 작성된 정의들로 구성되며,
각 정의는 버튼이나 텍스트처럼 화면에 나타나는 하나의 객체를 생성하는데 사용된다.

Activity --> 화면 <-- layout.xml
<위에꺼 그림으로 표현>


## 안드로이드 프로젝트 생성하기
**지난 시간 내용이므로 나중에 뺀다.**
domain에 `mash-up.co.kr` 작성

패키지이름은 다른 장치나 Google Play 등에서 애플리케이션을 식별값으로 사용하므로 고유값 정의.


## 사용자 인터페이스의 레이아웃 만들기

### 뷰 계층 구조

![ViewHierarchy][ViewHierarchy.png]

[ViewHierarchy.png]:https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/ViewHierarchy.png

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_centerInParent="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <ImageView
            android:layout_gravity="center_horizontal"
            android:src="@mipmap/ic_launcher"
            android:scaleType="fitXY"
            android:layout_width="100dp"
            android:layout_height="100dp"/>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <EditText
                android:hint="ID"
                android:layout_width="200dp"
                android:layout_height="wrap_content"/>
        </LinearLayout>

        <EditText
            android:hint="Password"
            android:layout_width="200dp"
            android:layout_height="wrap_content"/>

        <Button
            android:id="@+id/button_signin"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="로그인"/>
    </LinearLayout>
</RelativeLayout>
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


## 최상위 뷰(View)의 속성

#### id
* 뷰를 칭하는 심볼을 정의, 뷰 참조시 이용
* 해당 뷰를 식별할 수 있는 심볼, 뷰 참조시 이용
* 형식: @[+]id/ID
 * @: id를 리소스(R.java)에 추가 or 참조한다는 뜻
 * +: 새로운 id 정의할 때 사용
 * id: 예약어
 * ID: /뒤에 식별자로 지정
* xml에 id를 지정하면 R.java에 정의됨
* 코드에서 View findViewById(int id)로 참조
* 모든 뷰가 필요로 하지않는다. -> 코드와 상호동작할 것만 필요

#####@+id와 @id의 차이

* @+id - 리소스 **생성**
* @id -  기존에 생성된 리소스 **참조**

#### layout_width와 layout_height
* 뷰의 폭과 높이를 지정
* 모든 타입의 뷰에서 필요로 한다.
* 속성값
 * wrap_content - 내용물 크기만큼 채움
 * match_parent - 부모 크기에 맞춤
 * 상수값 - 값만큼 크기 고정.
   * 단위: px, in, mm, pt, dp(dip), sp(sip)
ex. 200dp
* 주위 다른 뷰들의 크기에 영향을 받음

##### lay_out_으로 시작하는 뷰 속성의 특징

* 부모 뷰그룹에 자신의 배치 정보를 전달해 주는 속성
 * 영역을 배정받는 자식 뷰는 해당 공간을 어떤 제약 없이 사용 가능

ex) gravity 속성
```xml
android:layout_gravity="center"  //자신의 위치
android:gravity="center" 	    //내용물의 위치
```

#### background

* 뷰의 배경을 어떻게 채울 것인지 지정
* 특별한 지정이 없으면 기본 모양으로 지정
* 보통 #RRGGBB형태로 RGB 색상값 지정
 * 혹은 이미지 리소스

```xml
android:background="#ffffff"				//RGB 색상값
android:background="@drawable/ic_launcher"  //이미지 리소스
```

#### padding
* 뷰와 내용물 간의 간격(안쪽 여백) 지정
* 4방향에 대한 여백을 따로 지정 가능

```xml
android:padding="30dp"       //4방향
android:paddingTop="8dp"     //상
android:paddingBottom="8dp"  //하
android:paddingLeft="16dp"   //좌
android:paddingRight="16dp"  //우
```

#### margin

* 뷰의 바깥 여백 지정
* padding과 마찬가지로 4방향에 대해 따로 지정 가능

```xml
android:layout_margin="20dp"       //4방향
android:layout_marginTop="8dp"     //상
android:layout_marginBottom="8dp"  //하
android:layout_marginLeft="16dp"   //좌
android:layout_marginRight="16dp"  //우
```

#### visibility
* 뷰의 표시 유무를 지정
 * visible: 보이는 상태
 * invisible: 자리를 차지하며 숨긴 상태
 * gone: 자리를 차지하지 않으며 숨긴 상태

#### enabled
* 해당 뷰의 활성화 여부 결정(default: true)


## 기본 위젯

### 위젯(Widget)

* 화면을 구성하는 최소 단위
* 눈에 보이며 사용자와 상호작용함
 * 정보 표시 및 사용자 입력 컴포넌트
 * 각기 자신만의 모습과 특징을 가짐
* android.widget 패키지
 * TextView, EditText, Button, CheckBox, RadioButton, ImageView 등

### TextView
* 텍스트를 출력하는 위젯
* 사용자 입력은 받아 들이지 않음

#### text
* 출력할 문자열 지정
* 문자열 리터럴이나 리소스에 대한 참조(reference) 사용 가능

> ##### 문자열 리소스(string resource) - strings.xml이라는 별도의 XML파일에 정의된 문자열
>
```xml
<resources>
    <string name="app_name">FirstApp</string>
    <string name="hello">Hello!!</string>
</resources>
```
보통 strings.xml이지만 원하는 이름으로 만들 수 있다.
res/values/ 아래에 위치해 있고, <resoruces>라는 루트 요소를 가지며, 자식들로 문자열 요소가 포함되어 있다면 가능.
*문자열 리소스를 사용하는 것을 권장 -> 지역화(localization) 때문에

#### textColor
* 문자열의 색상 지정
 * `#AARRGGBB` or `#RRGGBB`형식

#### textSize
* 글꼴의 크기 지정
 * sp, dp 등의 단위를 같이 지정

#### textStyle
* 글꼴의 스타일 지정
 * normal, bold, italic
   * 2개 이상의 스타일은 `|`로 묶어서 지정: `bold|italic`

#### typeface
* 글꼴의 유형 지정
 * noraml, sans, serif, monospace 중 하나 지정

#### singleLine
* 텍스트를 강제로 한줄에 출력(default: false)

#### gravity
* TextView 영역 내에서 텍스트 내용의 중력 방향 결정

#### ellipsize
* singleLine과 조합해 사용, 한 줄의 초과 내용 처리
* start: 문장의 앞 부분을 생략, 생략기호 `...`을 배치
* middle: 문장의 중간 생략, 생략기호 `...`을 배치
* end: 기본값, 끝 부분 생략, 생략기호 `...`을 배치
* marquee: 끝부 분 생략, `...`대신 문장의 끝을 점차 투명하게 보이도록 하는 페이드 아웃 효과 제공



### Button
* 사용자의 터치 동작으로 명령을 내리는데 사용
* onClick 속성으로 Java Code에서 작성한 이벤트 핸들러 함수 지정
```xml
<Button
    android:onClick="onClick"
    android:text="Button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

### EditText
* 텍스트를 입력 받을 수 있는 위젯

#### editable
* 사용자 입력을 통해 텍스트 내용의 변경 가능 여뷰를 설정(default: true)

#### digits
* 원하는 글자만 선택적으로 입력 받을 수 있게 함

```xml
android:digits="abcABC123"
```

#### hint
* 어떤 내용을 입력해야 하는지 알려주는 힌트

#### selectAllOnFocus
* 입력 포커스를 뒀을 때 텍스트 전체를 선택 상태로 만듦

#### input
* 입력되는 문자열을 제한 및 SW 키보드 변경
 * text, number, phone 등


### ImageView
각종 이미지 형태의 파일을 출력, 확대 축소 등의 다양한 기능 지원
png, jpg, gif 등의 포맷 지원

#### src
* 출력할 이미지 지정

```xml
android:src="@drawable/ic_launcher"
```

#### adjustViewbounds
* 축소된 원본 이미지로 ImageView 크기를 조정할지 여부 설정
 * 이미지가 ImageView 영역보다 커 종횡비 유지하여 축소시

#### maxHeight, maxWidth
* ImageView 영역에 그려질 이미지의 최대 크기 지정

#### scaleType
* 이미지의 확대, 축소 방식을 지정
 * matrix, fitXY, center, centerCrop 등

#### tint
* 이미지에 색조를 입힘
 * 보통 `#AARRGGBB`형식의 반투명한 색상 지정




## 안드로이드 화면 구성

![screen_form][screen_form.png]

[screen_form.png]: https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/screen_form.png

하나의 안드로이드 화면은 Activity가 관리하며, 화면은 다른 View들을 내부에 담는 ViewGroup과 화면 요소를 구성하는 Widget으로 구성
View는 주로 XML로 선언하나 Java Code에서 작성 가능

### 위젯: 화면 입출력 요소를 표현하는 뷰

![Widget][Widget.png]

[Widget.png]: https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/Widget.png

### ViewGroup: 위젯 또는 다른 뷰를 grouping하거나 배치할 때 사용하는 뷰

![ViewGroup][ViewGroup.png]

[ViewGroup.png]: https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/ViewGroup.png

### LayoutParams: 화면 배치정보
* 화면 구성을 위해 자식뷰가 부모 뷰그룹에 요청하는 배치 정보
 * 각 뷰그룹 마다 조금씩 다른 정보를 뷰에 요구
* layout_으로 시작하는 뷰 속성
 * 부모 뷰그룹에 자신의 배치정보를 전달해 주는 속성
  * 영역을 배정받는 자식뷰는 해당 공간을 어떤 제약도 없이 사용 가능
```xml
android:layout_weight="1"
android:layout_gravity="center"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_margin="20dp"
```
```Java
Button btn = new Button(this);  //Button 생성
FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);  //LayoutParams에 width, height 설정
params.setMargins(30, 40, 20, 20);  //margin 설정
btn.setLayoutParams(params);  //Button에 width, height 설정
```


##레이아웃(Layout)
* ViewGroup의 일종으로 다른 뷰들을 내부에 배치하는 컨테이너 역할
* 위젯 또는 다른 레이아웃을 내부에 배치하여 다양한 화면 구성
* 화면상에 직접 보이지 않음
* 주요 레이아웃
 * LinearLayout
 * RelativeLayout
 * FrameLayout

### LinearLayout
* 자식뷰를 수평, 수직 방향으로 일렬 배치

#### orientation
* LinearLayout의 자식들이 어느 방향으로 배치될지 결정
 * vertical - 수직
 * horizontal - 수평

#### layout_weight
* 중요도에 따라 자식뷰의 크기 분할
 * 0 이면 자신의 고유한 크기
   * 영역분할 대상에서 제외
 * 1 이상이면 중요도가 0이 아닌 형제뷰와의 비율에 따라 부모 영역 배분
   * 해당 방향의 크기 속성값은 0으로 지정해야 함
   * 해당 방향의 크기는 자동으로 설정되므로
 * 레이아웃의 유연성 유지를 위해 유용

![linearLayout][linearLayout.png]

[linearLayout.png]: https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/linearLayout.png

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical">

    <Button
        android:layout_weight="0"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button1"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="3"
        android:text="Button2"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:text="Button3"/>

</LinearLayout>
```

### RelativeLayout
* 뷰들의 상대적인 관계로 배치 -> **id 필수**
 * 기준이 되는 뷰에 반드시 **id**를 지정
* 특정 뷰가 다른 뷰의 위치에 종속적일 때 기준이 되는 뷰를 먼저 정의

![relativeLayout][relativeLayout.png]

[relativeLayout.png]: https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/relativeLayout.png

```xml
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:id="@+id/button_show_toast"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_weight="0"
        android:text="Toast 출력"/>

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_toRightOf="@id/button_show_toast"
        android:text="Button2"/>

    <Button
        android:id="@+id/buttonq"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignLeft="@id/button_show_toast"
        android:layout_alignRight="@id/button2"
        android:layout_below="@id/button_show_toast"
        android:text="Button1"/>

</RelativeLayout>
```

### FrameLayout
* 레이아웃의 좌측 상단에 모든 뷰들을 **겹쳐서 배치**
* 실행중에 자식뷰 관리 기능
 * addView()
 * removeView()
 * getChildCount()
* 2개 이상인 경우 추가된 순서대로 겹쳐 표시
* 겹쳐진 자식뷰들은 visibility 속성을 이용해 숨길수 있음
 * 탭 형태로 사용가능

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


> ### 전개(Inflation)
> * #### Inflation
>   1. 인플레이션, 통화 팽창; 인플레이션율, 물가 상승율
>   2. (공기나 가스로) 부풀리기
>
> * xml에서 정의된 Layout과 View속성을 읽어 실제 객체를 생성하는 동작
> 
> * Layout의 정보대로 객체를 생성하고 속성변경 메소드를 순서대로 호출
>   * 코드에서 직접할 수 있다.
>
> * xml에서 기술한 Layout은 이진형태로 컴파일되어 실행 파일에 내장
>  * 실행시 객체로 생성되어 존재
> 
> * Activity의 onCreate()안의 **setContentView()**가 하는 역할
> 



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

![R][R.png]

[R.png]: https://github.com/mash-up-kr/android-study/blob/chapter-01/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter01/art/R.png

> ##### R.java가 사라졌을 때?
> 프로젝트 진행 중 `R.layout.activity_main`과 같은 R.java에 정의된 id에 에러가 생길 때가 있다.
> **R.java가 사라져서 참조할 수 없을 때** 생기는 에러인데
> 이 경우 리소스의 xml에 오류가 없는지 확인한 뒤 `Android Studio 메뉴에 [Build] - [Clean Project]`를 해주면 R.java가 생겨 에러가 사라진다.

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
* 안드로이드 애플리케이션은 이벤트 기반으로 구동(event-driven).
* 명령행에서 실행된는 프로그램이나 스크립트와 달리 이벤트 기반으로 구동되는 애플리케이션들은 시작된 후 이벤트의 발생을(user, os, 다른 application) 기다린다.
 * 이벤트(Event): 각종 입력장치와 센서들이 감지한 유의미한 사건들
   ex) 사용자가 버튼을 누르는 경우

* 애플리케이션에서 이벤트를 기다리는 것을 가리켜 이벤트를 **"리스닝한다"**고 한다.
* 이벤트에 응답하기 위해 생성하는 객체를 리스너(listener)라 한다.
* 리스너는 해당 이벤트의 **리스너 인터페이스**를 구현하는 객체

* 안드로이드 SDK에는 다양한 이벤트들의 리스너가 존재
 * OnClickListener
 * OnLongClickListener
 * OnTouchListener
 * OnScrollChangeListener 등...

> 직접 인터페이스를 만들어 구현해도 된다.


#### 이벤트 처리

###### 1. 콜백 메소드 재정의
* 해당 클래스를 상속받아 콜백 메소드 재정의
 * 이벤트 콜백은 주로 View가 제공(유저와 상호작용하는 주체가 View라서)
 * 장점
   * 이벤트 처리를 위한 간단하고 직관적
 * 단점
   * 메소드 재정의 위해 상속 필요(기존 위젯의 경우 바로 처리 불가)
   * 모든 이벤트에 대해 콜백이 정의되어 있지 않다.

 * 주요 콜백 메소드
 ```Java
   boolean onTouchEvent(MotionEvent event)
   boolean onKeyDown(MotionEvent event)
   boolean onKeyUp(MotionEvent event)
   boolean onTrackballEvent(MotionEvent event)
 ```
 > 콜백 메소드(Callback Method): 특정 이벤트 발생시 시스템에 의해 자동으로 호출되는 메소드

```Java
class CustomTextView extends TextView {

	public CustomTextView(Context context) {
    super(context);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
    		String pos = "(" + event.getX() + ", " + event.getY() + ")";
    		this.setText(pos);
        	return true;
        }
		return super.onTouchEvent(event);
	}
}
```

###### 2. 리스너 인터페이스를 구현하는 클래스 사용

* 장점
 * 콜백 메소드와 달리 이벤트 처리를 위해 View를 상속받을 필요 없다.
* 단점
 * 리스너 구현을 위해 별도의 클래스 선언 필요

1. 리스너를 구현하는 클래스를 선언하고 추상메소드 구현
```Java
class CustomListener implements View.OnClickListener, View.OnTouchListener{

        @Override
        public void onClick(View v) {
            //Todo: View를 Click시 하고 싶은 동작 구현
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Todo: View를 Touch시 하고 싶은 동작 구현
            return false;
        }
    }
```

2. 리스너로 구현된 클래스 객체 생성
```Java
CustomListener listener = new CustomListener();
```

3. 이벤트를 감지할 수 있도록 리스너 등록
```Java
//Click, Touch를 구현했으므로 View가 알 수 있게 등록해줘야한다.
btnShowToast.setOnClickListener(listener);
btnShowToast.setOnTouchListener(listener);
```

###### 3. Activity가 리스너 구현
* 리스너를 구현하는 클래스를 따로 작성하는 대신 Activity에 리스너를 직접 구현
* 장점
 * 별도의 리스너 구현 클래스가 필요 없다.
 * 리스너 구현 클래스의 객체 생성이 필요 없다.
* 단점
 * Activity가 하위 단위인 View의 메소드를 제공한다는 점에서 구조적이지 못하다.
 * 각 View가 Activity에 종속되어 독립성이 떨어진다.

```Java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

		//implements한 리스너 구현
		@Override
    	public void onClick(View v) {
        	switch (v.getId()){
            	case R.id.button_show_toast:
                	//Todo: Click시 하고 싶은 동작 구현
                	break;
        	}
    	}

    	Button btnShowToast;

		@Override
    	protected void onCreate(Bundle savedInstanceㄱState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.activity_main);

        	btnShowToast = (Button) findViewById(R.id.button_show_toast);
        	btnShowToast.setOnClickListener(this);  //View 리스너 등록
    	}
    }
```

###### 4. View가 리스너 구현
* View를 상속한 클래스에서(CustomView 등) 리스너를 구현하여 사용
* 장점
 * View 스스로 자신에게 필요한 이벤트 핸들러를 포함하여 구조상 깔끔하고 재활용 유리
* 단점
 * 리스너 구현만 필요한 경우엔 별도로 View를 상속받아야 한다.

```Java
class CustomTextView extends TextView implements View.OnClickListener{

	public CustomTextView(Context context) {
    super(context);
    }

    @Override
    public void onClick(View v) {
    //Todo: View를 Click시 하고 싶은 동작 구현
    }
}
```

###### 5. 인터페이스 객체 구현

```Java
View.OnClickListener mClickListener = new View.OnClickListener() {
	@Override
    public void onClick(View v) {
    //Todo: View를 Click시 하고 싶은 동작 구현
    }
};

btnShowToast.setOnClickListener(mClickListener);
```

###### 6. 익명 내부 클래스 사용
이 리스너는 익명의 내부 클래스(anonymous inner class)로 구현
참조변수 없이 임시 객체 생성하여 리스너 등록
한곳에서만 사용하기 때문에 이름짓는 부담이 없다.

```Java
btnShowToast = (Button)findViewById(R.id.button_show_toast);
btnShowToast.setOnClickListener(new View.OnClickListener() {
    @Override
	public void onClick(View v) {
    //Todo: View를 Click시 하고 싶은 동작 구현
    }
});
```

> #### 핸들러 우선순위
> * 이벤트 핸들러가 중복 정의된 경우 처리 순서
>  * 범위가 좁은 핸들러가 먼저 호출
>  * 단, 우선순위 핸들러에서 true를 반환하면 다음 순위 핸들러는 호출되지 않음

> #### 클릭 리스너 등록 방법
> 1. setOnClickListener()
> ```Java
> Button.setOnClickListener(listener);
> ```
> 2. XML에서 onClick 속성
> ```xml
> android:onClick="onClick"
> ```

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

### Context

#### Context란?
* 애플리케이션 환경에 관한 글로벌 정보에 접근하기 위한 인터페이스
* Abstract클래스이며 실제 구현은 안드로이드 시스템에 의해 제공

#### 역할
* 애플리케이션에 관하여 시스템이 관리하고 있는 정보에 접근하기
ex) 리스소, 패키지
```Java
//애니메이션 리소스 참조
getResources().getAnimation(R.anim.abc_fade_in);
```

* 안드로이드 시스템 서비스에서 제공하는 API를 호출할 수 있는 기능
ex) Activity 실행, Intent 블로드캐스팅, Intent 수신 등
```Java
//Activity 실행
Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
```
위 코드처럼 getApplicationContext()를 쓰는 것보다는 MainActivity.this를 쓰는 게 좋다.(Activity는 Context를 상속받았다.)
getApplicationContext()는 프로세스 자체이기 떄문에 메모리 누수가 일어날 수 있기 때문

#### getBaseContext()와 getApplicationContext(), getContext(), this

##### getBaseContext()
* Activity의 Context
* 생성자나 Context에서 기본 설정된 Context

##### getApplicationContext()
* Service의 Context
* 애플리케이션 종료 이후에도 활동 가능한 글로벌한 Application의 Context

> 앱 종료후 메모리 유지를 피하기 위해서 getBaseContext()를 사용

##### getContext()
* 현재 실행되고 있는 View의 Context를 return 하는데 보통은 현재 활성화된 Activity의 Context가 된다.

##### this
* View.getContext()와 같다.





## 에뮬레이터에서 실행시키기
**이전 시간에 포함되는 내용이면 삭제**


## 안드로이드 앱 빌드 절차
**이전 시간에 포함되는 내용이면 삭제**

## 안드로이드와 모델 - 뷰 - 컨트롤러(MVC)

### 모델 - 뷰 - 컨트롤러(MVC)

####모델(Model)
애플리케이션의 **데이터**와 **비즈니스 로직**을 갖는다.
모델객체는 사용자 인터페이스를 모른다.
데이터를 보존하고 관리하는 것이 유일한 목적


####뷰(View)
자신을 화면에 그리는 방법과 터치나 마우스 클릭과 같은 사용자의 입력에 응답하는 방법을 안다.
**화면에서 볼 수 있는 것**이라면 뷰 객체
안드로이드는 구성 가능한 뷰 클래스들을 풍부하게 제공
커스텀 뷰 클래스들을 생성할 수도 있다.


####컨트롤러(Controller)
**뷰와 모델객체를 결속**시키며, **애플리케이션 로직**을 가진다.
뷰 객체에 의해 촉발되는 다양한 이벤트들에 응답하고, **모델 및 뷰 계층과 주고받는 데이터의 흐름을 관리**하기위해 설계
일반적으로 Activity, Fragment, Service의 서브클래스
> Activity, Fragment는 뷰와 컨트롤러의 특성을 모두 가지고 있다.
> View나 Controller를 한쪽으로 빼개 될 경우 View에 대한 바인딩이나 처리에서 중복 코드를 작성할 수 있다. -> 개선하기 위한 패턴 등장 MVVC, MVP

**MVC 그림 추가**

#### MVC의 장점
애플리케이션의 기능들이 많아지면 너무 복잡해서 이해하기 어려울 수 있다.
코드들을 클래스들로 분리하면 설계에 도움이 되고 전체를 이해하기 쉬워진다.
MVC계층으로 분리하면 개별적인 클래스 대신 계층의 관점으로 생각할 수 있다.
클래스를 더 쉽게 재사용할 수 있게 해준다. 여러가지 일을 혼자서 처리하는 클래스보다는 제한된 책임을 갖는 클래스를 재사용하는 것이 더 쉽기 때문






<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="크리에이티브 커먼즈 라이선스" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />이 저작물은 <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">크리에이티브 커먼즈 저작자표시 4.0 국제 라이선스</a>에 따라 이용할 수 있습니다.
