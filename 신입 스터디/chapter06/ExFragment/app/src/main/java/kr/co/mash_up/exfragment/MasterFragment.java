package kr.co.mash_up.exfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MasterFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView mListView;
    OnListItemClickListener mListItemClickListener = null;

    // Detail Fragment를 판단하기 위한 상수
    public static final int ITEM_TYPE_HELLO1 = 1;
    public static final int ITEM_TYPE_HELLO2 = 2;

    public MasterFragment() {
        // Required empty public constructor
    }

    public static MasterFragment newInstance() {
        MasterFragment fragment = new MasterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master, container, false);

        mListView = (ListView) view.findViewById(R.id.listView_menu);

        //1. 리스트 클릭 리스너 설정
        mListView.setOnItemClickListener(this);

        return view;
    }

    //2. 해당 프래그먼트를 사용하는 액티비티에서 리스트 아이템 클릭시 그 이벤트를
    // 수신받기 위한 리스너 인터페이스 정의
    // 이 리스너를 통해 해당 프래그먼트는 독립적으로 동작될 수 있다.
    public interface OnListItemClickListener {
        void onItemClick(int itemType);
    }

    //3. 외부에서 해당 프래그먼트에서 발생되는 리스트 아이템 클릭 이벤트 수신받기 위한 리스너 등록 메소드
    public void setOnListItemClickListener(OnListItemClickListener listener) {
        mListItemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int itemType = 0;

        switch (position) {
            case 0:
                itemType = ITEM_TYPE_HELLO1;
                break;
            case 1:
                itemType = ITEM_TYPE_HELLO2;
                break;
        }

        //4. 해당 프래그먼트에서 아이템 클릭 이벤트가 발생되면 등록된 리스너의 핸들러 메소드 호출
        // 핸들러 인자로 선택된 아이템 타입을 전달함으로써 리스너가 등록한 액티비티가 클릭된 아이템을 구분
        if (mListItemClickListener != null) {
            mListItemClickListener.onItemClick(itemType);
        }
    }
}
