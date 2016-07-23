

```
스터디 진행 : 2016년 7월 2일, 양소현
최초 작성자 : 양소현
최초 작성일 : 2016년 7월 1일
마지막 수정 : 2016년 7월 2일, 양소현
```

# broadcastreceiver

**broadcast란?**

일반적으로, 동사의 의미로 사용되는 브로트캐스트는 무엇인가를 동시에 모든 방향으로 던지는 것을 의미한다. 

android 장치에서는 와이파이 접속 영역에 들어갔다 나오거나 소프트웨어 패키지들이 설치되거나 전화나 문자 메세지가 오는 등 여러 event들이 발생하는데 이 때 시스템의 많은 컴포넌트들이 어떤 이벤트가 생겼는지 알 필요가 있을때 broadcast를 사용한다.




##1. broadcast intent 전송하기

브로드캐스트 인텐트를 전송하려면 인텐트를 생성하고 그것을 sendBroadcast(Intent)의 인자로 전달하면 된다.
시스템의 많은 컴포넌트들이 어떤 이벤트가 생겼는지 알 필요가 있을 때 안드로이드는 브로드캐스트 인텐트를 사용해서 그것에 관해 모든 컴포넌트에게 알려준다. 브로드캐스트 인턴트는 우리가 알고 있는 일반 인텐트와 유사하게 동작한다. 단지 차이점은, 브로드캐스트 인텐트는 여러 컴포넌트가 동시에 받을 수 있다는 것이다.
```JAVA
 void sendBroadcast (Intent intent [, String receivePermission] )

 void sendOrderedBroadcast (Intent intent, String receivePermission)
```
receivePermission : 허가받은 수신자에게만 방송을 보내고자 할때 지정.AndroidManifest.xml 의 <uses-permission> tag 에서 설정

순서있는 방송은 인텐트 필터의 android:priority 속성이 지정하는 중요도에 따라 수신순서가 결정된다.(숫자가 높은 것이 먼저 수신)

- android:enabled

시스템에 의해서 브로드캐스트 리시버가 instance화 되냐 안되냐를 true,false로 나타냄

default value : true

- android:exported

브로드캐스트 리시버가 이것의 application 외부에 있는 소스로부터 메세지를 받을 수 있냐 없냐를 true, false로 나타냄

default value : true

![batteryexample1.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/batteryexample1.JPG?raw=true)
![batteryexample2.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/batteryexample2.JPG?raw=true)

**Button 생성 & inflation**
```XML
  <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="kr.co.mash_up.wifiexample.MainActivity">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="New Button"
        android:id="@+id/button"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="129dp"
        />
</RelativeLayout>


```
```JAVA
public class MainActivity extends AppCompatActivity {


  
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setAction("exam.Action");

                sendBroadcast(intent);
            }
        });


    }
```
**CustomReceiver(Broadcast Receiver) 생성**

```JAVA
public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"CustomReceiver : " +intent.getAction(), Toast.LENGTH_LONG).show();
    }
}

```
**manifests 설정**

```XML
   <receiver
            android:name=".CustomReceiver">
            <intent-filter>
                <action android:name="exam.Action" />
            </intent-filter>
        </receiver>
```


##2. 동적 broadcast receiver

매니페스트에 등록된 독립형 브로드캐스트 수신자는 해당 Activity가 죽어도 계속 인텐트를 수신받는다. 여기서 해당 Activity가 살아있는 동안만 인텐트를 수신하게 하기 위해서는 동적 브로드캐스트 수신자를 사용하여야 한다. 이 때 동적 수신자는 매니페스트가 아닌 JAVA코드에 등록된다. 수신자를 등록할 때는 registerReceiver(Broadcast Receiver, IntentFilter)를 호출하고, 수신자를 해지할 때는 unregisterReceiver(BroadcstReceiver)를 호출한다. 동적으로 코드에 등록된 브로드캐스트 수신자는 자신을 클린업하는 것을 고려하며 onResume() 내부에서 등록하고 onPause()에서 등록을 해지한다.

- System broadcasts :
많은 시스템 이벤트들은 Intent class에 final static field로 정의되어 있다.

ex )

