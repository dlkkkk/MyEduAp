package com.example.myeduap;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.LocationClient;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;

import com.baidu.mapapi.model.LatLng;


import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private MapView mapView = null;
    TextView tvTitle;
    double la,lg;//坐标数据，纬度和经度
    String sightTitle;
    Button btBack,btDH;
    public BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //地图初始化
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.setAgreePrivacy(this.getApplicationContext(),true);
        LocationClient.setAgreePrivacy(true);
        SDKInitializer.initialize(this.getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setContentView(R.layout.activity_location);
        //初始化
        tvTitle = findViewById(R.id.tv_title);
        btBack = findViewById(R.id.map_back);
        btDH = findViewById(R.id.map_dh);
        mapView = (MapView) findViewById(R.id.bmpView);

        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        btDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("geo:"+lg+","+la+"");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });


        //获取Intent数据
        Intent intent = getIntent();
        String sightName = intent.getStringExtra("sight");

        //判断显示哪个景点
        switch (sightName){
            case "dq":
                la=30.264062;
                lg=120.157776;
                sightTitle="断桥残雪";
                break;
            case "tzw":
                la=30.231845;
                lg=120.148209;
                sightTitle="太子湾公园";
                break;
            case "xxsd":
                la=30.27289;
                lg=120.071528;
                sightTitle="西溪湿地";
                break;
        }
        tvTitle.setText(sightTitle);
        //显示及定位地图
        BaiduMap baiduMap = mapView.getMap();
        //设定终点位置
        LatLng latLng = new LatLng(la,lg);//地图坐标终点
        MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(18).build();//zoom为放大倍数
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mapStatus);
        baiduMap.setMapStatus(msu);

        //用图标识目标坐标位置
        BitmapDescriptor searchBDA = BitmapDescriptorFactory.fromResource(R.drawable.start);
        MarkerOptions options = new MarkerOptions().icon(searchBDA).position(latLng);
        baiduMap.addOverlay(options);

        //关闭窗口
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //生命周期管理
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
}
