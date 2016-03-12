
# 5강 - 액티비티와 인텐트

[TOC]

## 인텐트

인텐트는 안드로이드 시스템에서 커뮤니케이션을 담당하는 역할로 컴포넌트간 (Activity, ContentProvider, BroadcastReceiver, Service) 의 호출, 메시지 ,데이터 등을 주고받는데 이용한다. (컴포넌트들은 같은 애플리케이션에 존재할 수도 있고 다른 애플리케이션에 있을 수도 있음)

여러 화면으로 되어 있는 애플리케이션을 가정하여 보자. 각각의 화면은 별도의 액티비티로 구현된다. 
그러면 하나의 액티비티(화면)에서 다른 액티비티(화면)로 전환하려면 어떻게 해야할까? 이러한 경우에 **인텐트(intent)** 를 사용한다. 




### 인텐트의 종류
- 명시적 인텐트(explicit intent)
>명시적 인텐트에서는 타깃 컴포넌트의 이름을 지정한다. 즉 "애플리케이션 A의 컴포넌트 B를 구동시켜라"와 같이 명확하게 지정하는 것이다. 일반적으로 컴포넌트의 이름은 다른 애플리케이션의 개발자에게 알려져 있지 않기 때문에 명시적 인텐트는 주로`애플리케이션의 내부에서 사용`된다. 예를 들어 동일한 애플리케이션 내에 있는 다른 액티비티를 실행하는데 사용된다. 

- 암시적 인텐트(implicit intent)
>암시적 인텐트에서는 타깃 컴포넌트의 이름을 지정하지 않는다. 대신에 아주 암시적으로 컴포넌트를 지정하는 것이다. 예을 들어서 "지도를 보여줄 수 있는 컴포넌트이면 어떤 것이라도 좋다"와 같다. 암시적 인텐트는 일반적으로 `다른 애플리케이션의 컴포넌트를 구동하는 데 사용`된다. 암시적 인텐트의 경우, 특정한 타깃이 없으므로 안드로이드는 인텐트를 처리할 수 있는 가장 최적의 컴포넌트를 탐색하여야 한다. 안드로이드는 컴포넌트가 가지고 있는 인텐트 필터를 암시적 인텐트와 비교하여 탐색을 수행한다. 

###인텐트 실습하며 이해하기

