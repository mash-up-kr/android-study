package kr.co.mash_up.exfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class HelloPagerActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ViewPager mViewPager;
    HelloPagerAdapter mHelloPagerAdapter;
    ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_pager);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mFragments = new ArrayList<>();
        mFragments.add(HelloFragment1.newInstance());
        mFragments.add(HelloFragment2.newInstance());
        mFragments.add(HelloFragment3.newInstance());
        mFragments.add(HelloFragment1.newInstance());
        mFragments.add(HelloFragment2.newInstance());
        mFragments.add(HelloFragment3.newInstance());

        mHelloPagerAdapter = new HelloPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mHelloPagerAdapter);

        //ViewPager가 보여주는 현재 페이지의 변경사항들을 리스닝하는 방법이 OnPageChangeListener다.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //페이지가 이동되는 위치를 알려줌
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //현재 선택된 페이지
            @Override
            public void onPageSelected(int position) {
                //현재 Fragment의 이름을 Toolbar에 표시
                mToolbar.setTitle(mFragments.get(position).getClass().getSimpleName());
            }

            //페이지의 애니메이션 상태(드래그됨, 변동없음, 비동작)을 알려준다.
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //선택한 요소를 ViewPager의 첫화면으로 보여준다.
        int itemType = getIntent().getIntExtra("HelloFragment", 0);
        mViewPager.setCurrentItem(itemType);
    }

    /*
        FragmentStatePagerAdapter는 각 페이지를 관리하기 위해 액티비티가 아닌 프래그먼트를 사용하는
        PagerAdapter를 구현한 것이며, 프래그먼트의 상태도 저장하고 복원할 수 있다.

        --- FragmentStatePagerAdapter의 역할 ---
        우리가 반환하는 프래그먼트들을 액티비티에 추가 한다.
        프래그먼트들의 뷰들이 올바른 위치에 올 수 있도록 ViewPager가 그것들을 식별하는 것을 도와준다.
    */
    public class HelloPagerAdapter extends FragmentStatePagerAdapter {

        public HelloPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
