package com.example.myeduap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"Hong_onCreate");




    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Hong_onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Hong_onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Hong_onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Hong_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Hong_onDestroy");
    }
}