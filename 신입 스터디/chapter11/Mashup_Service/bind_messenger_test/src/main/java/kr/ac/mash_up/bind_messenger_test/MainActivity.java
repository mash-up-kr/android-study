package kr.ac.mash_up.bind_messenger_test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Messenger mService = null;
    //서비스와 통신하는 데 사용되는 메신저 객체


    public void ReceiveMessenger(View v){

        ComponentName cn = new ComponentName("kr.ac.mash_up.bind_messenger",
                "kr.ac.mash_up.bind_messenger.MessengerService");
        Intent intent = new Intent();
        intent.setComponent(cn);

        ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = new Messenger(service);
                Message msg = Message.obtain(null, 1);
                try {
                    mService.send(msg);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }
        };

        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
