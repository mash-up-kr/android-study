package com.mashup.studyexampleproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.mashup.studyexampleproject.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailActivity extends AppCompatActivity {
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.callNumber)
    TextView callNumber;
    @Bind(R.id.image)
    ImageView image;
    String str_name;
    String str_callNumber;
    int imageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent receiveIntent = getIntent();
        str_name = receiveIntent.getStringExtra("name");
        str_callNumber = receiveIntent.getStringExtra("callNumber");
        imageId = receiveIntent.getIntExtra("imageId", R.mipmap.ic_launcher);

        name.setText(str_name);
        callNumber.setText(str_callNumber);
        image.setImageResource(imageId);

        setResult(0,getIntent());
    }

    @OnClick(R.id.call)
    public void onCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+str_callNumber));
        startActivity(intent);
    }

    @OnClick(R.id.msg)
    public void onMsg(){
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+str_callNumber)));
    }

    @OnClick(R.id.finish)
    public void onFinish(){
        finish();
    }


}
