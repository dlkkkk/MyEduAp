package com.example.myeduap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class PlanActivity extends AppCompatActivity {

    EditText et_title,et_content,et_date,et_time;
    Button bt_clear,bt_insert,bt_query;
    private MyDBOpenHelper dbOpenHelper;//数据库对象
    TextView tv_Info;//文本框对象
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        //控件初始化
        et_title=findViewById(R.id.et_agendaTitle);
        et_content=findViewById(R.id.et_agendaContent);
        et_date=findViewById(R.id.et_agendaDate);
        et_time=findViewById(R.id.et_agendaTime);
        bt_clear=findViewById(R.id.bt_clean);
        bt_insert=findViewById(R.id.bt_insert);
        bt_query=findViewById(R.id.bt_query);
        tv_Info=findViewById(R.id.tv_info);
        //获取用户登陆信息，获取用户名
        SharedPreferences userinfo = getSharedPreferences("UserInfo",MODE_PRIVATE);
        userName = userinfo.getString("logUser","");
        //实例化数据库，创建以用户名为文件名的数据库文件（如已创建，则打开数据库）
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),userName,null,1);
        //设置日期
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        et_date.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(dayOfMonth));
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        //设置时间
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        et_time.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
                    }
                }, 0, 0, true);
                timePickerDialog.show();
            }
        });
        //插入计划日程到数据库
        bt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = "待办";//计划的默认状态为待办
                SQLiteDatabase dbWriter = dbOpenHelper.getWritableDatabase();//定义数据库写入对象，获取写入权限
                ContentValues cv = new ContentValues();//创建数据缓存区
                cv.put("title",et_title.getText().toString());//写入计划标题
                cv.put("date",et_date.getText().toString());//写入日期
                cv.put("time",et_time.getText().toString());//写入时间
                cv.put("content",et_content.getText().toString());//写入内容
                cv.put("state",state);                         //写入计划状态
                if (dbWriter.insert("agenda",null,cv)<0){
                    //数据插入到数据库表中，如果返回值<0表明插入失败
                    tv_Info.setText("日程创建失败！");
                }else {//插入成功后，读取数据库最新一条记录，显示到对应控件
                    SQLiteDatabase dbReader = dbOpenHelper.getReadableDatabase();//定义数据库对象，获取读的权限
                    //利用Cursor(游标)对象查询某条记录，当前查找最新一条记录
                    Cursor cursor = dbReader.query("agenda", null, null, null,
                            null, null, "_id desc", "0,1");//desc降序排列;asc升序排列
                    cursor.moveToFirst();//查询完后，游标返回到最顶端
                    int index = cursor.getColumnIndex("date");
                    String date = cursor.getString(index);
                    index = cursor.getColumnIndex("time");
                    String time = cursor.getString(index);
                    index = cursor.getColumnIndex("title");
                    String title = cursor.getString(index);
                    tv_Info.setText("创建一条新日程：\n" + date + "" + time + "" + title);
                }
            }
        });
        //跳转到查询界面
        bt_query.setOnClickListener((view -> {
            Intent intent = new Intent(PlanActivity.this,PlanListActivity.class);
            intent.putExtra("DBName",userName);//将当前数据库文件名发送给PlanListActivity
            startActivity(intent);
        }));
        //清除窗口内容
        bt_clear.setOnClickListener((view -> {
            et_title.setText("");
            et_content.setText("");
            et_date.setText("");
            et_time.setText("");
        }));
    }
}