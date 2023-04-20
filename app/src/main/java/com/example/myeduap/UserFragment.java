package com.example.myeduap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import static android.Manifest.permission.CALL_PHONE;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //声明全局参数
    private TextView userInfor, tv_personInfo;
    private Button bt_Message;
    private int LogRequestCode = 2, LogResultCode = 3;
    private int CallRequestCode = 1;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        //控件初始化
        userInfor = v.findViewById(R.id.userinfo_UserFragment);
        bt_Message = v.findViewById(R.id.bt_message);
        tv_personInfo = v.findViewById(R.id.tv_personInfo);

        //读取SharedPreferences文件，判断当前用户是否登陆，获取登陆用户名
        SharedPreferences spUserInfo = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String userName = spUserInfo.getString("logUser", "");
        if (!userName.equals(""))
            userInfor.setText(userName);

        tv_personInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_personInfo.getText().toString().equals("")) {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }
            }
        });

        //将个人信息显示在控件上
        SharedPreferences AllInfo = getActivity().getSharedPreferences("AllInfo", Context.MODE_PRIVATE);
        String name = AllInfo.getString("name", "");
        String birth = AllInfo.getString("birth", "");
        String gender = AllInfo.getString("gender", "");
        String hobby = AllInfo.getString("hobby", "");
        String area = AllInfo.getString("area", "");
        if (name.equals(userName))
            tv_personInfo.setText("昵   称：" + name + "\n\n" + "出生日期：" + birth + "\n\n" +
                    "性   别：" + gender + "\n\n" + "兴趣爱好：" + hobby + "\n\n" + "地   区：" + area);

        //点击登录控件，跳转到LoginActivity
        userInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //跳转时返回数据结果
                startActivityForResult(intent, LogRequestCode);
            }
        });
        //发短信
        bt_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSms = new Intent();
                intentSms.setAction(Intent.ACTION_SENDTO);
                intentSms.setData(Uri.parse("smsto:5556"));
                startActivity(intentSms);
            }
        });
        return v;
    }

    //接收消息
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断传过来的数据包
        if (requestCode == LogRequestCode && resultCode == LogResultCode) {
            String userName = data.getStringExtra("username");
            userInfor.setText(userName);
        }
    }

    /*---------------------------菜单操作-------------------------*/
    //创建菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu布局文件的加载
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.regmenu,menu);

//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.regmenu,menu);
//        return super.onCreateOptionsMenu(menu);
    }

    public int id = 0;
    //菜单选择项对应的操作
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Drawable drawable;
        //获取系统资源
        Resources resources = getResources();
        //通过Item的id来判断当前选中哪个
        switch (item.getItemId()) {
            case R.id.style_white:
                drawable = resources.getDrawable(R.color.white);
                getActivity().getWindow().setBackgroundDrawable(drawable);
                return true;
            case R.id.style_green:
                drawable = resources.getDrawable(R.color.colorStGreen);
                //获取窗口对象，设置背景画板
                getActivity().getWindow().setBackgroundDrawable(drawable);
                getActivity().getWindow().setBackgroundDrawable(drawable);
                return true;
            case R.id.app_version:
                //通过弹出对话框，呈现信息
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //设置对话框内容
                builder.setTitle("版本信息")
                        .setMessage("作者：寇洁\n软件名称：游湖指南\n版本号：yh0.1.0")
                        .setPositiveButton("确定", null)
                        .show();
                return true;
            case R.id.bk_music:
                Intent intent = new Intent(getActivity(), MusicService.class);
                if (id == 0) {
                    id = 1;
                } else {
                    id = 0;
                }
                if (id == 1) {
                    getActivity().startService(intent);
                } else {
                    getActivity().stopService(intent);
                }
        }
        return super.onOptionsItemSelected(item);
    }
}