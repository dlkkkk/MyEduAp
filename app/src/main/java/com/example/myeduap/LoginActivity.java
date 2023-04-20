package com.example.myeduap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText logUserName,logPW;
    private Button btReg,btSubmit;
    private int LogResultCode = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logUserName=findViewById(R.id.log_EditName);
        logPW=findViewById(R.id.log_EditPassword);
        btReg=findViewById(R.id.log_ButtonRegister);
        btSubmit=findViewById(R.id.log_ButtonLogin);


        //跳转到注册页面
        btReg.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });

        //登录控件操作
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否有内容
                if (logUserName.getText().toString().equals("")){
                    //如果为空，弹出警告
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("警告").setMessage("请输入用户名")
                            .setPositiveButton("确定",null).show();
                }
                else {
                    //查询SharedPreference文件
                    SharedPreferences userInfo = getSharedPreferences("UserInfo",MODE_PRIVATE);
                    //查找以用户名为字段的内容，将字段内容与输入密码进行比较，确定用户名和密码是否正确
                    if (userInfo.getString(logUserName.getText().toString(),"").equals(logPW.getText().toString())){
                        SharedPreferences.Editor editor = getSharedPreferences("UserInfo",MODE_PRIVATE).edit();
                        editor.putString("logUser",logUserName.getText().toString());
                        editor.apply();
                        //如果不为空，发送数据
                        Intent intent = new Intent();
                        intent.putExtra("username",logUserName.getText().toString());
                        //设置result信息
                        setResult(LogResultCode,intent);
                        //跳转到主页面
                        Intent mainIntent = new Intent(LoginActivity.this,MainFragmentActivity.class);
                        startActivity(mainIntent);
                        //关闭当前Activity
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "该用户不存在或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}