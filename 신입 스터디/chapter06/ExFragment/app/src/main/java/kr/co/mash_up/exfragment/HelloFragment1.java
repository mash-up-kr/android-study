package kr.co.mash_up.exfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelloFragment1 extends Fragment {

    public HelloFragment1() {
        // Required empty public constructor
    }

    public static HelloFragment1 newInstance() {
        HelloFragment1 fragment = new HelloFragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello1, container, false);
    }
}
