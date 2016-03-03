```
스터디 진행 : 2016년 2월 20일, 유슬기
최초 작성자 : 유슬기
최초 작성일 : 2016년 2월 19일
마지막 수정 : 2016년 3월 04일, 유슬기
```

# 3강 - 액티비티 생명주기

## 들어가면서
액티비티는 사용자에게 보여지는 화면을 담당한다. 화면을 담당한다는 것은 사용자와의 접촉과 간섭이 가장 많고, 그에 따른 변화가 심하다는 것을 의미한다.  
그러므로 액티비티의 상태 변화에 따라 적절한 대비를 해야 하고, 어떤 환경에서도 정상적으로 동작할 수 있게 해야한다.

이러한 작업을 돕기위해 안드로이드는 액티비티의 변화를 몇 가지로 분류하고, 그 분류에 따라 몇 가지의 생명주기 함수를 제공한다.

## 액티비티 생명주기 로깅하기
### 액티비티 생명주기 함수
![lifecycle.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/activity_lifecycle.png?raw=true)
위 그림과 같이 액티비티가 실행되고 종료될 때까지 일곱 가지 함수가 호출된다.

예제를 통해 직접 확인해본다.
### 로그 메세지 만들기
안드로이드는 android.util.Log 클래스를 이용해서 로그를 남길 수 있다.  
이 로그를 통해 앱 실행 중에 발생한 각종 정보를 모니터링 및 디버깅할 수 있다.

아래와 같은 메소드를 이용한다. *(이외에 v도 있고 i도 있고 w도 있고 e도 있음 -> 뒤에서 설명)*
```java
public static int d(String tag, msg)
```
**첫번째 매개변수** : 로그의 태그명. 일반적으로 태그명은 로그를 출력한 클래스명으로 한다.  
**두번째 매개변수** : 로그캣에 출력할 텍스트 메세지

### 프로젝트 생성
새로운 프로젝트를 생성한다. 액티비티에 모든 생명주기 함수를 재정의하고 로그를 남긴다.
```java
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
```
Log.d(..)를 호출하여 메세지를 로깅하기 전에 수퍼 클래스에 구현된 버전의 메소드를 호출한다는 것에 유의하자.  
특히, onCreate(..)의 경우는 수퍼 클래스 구현 버전을 호출하는 코드가 맨 앞에 있어야 한다.  
다른 메소드에서는 호출 순서가 그렇게 중요하지 않다.

앱을 실행하여 결과를 확인해보자.

액티비티가 실행되면 로그캣에 아래와 같이 onCreate, onStart, onResume 함수가 순서대로 호출되는 것을 확인할 수 있다.
```
02-19 05:38:07.822 1474-1474/? D/MainActivity: onCreate() called
02-19 05:38:07.822 1474-1474/? D/MainActivity: onStart() called
02-19 05:38:07.822 1474-1474/? D/MainActivity: onResume() called
```
이전키를 눌러 액티비티를 종료하면 아래와 같이 onPause, onStop, onDestroy 함수가 순서대로 호출된다.
```
02-19 05:38:19.194 1474-1474/? D/MainActivity: onPause() called
02-19 05:38:19.966 1474-1474/? D/MainActivity: onStop() called
02-19 05:38:19.966 1474-1474/? D/MainActivity: onDestroy() called
```

## 액티비티 상태에 따라 호출되는 생명주기 함수
액티비티는 다섯가지 상태 변화를 가진다.  
![activity_state_change.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/activity_state_change.png?raw=true)  

### 액티비티 실행과 종료 상태
사용자가 액티비티를 실행하고 이전키를 통해 종료하는 아주 일반적인 상황이다.  

이 과정에서 onCreate, onDestroy 함수가 중요하다.  
onCreate는 액티비티의 시작이고, onDestroy는 액티비티의 끝이다.  
따라서 일반적으로 onCreate에서는 액티비티를 실행하기 위한 객체 생성 및 초기화를 수행하고, onDestroy에서는 사용한 객체를 반환하는 작업을 수행한다.

