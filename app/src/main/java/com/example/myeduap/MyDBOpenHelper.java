package com.example.myeduap;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL("CREATE TABLE agenda(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL,date TEXT NOT NULL,time TEXT NOT NULL," +
                "content TEXT,state TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //数据库更新后，将当前的表格去除，创建新的表格
        db.execSQL("DROP TABLE IF EXISTS agenda");
        onCreate(db);
    }
}
