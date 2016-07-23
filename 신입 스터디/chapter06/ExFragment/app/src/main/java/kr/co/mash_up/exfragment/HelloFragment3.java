package kr.co.mash_up.exfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelloFragment3 extends Fragment {

    public HelloFragment3() {
        // Required empty public constructor
    }

    public static HelloFragment3 newInstance() {
        HelloFragment3 fragment = new HelloFragment3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello3, container, false);
    }
}
