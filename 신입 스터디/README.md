```
스터디 진행 : 2016년 5월 7일, 양소현
최초 작성자 : 양소현
최초 작성일 : 2016년 5월 5일
마지막 수정 : 2016년 5월 7일, 양소현
```

# Thread & Handler & Looper


**Thread**

컴퓨터 프로그램 수행 시 프로세스 내부에 존재하는 수행 경로, 즉 일련의 실행 코드. 프로세스는 단순한 껍데기일 뿐, 실제 작업은 스레드가 담당한다. 프로세스 생성 시 하나의 주 스레드가 생성되어 대부분의 작업을 처리하고 주 스레드가 종료되면 프로세스도 종료된다. 하나의 운영 체계에서 여러 개의 프로세스가 동시에 실행되는 환경이 멀티태스킹이고, 하나의 프로세스 내에서 다수의 스레드가 동시에 수행되는 것이 멀티스레딩이다


**Handler**

![handler_explain.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/handler_explain.JPG?,raw=true)

안드로이드는 UI 스레드라는 것이 존재하는데 UI 와 관련된 작업은 UI스레드만 할 수 있다. 
그래서 안드로이드는 백그라운드 스레드와 UI 스레드의 통신 방법으로 핸들러(Handler)를 제공한다.
핸들러는 메시지를 받거나 보낼수 있고, 메시지에 따라 특정 작업을 실행 할 수 있다. 


**Looper**

![LooperHandler.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/LooperHandler.JPG?,raw=true)

메인 스레드는 내부적으로 Looper를 가지며 그 안에는 Message Queue가 포함된다. Message Queue는 스레드가 다른 스레드나 혹은 자기 자신으로부터 전달받은 Message를 기본적으로 선입선출 형식으로 보관하는 Queue이다. Looper는 Message Queue에서 Message나 Runnable 객체를 차례로 꺼내 Handler가 처리하도록 전달한다. Handler는 Looper로부터 받은 Message를 실행, 처리하거나 다른 스레드로부터 메시지를 받아서 Message Queue에 넣는 역할을 하는 스레드 간의 통신 장치를 말한다.



##1. java의 Thread 사용 


![thread_1.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/thread_1.JPG?,raw=true)
![thread_2.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/thread_2.JPG?,raw=true)
![thread_3.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/thread_3.JPG?,raw=true)

**Button 생성 & inflation**
```XML
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="kr.co.mash_up.asynctask_example.MainActivity"
    android:orientation="vertical">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="시작"
        android:id="@+id/start_btn"
        >

    </Button>
</LinearLayout>

```
```JAVA
public class MainActivity extends AppCompatActivity {
    Button start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        start_btn=(Button)findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
```
**Thread 생성**

```JAVA
public class MainActivity extends AppCompatActivity {
    Button start_btn;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_btn = (Button) findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "첫번째 버튼 클릭됨.");

                RequestThread thread = new RequestThread();
                thread.start();


            }
        });
    }

    class RequestThread extends Thread {

        public void run() {
            for (int i = 0; i < 100; i++) {
                println("#" + i + " : 호출됨.");

                try {
                    Thread.sleep(500);//0.5초
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void println(String data) {
            Log.d(TAG, data);

        }

    }
}

```



##2. Handler 사용

**textView 생성 & inflation **

```XML
   <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="시작"
        android:id="@+id/textView"/>

```

```JAVA
public class MainActivity extends AppCompatActivity {
    Button start_btn;
    private static final String TAG = "MainActivity";
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

```

**handler 사용하지 않았을 시 오류**

```JAVA
  public void onClick(View v) {
                Log.d(TAG, "첫번째 버튼 클릭됨.");
                
                textView.setText("스레드 시작함");

                RequestThread thread = new RequestThread();
                thread.start();
            }
        });

```

```JAVA
 public void println(String data) {
            Log.d(TAG, data);
            textView.setText(data);//오류남(handler미사용시 UI접근불가능)

        }


```
**handler class정의 후 handler 객체 생성**
```JAVA
 class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

```
```JAVA
public class MainActivity extends AppCompatActivity {
    Button start_btn;
    private static final String TAG = "MainActivity";
    TextView textView;
    ResponseHandler handler = new ResponseHandler();

```

**handler를 통해 message전달**
```JAVA
public void println(String data) {
            Log.d(TAG, data);
          //  textView.setText(data);//오류남(handler미사용시 UI접근불가능)

            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            message.setData(bundle);

            handler.sendMessage(message);

        }

```

```JAVA
 class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            

            Bundle bundle= msg.getData();
            String data = bundle.getString("data");

            textView.setText(data);
        }
    }

```


