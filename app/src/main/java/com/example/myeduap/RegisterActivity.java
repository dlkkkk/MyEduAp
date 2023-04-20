package com.example.myeduap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //定义全局变量
    private EditText password,rePassword,reUserName;
    private Button BtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //控件初始化
        password=findViewById(R.id.edit_password);
        rePassword=findViewById(R.id.edit_repw);
        reUserName=findViewById(R.id.edit_name);
        BtRegister=findViewById(R.id.button_register);

        //确定按钮操作
        BtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断用户名是否为空
                if (reUserName.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                //判断密码是否为空
                else if (password.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                //判断两次密码是否一致
                else if (!password.getText().toString().trim().equals(rePassword.getText().toString().trim()))
                    Toast.makeText(getApplicationContext(), "两次输入密码不相同", Toast.LENGTH_SHORT).show();
                //将注册信息写入SharedPreference文件
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("UserInfo",MODE_PRIVATE).edit();
                    //通过查找用户名，获得对应密码
                    editor.putString(reUserName.getText().toString(),password.getText().toString());
                    editor.apply();
                    finish();
                    //跳转到登陆页面
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}