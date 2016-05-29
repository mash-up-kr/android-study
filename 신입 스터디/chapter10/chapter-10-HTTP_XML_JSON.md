# Chapter10
## 연결관리자
안드로이드는 앱을 인터넷이나 로컬 네트워크에 연결시켜주며 네트워크 운용이 가능하게 해준다.

모바일 환경에서 이용 가능한 네트워크 접속 방법은 모바일 네트워크, 무선 인터넷(Wi-Fi), 블루투스 등 여러가지가 있다.

#### 네트워크 연결 확인
네트워크 작업을 하기전에 먼저 네트워크나 인터넷에 연결되어 있는지 확인해야 한다. ConnectivityManager 클래스의 `getSystemService()` 메소드를 사용한다.

```java
getSystemService(CONNECTIVITY_SERVICE)
```
이 호출에 의해 리턴되는 ConnectivityManager 객체가 연결관리자이다.
연결관리자는 다음과 같은 기능을 제공한다.
- 사용 가능한 네트워크에 대한 정보를 조사한다
- 각 연결 방법의 현재 상태를 조사한다
- 네트워크 연결 상태가 변경될 때 모든 응용프로그램에게 인텐트로 알린다
- 한 연결에 실패하면 대체 연결을 찾는다

ConnectivityManager 객체의 `getAllNetworkInfo` 메소드를 사용해서 모든 네트워크의 정보를 받아올 수 있다. 이 메소드는 NetworkInfo의 array를 반환하므로 다음과 같이 사용한다.
```java
NetworkInfo[] info = manager.getAllNetworkInfo();
```

마지막으로 네트워크의 연결 상태를 체크해야 한다. `getState` 메소드를 사용한다.
```java
for (int i = 0; i < info.length; i++) {
	if (info[i].getState() == NetworkInfo.State.CONNECTED)
    	Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
}
```

장비의 네트워크 상태를 조사하려면 매니페스트에 다음 퍼미션을 주어야 한다.
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