##3. Runnable 객체 사용
http://micropilot.tistory.com/1994참고
새 스레드는 Thread() 생성자로 만들어서 내부적으로 run()을 구현하던지, Thread(Runnable runnable) 생성자로 만들어서 Runnable 인터페이스를 구현한 객체를 생성하여 전달하던지 둘 중 하나의 방법으로 생성하게 된다. 후자에서 사용하는 것이 Runnable로 스레드의 run() 메서드를 분리한 것이다. 따라서 Runnable 인터페이스는 run() 추상 메서드를 가지고 있으므로 상속받은 클래스는 run()코드를 반드시 구현해야 한다.
앞서 언급한대로 Message가 int나 Object같이 스레드 간 통신할 내용을 담는다면, Runnable은 실행할 run() 메서드와 그 내부에서 실행될 코드를 담는다는 차이점이 있다.



**Handler 객체 생성**
```JAVA
//ResposeHandler 객체 지우고 MAinactivity 내에 Handler 객체 정의


public class MainActivity extends AppCompatActivity {
    Button start_btn;
    private static final String TAG = "MainActivity";
    TextView textView;
    Handler handler = new Handler();

```

**Thread 내에서 사용될 println 함수 내에서 Runnable객체 사용**
```JAVA
  public void println(final String data) {
            Log.d(TAG, data);
          //  textView.setText(data);//오류남(handler미사용시 UI접근불가능)

            handler.post(new Runnable(){

                public void run(){
                    textView.setText(data);
                }

                });
        }
```

##4. Thread 정지 
http://happyourlife.tistory.com/m/post/121 참고
**정지버튼 생성**
```XML
 <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="중지"
        android:id="@+id/stop_btn"
        >

    </Button>
```

**flag를 만들어 정지버튼을 눌렀을 때 Thread 정지시키기**
```JAVA

public class MainActivity extends AppCompatActivity {
    Button start_btn;
    Button stop_btn;
    private static final String TAG = "MainActivity";
    TextView textView;
    Handler mHandler = new Handler();

    int cur = 0;
    boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        start_btn = (Button) findViewById(R.id.start_btn);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "첫번째 버튼 클릭됨.");
                textView.setText("스레드 시작함");

                RequestThread thread = new RequestThread();
                thread.start();


            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;


            }
        });
    }

    class RequestThread extends Thread {


        public void run() {

            running = true;
            while (running) {


                synchronized (this) {

                    mHandler.post(new Runnable() {

                        public void run() {
                            if (cur > 100)
                                cur = 0;

                            textView.setText("count : " + Integer.toString(cur));
                            cur++;
                        }

                    });

                    try {

                        Thread.sleep(500);

                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
    }   

}
```

##5. Looper 사용

![Looper_1.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/Looper_1.JPG?,raw=true)
![Looper_2.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/Looper_2.JPG?,raw=true)

http://blog.naver.com/elder815/220533768581참고



**xml파일로 View 생성**

```XML
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="kr.co.mash_up.asynctask_example.MainActivity"
    android:orientation="vertical">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="mainthread에서 받은 data"
       />

   <EditText
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:id="@+id/main_text"
       />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Backgroundthread에서 받은 data"
            android:id="@+id/textView"/>

        <EditText
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/back_text"
            />
    </LinearLayout>
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="시작"
        android:id="@+id/start_btn"
        >

    </Button>
</LinearLayout>



```

**View inflation**
```JAVA
public class MainActivity extends AppCompatActivity {
    
    Button start_btn;
    EditText main_text;//mainthread에서 받은 문자열
    EditText back_text;//mainthread에서 보내져 backthread에서 받은 문자열
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_text =(EditText)findViewById(R.id.main_text);
        back_text =(EditText)findViewById(R.id.back_text);
        start_btn=(Button)findViewById(R.id.start_btn);
        
          start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
```

**Mainthread에서 text받아와서 Backgroundthread 객체에 넘겨주기**
```JAVA
public class MainActivity extends AppCompatActivity {

    Button start_btn;
    EditText main_text;//mainthread에서 받은 문자열
    EditText back_text;//mainthread에서 보내져 backthread에서 받은 문자열

    BackgroundThread backgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_text =(EditText)findViewById(R.id.main_text);
        back_text =(EditText)findViewById(R.id.back_text);
        start_btn=(Button)findViewById(R.id.start_btn);

      backgroundThread = new BackgroundThread();
      
 	start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inStr = main_text.getText().toString();
                Message msgToSend = Message.obtain();
                msgToSend.obj = inStr;
                backgroundThread.handler.sendMessage(msgToSend);
            
            }
        });

        backgroundThread.start();
               
    }

	class BackgroundThread extends Thread{}
}

  
```

