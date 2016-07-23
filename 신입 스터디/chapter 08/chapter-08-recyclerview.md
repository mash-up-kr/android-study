# 예제로 RecyclerView 사용해보기


## RecyclerView 란

RecyclerView는 Android L Preview가 발표되면서 안드로이드 개발자들의 많은 관심을 받은 리스트 기반의 위젯이다.
일반적인 형태의 리스트는 ListView 위젯으로 충분히 구현 가능하지만, 
좀 더 다양한 형태의 리스트가 필요지면서 대부분의 사람들이 ListView를 커스텀하는데 구조적으로 한계가 있다고 생각했고,
`무한 스크롤`을 사용하는 트렌드가 생기면서 이런 구조적 한계 탓에 성능적인 이슈까지 들어나게되었다.

그래서 이 모든걸 개선하고자 나타난 위젯이 바로 RecyclerView다.

RecyclerView는 다음과 같은 요소들이 있다.
* Adapter - 아이템에 대한 View를 생성하고, ListView에서 사용하던 Adapter나 ArrayAdapter와 같은 역할을한다.
* LayoutManager - 리스트 아이템의 배치와 스크롤 방식, 형태를 결정짓는 가장 중요한 클래스다.
* ViewHolder - ListView에서 사용하던 ViewHolder 패턴을 RecyclerView에서는 강제하게 되면서 클래스가 생겼다. 아이템의 뷰들을 담고있다.
* ItemDecoration - 대이터를 나타내는 뷰를 제외하고 부수적인 뷰를 나타낼 때 사용한다.
* ItemAnimation - 아이템을 나타내는 뷰의 애니매이션 효과에 대해 정의 할 때 사용한다.

## RecyclerView 실습

이제 간단한 실습을 통해서 RecyclerView를 사용하는 법에 대하여 익혀보자
RecyclerView를 사용하기 전에 먼저 앱 모듈의 dependency에 RecyclerView의 서포트 라이브러리를 추가해야한다.

``` gradle
dependencies {
    ...
    compile 'com.android.support:recyclerview-v7:23.2.1'
}

```

앱 모듈의 build.gradle에 위와 같이 한줄을 추가해주면 이제 RecyclerView를 사용하기위한 준비는 끝났다.


### RecyclerView의 ViewHolder

우선 가장 먼저 RecyclerView를 통하여 표현 할 아이템의 ViewHolder 클래스를 만들어본다.
우리가 만들 ViewHolder 클래스를 생성해준다. `SampleViewHolder`라는 클래스를 만들어보자.

``` java
public class SampleViewHolder {
}
```

===

ListView와는 다르게 RecyclerView에서는 RecyclerView.ViewHolder를 상속받은 ViewHolder를 사용해야한다.
우리가 만들어준 SampleViewHolder클래스가 RecyclerView.ViewHolder를 상속하도록 만들어준다.

```java
public class SampleViewHolder extends RecyclerView.ViewHolder {
    public SampleViewHolder(View itemView) {
        super(itemView);
    }
}
```

ViewHolder의 생성자를 super를 통해 꼭 호출해줘야 하기 때문에 생성자도 함께 추가해준다.

===

이제 ViewHolder의 래이아웃을 구성해주자.

예제에서는 서포트 라이브러리로 지원하는 마테리얼 디자인의 CardView를 사용했다.
다시 한번 app 모듈 build.gradle 파일의 dependencies에 다음과 같이 추가해준다.

``` gradle
dependencies {
    ...
    compile 'com.android.support:cardview-v7:23.2.1'
}
```

res/layout 에 ViewHolder의 래이아웃으로 사용 할 xml 파일을 생성해준다.

``` xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/sample_vh_tv_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="15dp"
        android:gravity="center"
        android:textColor="#000000"
        android:textStyle="bold"
        android:textSize="22sp" />
</android.support.v7.widget.CardView>
```
CardView를 최상위 뷰로, TextView 하나로 타이틀만 표시하는 간단한 뷰로 구성해봤다.

===

이제 래이아웃을 구성했으니 전 단계에서 만들어준 ViewHolder에 래이아웃에서 구성해준 뷰들을 추가한다.

``` java
public class SampleViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvTitle;

    public SampleViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.sample_vh_tv_title);
    }
}
```