다음 코드를 실습해보자
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView result = (TextView) findViewById(R.id.textView);
        String sResult = "";

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Network[] network = manager.getAllNetworks();
        NetworkInfo[] info = new NetworkInfo[network.length];

        for(int i = 0; i < network.length; i++)
                info[i] = manager.getNetworkInfo(network[i]);

        for(NetworkInfo n : info) {
            sResult += (n.toString() + "\n\n");
        }

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            sResult += ("Active: \n" + activeNetwork.toString());
            result.setText(sResult);
        }
    }
}
```

##HTTP 요청
인터넷 연결 체크가 끝나면 네트워크 동작을 무엇이든지 할 수 있다. 이번에는 url을 이용해서 웹사이트의 html을 가져와보자.

안드로이드는 이런 동작을 위해 HttpURLConnection과 URL 클래스를 제공한다. 

URL 클래스는 아래와 같이 사용한다.
```java
String link = "http://www.google.com";
URL url = new URL(link);
```
규칙에 맞지 않은 무효한 URL을 설정하면 MalformedURL.Exception 예외가 발생한다.

다음, `openConnection` 메소드를 호출해서 HttpURLConnection 객체를 받아온다. 그 후 HttpURLConnection의 `connect` 메소드를 호출한다.
```java
HttpURLConnection conn = (HttpURLConncetion) url.openConnection();
conn.connect();
```

URLConnection 자체는 추상 클래스이며 프로토콜에 따라 Http, Https, Jar 등의 연결 객체가 리턴된다. 요청한 프로토콜에 따라 연결 객체의 타입이 달라지는데 통상적인 웹 주소라면 HttpURLConnection 객체가 리턴된다.

| 메소드 | 설명 |
|--------|--------|
| setConnectTimeout(int timeout) | 연결제한시간을 1/1000초 단위로 지정한다. 0이면 무한히 대기한다. |
| setReadTimeout (int timeout) | 읽기 제한 시간을 지정한다. 0이면 무한 대기한다 |
|setUseCaches (boolean newValue) | 캐시 사용 여부를 지정한다. |
| setDoInput (boolean newValue) |  입력을 받을 것인지를 지정한다. |
| setDoOutput (boolean newValue) |  출력을 할 것인지를 지정한다. |
| setRequestProperty (String field, String newValue) | 요청 헤더에 값을 설정한다. |
| addRequestProperty (String field, String newValue) | 요청 헤더에 값을 추가한다. 속성의 이름이 같더라도 덮어쓰지는 않는다. |

기본 속성을 설정한 후 각 연결 타입별로 속성을 추가로 설정할 수 있다.  
Http 연결의 경우 요청 방식을 지정해야 하는데, `setRequestMethod(String method)` 메소드로 GET, POST 등의 방법을 지정한다. 별 지정이 없으면 디폴트인 GET 방식이 적용된다.

모든 속성 설정이 완료되었으면 `getResponseCode()` 메소드로 서버에 요청을 보내는데 이 메소드는 원격지의 서버로 명령을 보내고 응답 결과를 받는다.

요청이 무사히 전달되었다면 HTTP_OK(200)가 리턴된다. URL이 발견되지 않으면 HTTP_NOT_FOUND(404)가 리턴되며, 인증에 실패하면 HTTP_UNAUTHORIZED(401)에러 코드가 리턴된다.

네트워크 액세스를 하는 프로그램은 다음 퍼미션이 있어야 한다.
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

다음 코드를 실습해보자.
```java
public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    MyNetworkThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(MainActivity.this, "Wait", "Downloading...");
                thread = new MyNetworkThread("http://www.google.com");
                thread.start();
            }
        });
    }

    class MyNetworkThread extends Thread {
        String addr;
        String result;

        MyNetworkThread(String addr) {
            this.addr = addr;
            result = "";
        }

        public void run() {
            StringBuilder html = new StringBuilder();
            try {
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        for (;;) {
                            String line = br.readLine();
                            if (line == null) break;
                            html.append(line + '\n');
                        }
                        br.close();
                        result = html.toString();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mAfterDown.sendEmptyMessage(0);
        }
    }

    Handler mAfterDown = new Handler() {
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            TextView result = (TextView)findViewById(R.id.textView);
            result.setText(thread.result);
        }
    };
}
```

#XML
XML은 웹 서비스의 기본 데이터 포맷으로 서버와 클라이언트의 중요한 통신수단이다. 서버는 클라이언트의 요청을 받아들여 처리하고 그 결과를 XML로 리턴하며 클라이언트는 XML을 분석하여 처리 결과를 얻는다.

안드로이드 xml parser에는 DOM, SAX, XmlPullParser 이렇게 세 종류가 있다. 자바 플랫폼에서 제공하는 DOM이나 SAX를 사용해도 되지만 안드로이드에서는 안드로이드가 제공하는 XmlPullParser를 사용해서 파싱하는 것을 권장한다.

XMLPullParser를 사용해서 xml를 파싱해보자.

## Open API
XMLPullParser를 사용하기에 앞서 이번 실습에서는 네이버에서 제공하는 오픈 API를 이용해서 xml을 받아올 것이다. [developers.naver.com](http://developers.naver.com) 페이지를 열어보자.  
(네이버 외에도 다음이나 구글, 인스타그램, 페이스북 등 다양한 곳에서 오픈 API를 제공한다.)

오픈 api 사용에 필요한 키값을 얻기 위해 로그인 후 Application-애플리케이션 등록을 한다. 예제에서는 검색 API를 사용할 것이므로 API 권한관리 탭에서 해당 api에 체크한다.

예제는 책을 검색하는 API를 사용한다. 그 외의 사용법을 알고 싶다면 developers.naver.com의 개발가이드를 참고하자.

## 기본 아키텍처
![OpenApi_Architecture](http://)  
1. OpenAPI에 보낼 URL을 작성해서 NetworkManager를 실행한다
2. 해당 url에서 받아온 xml 결과를 String으로 저장하여 반환한다
3. String에 저장한 xml 내용을 XMLParser에 전달한다
4. XML을 parsing해서 적절한 형태(배열 등)의 형태로 반환한다

## XMLPullParser
#### XmlPullParser의 주요 메소드
- getEventType() : 현재 읽은 XML 문서의 이벤트 종류 반환
	- START_DOCUMENT: 문서의 시작 부분을 읽음
    - END_DOUCUMENT: 문서의 마지막 부분을 읽음
    - START_TAG: 시작 태그(<>)를 읽음 -> 속성 부분 처리
    - END_TAG: 종료 태그(</>)를 읽음
    - TEXT: 태그 사이의 값을 읽음
- next(): xml 문서의 다음 항목으로 이동
- getName(): 현재 읽은 태그명 또는 속성명을 반환
- getText(): 현재 읽은 태그 사이의 값 반환

```java
    try{
//        XmlPullParser를 생성하기 위한 factory를 준비
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        XmlPullParser 생성
        XmlPullParser parser = factory.newPullParser();
//        String으로 전달받은 xml을 XmlPullParser에 설정
        parser.setInput(new StringReader(xml));

        for (int eventType = parser.getEventType();
             eventType != XmlPullParser.END_DOCUMENT;
             eventType = parser.next()) {

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    
                    break;
                case XmlPullParser.END_TAG:
                    
                    break;
                case XmlPullParser.TEXT:
                    
                    break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
```

## Parser
Xml 문서를 읽어서 하나의 요소를 하나의 DTO에 저장하는 작업으르 반복한다. 생성한 DTO는 ArrayList 등의 컬렉션 객체에 저장한다.
![parser01](http://)  

#### XML 문서와 DTO의 관계
![parser02](http://)  
- XML 문서의 한 항목 정보 -> 하나의 DTO 객체에 저장
- 한 항목이 포함한 태그(속성) -> DTO 객체의 하나의 멤버변수에 저장
- 하나의 XML 문서 내의 모든 항목들 -> 하나의 컬렉션 객체(ArrayList 등)에 저장

## 실습
##### activity_main.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/LinearLayout1"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="${relativePackage}.${activityClass}" >


    <EditText
        android:id="@+id/etKeyword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
    
    <Button
        android:id="@+id/btnSearch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="onClick"
        android:text="검색하기" />

    <ListView
        android:id="@+id/lvResult"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" >
    </ListView>

</LinearLayout>
```

##### ItemDto.java
```java
public class ItemDto {
    String title;
    String author;
    String price;
    
    // getter, setter 정의

    @Override
    public String toString() {
        return "제목: " + title + "\n저자: " + author;
    }
}
```

##### MyXmlParser.java
```java
public class MyXmlParser {

    //	Parsing 대상 태그를 구분용 상수
    final int NO_TAG = 0;
    final int TITLE = 1;
    final int AUTHOR = 2;
    final int PRICE = 3;


    public ArrayList<ItemDto> parse(String xml) {
//		Parsing 결과 생성한 dto 를 저장하기 위한 list
        ArrayList<ItemDto> list = new ArrayList<ItemDto>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            ItemDto dto = null;
            int tagType = NO_TAG;

            parser.setInput(new StringReader(xml));

//            읽어오는 부분의 이벤트를 구분하여 반복 수행
//            문서의 마지막이 될 때까지 반복
            for (int eventType = parser.getEventType();
                 eventType != XmlPullParser.END_DOCUMENT;
                 eventType = parser.next()) {

                switch(eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
//					네이버 책 검색의 title 태그의 경우 첫 부분에 item 태그에 속하지 않고 별도로 나오는 경우가 있으므로
//					item 태그를 읽고 객체를 생성한 후에만 읽도록 조건 검사 수행
                        if (parser.getName().equals("item")) {
                            dto = new ItemDto();
                        } else if (dto != null && parser.getName().equals("title")) {
                            tagType = TITLE;
                        } else if (parser.getName().equals("author")) {
                            tagType = AUTHOR;
                        } else if (parser.getName().equals("price")) {
                            tagType = PRICE;
                        } else {
                            tagType = NO_TAG;		// Parsing 대상 이외의 tag를 읽었을 경우
                        }

                        break;
                    case XmlPullParser.END_TAG:
//					닫는 item 태그가 나올 경우 dto 에 저장할 항목들이 다 완료되었음을 의미
//					리스트에 저장 후 새로운 dto 생성, 새로운 tag를 읽도록 초기화
                        if (parser.getName().equals("item")) {
                            list.add(dto);
                            dto = new ItemDto();
                            tagType = NO_TAG;
                        }
                        break;
                    case XmlPullParser.TEXT:
//                        직전에 읽은 tag의 종류에 따라 dto에 항목 저장
                        if (tagType == TITLE) {
                            dto.setTitle(parser.getText());
                        } else if (tagType == AUTHOR) {
                            dto.setAuthor(parser.getText());
                        } else if (tagType == PRICE) {
                            dto.setPrice(parser.getText());
                        }
                        break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
```

##### NetworkThread.java
clientId와 clientSecret 부분에 맨 처음 네이버 developer 사이트에서 애플리케이션을 생성하고 받은 clientId, clientSecret 값을 저장한다.  
검색 Api의 개발가이드를 보면 위의 두개 값은 헤더에 붙여서 전송해야 하는데, HttpURLConnection의 `setRequestProperty()`를 사용해서 헤더에 붙여주면 된다.
```java
public class NetworkThread extends Thread{

    Handler handler;
    String addr;

    public NetworkThread(Handler handler, String addr) {
        this.handler = handler;
        this.addr = addr;
    }


    @Override
    public void run() {
        String resultHtml = downloadHtml(addr);

        Message msg = new Message();
        msg.obj = resultHtml;

        handler.sendMessage(msg);
    }


    private String clientId = "네이버 developer 페이지에서 생성한 자신의 clientID";
    private String clientSecret = "마찬가지로 자신의 clientSecret 값";

    String downloadHtml(String addr) {
        StringBuilder receivedData = new StringBuilder();

        try {
            URL url = new URL(addr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            clientId와 clientSecret 값을 헤더에 넣어준다.
            conn.setRequestProperty("X-Naver-Client-Id", clientId);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            if (conn != null) {
                conn.setConnectTimeout(10000);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    for (;;) {
                        String line = br.readLine();
                        if (line == null) break;
                        receivedData.append(line + '\n');
                    }
                    br.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
            return "Error : " + e.getMessage();
        }
        return receivedData.toString();
    }

}

```


##### MainActivity.java

```java
public class MainActivity extends AppCompatActivity {

    String url;

    EditText etQuery;
    ListView lvResult;
    ArrayList<ItemDto> list;
    ArrayAdapter<ItemDto> adapter;
    MyXmlParser parser;

    ProgressDialog progDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuery = (EditText)findViewById(R.id.etKeyword);
        lvResult = (ListView)findViewById(R.id.lvResult);

        list = new ArrayList<ItemDto>();
        adapter = new ArrayAdapter<ItemDto>(this, android.R.layout.simple_list_item_1, list);
        lvResult.setAdapter(adapter);

        parser = new MyXmlParser();
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                url = "https://openapi.naver.com/v1/search/book.xml";
                String query = etQuery.getText().toString();

                try {
//    		URL의 get 방식으로 데이터를 전송하기 위하여 한글을 UTF-8 로 인코딩
                    query = URLEncoder.encode(query, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//			검색쿼리는 아래와 같은 방식으로 주소에 붙여준다
                url += "?query=" + query;
                url += "&display=10&start=1";

                progDlg = ProgressDialog.show(this, "검색중", "해당 단어를 검색중입니다.");

                NetworkThread thread = new NetworkThread(handler, url);
                thread.setDaemon(true);
                thread.start();

                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progDlg.dismiss();

            String xml = (String)msg.obj;
            Log.e("MainActivity", xml);
            ArrayList<ItemDto> list = parser.parse(xml);

//			Parsing 수행 후 결과 list를 apdater에 추가(기존의 adapter 내용을 지운 후 새로이 전체 추가)
            adapter.clear();
            adapter.addAll(list);

        }
    };

}

```


# JSON
JSON은 웹에서 정보를 주고 받는 경량화된 방법이다. XML보다 좀 더 간략화되었다.  
JSON의 특징 및 장점은 다음과 같다.
- 데이터 파일이 단순한 유니코드 텍스트 파일이어서 사람이 읽을 수도 있고 직접 편집도 가능하다. 하지만 형식이 너무 함축적이어서 XML보다 가독성은 떨어진다.
- 단순한 텍스트 파일이어서 네트워크로 전송하기 편리하며, 텍스트를 읽고 쓸 수 있는 모든 언어나 플랫폼에서 사용가능하다.
- 헤더 네임스페이스 같은 형식적인 정보다 구두점이 거의 없고 정보 자체만을 가지므로 XML보다 일반적으로 길이가 짧다.
- 대부분의 언어에 JSON 파서가 라이브러리 형태로 제공되므로 직접 문자열을 파싱할 필요가 없다. 안드로이드도 JSON 파서를 기본 제공한다.
- 많이 사용되므로 포맷이 규격화 되어있고, 신뢰성있는 라이브러리를 지원받을 수 있다.

#### JSON 형식
JSON 문서의 형태는 아래와 같다.
1. 배열: []안에 값을 콤마로 구분해서 나열한다. 순서대로 배열 요소의 순서가 매겨진다. ex) [1, 2, 3]
2. 객체: {}안에 '이름:값'의 형태로 멤버 하나를 표현하고 각 멤버는 콤마로 구분한다. 순서가 아닌 이름으로 읽기 때문에 멤버의 순서는 의미가 없다. ex) {"name":"홍길동", "age":24} 식으로 표기한다.
3. 단순 값: 수치, 문자열, 논리형, null 등의 4가지 타입을 지원한다.


