```
스터디 진행 : 2016년 3월 27일, 김동희
최조 작성자 : 김동희
최초 작성일 : 2016년 3월 27일
마지막 수정 : 2016년 3월 27일, 김동희
```

#  Chapter06 - Fragment

## Fragment란

Activity의 일(UI 관리) 처리를 대행할 수 있는 컨트롤러 객체
- UI는 화면 전체나 일부분이 될 수 있다.

View처럼 사용할 수 있는 Activity라고 할 수 있다.

Activity(생명주기)와 View(레이아웃에서의 배치)의 특징을 가지고 있다.
- Activity와 비슷한 LifeCycle을 가진다.

자신의 View를 하나 가지며 Layout xml로부터 inflate 된다.

Activity안의 모듈형 컨테이너.
- View, Event, Logic을 포함
- 재사용하기 쉽게 되도록 View와 Logic을 캡슐화
- xml Layout과 Java클래스의 조합

Activity에서만 존재하며 단독으로 실행 될 수 없다.
 - 호스트 Activity의 Life Cycle에 영향을 받는다.
 - Activity가 일시정지하는 경우, 그안의 Fragment도 일시 정지

하나의 Activity에서 다수의 Fragment를 사용할 수 있다.

Support Library를 이용해 모든 안드로이드 버전을 지원

Activity와 마찬가지로 Back Stack을 사용할 수 있으나, Activity처럼 다양한 Stack방식을 지원하지 않는다.


### 사용사례
- 반복되는 UI의 경우 재사용성을 높여준다.
- 테블릿과 같이 넓은 화면을 가진 경우 일반 폰과의 다른 인터페이스를 제공하기 위해 사용
- 화면 방향에 따라 다른 UI를 구성할 경우

![Fragment1][fragment1.png]
[fragment1.png]:https://github.com/mash-up-kr/android-study/blob/chapter-06/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter06/images/fragment1.png


## Fragment Life Cycle

![Fragment Life Cycle][fragment life cycle.png]
[fragment life cycle.png]:https://github.com/mash-up-kr/android-study/blob/chapter-06/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter06/images/fragment%20life%20cycle.png

### onAttach()
Fragment가 Activity에 붙을 때 호출

### onCreate()
Activity에서의 onCreate()와 비슷하나, UI관련 작업은 할 수 없다.
Fragment가 생성될 때 호출
Fragment가 재개되었을 때 유지하고자 하는 것을 초기화

### onCreateView()
UI를 처음으로 그릴 때 호출
Layout을 inflater을 하여 View작업을 하는 곳
Fragment가 사용할 View를 반환
사용하지 않으면 null을 반환

### onActivityCreated()
Activity에서 Fragment를 모두 생성하고 난다음 호출
Activity의 onCreate()에서 setContentView()한 다음이라고 생각

### onStart()
Fragment가 화면에 표시될때 호출
사용자의 Action과 상호작용할 수 없다.

### onResume()
Fragment가 화면에 완전히 그려졌으며, 사용자의 Action과 상호 작용 가능

### onPause()
Fragment가 사용자의 Action과 상호작용을 중지

### onStop()
Fragment가 화면에 더 이상 보이지 않고, 기능이 중지되었을 때 호출

### onDestroyView()
View리소스를 해제할 수 있도록 호출

### onDetach()
Fragment가 Activity와 연결이 끊어지는 중일 때 호출


## Fragment 사용하기

### Fragment를 호스팅(포함되어 실행)하기 위해 필요한 2가지
1. Activity 자신의 레이아웃에 Fragment 뷰의 위치 정의

2. Fragment 인스턴스의 생명주기 관리
- Fragment는 Activity를 대신하여 동작하므로 Activity의 상태를 반영해야 한다.
- Fragment의 Life Cycle 함수는 OS가 아닌 호스팅하는 Activity에서 호출
 - OS는 액티비티가 사용중인 프래그먼트를 알지 못한다.


### Activity에서 Fragment를 호스팅하는 2가지 방법

#### 정적 - Fragment를 Activity의 Layout에 추가


```xml
//activity_main.xml

<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <fragment
      android:name="kr.co.mash_up.exfragment.MasterFragment"
      android:id="@+id/fragment_master"
      android:layout_width="match_parent"
      android:layout_height="match_parent"/>
    
</RelativeLayout>
```

- 레이아웃에 추가만 하면 되므로 간단하다

- Activity의 Layout에 고정되어 유연하지 못하다.

- 액티비티가 동작하는 동안 교체 불가

#### 동적 - Java 코드로 추가

Activity의 Layout 파일안에 Container로 사용할 FrameLayout을 정의한다.

```xml
//activity_main.xml

<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:id="@+id/container_fragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</RelativeLayout>
```

Activity에서 Container View를 찾아 Fragment를 추가한다.

```java
//MainActivity.java

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();  //Fragment Manager 객체를 가져온다.
        fm.beginTransaction()  //트랜잭션을 이용해 Container View에 Fragment 추가
                .add(R.id.container_fragment, MasterFragment.newInstance())
                .commit();  //트랜잭션 적용
    }
```

- 정적인 방법보다 복잡하다.

- 실행중에 Fragment를 제어할 수 있는 유일한 방법

- 진정한 유연성을 취하려면 코드에서 추가해야 한다.

- Activity의 Layout에 Fragment를 추가할 컨테이너 뷰라고 불리는 뷰계층 필요


### 프래그먼트 생성 과정

레이아웃 파일에 ui구성
클래스를 생성하고 그것의 뷰를 우리가 정의한 레이아웃으로 설렂ㅇ
레이아웃으로부터 인플레이트 된 위젯들을 코드에 연결

fragement manager -> fragment를 관리하고 액티비티의 뷰계층에 추가하는 역할
프래그먼트 리스트와 프래그먼트 트랜잭션의 백스택을 처리한다.

코드에서 프래그먼트를 추가하려면 액티비티의 fragment manager를 명시적으로 호출해야 한다.





















































<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="크리에이티브 커먼즈 라이선스" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />이 저작물은 <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">크리에이티브 커먼즈 저작자표시 4.0 국제 라이선스</a>에 따라 이용할 수 있습니다.
