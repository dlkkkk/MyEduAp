package com.example.myeduap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlanListActivity extends AppCompatActivity {

    ListView listView;
    MyDBOpenHelper dbOpenHelper;//数据库对象
    SQLiteDatabase dbReader,dbWriter;//读，写
    SimpleCursorAdapter adapter;//游标适配器
    String[] args;
    String DBName;//数据库名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);

        listView = findViewById(R.id.list_content);
        //获取数据库文件名
        Intent intent = getIntent();
        DBName = intent.getStringExtra("DBName");
        dbOpenHelper = new MyDBOpenHelper(getApplicationContext(),DBName,null,1);//打开数据库
        dbReader = dbOpenHelper.getReadableDatabase();//获取读权限
        dbWriter = dbOpenHelper.getWritableDatabase();//获取写权限
        showAll();//内容显示

        listView.setOnItemClickListener((parent, view, position, id) -> {//点击list中的条目，进行相应操作
            TextView itemID = view.findViewById(R.id.list_id);//获取某条记录的ID号
            args = new String[]{itemID.getText().toString()};//通过ID号进行记录的查询
            Cursor cursor = dbReader.query("agenda",null,"_id=?",args,null,null,null);
            cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            //弹出对话框，显示记录的具体内容
            AlertDialog.Builder builder = new AlertDialog.Builder(PlanListActivity.this);
            builder.setTitle("日程标题："+title);
            builder.setMessage("内容："+content);
            //如果完成，则将数据库中记录修改为“完成”
            builder.setPositiveButton("完成",(dialog, which) -> {
                ContentValues cv = new ContentValues();
                cv.put("state","完成");
                dbWriter.update("agenda",cv,"_id=?",args);
                showAll();
            });
            //如果未完成，则将记录改为“待办”
            builder.setNegativeButton("待办",(dialog, which) -> {
                ContentValues cv = new ContentValues();
                cv.put("state","待办");
                dbWriter.update("agenda",cv,"_id=?",args);
                showAll();
            });
            builder.show();//显示对话框
        });
    }
    //自定义showAll()方法，用于将查询数据库的内容进行呈现
    public void showAll(){
        Cursor cursor = dbReader.query("agenda",null,null,null,
                null,null,"date,time");//查询表中的所有内容，按照日期和时间进行排序
        cursor.moveToFirst();//游标移到最顶端
        String[] from = {"_id","date","time","title","state"};//字段名称，设置为显示数据源
        int[] to = {R.id.list_id,R.id.list_date,R.id.list_time,R.id.list_title,R.id.list_state};//控件ID，设置为数据显示目标地
        adapter = new SimpleCursorAdapter(PlanListActivity.this,R.layout.listitem,cursor,
                from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);//将数据显示关系进行绑定
        listView.setAdapter(adapter);//显示数据
    }
    //关闭全局变量打开的数据库对象
    @Override
    protected void onDestroy(){
        super.onDestroy();
        dbReader.close();
        dbWriter.close();
    }
}