package nova.croprotector;

import android.Manifest;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKManager;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //DJI
    private static final String TAG = MainActivity.class.getName();
    public static final String FLAG_CONNECTION_CHANGE = "dji_sdk_connection_change";
    private static BaseProduct mProduct;
    private Handler mHandler;


    private long exitTime = 0;

    //Fragment 对象
    private UAV_fragment uavFragment;
    private Shoot_fragment shootFragment;
    private Robot_fragment robotFragment;
    private Map_fragment mapFragment;
    private EditPassword_fragment passwordFragment;
    private History_fragment historyFragment;
    private Analysis_fragment analysisFragment;
    private help_fragment helpFragment;
    private FragmentManager fManager;


    //left menu
    private DrawerLayout drawerLayout;

    private final String[] PERMISSIONS=new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA,
    };

    //header里的用户名信息
    private String userinfo;
    private TextView username;

    //用户文件存储
    private SharedPreferences sp1;
    private SharedPreferences.Editor editor1;
    private SharedPreferences sp2;
    private SharedPreferences.Editor editor2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_string,R.string.close_string);
        actionBarDrawerToggle.syncState();


        //利用sharedpreferences判断用户是否在已登录的状态下，用户注销就清空userdata，登录就将用户信息写入userdata
        sp2=getSharedPreferences("infodata",MODE_PRIVATE);
        editor2=sp2.edit();
        sp1=getSharedPreferences("userdata",MODE_PRIVATE);
        editor1=sp1.edit();
        boolean isLogin=sp1.getBoolean("isLogin",false);

        //设置username
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        if(isLogin){
            userinfo=sp1.getString("username","用户信息丢失");
            Log.d("MainActivity", userinfo);
            ((TextView)headerLayout.findViewById(R.id.username)).setText(userinfo);
        }
        else{
            //Login_activity.actionStart(MainActivity.this);
            ((TextView)headerLayout.findViewById(R.id.username)).setText("未登录");
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED) {
            // When the compile and target version is higher than 22, please request the
            // following permissions at runtime to ensure the
            // SDK work well.

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            }

        }


        //Initialize DJI SDK Manager
        mHandler = new Handler(Looper.getMainLooper());
        DJISDKManager.getInstance().registerApp(this, mDJISDKManagerCallback);

        navigationView.setCheckedItem(R.id.home);
        fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        uavFragment = new UAV_fragment();
        fTransaction.add(R.id.fragment_container,uavFragment).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fManager = getSupportFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        uavFragment = new UAV_fragment();
                        fTransaction.replace(R.id.fragment_container,uavFragment).commit();
                        break;
                    case R.id.robot:
                        robotFragment=new Robot_fragment();
                        fTransaction.replace(R.id.fragment_container,robotFragment).commit();
                        break;
                    case R.id.camera:
                        shootFragment = new Shoot_fragment();
                        fTransaction.replace(R.id.fragment_container,shootFragment).commit();
                        break;
                    case R.id.map:
                        mapFragment = new Map_fragment();
                        fTransaction.replace(R.id.fragment_container,mapFragment).commit();
                        break;
                    case R.id.history:
                        historyFragment=new History_fragment();
                        fTransaction.replace(R.id.fragment_container, historyFragment).commit();
                        break;
                    case R.id.password:
                        passwordFragment = new EditPassword_fragment();
                        fTransaction.replace(R.id.fragment_container, passwordFragment).commit();
                        break;
                    case R.id.analysis:
                        analysisFragment = new Analysis_fragment();
                        fTransaction.replace(R.id.fragment_container, analysisFragment).commit();
                        break;
                    /*case R.id.help:
                        helpFragment = new help_fragment();
                        fTransaction.replace(R.id.fragment_container, helpFragment).commit();
                        break;*/
                    case R.id.logout:
                        editor1.clear();
                        editor1.commit();
                        editor2.clear();
                        editor2.commit();
                        Login_activity.actionStart(MainActivity.this);
                        break;
                    default:
                        break;
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    // DJISDKManager callback and implementations of onGetRegisteredResult and onProductChanged
    private DJISDKManager.SDKManagerCallback mDJISDKManagerCallback = new DJISDKManager.SDKManagerCallback() {
        @Override
        public void onRegister(DJIError error) {
            Log.d(TAG, error == null ? "success" : error.getDescription());
            if(error == DJISDKError.REGISTRATION_SUCCESS) {
                DJISDKManager.getInstance().startConnectionToProduct();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(getApplicationContext(), "register sdk failed, check if network is available", Toast.LENGTH_LONG).show();
                    }
                });
            }
            Log.e("TAG", error.toString());
        }
        @Override
        public void onProductChange(BaseProduct oldProduct, BaseProduct newProduct) {
            mProduct = newProduct;
            if(mProduct != null) {
                mProduct.setBaseProductListener(mDJIBaseProductListener);
            }
            notifyStatusChange();
        }
    };

    //Finally methods for DJIBaseProductListener, DJIComponentListener, notifyStatusChange and Runnable need to be implemented
    private BaseProduct.BaseProductListener mDJIBaseProductListener = new BaseProduct.BaseProductListener() {
        @Override
        public void onComponentChange(BaseProduct.ComponentKey key, BaseComponent oldComponent, BaseComponent newComponent) {
            if(newComponent != null) {
                newComponent.setComponentListener(mDJIComponentListener);
            }
            notifyStatusChange();
        }
        @Override
        public void onConnectivityChange(boolean isConnected) {
            notifyStatusChange();
        }
    };
    private BaseComponent.ComponentListener mDJIComponentListener = new BaseComponent.ComponentListener() {
        @Override
        public void onConnectivityChange(boolean isConnected) {
            notifyStatusChange();
        }
    };
    private void notifyStatusChange() {
        mHandler.removeCallbacks(updateRunnable);
        mHandler.postDelayed(updateRunnable, 500);
    }
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(FLAG_CONNECTION_CHANGE);
            sendBroadcast(intent);
        }
    };
    //DJI finished


    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    //双击退出程序，否则Toast提示
    @Override
    public void onBackPressed() {
            //两次返回键小于2s则退出程序
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
    }

}