```
{
    "result": {
    "total": 3,
            "userquery": "목동",
            "items": [
                {
                    "address": "서울특별시 양천구 목동 ", 
                    "addrdetail": {
                            "country": "대한민국",
                            "sido": "서울특별시",
                            "sigugun": "양천구",
                            "dongmyun": "목동",
                            "rest": ""
                     },
                    "isRoadAddress": false,
                        "point": {
                            "x": 126.8711937,
                            "y": 37.5303834
                        }
                },
                {
                    "address": "경기도 광주시 목동 ",
                    "addrdetail": {
                            "country": "대한민국",
                            "sido": "경기도",
                            "sigugun": "광주시",
                            "dongmyun": "목동",
                            "rest": ""
                    },
                    "isRoadAddress": false,
                        "point": {
                            "x": 127.1989000,
                            "y": 37.3817000
                        }
                }
            ]
    }
}
```

#### JSON 파서
org.json 패키지가 제공하는 클래스를 이용해서 JSON 파일을 읽어들인다. (각 언어별로 JSON 파서를 제공한다.)

**JSONArray** 클래스는 JSON 파일에서 배열을 읽어들인다. 생성자로 JSON 문자열을 전달하면 문자열을 파싱하여 내부 메모리에 배열 형태로 저장한다.

- Object get (int index)
- int getInt (int index)
- String getString (int index)
- boolean getBoolean (int index)
- JSONArray getJSONArray (int index)
- JSONObject getJSONObject (int index)

