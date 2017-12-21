package fxjwsq.taiji.com.sample;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.ClusterItem;
import adapter.ClusterManager;
import adapter.dao.UserPolygons;
import utils.StringUtil;

public class BaiduMapActivity extends AppCompatActivity implements BaiduMap.OnMapLoadedCallback {

    // 定位相关
    LocationClient mLocClient;
    private static final String TAG = "OfflineBaiduMapActivity";
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    public MyLocationListenner myListener = new MyLocationListenner();
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    // UI相关
    private MyLocationData locData;
    private ClusterManager<MyItem> mClusterManager;
    MapStatus ms;
    private Button offline_baidumap_kqdk,offline_baidumap_dkqk;
    private boolean isInPolygons;
    private String userId;
    private JSONArray signList = null;
    private List<UserPolygons> polygonList = new ArrayList<UserPolygons>();
    private HashMap<String,UserPolygons> currentPolygons_map = new HashMap<String,UserPolygons>();
    private ArrayList<UserPolygons> dakaPolygons = new ArrayList<UserPolygons>();
    private JSONArray dakaList = null;
    ProgressDialog pDialog;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);

        verifyStoragePermissions(this);

        initOfflineMap();
        mMapView = (MapView) findViewById(R.id.bmapView);

        initUi();
    }




    public void initOfflineMap(){
        MKOfflineMap mOffline = null;
        mOffline = new MKOfflineMap();
        mOffline.init(new MKOfflineMapListener() {
            @Override
            public void onGetOfflineMapState(int type, int state) {
                switch (type) {
                    case MKOfflineMap.TYPE_DOWNLOAD_UPDATE:

                        break;
                    case MKOfflineMap.TYPE_NEW_OFFLINE:

                        break;

                    case MKOfflineMap.TYPE_VER_UPDATE:
                        break;
                }

            }
        });
    }


    private void initUi(){
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //开启查看本地城市
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        LatLng ll = new LatLng(42.0192501071,121.660822129);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//        LatLng latLng = new LatLng(42.0192501071,121.660822129);
//        OverlayOptions ooPolygon = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_gcoding));
//        mBaiduMap.addOverlay(ooPolygon);


        //initClusters();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
//            location.setLatitude(42.0192501071);
//            location.setLongitude(121.660822129);
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            Toast.makeText(BaiduMapActivity.this,mCurrentLat+"-"+mCurrentLon, Toast.LENGTH_SHORT).show();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (String.valueOf(mCurrentLat).contains("4.9E-324")||String.valueOf(mCurrentLon).contains("4.9E-324")) {
                //阜新市中心点经纬度
                LatLng ll = new LatLng(42.0192501071,121.660822129);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            Log.i(TAG,mCurrentLat+"--"+mCurrentLon);
            isInPolygon();
            updateCurrentLocation();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    private void initClusters() {
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);
        // 添加Marker点
//        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
//            @Override
//            public boolean onClusterItemClick(MyItem item) {
//                Toast.makeText(OfflineBaiduMapActivity.this,
//                        "点击单个Item", Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });
    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
        }
    }
    //判断是否在责任区内
    public void isInPolygon(){
        if(polygonList==null) return;
        for(UserPolygons userPolygon:polygonList){
            List<LatLng> pts = userPolygon.getLatlons();
            if(pts.size()==0) continue;
            //判断是否在责任区
            LatLng currentPt = new LatLng(mCurrentLat, mCurrentLon);
            boolean isInPolygon = SpatialRelationUtil.isPolygonContainsPoint(pts,currentPt);

            Log.i(TAG,mCurrentLat+"-"+mCurrentLon+" `s inPolygon is "+isInPolygon+"");
            if(isInPolygon){
                currentPolygons_map.put(userPolygon.getZrqdm(),userPolygon);
                isInPolygons = isInPolygon;
            }else{
                currentPolygons_map.remove(userPolygon.getZrqdm());
            }
        }
    }
    private void updateCurrentLocation() {
        /*String userId = SharedPreferencesUtils.getValue(this, Constants.USER_SETTING_USERID,null);
        JojtApiUtils.updateCurrentLocation(userId, mCurrentLat, mCurrentLon , System.currentTimeMillis()/1000,new JojtApiUtils.ApiCallBack() {
            @Override
            public void onSuccess(Object obj) {
                Log.i(TAG,"updateCurrentLocation is success "+ obj.toString());
            }
            @Override
            public void onError() {
                Log.e(TAG,"updateCurrentLocation is error");
            }
        });*/
    }
    @Override
    public void onMapLoaded() {
        ms = new MapStatus.Builder().zoom(14).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

    @Override
    protected void onDestroy() {
        // TODO 自动生成的方法存根
        if(pDialog!=null){
            pDialog.dismiss();
        }
        super.onDestroy();
        mLocClient.unRegisterLocationListener(myListener);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
