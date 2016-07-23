package kr.ac.mash_up.intentservice_download;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Object path = msg.obj;

            if (msg.arg1 == RESULT_OK && path != null){
                Toast.makeText(getApplicationContext(), " "+path.toString()+"을 다운로드하였음.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "다운로드 실패", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void downloadClick(View v){
        Intent intent = new Intent(this, MyIntentService.class);
        Messenger messenger = new Messenger(handler);
        intent.putExtra("MESSENGER", messenger);
        intent.setData(Uri.parse("http://www.google.com/index.html"));
        intent.putExtra("urlpath", "http://www.google.com/index.html");
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