|Event                             | Description                                                                                    |
|----------------------------------|------------------------------------------------------------------------------------------------|
|Intent.ACTION_BOOT_COMPLETED      | Boot completed. Requires the android.permission.RECEIVE_BOOT_COMPLETED permission              |
|Intent.ACTION_POWER_CONNECTED     | Power got connected to the device.                                                             |
|Intent.ACTION_POWER_DISCONNECTED  | Power got disconnected to the device.                                                          |
|Intent.ACTION_BATTERY_LOW         | Triggered on low battery. Typically used to reduce activities in your app which consume power. |
|Intent.ACTION_BATTERY_OKAY        | Battery status good again.                                                                     |



- IntentFilter :
addCategory(String), addAction(String), addDataPath(String)등의 메소드를 호출하여 필터를 구성한다.

![intentfilter.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/intentfilter.JPG?raw=true)

위의 그림과 같이 무수히 많은 intent들이 다른 컴포넌트들에게 메시지를 보내지만, 호출을 받는 컴포넌트의 intent filter에 호출한 intent의 action 값이 정의 되어 있어야 통과 할 수 있다. 그래야 해당 intent를 컴포넌트에 전달하게 되고, 해당 컴포넌트는 intent에 실려온 메시지를 받아 작업을 수행 할 수 있게 되는 것이다. 

즉,  해당 intent를 호출 할 때, intent filter를 지정한 Activity에서는 메시지를 전달 받을 수 있게 된다.


![batteryexample1.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/batteryexample1.JPG?raw=true)
![batteryexample3.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/batteryexample3.JPG?raw=true)

**BatteryChangeReceiver(Broadcast Receiver) 생성**



```JAVA
public class BatteryChangeReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "BatteryChangeReceiver : "+intent.getAction(), Toast.LENGTH_LONG).show();


        }

}
```

```JAVA
public class MainActivity extends AppCompatActivity {


    private BatteryChangeReceiver batBR = new BatteryChangeReceiver();

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setAction("exam.Action");

                sendBroadcast(intent);
            }
        });


    }



        @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();

        filter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(batBR, filter);




    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batBR);



    }
}
```



##3. Connectivity manager를 사용하여 네트워크 연결상황  broadcast로 전달

![wifiexample1.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/wifiexample1.JPG?raw=true)
![wifiexample2.jpg](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/wifiexample2.jpg?raw=true)
![wifiexample3.jpg](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/wifiexample3.jpg?raw=true)
![wifiexample4.jpg](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/wifiexample4.jpg?raw=true)

**NetworkUtil(java file) 생성**

```JAVA
public class NetworkUtil {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

}
```


**NetworkChangeReceiver(Broadcast Receiver) 생성**



```JAVA
public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

    }
}

```

**manifests 설정(permission 추가)**

```XML
 <?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="kr.co.mash_up.wifiexample2">

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <receiver
            android:name=".NetworkChangeReceiver">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
                
            </intent-filter>
        </receiver>
    </application>

</manifest>
```

##4. Pending intent
PendingIntent는 인텐트 정보를 가지고 있다가 받을 수 있는 어플리케이션에 그 인텐트를 전달한다. 대기 상태로 만들고, 위임하는 클래스이다. 지연 인텐트를 만들때 아래 메서드 중 하나를 사용해 인텐트를 어떻게 사용할지 미리 밝혀 주어야 한다. 

getActivity(Context, int, Intent, int), getActivities(Context, int, Intent[], int), getBroadcast(Context, int, Intent, int),  getService(Context, int, Intent, int); 

|Flag                             | Description                                                                                    |
|----------------------------------|------------------------------------------------------------------------------------------------|
|int FLAG_CANCEL_CURRENT       | Flag indicating that if the described PendingIntent already exists, the current one should be canceled before generating a new one.         |
|int FLAG_NO_CREATE     | Flag indicating that if the described PendingIntent does not already exist, then simply return null instead of creating it.                                                             |
|int FLAG_ONE_SHOT  | Flag indicating that this PendingIntent can be used only once.                                                           |
|int FLAG_UPDATE_CURRENT        | Flag indicating that if the described PendingIntent already exists, then keep it but replace its extra data with what is in this new Intent. |

 
ex) PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

대기하는 intent, 조건이 맞았을때 시스템 OS에서 intent를 broadcast로 던져줌

FLAG_CANCEL_CURRENT : 조건이 맞았을 때 현재하고 있던 것 멈추고 시작함

