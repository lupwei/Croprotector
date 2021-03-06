package nova.croprotector;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class HistoryMap_fragment extends Fragment implements CompoundButton.OnCheckedChangeListener,LocationSource,AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;

    private int choice=0;

	private View view;

    public static Map_fragment newInstance() {
        Map_fragment fragment = new Map_fragment();
        return fragment;
    }

    public HistoryMap_fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history_map, container, false);
        return view;
    }

	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	    initview(savedInstanceState,view);

        //beta版测试数据
        LatLng latLng = new LatLng(46.11771441,85.66042721);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("苹果痂").snippet("苹果黑星菌"));
        LatLng latLng0 = new LatLng(46.1176103,85.66013753);
        final Marker marker0 = aMap.addMarker(new MarkerOptions().position(latLng0).title("樱桃白粉病").snippet("叉丝单囊壳菌"));
        LatLng latLng1 = new LatLng(46.11736487,85.65952599);
        final Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLng1).title("玉米灰斑病").snippet("尖孢科孢子虫"));
        LatLng latLng2 = new LatLng(46.11653192,85.66003025);
        final Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLng2).title("葡萄黑腐病").snippet("葡萄球座菌"));
        LatLng latLng3 = new LatLng(46.11668066,85.66159666);
        final Marker marker3 = aMap.addMarker(new MarkerOptions().position(latLng3).title("甜椒菌斑").snippet("野油菜黃單孢菌"));
        LatLng latLng4 = new LatLng(46.11644267,85.66081345);
        final Marker marker4 = aMap.addMarker(new MarkerOptions().position(latLng4).title("甜椒菌斑").snippet("野油菜黃單孢菌"));
        //圆形菜单
        CircleMenu circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#8BC34A"), R.mipmap.ic_menu_white_48dp, R.mipmap.ic_close_white_48dp)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.ic_format_list_bulleted_white_48dp)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.ic_map_white)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    //图标点击事件
                    @Override
                    public void onMenuSelected(int index) {
                        choice=index;
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {
                if(-1==choice) return;
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                switch(choice){
                    //列表图标
                    case 0:
                        choice=-1;
                        History_fragment historyFragment=new History_fragment();
                        fTransaction.replace(R.id.fragment_container,historyFragment).commit();
                        break;
                    //地图图标
                    case 1:
                        choice=-1;
                        HistoryMap_fragment historyMapFragment=new HistoryMap_fragment();
                        fTransaction.replace(R.id.fragment_container,historyMapFragment).commit();
                        break;
                }
            }

        });

    }
	
	
    private void initview( Bundle savedInstanceState,View view){
        mapView= (MapView) view.findViewById(R.id.map_history);
        mapView.onCreate(savedInstanceState);
        if (aMap==null)
        {
            aMap=mapView.getMap();
        }

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener=listener;
        if(locationClient==null){
            locationClient=new AMapLocationClient(getActivity());
            clientOption=new AMapLocationClientOption();
            locationClient.setLocationListener(this);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
            clientOption.setOnceLocationLatest(true);//设置单次精确定位
            locationClient.setLocationOption(clientOption);
            locationClient.startLocation();
        }

    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener=null;
        if(locationClient!=null){
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient=null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null&&aMapLocation != null) {
            if (aMapLocation != null
                    &&aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        }
        else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(locationClient!=null){
            locationClient.onDestroy();
        }
    }
}