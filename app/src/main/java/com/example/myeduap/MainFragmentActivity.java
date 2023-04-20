package com.example.myeduap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainFragmentActivity extends AppCompatActivity {

    private ImageButton BtIndex,BtFind,BtPlan,BtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        //控件初始化
        BtIndex=findViewById(R.id.fragment_b1);
        BtFind=findViewById(R.id.fragment_b2);
        BtPlan=findViewById(R.id.fragment_b3);
        BtUser=findViewById(R.id.fragment_b4);
        BtIndex.setOnClickListener(new MyClickListener());
        BtFind.setOnClickListener(new MyClickListener());
        BtPlan.setOnClickListener(new MyClickListener());
        BtUser.setOnClickListener(new MyClickListener());
        //将主界面预加载
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_top,new IndexFragment()).commit();

    }

//    /*---------------------------菜单操作-------------------------*/
//    //创建菜单
//
//    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        //menu布局文件的加载
////        super.onCreateOptionsMenu(menu,inflater);
////        inflater.inflate(R.menu.regmenu,menu);
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.regmenu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public int id = 0;
//    //菜单选择项对应的操作
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Drawable drawable;
//        //获取系统资源
//        Resources resources = getResources();
//        //通过Item的id来判断当前选中哪个
//        switch (item.getItemId()){
//            case R.id.style_white:
//                drawable=resources.getDrawable(R.color.white);
//                this.getWindow().setBackgroundDrawable(drawable);
//                return true;
//            case R.id.style_green:
//                drawable=resources.getDrawable(R.color.colorStGreen);
//                //获取窗口对象，设置背景画板
//                this.getWindow().setBackgroundDrawable(drawable);
//                this.getWindow().setBackgroundDrawable(drawable);
//                return true;
//            case R.id.app_version:
//                //通过弹出对话框，呈现信息
//                AlertDialog.Builder builder=new AlertDialog.Builder(this);
//                //设置对话框内容
//                builder.setTitle("版本信息")
//                        .setMessage("作者：寇洁\n软件名称：游湖指南\n版本号：yh0.1.0")
//                        .setPositiveButton("确定",null)
//                        .show();
//                return true;
//            case R.id.bk_music:
//                Intent intent = new Intent(this,MusicService.class);
//                if (id == 0){
//                    id = 1;
//                }else {
//                    id = 0;
//                }
//                if (id == 1){
//                    this.startService(intent);
//                }else {
//                    this.stopService(intent);
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //声明一个FragmentManager对象，通过getSupportFragmentManager()的方法进行初始化
            FragmentManager fragmentManager=getSupportFragmentManager();
            //声明一个FragmentTransaction对象，利用fragmentManager对象的beginTransaction()方法进行初始化
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            switch (view.getId()){
                case R.id.fragment_b1:
                    //利用add()方法，将fragment加载到对应布局的区域
                    fragmentTransaction.add(R.id.fragment_top,new IndexFragment());
                    break;
                case R.id.fragment_b2:
                    fragmentTransaction.add(R.id.fragment_top,new FindFragment());
                    break;
                case R.id.fragment_b3:
                    fragmentTransaction.add(R.id.fragment_top,new PlanFragment());
                    break;
                case R.id.fragment_b4:
                    fragmentTransaction.add(R.id.fragment_top,new UserFragment());
                    break;
            }
            //利用commit()方法，将fragment呈现出来
            fragmentTransaction.commit();
        }
    }
}