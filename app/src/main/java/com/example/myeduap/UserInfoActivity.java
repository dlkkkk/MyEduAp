package com.example.myeduap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private EditText userName,userBirth,userHobby;
    private TextView userOutput;
    private RadioGroup userGender;
    private Button BtSubmit,BtCancel;
    private String[] strHobby;
    private boolean[] checkedID;
    String strGender;
    private Spinner SpArea = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //控件初始化
        userName=findViewById(R.id.edit_name);
        userBirth=findViewById(R.id.edit_birth);
        userGender=findViewById(R.id.radio_gender);
        userHobby=findViewById(R.id.edit_hobby);
        userOutput=findViewById(R.id.text_output);
        BtSubmit=findViewById(R.id.button_submit);
        BtCancel=findViewById(R.id.button_cancel);
        strHobby=new String[]{"体育","音乐","旅行","美术","文学"};
        checkedID=new boolean[strHobby.length];
        SpArea = findViewById(R.id.spinner_area);

        //设置选择下拉框
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.area,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpArea.setAdapter(adapter);
        SpArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selected = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //出生年月选择
        userBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        userBirth.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(dayOfMonth));
                    }
                    //设置默认起始年月日为当前时间
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                //显示
                datePickerDialog.show();
            }
        });
        //兴趣选择对话框
        userHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //新建多选对话框
                AlertDialog.Builder myDialog = new AlertDialog.Builder(UserInfoActivity.this);
                //设置对话框标题
                myDialog.setTitle("选择兴趣");
                //设置多选框模式
                myDialog.setMultiChoiceItems(strHobby, checkedID, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        //判断当前哪一个是被选中的
                        checkedID[which]=isChecked;
                    }
                });
                //设置确定键的操作
                myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String text = "";
                        //寻找选中的内容
                        for(int i=0;i<checkedID.length;i++){
                            if(checkedID[i])
                                text=text+strHobby[i]+" ";
                        }
                        userHobby.setText(text);
                    }
                });
                //取消键，清空
                myDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        userHobby.setText("");
                    }
                });
                //呈现
                myDialog.show();
            }
        });
        //操作性别选择控件
        userGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.id_male:
                        strGender="男";
                        break;
                    case R.id.id_female:
                        strGender="女";
                        break;
                }
            }
        });
        //确定按钮操作
        BtSubmit.setOnClickListener(new View.OnClickListener() {
           NotificationManager manager;
           NotificationChannel channel;
           String channelID = "RegResult";
           int REG_NOTIFICATION_ID=1000;
            @Override
            public void onClick(View view) {
                //判断用户名是否存在
                if(userName.getText().toString().trim().equals(""))
                {
                    //如果为空，弹出警告对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
                    builder.setTitle("警告").setMessage("请输入姓名！").setPositiveButton("确定",null).show();
                }
                //如果不为空，收集信息
                else {
                    //将信息写入SharedPreference文件
                    SharedPreferences.Editor editor = getSharedPreferences("AllInfo",MODE_PRIVATE).edit();
                    editor.putString("name",userName.getText().toString());
                    editor.putString("birth",userBirth.getText().toString());
                    editor.putString("gender",strGender);
                    editor.putString("hobby",userHobby.getText().toString());
                    editor.putString("area",SpArea.getSelectedItem().toString());
                    editor.apply();

                    /*-----------------Notification操作---------------------*/
                    manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    //判断操作系统版本是否是8.0以上
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        //参数：channel的id,名称,Notification的优先级
                        channel=new NotificationChannel(channelID,"注册信息",NotificationManager.IMPORTANCE_DEFAULT);
                        //加载
                        manager.createNotificationChannel(channel);
                    }
                    Notification notification=new NotificationCompat.Builder(getApplicationContext(),channelID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("标题：注册成功！")
                            .setContentText(userOutput.getText().toString())
                            .setShowWhen(true)
                            .setAutoCancel(true)
                            .build();
                    manager.notify(REG_NOTIFICATION_ID,notification);
                    finish();
                }
            }
        });
        //取消按钮的操作
        BtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setText("");
                userBirth.setText("");
                userOutput.setText("");
                userGender.clearCheck();
                userBirth.setText("");
                userHobby.setText("");
            }
        });
    }
}