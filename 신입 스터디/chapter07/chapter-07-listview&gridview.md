```
스터디 진행 : 2016년 3월 12일, 양소현
최초 작성자 : 양소현
최초 작성일 : 2016년 3월 11일
마지막 수정 : 2016년 3월 12일, 양소현
```

# 선택위젯
여러개의 item을 중에 하나를 선택할 수 있는 위젯

ex) 리스트뷰, 그리드뷰, 스피너, 갤러리

**선택위젯의 특징**

직접 위젯에 원본데이터를 설정할 수 없다.-> 데이터 설정 및 관리를 하기 위해 어댑터를 사용

어댑터에서 만들어주는 뷰를 이용하여(getView()method 사용) 리스트뷰의 아이템을 보여준다. 

![selectionwidget.JPG](https://github.com/SoHyunYang/androidstudy_test/blob/master/selectionwidget.JPG?,raw=true)
# List View

##1. textView 하나를 List View의 item으로 만들기
![listView1.JPG](https://github.com/SoHyunYang/androidstudy_test/blob/master/listView1.JPG?,raw=true)

**listView 생성**
```XML
    <ListView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/listView"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true" />
```
**listView inflation**

```JAVA
public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listView);

    }
```
**inner class로 Adaptor 생성**

```JAVA
 public int getCount()
```
어댑터에서 관리하는 아이템의 개수를 리턴하는 함수
```JAVA
 public Object getItem(int position)
```
해당 index에 대한 아이템을 리턴하는 함수
```JAVA
 public long getItemId(int position)
``` 
해당 index에 대한 position을 리턴하는 함수
```JAVA
public View getView(int position, View convertView, ViewGroup parent)
```
각 아이템에 보일 뷰를 리턴하는 함수

첫번째 파라미터 : 아이템의 인덱스를 의미, 리스트뷰에서 보일 아이템의 위치 정보 

두번째 파라미터 : 현재 인덱스에 해당하는 뷰 객체 

세번째 파라미터 : 이 뷰를 포함하고 있는 부모 컨테이너 객체

 
```JAVA
class ListViewAdaptor extends BaseAdapter
    {
        String[] name={"양소현","유슬기","김정민","김동희","고민규"};

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView view = new TextView(getApplicationContext());

            view.setText(name[position]);
            view.setTextSize(60.0f);
            return view;
        }
```
**listView에 Adaptor를 설정해 주기**
```JAVA
public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listView);

        adaptor = new ListViewAdaptor();
        listView.setAdapter(adaptor);
    }

```


##2. 하나의 부분화면을 List View의 item으로 만들기
![listView2.JPG](https://github.com/SoHyunYang/androidstudy_test/blob/master/listView2.JPG?,raw=true)

**XML파일로 view group 만들기 (view 구성)**

```XML
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal" android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView

        android:layout_width="100dp"
        android:layout_height="100dp"
        android:src="@drawable/flower" />
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_gravity="center">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceSmall"
        android:text="Small Text"
        android:id="@+id/names"
        android:textSize="30dp"
       />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="?android:attr/textAppearanceSmall"
        android:text="Small Text"
        android:id="@+id/phones"
        android:textSize="30dp"
         />
        </LinearLayout>
</LinearLayout>

```
**만든 view group을 화면에 붙이기**
```JAVA
public class ListItemView extends LinearLayout
{
    public ListItemView(Context context) {
        super(context);

        init(context);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);

    }

    public void init(Context context){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item, this, true);


    }

}
```
**만든 TextView안에 들어가는 text설정 해주기**

```JAVA
  public void init(Context context){

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item, this, true);

        namesTextView = (TextView)findViewById(R.id.names);
        phonesTextView = (TextView)findViewById(R.id.phones);
     

    }
    
    public void setName(String name){
        namesTextView.setText(name);
    }

    public void setPhone(String phone){
        phonesTextView.setText(phone);
    }
```
**Adaptor에 data입력, 보여줄 item 입력**
```JAVA
  class ListViewAdaptor extends BaseAdapter
    {
        String[] name={"양소현","유슬기","김정민","김동희","고민규"};
        String[] phone={"010-1111-1111","010-2222-2222","010-3333-3333","010-4444-4444","010-5555-5555"};


        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListItemView view = new ListItemView (getApplicationContext());
            view.setName(name[position]);
            view.setPhone(phone[position]);


            return view;
        }
    }
```

##3. data가 adaptor 밖에 정의 되어있을 경우  

```JAVA
public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdaptor adaptor;

    String[] name={"양소현","유슬기","김정민","김동희","고민규"};
    String[] phone={"010-1111-1111","010-2222-2222","010-3333-3333","010-4444-4444","010-5555-5555"};

```
**arrayList 객체를 따로 만들어서 data 관리**
```JAVA
   class ListViewAdaptor extends BaseAdapter
    {
       
        ArrayList items =new ArrayList();
```

**listitem에 대한 class 생성**
```JAVA
public class ListItem {
    String name;
    String phone;

    public ListItem() {
     
    }

    public ListItem(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
```
**adaptor에 item설정해주기**
```JAVA
class ListViewAdaptor extends BaseAdapter
    {

        ArrayList items = new ArrayList();

        public void addItem(ListItem item){
            items.add(item);
        }
```        


```JAVA
 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listView);

        adaptor = new ListViewAdaptor();
        adaptor.addItem(new ListItem(name[0],phone[0]));
        adaptor.addItem(new ListItem(name[1],phone[1]));
        adaptor.addItem(new ListItem(name[2],phone[2]));
        adaptor.addItem(new ListItem(name[3],phone[3]));
        adaptor.addItem(new ListItem(name[4],phone[4]));



        listView.setAdapter(adaptor);


    }
```
**ListItem type으로 ArrayList를 만들고 ListItem객체를 만들어 ListItem 속에 정의되어 있는 data를 화면에 보여주기**
```JAVA
class ListViewAdaptor extends BaseAdapter{

        ArrayList<ListItem> items = new ArrayList<ListItem>();

```

```JAVA
        public View getView(int position, View convertView, ViewGroup parent) {

            ListItemView view = new ListItemView(getApplicationContext());
            ListItem curItem = items.get(position);

            view.setName(curItem.getName());
            view.setPhone(curItem.getPhone());

            return view;
        }
```


##4. List View의 재활용


선택위젯은 보통 데이터가 많아 스크롤할 때 뷰를 재활용해야 버벅거림 발생을 방지할 수 있다.
즉, 한번 만들어진 뷰가 화면 상태에 그대로 다시 보일 수 있도록 한다.
이것은 이미 만들어진 뷰들을 그대로 사용하면서 데이터만 바꾸어 보여주는 방식으로, convertview(현재 인덱스에 해당하는 뷰 객체)를 이용한다.

```JAVA

     public View getView(int position, View convertView, ViewGroup parent) {

            ListItemView view = null;

            if(convertView ==null){
                view = new ListItemView (getApplicationContext());
            }else{
                view=(ListItemView) convertView;
            }
```


# Grid View
테이블 형태와 유사하게 격자 모양으로 아이템 배치를 해주는 위젯

![gridview.JPG](https://github.com/SoHyunYang/androidstudy_test/blob/master/gridview.JPG?,raw=true)

**gridView 생성**
```XML
    <GridView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/gridView"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true"
        android:numColumns="3"/>
```
**gridView inflation**

```JAVA
public class MainActivity extends AppCompatActivity {

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView=(GridView)findViewById(R.id.gridView);

    }
```
**inner class로 Adaptor 생성**
```JAVA
class GridViewAdaptor extends BaseAdapter
    {
        String[] name={"양소현","유슬기","김정민","김동희","고민규"};

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView view = new TextView(getApplicationContext());

            view.setText(name[position]);
            view.setTextSize(30.0f);
            return view;
        }
```
**gridView에 Adaptor를 설정해 주기**
```JAVA
public class MainActivity extends AppCompatActivity {

    GridView gridView;
    GridViewAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView=(GridView)findViewById(R.id.gridView);

        adaptor = new GridViewAdaptor();
        gridView.setAdapter(adaptor);
    }

```