다른 생명주기들은 반복적으로 실행될 수 있기 때문에 객체 생성 및 초기화 과정을 수행할 수 없다.

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```
위 예제에서 onCreate는 setContentView 함수를 사용하여 레이아웃을 생성 및 설정하고 있다.  
onCreate 함수는 액티비티가 시작되는 가장 첫 번째 생명주기 함수이므로 여기서 액티비티가 실행되기 위한 화면 설정 작업이 가장 적합하다.

onDestroy 함수는 추가되지 않았다. 안드로이드의 경우 생성된 객체 메모리를 반환하는 함수가 없기 때문이다.  
따라서 onDestroy 함수에서는 특별히 할 일이 없다.  
하지만 만일 해당 액티비티 내에서 특정 파일을 열어 읽고 쓰는 작업을 했다면 onDestroy 함수에서 파일을 닫아 리소스를 반환해야 한다.

(자바는 자동으로 메모리를 반환해 주는 가비지 콜렉터가 있다.  
가비지 콜렉터는 더 이상 사용하지 않는 객체 메모리를 자동으로 반환한다.  
그러나 메모리가 아닌 물리적인 파일 리소스 등은 가비지 콜렉터 영역 밖이다.  
그러므로 열어서 사용했다면 반드시 명시적으로 닫아 주어야 한다.)

### 액티비티 일시 정지와 재실행 상태
액티비티 일시 정지는 실행된 액티비티 뒤에 이전 액티비티가 보이는 상태다.
![lifecycle_03.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/lifecycle_03.png?raw=true)  

액티비티 실행된 상태에서 새로운 액티비티가 실행함  
-> 새로운 액티비티 실행됨. 새로 실행된 액티비티는 화면을 가득 채우지 않아 이전 액티비티가 보임. 이때 **onPause** 함수가 호출된다.  
-> 이전 키를 눌러 이전 액티비티가 복귀되고 **onResume** 함수가 호출된다.

예를 들어 동영상을 재생중인 액티비티가 있다고 가정하자.  
이때 동영상 액티비티 위로 작은 액티비티가 실행되어 화면을 가리면 동영상을 보는 데 불편하다.  
따라서 onPuase 함수에서 동영상을 잠시 중단하고, 가린 액티비티가 종료되면 onResume에서 다시 동영상을 재생하면 된다.

onPause가 호출될 때 하던 작업을 무조건 중단할 필요는 없다.  
동영상 재생처럼 onPuase에서 작업을 중단하고 onResume에서 재개해야할 필요성이 있는 케이스는 많지 않다.

예제를 통해 확인해보자. 먼저, 화면을 가릴 작은 레이아웃과 액티비티를 구현한다.  

*화면을 가릴 작은 액티비티 레이아웃 리소스*  
**res/layout/activity_b.xml**
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="10dp"
    android:orientation="vertical"
    >
    
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="작은 B액티비티"
        />

</LinearLayout>
```
*화면을 가릴 작은 액티비티*  
**src/BActivity.java**
```java
public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }
}
```

**AndroidManifest.xml**
```xml
	<activity
            android:name=".BActivity"
            android:label="B 액티비티"
            android:theme="@style/Theme.AppCompat.Dialog" />
```
AndroidManifest.xml에 추가된 B액티비티의 theme 속성을 `android:theme="@style/Theme.AppCompat.Dialog` 값으로 설정하면 화면 중앙에 작은 액티비티 모양으로 실행된다.  
이렇게 작은 액티비티를 다이얼로그 액티비티라고 부른다.  
해당 다이얼로그 액티비티를 실행을 위한 버튼과 코드를 추가해보자.  

*화면을 가릴 작은 액티비티 실행*  
**src/MainActivity.java**
```java
public class MainActivity extends AppCompatActivity {

    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
       
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BActivity.class));
            }
        });
    }
}
```
실행하여 결과를 확인해보자.
```
02-20 06:45:17.489 10961-10961/? D/MainActivity: onCreate() called
02-20 06:45:17.495 10961-10961/? D/MainActivity: onStart() called
02-20 06:45:17.496 10961-10961/? D/MainActivity: onResume() called

02-20 06:45:21.011 10961-10961/? D/MainActivity: onPause() called
02-20 06:45:22.673 10961-10961/? D/MainActivity: onResume() called
```

###  액티비티 정지와 재실행 상태
정지 상태는 일시 정지 상태와 달리 화면을 완전히 가린 다른 액티비티가 실행된 상태를 말한다.   
이전 액티비티가 전혀 보이지 않는다.

![lifecycle_04.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/lifecycle_04.png?raw=true)  
화면을 완전히 가릴 C 액티비티가 실행되기 전에 onPause, onStop 함수가 호출된다.  
이전키를 눌러 C 액티비티를 종료시키면 onRestart, onStart, onResume 함수가 순서대로 호출된다.  
여기서 onRestart 함수보다는 onStart 함수를 onStop의 짝으로 더 많이 사용한다.  

예제를 통해 확인해보자.  