우리는 TextView 만을 사용하기 때문에 TextView만 추가되었다.

SampleViewHolder의 생성자를 통해 받아오고 TextView를 바인딩하는 과정에서 사용되는 itemView는 
나중에 Adapter에서 생성하여 ViewHolder 클래스 인스턴스를 생성하는 단계에서 넘겨주게된다.
Adapter를 설명하는 과정에서 다시 한번 살펴보자.

### RecyclerView의 Adapter

ViewHolder를 만들었으니 RecyclerView에서 아이템들을 관리하고 뷰를 바인딩하는 부분을 구현해야하는데, 그게 바로 Adapter다.

100개의 아이템으로 몇 번째 아이템인지, 만들어놓은 ViewHolder의 title에 표시하는걸 구현해보자.
RecyclerView.Adapter를 상속받은 SampleAdapter 클래스를 생성한다.

``` java
public class SampleAdapter extends RecyclerView.Adapter<SampleViewHolder> {
    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
```

RecyclerView.Adapter를 상속받게 되면 위와같이 3가지 메써드를 Override하게되는데 간단하게 설명하면,
* `onCreateViewHolder` 메써드는 ViewHolder를 생성하는 곳
* `onBindViewHolder` 메써드는 생성된 ViewHolder를 수정하는 곳 (대이터를 입력하거나, 뷰의 속성을 변경하는 등)
* `getItemCount` 메써드는 Adapter가 갖고있는 대이터 리스트의 사이즈를 반환하는 곳
이라고 설명 할 수 있다.

RecyclerView.Adapter 클래스는 제네릭 타입으로 ViewHolder를 상속받은 클래스를 갖는데, 위의 코드에서 확인 할 수 있듯이 우리가 만들어준 SampleViewHolder를 지정해놓는다.
멀티타입 ViewHolder를 갖는 RecyclerView를 만드는경우 제네릭 타입을 지정하지 않아도된다.

===

만든 Adapter에 Context를 받는 생성자를 만들어준다. Context는 ViewHolder로 전달 할 itemView를 생성하는데 사용된다.
또한 대이터 리스트를 담는 리스트 그리고 대이터를 초기화한다.

``` java
public class SampleAdapter extends RecyclerView.Adapter<SampleViewHolder> {

    private static final int COUNT = 100;

    private final Context context;
    private final List<Integer> items;

    public SampleAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>(COUNT);
        initSampleData();
    }

    private void initSampleData() {
        for (int position = 0; position < COUNT; position++) {
            addItem(position);
        }
    }
    ...
}
```

===

`initSampleData`메써드를 보면 addItem 이라는 메써드를 호출하는 것을 확인 할 수 있다.
Adapter에 대이터를 추가하고 제거 할 수 있는 `addItem` 과 `removeItem` 메써드를 추가해준다.

``` java
public class SampleAdapter extends RecyclerView.Adapter<SampleViewHolder> {
    ...
    
    public void addItem(int position) {
        items.add(position, position + 1);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }
    
    ...
}
```

`notifyItemInserted` 와 `notifyItemRemoved`를 호출하는 것은 Adapter가 새로운 대이터가 추가되었다는 것을 RecyclerView에게 인지시켜주기 위해서이다.
호출을 하지 않으면 `addItem`, `removeItem`이 호출되어도 아무일도 벌어지지않으니 꼭 호출해야한다.

===

대이터를 담는 List를 만들었으니 Adapter를 처음 생성했을 때 Override 해줬던 `getItemCount`를 수정해준다.

``` java
    @Override
    public void getItemCount() {
        items.size();
    }
```

===

이제 처음으로 만들었던 ViewHolder를 생성하고 대이터를 채워주는 부분을 구현해보자.
Adapter를 생성 할 때 Override 해줬던 메써드들 중 `onCreateViewHolder`와 `onBindViewHolder`가 그 부분이다.

``` java
    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(context)
                .inflate(R.layout.viewholder_sample, parent, false);

        return new SampleViewHolder(itemView);
    }
```

일반적으로 View를 inflate 하는 과정이다. Adapter의 생성자로 받아온 Context와 SampleViewHolder의 
래이아웃이 구성된 `R.layout.viewholder_sample`을 이용하여 ViewHolder의 itemView가 될 뷰를 inflate하고 
SampleViewHolder 인스턴스를 생성해서 리턴해줬다.

