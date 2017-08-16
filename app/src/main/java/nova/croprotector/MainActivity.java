package nova.croprotector;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.ButterKnife;

import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKManager;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    //DJI
    private static final String TAG = MainActivity.class.getName();
    public static final String FLAG_CONNECTION_CHANGE = "dji_sdk_connection_change";
    private static BaseProduct mProduct;
    private Handler mHandler;


    //底边栏所需变量
    private RadioGroup rg_button_bar;
    private RadioButton rb_UAV;
    private RadioButton rb_User;

    private long exitTime = 0;

    //Fragment 对象
    private UAV_fragment uavFragment;
    private Shoot_fragment shootFragment;
    private Map_fragment mapFragment;
    private User_fragment userFragment;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // When the compile and target version is higher than 22, please request the
        // following permissions at runtime to ensure the
        // SDK work well.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA
                    }
                    , 1);
        }
        //Initialize DJI SDK Manager
        mHandler = new Handler(Looper.getMainLooper());
        DJISDKManager.getInstance().registerApp(this, mDJISDKManagerCallback);

        fManager = getFragmentManager();
        rg_button_bar = (RadioGroup) findViewById(R.id.button_bar);
        rg_button_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_UAV = (RadioButton) findViewById(R.id.button_UAV);
        rb_User = (RadioButton) findViewById(R.id.button_user);
        rb_User.setChecked(true);
        rb_UAV.setChecked(true);
        //模拟一次点击，既进去后选择第一项
        rb_User.performClick();
        rb_UAV.performClick();
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
                        Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "register sdk failed, check if network is available", Toast.LENGTH_LONG).show();
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

    //fragment跳转
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.button_UAV:
                if(uavFragment == null){
                    uavFragment = new UAV_fragment();
                    fTransaction.add(R.id.fragment_container,uavFragment);
                }else{
                    fTransaction.show(uavFragment);
                }
                break;
            case R.id.button_shoot:
                if(shootFragment == null){
                    shootFragment = new Shoot_fragment();
                    fTransaction.add(R.id.fragment_container,shootFragment);
                }else{
                    fTransaction.show(shootFragment);
                }
                break;
            case R.id.button_map:
                if(mapFragment == null){
                    mapFragment = new Map_fragment();
                    fTransaction.add(R.id.fragment_container,mapFragment);
                }else{
                    fTransaction.show(mapFragment);
                }
                break;
            case R.id.button_user:
                if(userFragment == null){
                    userFragment = new User_fragment();
                    fTransaction.add(R.id.fragment_container,userFragment);
                }else{
                    fTransaction.show(userFragment);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(uavFragment != null)fragmentTransaction.hide(uavFragment);
        if(shootFragment != null)fragmentTransaction.hide(shootFragment);
        if(mapFragment != null)fragmentTransaction.hide(mapFragment);
        if(userFragment != null)fragmentTransaction.hide(userFragment);
    }

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
