package kr.ac.mash_up.bind_messenger;

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

    boolean mBound;
    //바운드 여부를 표시

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    public void MessengerService(View v){
        if (!mBound) return;
        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO,0,0);
        try {
            mService.send(msg);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, MessengerService.class),mConnection, Context.BIND_AUTO_CREATE);
    }
}
