package kr.ac.mash_up.bind_binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalService extends Service {

    private final IBinder mBinder = new LocalBinder();

    private final Random mGenerator = new Random();

    public class LocalBinder extends Binder{
        LocalService GetService(){
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }
}
