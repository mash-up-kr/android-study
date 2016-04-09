```
스터디 진행 : 2016년 4월 09일, 유슬기
최초 작성자 : 유슬기
최초 작성일 : 2016년 4월 09일
마지막 수정 : 2016년 4월 09일, 유슬기
```

# Styles and Themes

## 스타일과 테마란?
스타일과 테마는 높이, 패딩, 폰트색, 폰트 크기, 배경 색 등을 미리 정의해놓고 여러 UI 요소들에서 공유해서 사용하는 기법이다.

스타일과 테마의 차이는 다음과 같다.

- **스타일** :  레이아웃 XML에 있는 하나의 **엘리먼트 단위**로 적용할 수 있는 속성들의 집합
- **테마** : 어플리케이션 내의 모든 액티비티 또는 하나의 **액티비티 단위**로 적용할 수 있는 속성들의 집합

## Styles & Themes의 장점
1. **어플리케이션 용량 저하** : 이미지 대신 스타일과 테마로 작을 하게 되면 앱을 좀 더 가볍게 만들 수 있다.
2. **스타일의 재사용** : 잘 정의된 스타일과 테마의 경우 재사용성이 높아서 전체적인 분위기가 비슷하다면 그대로 사용할 수 있다.
3. **수정의 용이성** : 한 곳에서 수정을 하면 앱 전반에 자동으로 적용이 되므로 수정이 쉽다.


# Style
## Style 생성하기
프로젝트 내 res/value/styles.xml에 설정한다.
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="CodeFont" parent="@android:style/TextAppearance.Medium">
        <item name="android:layout_width">fill_parent</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:textColor">#00FF00</item>
        <item name="android:typeface">monospace</item>
    </style>
</resources>
```
- 루트노드는 항상 `<resources>`여야 한다.
- 하나의 스타일을 생성할 때마다 `<style>` 엘리먼트를 추가하여 고유한 name 속성과 parent 속성을 지정한다. 이 때, parent 속성은 필수사항이 아니다.
- parent : 전체 스타일을 지정하지 않고 기존의 스타일을 상속받아 부분만 바꿀 경우에 부모 Style을 지정하는 필드다.
- itme : 문자열이나 16진수 색상값, 다른 리소스 참조 등을 지정할 수 있다.

일반적으로 안드로이드의 표준 스타일 리소스를 상속받은 뒤, 변경하고 싶은 부분만 바꾸어주는 방식을 사용한다.

### Style 상속
`<style>`의 parent 속성을 이용해 이전에 설정해둔 스타일 속성을 상속받을 수 있다.
```xml
<style name="LargeFont">
    <item name="android:textSize">40sp</item>
</style>

<style name="LargePinkFont" parent="@style/LargeFont">
    <item name="android:textColor">#FF4081</item>
</style>
```
LargePinkFont는 parent 속성으로 LargeFont를 상속받음으로써 textSize가 40sp고 textColor가 #FF4081 스타일로 정의되었다.

더 간단하게 스타일 상속받는 법 : 
```xml
<style name="LargeFont">
    <item name="android:textSize">40sp</item>
</style>

<style name="LargeFont.Red">
    <item name="android:textColor">#C80000</item>
</style>

<style name="LargeFont.Red.Bold">
    <item name="android:textStyle">bold</item>
</style>
```



## Style 적용하기
```xml
<TextView
    style="@style/CodeFont"
    android:text="hello" />
```
적용할 엘리먼트의 style 속성을 이용해 지정한다.

# Theme
## Theme 생성하기
프로젝트 내 res/value/styles.xml에 설정한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
	<style name="CustomTheme">
		<item name="android:windowNoTitle">true</item>
		<item name="windowFrame">@drawable/screen_frame</item>
		<item name="panelForegroundColor">#FF000000</item>
		<item name="panelTextColor">?panelForegroundColor</item>
		<item name="panelTextSize">14</item>
		<item name="menuItemTextColor">?panelTextColor</item>
		<item name="menuItemTextSize">?panelTextSize</item>
	</style>
</resources>
```
리소스 참조 방식
- **@** : 이전에 미리 정의된 리소스를 참조
- **?** : 현재 로드된 테마에서의 리소스 값을 참조

name 값에 의해 특정 `<item>`을 참조하면서 수행되어진다. 예를 들면 panelTextColor는 현재 테마에 존재하는 panelForegroundColor 값을 사용한다는 의미가 된다. 

## Theme 적용하기
테마는 앱 전체에 설정하거나 Activity 단위로 설정 하기 때문에 매니페스트 파일에서 적용이 이루어진다.

- 앱 전체에 테마 적용하기
```xml
<application
        ...
        android:theme="@style/CustomTheme">
```

- 하나의 액티비티에 테마 적용하기
```xml
<activity
		...
        android:theme="@style/CustomTheme">
```

테마는 java코드로도 적용시킬 수 있다. 하지만 테마 설정은 꼭 뷰의 인스턴스화보다 선행되어야 한다.  
즉, setContentView()나 inflate()가 호출되기 전에 테마 설정이 되어야 한다는 뜻이다.

setTheme() 메소드를 이용해 테마를 설정한다.
```java
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(android.R.style.Theme_Light);
    setContentView(R.layout.activity_main);
}
```
하지만 대부분의 경우 화면에 테마 적용을 하는 일은 XML에서 하는 것이 유리하며, 권고사항이다.

## AppTheme을 수정하면
```xml
<resources xmlns:android="http://schemas.android.com/apk/res/android">
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <!-- These are your custom properties -->
        <item name="android:buttonStyle">@style/Widget.Button.Custom</item>
        <item name="android:textViewStyle">@style/Widget.TextView.Custom</item>
    </style>

    <!-- This is the custom button styles for this application -->
    <style name="Widget.Button.Custom" parent="android:Widget.Button">
        <item name="android:textColor">#0000FF</item>
    </style>

    <!-- This is the custom textview styles for this application -->
    <style name="Widget.TextView.Custom" parent="android:Widget.TextView">
        <item name="android:textColor">#00FF00</item>
    </style>
</resources>
```
기존에 있던 AppTheme 테마에 buttonStyle과 textViewStyle을 추가했다. 그 결과 디폴트 스타일이 우리가 커스텀한대로 새로 적용되어 나왔다.  

아래와 같이 매니페스트에 기본으로 적용되어 있는 theme이 AppTheme이다. 때문에 따로 테마를 지정하지 않았어도 커스텀한 내용이 적용되어 나온 것이다.

```xml
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
```

## 스타일&테마는 간단하니까 자율 실습