package com.example.myeduap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicPlayActivity extends AppCompatActivity {

    private TextView tvMusicName,tvProcess,tvTotal;//当前进度,总时间
    private ImageView ivMusicPic;
    private ImageButton ibPlay, ibPause;
    private SeekBar musicProgressBar;//进度条
    private String[] musicNameList={"梅坞寻茶","welcome to Wonderland","河山大好"};
    private int[] musicPicId={R.drawable.musicpic_mwxc,R.drawable.musicpic_wtw,R.drawable.musicpic_hsdh};
    private int[] musicFileId={R.raw.mwxc,R.raw.wtw,R.raw.hsdh};
    private int id=0;
    private Intent intentActivity,intentService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);

        tvMusicName = findViewById(R.id.musicPlay_musicName);
        ivMusicPic = findViewById(R.id.musicPlay_picture);
        ibPlay = findViewById(R.id.musicPlay_play);
        ibPause = findViewById(R.id.musicPlay_pause);


        //intent初始化
        intentActivity = getIntent();
        //存放传递过来的信息
        String musicName = intentActivity.getStringExtra("musicName");
        //传递歌曲名
        tvMusicName.setText(musicName);
        //id进行定位
        for (int i=0;i<musicNameList.length;i++){
            if (musicNameList[i].equals(musicName)){
                id=i;
                break;
            }
        }
        //传递歌曲封面
        ivMusicPic.setImageResource(musicPicId[id]);

        //传值
        intentService = new Intent(MusicPlayActivity.this,MusicService.class);
        intentService.putExtra("MusicName",musicNameList[id]);
        intentService.putExtra("MusicFile",musicFileId[id]);
        //action的播放与暂停
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentService.putExtra("Action","play");
                startService(intentService);
                ibPlay.setVisibility(View.INVISIBLE);
                ibPause.setVisibility(View.VISIBLE);
            }
        });
        ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentService.putExtra("Action","pause");
                startService(intentService);
                ibPlay.setVisibility(View.VISIBLE);
                ibPause.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intentService);
    }
}