**JSONObjcet** 클래스는 JSON 파일에서 객체를 읽어들인다. 생성자로 JSON 문자열을 전달하면 파싱 후 객체의 멤버 집할을 가지게 된다.

- Object get (String key)
- int getInt (String key)
- String getString (String key)
- boolean getBoolean (String key)
- JSONArray getJSONArray (String key)
- JSONObject getJSONObject (String key)

JSONArray의 메서드와 목록은 동일하지만 첨자가 아니라 멤버의 이름을 인수로 받는다.

## GSON
실습에서는 기본으로 제공되는 JSON 파서 말고 구글 라이브러리 중 하나인 GSON을 사용할 것이다. GSON은 json String과 아이템의 틀이 되는 Dto 클래스를 만들어서 그 클래스를 인자로 넘겨주면 XmlPullParser와 같이 Parser를 따로 만들지 않아도 알아서 json을 Dto 클래스에 적용시킨다.

##### fromJson() 메소드
```java
ResultObject resultObject = new Gson().fromJson(jsonString, ResultObject.class);
```
Gson()객체를 선언하고 `fromJson()`에 Json 문서가 String 값으로 저장되어 있는 jsonString과 Json 아이템들의 틀을 정의한 Dto 클래스인 ResultObject 클래스를 넘겨준다. jsonString이 틀에 맞게 정리되어 ResultObject 객체로 리턴된다.

