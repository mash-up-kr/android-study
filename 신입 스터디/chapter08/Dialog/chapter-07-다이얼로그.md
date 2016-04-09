```
스터디 진행 : 2016년 4월 09일, 유슬기
최초 작성자 : 유슬기
최초 작성일 : 2016년 4월 09일
마지막 수정 : 2016년 4월 09일, 유슬기
```

# 다이얼로그
다이얼로그는 확인/취소 같이 사용자의 응답을 받기 위한 용도로 사용되는 작은 창이다.

확인/취소 뿐만이 아니라 날짜(DatePickerDialog)나 시간(TimePickerDialog) 등을 선택할 수 있는 다양한 다이얼로그를 볼 수 있는데, 이러한 모든 다이얼로그는 Dialog 클래스의 서브 클래스다. 예제에서는 Dialog의 서브 클래스 중에 하나인 AlertDialog를 이용할 것이다.

##DialogFragmnet
다이얼로그는 프래그먼트에 포함되어 같이 동작하도록 하는게 좋다. 이를 위해 DialogFragment를 함께 사용할 것이다.

DialogFragment를 사용하지 않고 AlertDialog를 보여주는 것도 가능하지만 권장사항은 아니다. 사용할때의 장점이 많기 때문이다.

DialogFragment는 사용자가 back버튼을 누르거나 화면을 회전시킬 때 생명주기 이벤트가 올바르게 처리되도록 보장한다. 또, 다이얼로그의 UI를 크고 작은 화면에서 다르게 나타내고 싶을 때 구성 요소로 재사용할 수 있게 해주기도 한다.

###DialogFragment 생성
android.support.v4.app.DialogFragment를 상속하는 새로운 클래스를 생성한다.
```java
import android.support.v4.app.DialogFragment;

public class SampleDialogFragment extends DialogFragment{

}
```

DialogFragment를 화면에 보여주기 위해 FragmentManager가 onCreateDialog()를 호출한다. onCreateDialog() 안에 메세지와 확인/취소 버튼 2개를 갖는 간단한 AlertDialog를 생성해보자.
```java
public class SampleDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("샘플 다이얼로그입니다")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }
}
```

