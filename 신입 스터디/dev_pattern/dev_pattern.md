```
스터디 진행 : 2016년 7월 23일, 김동희
최조 작성자 : 김동희
최초 작성일 : 2016년 7월 23일
마지막 수정 : 2016년 7월 23일, 김동희
```

#  MVC, MVVM, MVP

**MVC 그림 추가**
그림 출처 https://tomyrhymond.wordpress.com/2011/09/16/mvc-mvp-and-mvvm/


## Model - View - Controller(MVC)

### Model

애플리케이션의 **데이터**와 **비즈니스 로직**(데이터를 조작하는 로직)을 갖는다.
Model 객체는 사용자 인터페이스를 모른다.
데이터를 보존하고 관리하는 것이 유일한 목적

### View
자신을 화면에 그리는 방법과 터치나 마우스 클릭과 같은 사용자의 입력에 응답하는 방법을 안다.
**화면에서 볼 수 있는 것**이라면 View 객체
안드로이드는 구성 가능한 View 클래스들을 풍부하게 제공
Custom View 클래스를 생성할 수 있다.

### Controller
**View와 Model객체를 결속**시키며, **애플리케이션 로직**을 가진다.
View 객체에 의해 촉발되는 다양한 이벤트들에 응답하고, **Model 및 View 계층과 주고받는 데이터의 흐름을 관리**하기위해 설계
일반적으로 Activity, Fragment, Service의 서브클래스

> Activity, Fragment는 View와 Controller의 특성을 모두 가지고 있다.(Coupling이 강함)
> View나 Controller를 한쪽으로 분리할 경우 View에 대한 Binding이나 처리에서 중복 코드를 작성할 수 있다. -> **개선하기 위한 패턴 등장 MVVM, MVP**

> 외부의 모든 이벤트를 Controller에서 처리하되(Activity..) View에 관여하지 않는 것이 원칙
> 하지만 Activity 특성상 View에 관여하지 않을 수 없다.
> 결국 Android에서는 MVC를 계속적으로 유지하기에는 무리
> Unit Test도 어렵다.

* 모든 입력은 Controller에서 처리
* 입력이 Controller로 들어오면 Controller는 입력에 해당하는 Model을 조작(update)하고, Model을 나타내줄 View를 선택
* Controller는 View를 선택할 수 있기 때문에 하나의 Controller가 여러개의 View를 선택하여 Model을 나타내어 줄 수 있다.
* 이때 Controller는 View만 선택하고 Update 시켜주지 않기 때문에 View는 Model을 이용하여 Update한다.
* Model을 직접 사용하거나 Model에서 View에게 Notify해주는 방법, View에서 Polling을 통해 Model의 변화를 알아채는 방법 등이 있다.
* View는 Model을 이용하기 때문에 **서로간의 의존성을 완벽히 피할수 없다는 단점**
* 좋은 MVC는 View와 Model의 의존성을 최대한 낮게 한 것

Controller들은 행동의 토대가 되고 여러 View에 공유될 수 있다.
View와 Model은 직접적으로 통신할 수 있다.

#### MVC의 장점
애플리케이션의 기능들이 많아지면 너무 복잡해서 이해하기 어려울 수 있다.
코드들을 클래스들로 분리하면 설계에 도움이 되고 전체를 이해하기 쉬워진다.
MVC계층으로 분리하면 개별적인 클래스 대신 계층의 관점으로 생각할 수 있다.
클래스를 더 쉽게 재사용할 수 있게 해준다. 여러가지 일을 혼자서 처리하는 클래스보다는 제한된 책임을 갖는 클래스를 재사용하는 것이 더 쉽기 때문

## Model - View - ViewModel(MVVM)

### ViewModel
Controller와 View구조를 통합적으로 가지고 있다고 생각
View를 나타내주기 위한 Model
View보다는 Model과 유사하게 디자인
View에 Binding될 때 가장 강력

* MVP같이 Viwe에서 입력처리

### 장점
`Command`와 `DataBinding`으로 MVP와 달리 **View와의 의존성을 완벽히 분리**
`Command`를 통하여 `Behavior`를 View의 특정한 ViewAction(Event)와 연결할 수 있으며, ViewModel의 속성과 특정 View의 속성을 Binding시켜줌으로서 ViewModel의 속성이 변경될 때마다 View를 업데이트 시켜줄 수 있다.

View 라이브러리 등이 구현되는 경우가 있다.

> ViewModel이 View와의 상호작용 및 제어에 집중
> ViewModel에서 직접 View의 이벤트를 처리하기 때문에 Controller의 역할도 함께 수행하게 되어 점점 코드가 집중되는 구조가 될수 있다.
> 또한 초기화와 외부 이벤트(View에 의한 것이 아닌 이벤트)를 제외하고는 Activity의 역할이 모호해진다.

