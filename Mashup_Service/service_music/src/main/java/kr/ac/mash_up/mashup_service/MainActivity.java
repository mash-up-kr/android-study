package kr.ac.mash_up.mashup_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "음악 서비스";

    Button btn_start, btn_stop;

    public void musicService(View v){
        switch (v.getId()){
            case R.id.btn_start:
                Log.d(TAG, "START 버튼");
                startService(new Intent(this, MusicService.class ));
                break;
            case R.id.btn_stop:
                Log.d(TAG, "STOP 버튼");
                stopService(new Intent(this, MusicService.class));
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
    }
}