![**다이얼로그 이미지 01**](https://github.com/seulgi49/study08-Dialog/blob/master/chap08/sample_dialog.PNG?raw=true)  
이 클래스의 인스턴스를 생성하고 해당 객체에서 show()를 호출하면 위와 같은 다이얼로그가 나타난다.
```java
// MainActivity에 다음의 코드를 추가하여 다이얼로그를 띄운다
DialogFragment dialog = new SampleDialogFragment();
dialog.show(getSupportFragmentManager(), "SampleDialogFragment");
```
(두 번째 인수 "SampleDialogFragment"는 프래그먼트의 고유한 태그 이름이다. 시스템이 필요에 따라 프래그먼트의 상태르르 저장하고 복원하는 데 사용한다. findFragmentByTag()를 호출해서 해당 프래그먼트를 파악할 수 있다.)

### AlertDiaolg 빌드
AlertDiaolg에는 세 가지 영역이 있다.  
![**AlertDialog Detail 01 이미지**](https://github.com/seulgi49/study08-Dialog/blob/master/chap08/alert_dialog_detail.png?raw=true)  
1. **제목 영역**  
	제목 영역에는 아이콘과 제목 문자열을 설정할 수 있다. 제목이 필요하지 않은 경우, 제목 문자열을 설정하지 않으면 된다.  
2. **내용 영역**  
	단순한 문자열이나 리스트 형태도 설정 가능하다.  
3. **버튼 영역**  
	세 가지 버튼을 추가할 수 있다.

AlertDialog.Builder 클래스가 AlertDialog를 생성할 수 있게 해주는 API를 제공하며, 여기에 사용자 지정 레이아웃도 포함된다.

AlertDialog는 다음과 같이 구축한다.
```java
//1. AlertDialog.Builder 생성자를 통해 인스턴스를 생성한다
AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//2. setter 메소드로 AlertDialog의 메세지와 제목을 설정한다
builder.setMessage("샘플 다이얼로그입니다")
       .setTitle("Sample Dialog");

//3. create()로 AlertDialog를 얻어온다.
AlertDialog dialog = builder.create();
```

###버튼 추가
다이얼로그에 버튼을 추가하려면 setPositiveButton()과 setNegativeButton()을 호출하면 된다.
```java
...
// 버튼 추가
builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // 사용자가 확인 버튼을 누름
           }
       });
builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               // 사용자가 다이얼로그를 캔슬함
           }
       });
...
```
setPositiveButton()과 setNegativeButton()에는 버튼의 제목, 사용자가 버튼을 눌렀을 때 수행할 작업을 정의하는 DialogInterface.OnClickListener가 필요하다.

다이얼로그에 추가할 수 있는 버튼은 긍정, 부정, 중립 버튼 세 가지가 있다.

- 긍정적(Positive): 다이얼로그가 보여주는 것을 수락할 때 사용자가 누르는 버튼
- 부정적(Negative): 작업을 취소할 때 누르는 버튼
- 중립적(Neutral): 사용자가 작업을 계속하고 싶지는 않지만 취소하고자 하는 것도 아닐 때 사용됨 (ex. '나중에 알림')

이 같은 버튼들은 각 유형당 버튼 하나씩만 추가할 수 있다.

### 목록 추가
AlertDiaolg에 추가할 수 있는 리스트는 세 가지 종류가 있다.
- 일반적인 단일 선택 리스트
- 라디오 버튼이 있는 단일 선택 리스트
- 체크 박스가 있는 복수 선택 리스트

![**single_choice_01 이미지**](https://github.com/seulgi49/study08-Dialog/blob/master/chap08/single_choice.PNG?raw=true)  
위와 같이 구현하려면 setItems()를 사용하면 된다.
```java
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("single choice")
            .setItems(new String[]{"뮤지컬 관람", "공원 산책", "음악 감상"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
    return builder.create();
}
```
리스트는 다이얼로그의 내용 영역에 나타나므로 메세지와 리스트를 동시에 사용할 수 없다.

위 예제처럼 setItems()를 호출하여 배열을 전달하거나 setAdapter()를 사용해서 목록을 지정해도 된다. setAdapter()를 사용할 경우 동적인 데이터가 있는 목록을 ListAdapter로 지원할 수 있게 해준다.

이 때, 목록을 선택하면 onDismiss()가 호출돼서 다이얼로그를 무시하게 된다. 이렇게 되는 것을 원하지 않으면 라디오 버튼이나 체크박스가 있는 리스트를 추가하면 된다.

### 다중 선택 목록 추가
![다중선택목록 이미지](https://github.com/seulgi49/study08-Dialog/blob/master/chap08/multiple_choice.PNG?raw=true)  
다중 선택 목록(with 체크박스)을 추가하려면 setMultiChoiceItems()를 사용한다.  
(라디오 버튼이 있는 단일 선택 목록은 setSingleChoiceItems()를 사용한다)
```java
public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
                final OnMultiChoiceClickListener listener)
```
- items 
	: 리스트에 표시될 아이템들의 텍스트
- checkedItems 
	: 어떤 아이템에 체크를 해놓을 건지 명시한다. 만일 아무 항목에도 체크하지 않을 거라면 null을 넣으면 된다.  
    단, null을 넣지 않고 boolean배열을 넣을 때 이 배열의 길이는 첫번째 파라미터인 items 배열의 길이와 같아야 한다.

```java
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("multiple choice")
            .setMultiChoiceItems(new String[]{"영국", "프랑스", "호주", "중국", "일본"},
                    new boolean[]{false, false, false, true, false},
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                // 사용자가 아이템을 체크했을 때 액션
                            }
                        }
                    })
            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            })
            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
    return builder.create();
}
```

### 액티비티와 통신하기
지금까지 만든 다이얼로그들은 자신을 호출한 액티비티에 아무런 정보도 넘겨주지 못했다. DialogFragment가 필요한 작업을 알아서 수행할 수도 있지만 대부분의 경우 이벤트를 다이얼로그를 호출한 액티비티에 직접 전달하고자 할 수 있다. 

DialogFragment에 클릭 메서드가 있는 인터페이스를 정의하고 액티비티와 연결해보자.
```java
// 리스너 인터페이스 선언
public interface SampleDialogListener {
    public void onPositiveClick(DialogFragment dialog);
    public void onNegativeClick(DialogFragment dailog);
}

// 리스너 인터페이스의 빈 객체 선언
SampleDialogListener sampleDialogListener;


// 리스너 인터페이스와 액티비티 연결
@Override
public void onAttach(Activity activity) {
    super.onAttach(activity);

    try{
        sampleDialogListener = (SampleDialogListener) activity;
    } catch (ClassCastException e) {
        throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
    }
}
```
(onAttach() 콜백 메소드는 fragment가 activity에 추가되고 나면 호출된다. 이 때 Activity가 파라미터로 전달된다.)

리스너 역할을 할 인터페이스를 만들었다. 그리고 이 안에는 다이얼로그에서 수행하는 액션에 따라 액티비티와 통신할 메서드를 선언하는데, 이 메서드에는 기본적으로 DialogFragment를 파라미터로 넣어주어야 한다.

다이얼로그를 호스팅하는 액티비티는 다이얼로그의 인스턴스를 만든다. 이 때, SampleDialogListener 인터페이스 구현을 통해 다이얼로그의 이벤트를 수신하게 된다.
```java
public class MainActivity extends FragmentActivity implements SampleDialogFragment.SampleDialogListener{
	...

    @Override
    public void onPositiveClick(DialogFragment dialog) {
    }

    @Override
    public void onNegativeClick(DialogFragment dailog) {
    }
}
```

다이얼로그에서 아이템을 선택하고 확인을 누르면 선택된 아이템의 텍스트가 토스트 메세지로 뜨도록 할 것이다. 액티비티와 통신할 메소드에 아이템 리스트와 선택된 아이템의 리스트가 파라미터로 넘어가도록 코드를 추가하자.

우선, 선택된 아이템의 인덱스를 저장하기 위해 DialogFragment에 ArrayList를 선언하고, 리스너 인터페이스 안 메소드의 파라미터를 수정한다.
```java
public class SampleDialogFragment extends DialogFragment{
	...
    // 선택된 아이템 인덱스 저장을 위한 리스트
    private ArrayList<Integer> selectedItemsIndexList;
    // setMultiChoiceItems()안에서 초기화했던 아이템 리스트도 따로 선언해준다.
    private String[] itemList = {"일본", "중국", "영국", "프랑스", "이탈리아"};
    ...
    public interface SampleDialogListener {
        public void onPositiveClick(DialogFragment dialog, String[] itemList, ArrayList<Integer> selectedList);
        public void onNegativeClick(DialogFragment dailog);
    }
}
```
이제 onCreateDialog 메소드를 수정한다.
```java
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    selectedItemsIndexList = new ArrayList();

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("multiple choice")
            .setMultiChoiceItems(itemList, null,
                    new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) {
                                // 사용자가 아이템을 체크하면 selected items list에 추가한다
                                selectedItemsIndexList.add(which);
                            } else if (selectedItemsIndexList.contains(which)) {
                                // 아이템이 이미 리스트에 있으면 지운다
                                selectedItemsIndexList.remove(Integer.valueOf(which));
                            }
                        }
                    })
            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 리스너 인터페이스 속의 메소드 작동 (연결된 액티비티와 통신)
                    sampleDialogListener.onPositiveClick(SampleDialogFragment.this, itemList, selectedItemsIndexList);
                }
            })
            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sampleDialogListener.onNegativeClick(SampleDialogFragment.this);
                }
            });
    return builder.create();
}
```

### 커스텀 다이얼로그
다이얼로그에서 커스텀 레이아웃을 원하는 경우, 레이아웃을 생성한 다음 이를 AlertDialog에 추가하면 된다. 이 때 AlerDialog.Builder 객체의 setView()를 호출하는 방법을 쓴다.

기본적으로 커스텀 레이아웃이 다이얼로그를 가득 채우지만 필요하다면 AlertDialog.Builder 메소드를 사용해서 버튼과 제목을 추가할 수 있다.

![**custom_dialog 이미지**](https://github.com/seulgi49/study08-Dialog/blob/master/chap08/custom_dailog.PNG?raw=true)  
위 이미지와 같이 레이아웃을 생성하자.
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <ImageView
        android:src="@drawable/dialog_header"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:background="#E20E6E"
        android:scaleType="center"
        />
    <EditText
        android:id="@+id/username"
        android:inputType="textEmailAddress"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:layout_marginLeft="4dp"
        android:layout_marginRight="4dp"
        android:layout_marginBottom="4dp"
        android:hint="Username" />
    <EditText
        android:id="@+id/password"
        android:inputType="textPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        android:layout_marginLeft="4dp"
        android:layout_marginRight="4dp"
        android:layout_marginBottom="16dp"
        android:fontFamily="sans-serif"
        android:hint="Password"/>
</LinearLayout>
```

DialogFragment 안에 레이아웃을 인플레이트 하려면 getLayoutInflater()로 LayoutInflater를 가져와 inflate()를 호출해야 한다. 그런 다음, setView()를 호출해서 레이아웃을 다이얼로그에 배치한다.
```java
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    LayoutInflater inflater = getActivity().getLayoutInflater();

    builder.setView(inflater.inflate(R.layout.custom_dialog, null))
            .setPositiveButton("Sing in", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
    return builder.create();
}
```

커스텀 다이얼로그를 생성할 때 Activity를 대신 배치해도 된다. 간단하게 액티비티를 하나 추가하고 매니페스트에서 해당 액티비티의 테마 속성을 다음과 같이 변경해주면 끝이다.
```java
<activity android:theme="@android:style/Theme.Holo.Dialog" >
```








