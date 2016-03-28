package kr.co.mash_up.exfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

// 여러 프래그먼트를 추가, 제거, 변경하는 등 관리가 주역할
public class MainActivity extends AppCompatActivity {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1. 액티비티에 사용될 레이아웃 생성
        // 좌측영역에 배치될 메뉴 리스트 프래그먼트는 XML에 정적으로 정의되어 있어
        // 레이아웃 생성시 같이 생성 및 추가된다.
        setContentView(R.layout.activity_main);

        //Fragment Manager 참조
        fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.container_fragment);

        if(fragment == null){
            //2. 액티비티 레이아웃 우측에 텍스트뷰어 프래그먼트를 추가
            HelloFragment1 helloFragment1 = HelloFragment1.newInstance();

            fm.beginTransaction()
                    .add(R.id.container_fragment, helloFragment1, "hello1")
                    .commit();
        }

        //3. 액티비티 레이아웃 좌측에 리스트 메뉴 프래그먼트에서 아이템을 선택했을 때
        // 이벤트 처리하기 위한 리스너 구현 및 등록
        MasterFragment masterFragment =
                (MasterFragment)fm.findFragmentById(R.id.fragment_master);

        masterFragment.setOnListItemClickListener(new MasterFragment.OnListItemClickListener() {

            @Override
            public void onItemClick(int itemType) {

                //4. 액티비티 우측 영역 프래그먼트 컨테이너에 현재 보여지고 있는 프래그먼트 참조
                // 선택된 아이템이 현재 보여지고 있는 프래그먼트라면 아무 처리도 하지않고 끝내고,
                // 아니라면 보여줘야할 프래그먼트를 생성
                Fragment fragment = fm.findFragmentById(R.id.container_fragment);

                if (itemType == MasterFragment.ITEM_TYPE_HELLO1){
                    if(fragment instanceof HelloFragment1){
                        return;
                    }
                    fragment = HelloFragment1.newInstance();
                }else if(itemType == MasterFragment.ITEM_TYPE_HELLO2){
                    if(fragment instanceof HelloFragment2){
                        return;
                    }
                    fragment = HelloFragment2.newInstance();
                }

                //5. 선택된 아이템에 해당하는 프래그먼트를 액티비티 우측에 배치
                fm.beginTransaction()
                        .replace(R.id.container_fragment, fragment)
                        .commit();
            }
        });
    }
}