## Model - View - Presenter(MVP)

전체적으로 MVC와 비슷
Controller가 Presenter라고 보면 될듯
다른점
View에 대한 관리와 접근은 Activity에서
Model에 대한 컨트롤러 역할은 Presenter에서

MVC와 다르게 입력이 View에서 처리
Presenter는 View의 인스턴스를 갖고 있으며 1:1관계고, 그에 해당하는 Model의 인스턴스도 가지기 때문에 View와 Model사이에서 다리와 같은 역할
View에서 이벤트 발생시 Presenter에게 전달하고, Presenter는 해당 이벤트에 따른 Model을 조작하고 그 결과를 Binding을 통해 View에게 통보하여 View를 업데이트 시켜준다.
**MVC와 다르게 Presenter를 통해 Model과 View를 완벽히 분리**

1. View는 실제 View에 대한 직접적인 접근 담당
2. View에서 발생하는 이벤트는 직적 핸들링하나 Presenter에 위임하도록 한다.
3. Presenter는 실질적인 기능을 제어하는 곳으로 ViewController로 이해하면 쉽다.
4. Model은 비즈니스 로직을 실질저긍로 수행

>Presenter:View는 1:1매칭. Presenter가 주요기능을 관장하되 실제 View에서 발생하는 이벤트는 >Presenter(이벤트: View -> Presenter)로 전달하여 처리하도록 하고, 처리된 결과는 Presenter가 View에 전달하도록 하여 처리(처리 결과 표현: Presenter -> View)

View를 Model로부터 분리.
Presenter는 Model과 View 사이의 중재자 역할
Unit Test를 쉽게 작성할 수 잇다.

### Model
View에서 표시하고자하는 data를 제공해준다.

### View
일반적으로 Activity에서 구현하고 Presenter에 대한 참조가 포함
View가 하는 일은 Presenter의 인터페이스를 상속받아 메소드 호출
화면을 구성
사용자 행위가 일어나면 Presenter로 위임
Presenter가 제공하는 포맷팅된 결과를 화면에 갱신

### Presenter
View와 Model 사이의 중재자
data를 Model로부터 받거나 View에 적합하게 돌려준다.
사용자 상호작용을 처리하기 위해 interactor 인터페이스 호출
interactor로 부터 결과를 수신하기 위해 Listener 인터페이스 구현
interactor로 부터 결과를 View에 갱신하기 위해 View 메소드 호출

### 장점
View는 Model을 따로 알고 있지 않아도 도니다.

### 단점
View와 1:1이기 때문에 View와의 강한 의존성

> View에 대한 직접적인 접근이 요구되는 Android의 Activity는 직접적인 View 접근은 Activity가 하도록하고 이에대한 제어는 Presenter가 하도록하고 있다.


### MVP 공부시 참고하면 좋을 자료
[Simple-MVP](https://github.com/tinmegali/simple-mvp)
[Android MVP](https://github.com/antoniolg/androidmvp)</br>
[EffectiveAndroidUI](https://github.com/pedrovgs/EffectiveAndroidUI)</br>
[android-arch-sample](https://github.com/remind101/android-arch-sample)</br>
[Google Samples - Android architecture](https://github.com/googlesamples/android-architecture/tree/master)</br>
[MODEL VIEW PRESENTER (MVP) IN ANDROID, PART 1](http://www.tinmegali.com/en/model-view-presenter-android-part-1/)</br>
[fevi-regacy](https://github.com/dusskapark/fevi-regacy) - 이건 보기 어려울 수 있다.</br>
[Bourbon](https://github.com/hitherejoe/Bourbon) - 이것도 보기 어려울 수 있다.</br>
[Model-View-Presenter Architecture in Android Applications](http://macoscope.com/blog/model-view-presenter-architecture-in-android-applications/)

## 참조
[Android 와 개발패턴](http://tosslab.github.io/android/2015/03/01/01.Android-mvc-mvvm-mvp.html)</br>
[MVC, MVP, MVVM 차이점](http://hackersstudy.tistory.com/71)</br>
[MODEL VIEW PRESENTER (MVP) IN ANDROID, PART 1(번역)](http://tiii.tistory.com/24)</br>
[MVP for Android: how to organize the presentation layer](http://antonioleiva.com/mvp-android)</br>

<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="크리에이티브 커먼즈 라이선스" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />이 저작물은 <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">크리에이티브 커먼즈 저작자표시 4.0 국제 라이선스</a>에 따라 이용할 수 있습니다.