여기서 Dto 안의 멤버 변수들의 이름이 Json의 객체명과 일치해야 하는 점을 유의하자.  
만약 Json 문서와 다른 이름으로 사용하고 싶다면 해당 변수명 앞에 `@SerializedName("*json쪽의 이름*")`을 붙이면 된다.

## 

이번 실습에서도 네이버의 오픈 Api를 이용한다. 지도 Api를 사용해서 주소를 좌표값으로 바꾸는 실습을 진행할 것이다. API 권한 설정에서 지도 API에도 체크를 해야 지도 Api를 사용할 수 있다.

(주소를 좌표값으로 바꾸는 것 외에도 다른 기능이 궁금하다면 [https://developers.naver.com/docs/map/overview](https://developers.naver.com/docs/map/overview) 여기를 참고하세요)

본격적으로 시작하기에 앞서 build.gradle에 아래와 같이 추가하고 동기화한다.
```
dependencies {
    ...
    compile 'com.google.code.gson:gson:2.6.2'
}
```

##### activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.example.testing.jsonparser.MainActivity">

    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/editText"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:hint="주소입력 ex) 목동"
        />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="주소 -> 좌표 변환"
        android:id="@+id/button"
        android:layout_below="@+id/editText"
        android:layout_centerHorizontal="true" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="변환결과: "
        android:layout_marginTop="28dp"
        android:layout_below="@+id/button"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:id="@+id/textView2" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="New Text"
        android:id="@+id/tvResult"
        android:layout_below="@+id/textView2"/>
</RelativeLayout>
```


##### ResultObject.java
```java
public class ResultObject {
    Result result;

    public class Result {
        int total;
        String userquery;
        ItemDto[] items;

        public ItemDto[] getItems() {
            return items;
        }
    }

    public Result getResult() {
        return result;
    }
}
```

객체 안에 객체가 들어간다면 class Point와 같이 내부에 클래스를 선언해주거나 AddrDetail과 같이 새로운 자바 파일로 클래스를 선언해줘도 된다.
##### ItemDto.java
```java
public class ItemDto {
    String address;
    @SerializedName("addrdetail")
    AddrDetail addrDetail;
    Boolean isRoadAddress;
    Point point;

    class Point {
        float x;
        float y;
    }

    //getter, setter 정의
}
```

##### AddrDetail.java
```java
public class AddrDetail {
    String country;
    String sido;
    String sigugun;
    String dongmyun;
    String rest;

    // getter, setter 정의
}
```

##### NetworkThread.java
XmlPullParser의 NetworkThread와 동일하므로 생략한다.

##### MainActivity.java
MainActivity도 XmlPullParser의 MainActivity와 거의 동일하다.  
Json String을 받아온 뒤 핸들러에서 GSON을 사용했다.
```java
public class MainActivity extends AppCompatActivity {
    String url;

    Button button;
    EditText etAddress;
    TextView tvResult;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        etAddress = (EditText) findViewById(R.id.editText);
        tvResult = (TextView) findViewById(R.id.tvResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://openapi.naver.com/v1/map/geocode?encoding=utf-8&coord=latlng&output=json&query=";
                String query = etAddress.getText().toString();

                try {
                    url += URLEncoder.encode(query, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                progressDialog = ProgressDialog.show(MainActivity.this, " 변환중", "해당 좌표를 변환중입니다.");

                NetworkThread thread = new NetworkThread(handler, url);
                thread.setDaemon(true);
                thread.start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();

            String jsonString = (String)msg.obj;
            Log.e("MainActivity", jsonString);
//            GSON이 사용되는 부분
            ResultObject resultObject = new Gson().fromJson(jsonString, ResultObject.class);

            String result = "";
            for(ItemDto item: resultObject.getResult().getItems()) {
                result += "좌표: ";
                result += item.getPoint().x + ", " + item.getPoint().y + "\n";
            }

            tvResult.setText(result);
        }
    };
}
```