**AndroidManifest.xml**
```xml
<activity
        android:name=".BActivity"
        android:label="B 액티비티"
        /> 
<!-- android:theme="@style/Theme.AppCompat.Dialog" 이 코드를 삭제해준다.-->
```
실행하여 결과를 확인해본다.
```
02-20 06:42:50.687 10961-10961/? D/MainActivity: onCreate() called
02-20 06:42:50.702 10961-10961/? D/MainActivity: onStart() called
02-20 06:42:50.702 10961-10961/? D/MainActivity: onResume() called

02-20 06:42:52.815 10961-10961/? D/MainActivity: onPause() called
02-20 06:42:53.355 10961-10961/? D/MainActivity: onStop() called

02-20 06:42:57.019 10961-10961/? D/MainActivity: onRestart() called
02-20 06:42:57.020 10961-10961/? D/MainActivity: onStart() called
02-20 06:42:57.020 10961-10961/? D/MainActivity: onResume() called
```
**또는 화면 잠금에 의한 정지**  
화면 잠금이 되는 상태 또한 실행중이던 액티비티가 보이지 않는 상태이므로 onPause, onStop 함수가 호출된다. 

화면을 다시 켜면 onRestart, onStart, onResume 함수가 호출된다.

**또는 홈키에 의한 정지**  
홈키를 누르면 강제로 홈 액티비티로 전환되고 이때 역시 해당 액티비티는 화면에서 보이지 않는 상태다. onStop 함수까지 호출된다.

홈 메뉴에서 해당 앱을 다시 시작시키거나 실행중인 앱 목록에서 해당 앱을 선택하면 onRestart, onStart, onResume 함수가 호출된다.

**상단바로 가려지는 경우는 예외**

### 액티비티 강제 종료와 재실행 상태
#### 시스템 환경 변화에 의한 종료
시스템 환경 설정 값이 달라지는 경우다. 화면을 가로에서 세로 혹은 그 반대로 회전하는 경우가 대표적이다.

화면을 회전시켜보자.
```
02-19 07:39:01.976 21331-21331/? D/MainActivity: onPause() called
02-19 07:39:01.976 21331-21331/? D/MainActivity: onStop() called
02-19 07:39:01.976 21331-21331/? D/MainActivity: onDestroy() called
02-19 07:39:02.026 21331-21331/? D/MainActivity: onCreate() called
02-19 07:39:02.026 21331-21331/? D/MainActivity: onStart() called
02-19 07:39:02.026 21331-21331/? D/MainActivity: onResume() called

```
위와 같은 로그를 확인할 수 있다.  
이때 생명주기는 onPause, onStop, onDestroy까지 호출되어 액티비티를 종료하고, 이어 onCreate, onStart, onResume까지 호출되어 액티비티를 다시 실행하게 된다.

**그렇다면 왜 시스템 환경이 변경되면 액티비티를 종료하고 다시 구동시키는 걸까?**

시스템 환경이 변할 때 그에 적합한 다른 리소스를 적용하기 위해서다.  
화면에 새로운 레이아웃 리소스를 적용하려면 setContentView 함수가 호출되어야 하고, 해당 함수는 대부분 onCreate 함수에서 처리한다.   따라서 아예 액티비티를 종료하고 다시 실행하여 onCreate 함수부터 다시 호출되도록 유도하는 것이다.

예제를 통해 확인하자. 세로와 가로 전용 레이아웃을 구성한다.
![add_layout_folders.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/add_layout_folders.PNG?raw=true)

이런 식으로 화면 활용도를 높혀 줄 수도 있지만 굳이 가로와 세로로 분리할 필요가 없는 화면 구성도 많을 것이다.  
이러한 경우 화면을 회전시킬 때마다 액티비티의 모든 생명주기 함수를 다시 호출하는 것은 오히려 성능만 떨어뜨릴 뿐이다.

이를 위해 안드로이드는 시스템 정보가 변경되더라도 액티비티를 재실행하지 않고 onConfigurationChanged라는 별도의 함수만 호출해 주도록 하는 방법이 있다.  
다음과 같이 수정된 AndroidManifest.xml 파일을 살펴보자.  

**AndroidManifest.xml**
```xml
<activity android:name=".MainActivity"
            android:label="@string/app_name"
            android:configChanges="orientation|screenSize"
            >
```
orientation 값은 화면 방향이 바뀌는 것을 의미하고, screenSize는 화면 해상도가 변경되는 것을 의미한다.  
즉, 화면 방향이 바뀌거나 화면 해상도가 변경되는 경우 액티비티의 생명주기를 다시 시작하지 않고 액티비티에 재정의된 onConfigurationChanged에서 직접 처리하겠다는 의미이다.

다음은 액티비티에 onConfigurationChanged 함수를 재정의한다.  

