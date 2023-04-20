package com.example.myeduap;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    private MediaPlayer mp;//播放媒体对象
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mp = MediaPlayer.create(this,R.raw.mwxc);
        mp.start();
    }

    //适用于频繁调用
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        String musicName = intent.getStringExtra("MusicName");
//        //0为默认数据
//        int musicFileId = intent.getIntExtra("MusicFile",0);
//        //判断当前状态：播放or暂停
//        String action = intent.getStringExtra("Action");
//        switch (action){
//            case "play":
//                //判断媒体对象是否存在
//                if (mp==null){
//                    //若不存在,传递musicFileId,打开文件
//                    mp=MediaPlayer.create(this,musicFileId);
//                }
//                mp.start();//播放
//                break;
//            case "pause":
//                mp.pause();
//                break;
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }

    //释放所有资源
    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
        mp.release();
    }
}