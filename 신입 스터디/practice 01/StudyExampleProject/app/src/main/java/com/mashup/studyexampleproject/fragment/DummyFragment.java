package com.mashup.studyexampleproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mashup.studyexampleproject.R;
import com.mashup.studyexampleproject.activity.ContactDetailActivity;
import com.mashup.studyexampleproject.adapter.MainRecyclerViewAdapter;
import com.mashup.studyexampleproject.model.ContactItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DummyFragment extends Fragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    MainRecyclerViewAdapter adapter;
    private static int REQUEST_DETAIL = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dummy, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerViewInit();
    }

    private void initRecyclerViewInit() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter = new MainRecyclerViewAdapter(getActivity().getApplicationContext());
        adapter.setListener(new MainRecyclerViewAdapter.onEventListener() {
            @Override
            public void onAdd(String name, String callNumber) {
                adapter.addItem(new ContactItem(callNumber, name, R.mipmap.ic_launcher));
                adapter.notifyItemInserted(1);
            }
            @Override
            public void onClick(ContactItem data) {
                goDetail(data);
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.setData();
        adapter.notifyDataSetChanged();

    }

    private void goDetail(ContactItem data) {
        startActivityForResult(new Intent(getActivity().getApplicationContext(), ContactDetailActivity.class)
                .putExtra("name", data.getName())
                .putExtra("callNumber", data.getCallNumber())
                .putExtra("imageId", data.getImageId()), REQUEST_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_DETAIL){
            String name = data.getStringExtra("name");
            Toast.makeText(getActivity().getApplicationContext(), name + "에서 나옴.", Toast.LENGTH_SHORT).show();
        }
    }


}