**background thread Class & Handler 정의**


```JAVA
  class BackgroundThread extends Thread{
        BackgroundHandler handler;
        public BackgroundThread() {
            handler= new BackgroundHandler();
        }
        
        public void run(){
            Looper.prepare();
            Looper.loop();
        }
    }
    
    class BackgroundHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Message resultMsg = Message.obtain();
            resultMsg.obj = "Hello"+msg.obj ;
            
            mainHandler.sendMessage(resultMsg);
        }
    }

```

```JAVA
public class MainActivity extends AppCompatActivity {

    Button start_btn;
    EditText main_text;//mainthread에서 받은 문자열
    EditText back_text;//mainthread에서 보내져 backthread에서 받은 문자열

    BackgroundThread backgroundThread;
    MainHandler mainHandler; //mainHandler객체 정의
```

**Mainthread Handler 정의**

```JAVA

   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_text =(EditText)findViewById(R.id.main_text);
        back_text =(EditText)findViewById(R.id.back_text);
        start_btn=(Button)findViewById(R.id.start_btn);
        mainHandler= new MainHandler();//mainhandler 객체 생성
```

```JAVA
 class MainHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            String str=(String)msg.obj;
            back_text.setText(str);

        }
    }

```

# AsyncTask
http://blog.naver.com/shadowbug/220461684842 참고
![asynctask_structure.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/asynctask_structure.JPG?,raw=true)


AsyncTask는 스레드나 메시지 루프 등의 작동 원리를 몰라도 하나의 클래스에서 UI작업과 backgrond 작업을 쉽게 할 수 있도록 안드로이드에서 제공하는 클래스이다. 캡슐화가 잘 되어 있기 때문에 사용시 코드 가독성이 증대되는 장점이 있으며, 태스크 스케쥴을 관리할 수 있는 콜백 메서드를 제공하고, 필요할 때 쉽게 UI 갱신도 가능하며 작업 취소도 쉽다. 따라서 리스트에 보여주기 위한 데이터 다운로드 등 UI와 관련된 독립된 작업을 실행할 경우 AsyncTask로 간단하게 구현할 수 있다.


![asynctask_1.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/asynctask_1.JPG?,raw=true)
![asynctask_2.JPG](https://github.com/SoHyunYang/androidtest_thread/blob/master/asynctask_2.JPG?,raw=true)

**xml파일로 View 생성**
```XML
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="kr.co.mash_up.asynctask_example.MainActivity"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="전송상태"
        android:id="@+id/textView"/>

    <ProgressBar
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:id="@+id/progressBar"
        style="?android:attr/progressBarStyleHorizontal"

    />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="시작"
        android:id="@+id/start_btn"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="중지"
        android:id="@+id/end_btn"/>

</LinearLayout>
   
```
**View inflation**

```JAVA
public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;
    Button start_btn;
    Button end_btn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView=(TextView)findViewById(R.id.textView);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        start_btn=(Button)findViewById(R.id.start_btn);
        
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        end_btn=(Button)findViewById(R.id.end_btn);
        
        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        
        
    }
}
```
**inner class로 BackgroundThread 생성**
```JAVA
int value =0 ; //변수설정

```
```JAVA
  class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {

            value = 0;
            progressBar.setProgress(value);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            value = 0;
            progressBar.setProgress(value);
            textView.setText("끝남");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


            progressBar.setProgress(values[0].intValue());
            textView.setText("진행중 : " + values[0].toString());

        }

        @Override
        protected Integer doInBackground(Integer... params) {

            while(!isCancelled()) {
                value++;
                if (value >= 100) {
                    break;
                } else {
                    publishProgress(value);
                }

                try {
                    Thread.sleep(200);//0.2초
                } catch (Exception e) {
                }

            }

            return value;
        }
    }

```
**Button의 Listener 설정해 주기**
```JAVA

    start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new BackgroundTask();
                task.execute(100);

            }
        });
        end_btn=(Button)findViewById(R.id.end_btn);

        end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.cancel(true);
                textView.setText("취소됨");
            }
        });

```

###참고문헌###
Do it! 안드로이드 앱 프로그래밍


[네이버 지식백과] 스레드 [thread] (IT용어사전, 한국정보통신기술협회)

[출처] [Android Thread] 쓰레드 , 핸들러 |작성자 사자머리님

[출처] 안드로이드 백그라운드 잘 다루기 - Thread, Looper, Handler|작성자 mari