**src/MainActivity.java**
```java
public class MainActivity extends AppCompatActivity {
    ...

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "onConfigurationChanged()");

        setContentView(R.layout.activity_main);
    }
}
```
단말기가 회전되면 seContentView 함수를 호출하여 화면 레이아웃을 다시 설정한다.  
물론 화면 레이아웃을 변경할 필요가 없다면 setContentView 함수를 사용할 필요는 없다.  
실행해서 결과를 확인해 보자.

```
02-19 08:20:12.922 1439-1439/? D/MainActivity: onCreate() called
02-19 08:20:12.924 1439-1439/? D/MainActivity: onStart() called
02-19 08:20:12.924 1439-1439/? D/MainActivity: onResume() called
02-19 08:20:18.606 1439-1439/? D/MainActivity: onConfigurationChanged()
02-19 08:20:29.497 1439-1439/? D/MainActivity: onConfigurationChanged()
02-19 08:20:31.646 1439-1439/? D/MainActivity: onConfigurationChanged()
```
onResume 함수까지 호출이 된 후, 회전을 여러번 해봐도 액티비티 종료와 재실행 함수가 전혀 호출되지 않고 onConfigurationChanged 함수만 호출되었다.

#### 시스템에 의한 강제 종료
안드로이드 시스템은 앱 프로세스 상태를 포그라운드와 백그라운드 두 가지로 구분하고, 시스템 메모리가 부족해지면 백그라운드 상태의 앱 프로세스를 강제로 종료하여 자원을 확보한다.  
여기서 포그라운드는 앱의 액티비티가 화면에 보이는 상태고, 백그라운드는 다른 앱 액티비티에 완전히 가려져서 보이지 않는 상태를 말한다.

adb 툴을 이용하여 앱 프로세스 상태를 확인해보면  
(cmd로 `adb shell dumpsys activity > appProcessInfo.txt`)

*앱이 화면에 보이는 상태*  
**appProcessInfo.txt**
```
Proc # 0: fore  F/A/T  trm: 0 1439:kr.co.mash_up.lifecycletest/u0a68 (top-activity)
```
/F, 즉 포그라운드 상태의 앱 프로세스다.

*홈키로 인해 앱이 화면에 보이지 않는 상태*  
**appProcessInfo.txt**
```
Proc # 1: prev  B/ /LA trm: 0 1439:kr.co.mash_up.lifecycletest/u0a68 (previous)
```
/B, 즉 백그라운드 상태의 앱 프로세스다.

백그라운드 상태는 시스템이 메모리 부족 시 강제로 종료할 수 있는 불안정한 상태다. 앱에서 실행해서 확인해보자.
```
02-19 08:59:09.889 8727-8727/? D/MainActivity: onCreate() called
02-19 08:59:09.891 8727-8727/? D/MainActivity: onStart() called
02-19 08:59:09.891 8727-8727/? D/MainActivity: onResume() called
02-19 08:59:13.176 8727-8727/? D/MainActivity: onPause() called
02-19 08:59:13.522 8727-8727/? D/MainActivity: onStop() called

02-19 08:59:19.864 8892-8892/? D/MainActivity: onCreate() called
02-19 08:59:19.865 8892-8892/? D/MainActivity: onStart() called
02-19 08:59:19.865 8892-8892/? D/MainActivity: onResume() called
```
위의 로그에서 앱 프로세스가 종료되었지만 onDestroy 함수가 호출되지 않았음을 확인할 수 있다.  
앱을 다시 실행시켜 보면 에디트 텍스트 등이 초기화 상태가 되어있다.  
액티비티 생명주기 함수도 onCreate부터 onResume까지 다시 호출되었다.


![lifecycle_05.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/lifecycle_05.png?raw=true)  
홈으로 이동하면 이전 앱 액티비티가 화면에 보이지 않는 백그라운드 상태가 된다. 이때 onPause, onStop까지 호출된다.   
-> 현재 구동중인 앱 프로세스를 강제종료하면 앱은 종료되었음에도 onDestroy가 호출되지 않는다.  
-> 앱으로 복귀하면 onCreate부터 다시 시작한다.

앱 프로세스가 죽었기 때문에 이전 앱에 존재했던 액티비티 객체와 멤버 변수가 사라져 버렸다.  
따라서 앱으로 복귀했지만 토글 버튼이나 에디트 텍스트 등의 변경상태는 초기화되어 있다.

## 액티비티 데이터 복원
안드로이드에서는 앱 액티비티에서 사용된 데이터를 복원하기 위해 다른 프로세스로 데이터를 잠시 백업해두는 방법을 취한다.  
여기서 다른 프로세스는 액티비티 매니저가 존재하는 시스템 프로세스를 말한다.