####<명시적 인텐트>
#####1. 액티비티 시작시키기
![enter image description here](http://s12.postimg.org/y895g78vh/explicit_practice1.jpg)

**< AndroidManifest.xml >** 
``` python
<activity
    android:name=".B_Activity"
    android:label="B 액티비티"/>
```
매니페스트(Manifest)는 안드로이드 운영체제에게 애플리케이션을 설명하는 메타데이터를 포함한다.  애플리케이션의 모든 액티비티는 운영체제가 액세스할 수 있게 반드시 매니페스트에 선언되어야 한다. (만일 등록하지 않으면 시스템이 액티비티의 존재를 알 수 없다 )
 
**< A_Activity.java >**

``` python
public class A_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        Button btn_b = (Button) findViewById(R.id.btn_startactivity_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A_Activity.this, B_Activity.class);
                startActivity(intent);
            }
        });
    }
}
```

>public Intent(Context packageContext, Class<?> cls)
>- packageContext : 호출하는 Activity (호출이 발생한 액티비티)
>- cls : 호출할 Activity (호출의 목적지)
>
>public void startActivity(Intent intent)

이렇게 인텐트 설정이 끝났으면, 인텐트를 실행해야한다. 
여기서 알아둘 것은 인텐트 자체를 생성함으로써 인텐트가 실행되는 것이 아니라, 인텐트는 "실행할 액티비티의 정보"만을 담고 있고, 실제로 그 일을 수행하는 것은 startActivity()메소드를 통해 메세지를 전달받은 Activity Manager이다. (Intent를 우편물, startActivity()를 우편배달원이라 생각하면 쉽다.)

![enter image description here](http://s28.postimg.org/4pdmapmkt/start_Activity.jpg)
인텐트를 전달받은 Activity Manager은 시작시킬 액티비티 클래스로 지정된 이름이 
그 패키지의 매니페스트에 선언되어 있는지 확인하고 만일 선언되어 있다면
그 Activity 인스턴스를 생성하고 그것의 onCreate(…)메서드를 호출한다. 




#####2. 액티비티 간의 데이터 전달
![enter image description here](http://s23.postimg.org/86df6mtzv/activituresult.jpg)

**< A_Activity.java >**

``` python
public class A_Activity extends AppCompatActivity {

    static final int GET_STRING = 1;
    TextView result_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);


        result_txt = (TextView)findViewById(R.id.return_txt);
        Button btn_return = (Button) findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A_Activity.this, Sub_Activity.class);
                startActivityForResult(intent, GET_STRING);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_STRING){
            if (resultCode == RESULT_OK){
                result_txt.setText(data.getStringExtra("INPUT_TEXT"));
            }
        }
    }
}

```

>public void startActivityForResult(Intent intent, int requestCode)
>- intent : 인텐트 객체
>- requestCode : 사용자가 정의한 정수. 자식 액티비티에 전달되었다가 부모 액티비티가 다시 돌려받는다. **요청 코드는 부모 액티비티가 여러 타입의 자식 액티비티들을 시작시키고, 어떤 자식 액티비티가 결과를 돌려주는지 알 필요가 있을 때 사용한다.**

`Q. 예제 코드에서 requestCode에 들어가 있는 static final int GET_STRING 은 무엇인가?`

- static : 프로그래밍에서 자주 변하지 않는 일정한 값이나 설정 정보 같은 공용자원에 대한 접근에 있어서 매번 메모리에 로딩하거나 값을 읽어들이는 것보다 일종의 '전역변수'와 같은 개념을 통해 접근하여 비용도 줄이고, 효율도 높일 수 있는 키워드
- final : 상속불가 또는 변할 수 없는 상수 선언에 사용된다. final로 선언된 변수는 값을 초기화만 할 수 있고, 그 값의 변경 및 새로운 할당이 불가능하다.

[static final 상수를 사용하는 이유][1]

>protected void onActivityResult(int requestCode, int resultCode, Intent data)
>- requestCode : A_Activity(부모 액티비티)에서 준 코드
>- resultCode ,data :  자식 액티비티인 Sub_Activity의 setResult(...)로 전달되었던 것

**< Sub_Activity.java >**

``` python
public class Sub_Activity extends AppCompatActivity {

    EditText input_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        input_txt = (EditText)findViewById(R.id.input_txt);
        Button btn_ok = (Button)findViewById(R.id.btn_complete);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("INPUT_TEXT", input_txt.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
```
>public void setResult(int resultCode)
>public void setResult(int resultCode, Intent intent)

결과 코드(result code)는 두 개의 사전 정의된 상수 중 하나다. 그 상수들은 Activity.RESULT_OK(정수 -1) 또는 Activity.RESULT_CANCELED(정수 0)이다. 서브 액티비티가 어떻게 끝났는지에 따라 부모 액티비티에서 다른 액션을 취할 필요가 있을 때 사용하면 용이하다. 

>public Intent putExtra(String name, String value)

엑스트라는 호출하는 액티비티가 인텐트에 포함시킬 수 있는 임의의 데이터이다. 수신 액티비티는 엑스트라에 저장된 데이터를 사용할 수 있다.  인텐트에 들어가는 데이터는 "키"의 역할을 하는 name과 그에 해당하는 "값"인  value가 짝을 이루어 저장된다.

![enter image description here](http://s9.postimg.org/uwnmjz7rz/activityresult_diagram.jpg)

####<암시적 인텐트>
>인텐트 객체 
>- **컴포넌트 이름(component name)** : 인텐트를 처리하는 타깃 컴포넌트의 이름이다. 타킷 컴포넌트의 완전한 이름과 패키지 이름을 적어주면 된다. 만약 컴포넌트의 이름이 없으면 암시적 인텐트가 되어서 안드로이드가 최적의 타깃 컴포넌트를 찾아준다.
>- **액션(action)** : 
>>|   상수   |    타깃 컴포넌트   |  액션  |
| :-------- | :--------:| :--|
| ACTION_VIEW	|  액티비티  | 데이터를 사용자에게 표시한다.   |
| ACTION_EDIT		|  액티비티  |  사용자가 편집할 수 있는 데이터를 표시한다.  |
| ACTION_MAIN	|  액티비티  | 태스크의 초기 액티비티로 설정한다.  |
| ACTION_CALL 	|  액티비티  | 전화 통화를 시작한다.  |
| ACTION_SYNC	|  액티비티  | 모바일 장치의 데이터를 서버 상의 데이터와 일치시킨다.  |
| ACTION_DIAL 	|  액티비티  | 전화번호를 누르는 화면을 표시한다. |

>- **데이터(data)** :  실행될 컴포넌트가 특정 데이터를 필요로 한다면 추가할 수 있다. 예를 들어 액션이 음악을 재생한다면 데이터는 음악 파일의 경로가 될 수 있다. 데이터는 URI 형식을 사용한다. 
>- **카테고리(category)** : 해당 액티비티의 분류에 해당한다. 
>|   상수   |    설명  | 
| :-------- | :--------:|
| android.intent.category.APP_DEFAULT |  별도의 카테고리가 없는 액티비티 | 
| android.intent.category.APP_BROWSER |  웹 브라우저 액티비티 | 
| android.intent.category.APP_CONTACTS |  주소록 액티비티  |  
| android.intent.category.APP_CALENDAR |  달력 액티비티  | 
| android.intent.category.APP_EMAIL |  이메일 발송 액티비티  | 
| android.intent.category.APP_GALLERY |  갤러리 액티비티  |
| android.intent.category.APP_MAPS |  지도 액티비티  | 
| android.intent.category.APP_MESSAGING |  메시지 발송 액티비티  | 
| android.intent.category.APP_MUSIC | 음악 재생 액티비티  | 


![enter image description here](http://s10.postimg.org/sq1o42p2x/imlicit.jpg)

**<Implicit_app/MainActivity.java >**
``` python
public class MainActivity extends AppCompatActivity {

    public void onclick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.call :
                intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:1234567890"));
                break;
            case R.id.map :
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("geo:37.30.127.2?z=10"));
                break;
            case R.id.web :
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.naver.com"));
                break;
            case R.id.contact :
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("content://contacts/people/"));
                break;
            case R.id.B_app :
                intent = new Intent();

                ComponentName componentName = new ComponentName(
                        "mashup.ac.kr.b_app",
                        "mashup.ac.kr.b_app.MainActivity");
                intent.setComponent(componentName);
                intent.putExtra("Name","Jungmin");
                break;
        }

        if (intent != null){
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
```


##인텐트와 태스크
![enter image description here](http://s12.postimg.org/xf7pj6eul/task_ex1.jpg)

사용자에게는 이러한 지도 뷰어와 같은 액티비티가 우리 애플리케이션의 일부인 것처럼 보일 것이다. 실제로는 지도 뷰어는 다른 애플리케이션에 정의되어 있고 그 애플리케이션 프로세스 안에서 실행된다. 안드로이드는 두 개의 액티비티를 같은 태스크(task)안에서 유지한다. **태스크(task)**는 `어떤 작업을 수행하기 위하여 사용자가 상호작용하는 액티비티들의 그룹`이다. 안드로이드는 다른 애플리케이션의 액티비티도 동일한 테스크 안테 유지시킴으로써 중간에 끊어짐이 없이 매끄러운 사용자 경험을 제공한다. 

장치의 홈 화면은 대부분의 태스크가 시작되는 곳이다. 사용자가 애플리케이션을 터치하면 애플리케이션의 태스크를 찾는다. 만약 애플리케이션이 최근에 실행되지 않아서 애플리케이션을 위한 태스크가 없다면 새로운 태스크가 생성되고 애플리케이션의 메인 액티비티가 스택의 바닥에 추가된다. (루트 액티비티)

  [1]: http://devbible.tistory.com/30

