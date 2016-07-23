package kr.ac.mash_up.mashup_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {
    private static final String TAG = "음악 서비스";

    MediaPlayer player;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "service onCreate()");

        player = MediaPlayer.create(this, R.raw.bari);
        player.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Music Service가 시작되었습니다.", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "service onStartCommand()");
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Music Service가 중지되었습니다.", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "service onDestroy()");
        player.stop();
    }
}