![lifecycle_restore.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/lifecycle_restore.png?raw=true)  
onSaveInstanceState 함수는 onStop 직전에 호출된다.  
해당 함수에서는 시스템에 의한 강제 종료에 대비해서 백업할 데이터를 시스템 프로세스로 전달한다.  
백업된 데이터는 onCreate 함수의 인자를 통해 전달된다. 그러므로 onCreate 함수에서 객체를 복원하면 된다.  
백업된 데이터는 onRestoreInstanceState 함수에도 전달된다. 그러므로 onCreate랑 onRestoreInstanceState 함수 둘 중 하나를 선택해서 데이터를 복원하면 된다.

예제를 통해 살펴보자.

**src/MainActivity.java**

```java
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button buttonB, buttonC;
    EditText editText;
    ToggleButton toggleButton;
    RatingBar ratingBar;

    //번들 매개변수 savedInstanceState에 백업된 데이터가 전달된다
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() called");

        buttonB = (Button)findViewById(R.id.buttonB);
        buttonC = (Button)findViewById(R.id.buttonC);
        editText = (EditText)findViewById(R.id.editText);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        ...
    }

    ...
    
    //액티비티 데이터를 백업할 수 있는 함수
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");

        Boolean isChecked = toggleButton.isChecked();
        String backupString = editText.getText().toString();
        Float rating = ratingBar.getRating();

        outState.putBoolean("IS_CHECKED", isChecked);
        outState.putString("BACKUP_STRING", backupString);
        outState.putFloat("RATING", rating);

        super.onSaveInstanceState(outState);
    }

    //백업한 데이터를 전달받아 복원할 수 있는 함수
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState()");

        if(savedInstanceState != null) {
            toggleButton.setChecked( savedInstanceState.getBoolean("IS_CHECKED") );
            editText.setText( savedInstanceState.getString("BACKUP_STRING") );
            ratingBar.setRating( savedInstanceState.getFloat("RATING") );
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}
```
액티비티에 onSavedInstanceState 함수를 재정의하면 onStop 함수 전에 호출된다. 이 함수에 매개 변수로 ouState 번들 객체가 전달되는데 여기에 백업할 데이터를 추가하면 된다.  
onRestoreInstanceState 함수를 재정의하면 onStart 함수 다음에 호출된다. 이 함수의 매개 변수로는 savedInstanceState 번들 객체가 전달되는데 이 객체에는 onSavedInstanceState에서 백업한 데이터가 존재한다. savedInstanceState를 이용해서 백업한 데이터를 복원하면 된다.  
복원할 데이터는 onCreate 함수의 매개변수로도 전달된다. 따라서 onRestoreInstanceState 대신 onCreate에서도 데이터를 복원해도 된다.

## 액티비티 생명주기 다시 알아보기
![activity_lifecycle_review.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/activity_lifecycle_review.png?raw=true)  
## 두 액티비티 간 생명주기
![activity_lifecycle_between.png](https://github.com/mash-up-kr/android-study/blob/chapter-03/%EC%8B%A0%EC%9E%85%20%EC%8A%A4%ED%84%B0%EB%94%94/chapter03/images/activity_lifecycle_between.png?raw=true)  
A 액티비티에서 B 액티비티를 실행하는 경우,  
현재의 A 액티비티를 잠시 중단시키고, 실행될 B 액티비티를 빠르게 보여주기 위해 B 액티비티의 생명주기 함수를 순서대로 호출한다.   
그 다음 이전 액티비티의 나머지 생명주기 함수가 호출되어 모든 작업을 정리하게 된다.  

예제를 통해 확인해보자.
## 로깅 레벨과 관련 메서드들
안드로이드는 메세지의 중요도를 나타내는 다섯 개의 로그 레벨을 지원한다.   
e가 가장 높은 단계이며, v가 가장 낮은 단계이다. 일반적으로 다음과 같은 용도로 활용된다  

 로그 레벨  |   메소드  | 활용
----------|-------------|-------------------------------------------
ERROR	  |Log.e(...)   |실행중인 앱에 치명적인 에러가 발생했음을 알림
WARNING   |Log.w(...)   |실행중인 앱이 문제가 발생하라 소지가 있음을 알림
INFO	  |Log.i(...)   |각종 클래스 변수의 내용을 확인
DEBUG	  |Log.d(...)   |문제 발생 시 원인 분석의 기반이 되는 정보를 출력
VERBOSE   |Log.v(...)   |다양한 정보를 출력

각 단계는 일반적인 활용 예일 뿐이므로 입맛에 맞게 활용하면 된다.

