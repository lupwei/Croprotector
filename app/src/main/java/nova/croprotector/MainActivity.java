package nova.croprotector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /*
    UAVRb-----"无人机"按钮；
    ShootRb-----“拍照”按钮；
    MapRb------“地图”按钮；
    UserRb------“我的”按钮
    */
    private RadioButton UAVRb,ShootRb,MapRb,UserRb;
    private ImageView iv,iv1;
    private boolean firstIc = true;

    //DJI
    private static final String TAG = MainActivity.class.getName();
    public static final String FLAG_CONNECTION_CHANGE = "dji_sdk_connection_change";
    private static BaseProduct mProduct;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        UAVRb = (RadioButton) findViewById(R.id.rb_about);
        ShootRb = (RadioButton) findViewById(R.id.rb_introduction);
        MapRb = (RadioButton) findViewById(R.id.rb_location);
        UserRb = (RadioButton) findViewById(R.id.rb_service);

        //Initialize DJI SDK Manager
        mHandler = new Handler(Looper.getMainLooper());
        DJISDKManager.getInstance().registerApp(this, mDJISDKManagerCallback);

        initListener();
    }

    //设置监听事件；
    private void initListener() {
        UAVRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_1);
                iv1.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        });
        ShootRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_2);
                iv1.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        });
        MapRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_3);
                iv1.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        });
        UserRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_4);
                iv1.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIc) {
                    iv1.setImageResource(R.mipmap.ic_5);
                    firstIc = false;
                }else {
                    iv1.setImageResource(R.mipmap.ic_4);
                    firstIc = true;
                }
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
}