- alarmmanager
```JAVA
void set(int type, long triggerAtTime, PendingIntent operation);

void setRepeating(int type, long triggerAtTime, long interval, PendingIntent operation)
```

|type                    | Description                                                                                    |
|----------------------------------|------------------------------------------------------------------------------------------------|
|RTC    | System.currentTimeMills() 메서드로 구한 세계 표준시로 지정한다.             |
|RTC_WAKEUP  |    위와 같되 장비도 깨운다. |
|ELAPSED_REALTIME | SystemClock.elapsedRealtime 메서드로 구한 부팅된 이후의 경과 시간으로 지정한다.                                |
| ELAPSED_REALTIME_WAKEUP      | 위와 같되 장비도 깨운다. |


triggerAtTime : type에 부합되는 시간값 

operation :  알람을 처리할 개체를 설정한다. 주로 BR을 많이 사용한다.
![alarmexample1.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/alarmexample1.JPG?raw=true)
![alarmexample2.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/alarmexample2.JPG?raw=true)

**XML 구성**
```XML
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="kr.co.mash_up.alarmexample.MainActivity" >



    <Chronometer
        android:id="@+id/chronometer"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal" />

    <Button
        android:id="@+id/setnocheck"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Set Alarm 10 sec later - alarmManager.set()" />



</LinearLayout>

```

**MainActivity 구성**
```JAVA
 public class MainActivity extends ActionBarActivity {

    Chronometer chronometer;
    Button btnSetNoCheck;
    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer)findViewById(R.id.chronometer);
        btnSetNoCheck = (Button)findViewById(R.id.setnocheck);
        btnSetNoCheck.setOnClickListener(onClickListener);

    }


    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();

            //10 seconds later
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, 10);

            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(getBaseContext(),
                            RQS_1, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager =
                    (AlarmManager)getSystemService(Context.ALARM_SERVICE);

            if(v==btnSetNoCheck){
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        cal.getTimeInMillis(), pendingIntent);
                Toast.makeText(getBaseContext(),
                        "call alarmManager.set()",
                        Toast.LENGTH_LONG).show();


        }

    };

};
}

```
**AlarmReceiver(Broadcast Receiver) 생성**



```JAVA
public class AlarmReceiver extends BroadcastReceiver {
 
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,
                "AlarmReceiver.onReceive()",
                Toast.LENGTH_LONG).show();

    }
}

```

**manifests 확인**

```XML
 <receiver
            android:name=".AlarmReceiver"
           ></receiver>

```

##5. goAsync()

브로드캐스트 수신자는 짧은 시간(10초) 동안만 살아 있으므로 그것을 사용해서 할 수 있는 일에는 제약이 따른다. 예를 들어, 비동기 API를 사용할 수 없다. onReceive(Context, intent)메소드가 실행되는 동안만 수신자가 살아있기 때문이다. 따라서 그 메소드에서 너무 과도하게 많은 일을 할 수 없다. 즉, 네트워킹도 어렵고 데이터베이스와 같은 영구적인 스토리지를 사용하는 일도 처리하기 어렵다.

**goAsync()란?**

onReceive(Context, Intent)에서 불려지는 함수로, 이 함수가 끝나고 리턴된 후에 또다른 thread를 형성하여 일을 진행하도록 하기 때문에 broadcast에 대한 응답이 thread가 진행되는 동안 살아있도록 한다.
![goasync.JPG](https://github.com/SoHyunYang/androidtest_broadcastreceiver/blob/master/goasync.JPG?raw=true)

```JAVA
final PendingResult result = goAsync();
Thread thread = new Thread(){
  public void run(){
  //코드 내용
  result.finish();
  };
  thread.start();
}
```




###참고문헌###
Do it! 안드로이드 앱 프로그래밍

실무에 바로 적용하는 안드로이드 프로그래밍

http://koreaparks.tistory.com/128

http://android-er.blogspot.kr/2015/04/example-of-using-alarmmanager-to.html

http://blog.naver.com/ruly2001/70166117812

http://www.vogella.com/tutorials/AndroidBroadcastReceiver/article.html

 [안드로이드] Broadcast Receiver|작성자 낭만캠퍼
 
 알람(AlarmManager)|작성자 루미주인
