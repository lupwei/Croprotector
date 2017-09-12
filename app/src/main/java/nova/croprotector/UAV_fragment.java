package nova.croprotector;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import dji.sdk.base.BaseProduct;
import dji.sdk.products.Aircraft;


public class UAV_fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "UAV_fragment";
    //private TextView mTextConnectionStatus;
    private TextView mTextProduct;
    private Button mBtnOpen;
    View view;

    public UAV_fragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_uav,container,false);
        initUI();
        // Register the broadcast receiver for receiving the device connection's changes.
        IntentFilter filter = new IntentFilter();
        filter.addAction(FPVDemoApplication.FLAG_CONNECTION_CHANGE);
        getActivity().registerReceiver(mReceiver, filter);

        return view;
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }
    @Override
    public void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }
    @Override
    public void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }
    public void onReturn(View view){
        Log.e(TAG, "onReturn");
        //this.finish();
        getActivity().unregisterReceiver(mReceiver);
    }

    private void initUI() {
        //mTextConnectionStatus = (TextView) view.findViewById(R.id.text_connection_status);
        mTextProduct = (TextView) view.findViewById(R.id.text_product_info);
        mBtnOpen = (Button) view.findViewById(R.id.connect);
        mBtnOpen.setOnClickListener(this);
        mBtnOpen.setEnabled(true);

        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                help_fragment helpFragment=new help_fragment();
                fTransaction.replace(R.id.fragment_container,helpFragment).commit();
            }
        });

    }
    protected BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshSDKRelativeUI();
        }
    };
    /*@Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }*/
    private void refreshSDKRelativeUI() {
        BaseProduct mProduct = FPVDemoApplication.getProductInstance();
        if (null != mProduct && mProduct.isConnected()) {
            Log.v(TAG, "refreshSDK: True");
            mBtnOpen.setEnabled(true);
            String str = mProduct instanceof Aircraft ? "DJIAircraft" : "DJIHandHeld";
            //mTextConnectionStatus.setText("Status: " + str + " connected");
            //mProduct.setDJIVersionCallback(this);
            if (null != mProduct.getModel()) {
                mTextProduct.setText("" + mProduct.getModel().getDisplayName());
            } else {
                mTextProduct.setText(R.string.product_information);
            }
        } else {
            Log.v(TAG, "refreshSDK: False");
            mBtnOpen.setEnabled(false);
            mTextProduct.setText(R.string.product_information);
            //mTextConnectionStatus.setText(R.string.connection_loose);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect: {
                UAVControllerActivity.actionStart(getActivity());
                break;
            }
            default:
                break;
        }
    }
}
