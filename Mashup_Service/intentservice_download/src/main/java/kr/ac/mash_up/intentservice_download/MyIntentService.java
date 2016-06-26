package kr.ac.mash_up.intentservice_download;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MyIntentService extends IntentService {
    private int result = Activity.RESULT_CANCELED;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Uri data = intent.getData();
        String urlPath = intent.getStringExtra("urlpath");
        String buffer = " ";

        InputStream stream = null;

        try {
            URL url = new URL(urlPath);
            stream = url.openConnection().getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);

            int i = 0, next = -1;
            while((next = reader.read()) != -1){

                Log.v("바이트 읽기", String.valueOf(next));
                buffer += " " + (char) next;
                Log.v("바이트 읽기", buffer);
                if (++i > 100) break;
            }

            result = Activity.RESULT_OK;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null){
                try {
                    stream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        Bundle extras = intent.getExtras();

        if (extras != null){
            Messenger messenger = (Messenger) extras.get("MESSENGER");
            Message msg = Message.obtain();
            msg.arg1 = result;
            msg.obj = buffer;
            try {
                messenger.send(msg);
            }catch (android.os.RemoteException e1){
                Log.w(getClass().getName(), "Exception sending message", e1);
            }
        }
    }

}