``` java
    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        holder.tvTitle.setText(String.format(TITLE_FORMAT, items.get(position)));
    }
```

위 코드에서 `TITLE_FORMAT`은 타이틀에 그냥 숫자만 넣기 밋밋할 것 같아 넣어줬다.
`TITLE_FORMAT`은 `private static final String TITLE_FORMAT = "%d 번째 아이템";` 과 같이 지정 해주면된다.

===

이렇게 Adapter를 구현함으로써 RecyclerView을 기본적으로 사용하기위한 준비는 끝이났다.

### RecyclerView의 LayoutManager

RecyclerView에서 가장 중요한 핵심이다. 아이템의 배치를 관리하고 스크롤, 아이템의 뷰 레이아웃도 관리한다.
LayoutManager를 커스텀하면 다양한 형태의 리스트를 표현 할 수 있다.

RecyclerView에 기본적으로 구현되어있는 LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager 들을 사용하여 RecyclerView를 빠르게 구현 해 볼 수 있으며 
생각보다 많은 개발자들이 다양한 LayoutManager를 구현 혹은 RecyclerView 자체를 커스터마이징 해놓았다. 라이브러리들은 [이곳](http://android-arsenal.com/tag/199)에서 확인 할 수 있다.

===


그럼 지금까지 만든 Adapter를 사용해서 RecyclerView를 구성해보자.

MainActivity에서 RecyclerView를 보여줄건데 이를 위해 MainActivity의 래이아웃을 수정한다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="kr.co.mash_up.android_sample_recyclerview.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/main_rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </android.support.v7.widget.RecyclerView>
</RelativeLayout>
```

그리고 MainActivity에서 다음과 같이 RecyclerView에 LayoutManager와 SampleAdapter를 설정해준다.

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SampleAdapter(this));
    }
}
```

이렇게 가장 기본적인 RecyclerView를 사용하는 과정을 끝냈다.

이제 앱을 빌드하여 실행해보면 매인 화면에서 1~100까지의 아이템들이 잘 보여지는 것을 확인 할 수 있다.
요기서 한가지 확인 할 수 있는건, 우리는 분명 CardView를 이용하여 ViewHolder를 구성했는데 그 어디에도 카드를 볼 수 없다는 것이다.

아이템 사이 사이에 여백이 없기 때문에 이런 현상이 발생하는건데, 아이템간의 여백을 만드는 방법은 두가지가있다.

* layout xml상에서 margin을 주는 방법
* ItemDecoration을 이용하여 여백을 만들어주는 방법

이 두가지이다. 우리는 ItemDecoration을 사용해서 아이템 사이에 여백을 만들어주도록 해보자.

### RecyclerView의 ItemDecoration

ItemDecoration는 다양한 이유로 사용 될 수 있다. 말 그대로 RecyclerView에 표현되는 Item들에 대해서 데코래이션을 하는 역할인데,
우리는 이를 이용하여 여백을 주도록 해보자.

우선 SpacingItemDecoration 이라는 클래스를 만들고 다음과같이 `복붙`한다.

``` java
public class SpacingItemDecoration extends ItemDecoration {
    private final ItemSpacingOffsets mItemSpacing;

    public SpacingItemDecoration(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpacingItemDecoration(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.twowayview_SpacingItemDecoration, defStyle, 0);

        final int verticalSpacing =
                Math.max(0, a.getInt(R.styleable.twowayview_SpacingItemDecoration_android_verticalSpacing, 0));
        final int horizontalSpacing =
                Math.max(0, a.getInt(R.styleable.twowayview_SpacingItemDecoration_android_horizontalSpacing, 0));

        a.recycle();

        mItemSpacing = new ItemSpacingOffsets(verticalSpacing, horizontalSpacing);
    }

    public SpacingItemDecoration(int verticalSpacing, int horizontalSpacing) {
        mItemSpacing = new ItemSpacingOffsets(verticalSpacing, horizontalSpacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        mItemSpacing.getItemOffsets(outRect, view, parent, state);
    }
}

```

===

``` gradle
buildscript {
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}

...

dependencies {
    ...
    compile ('com.github.ozodrukh:CircularReveal:1.3.1@aar') {
        transitive = true;
    }
}
```
