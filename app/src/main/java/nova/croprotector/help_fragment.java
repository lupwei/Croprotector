package nova.croprotector;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.InputStreamBitmapDecoderFactory;

import java.io.IOException;

public class help_fragment extends android.app.Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_help, container, false);
        LargeImageView largeImageView = (LargeImageView) view.findViewById(R.id.djiHelp_imageView);
        try {
            largeImageView.setImage(new InputStreamBitmapDecoderFactory(this.getActivity().getAssets().open("dji_help.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    //主界面获取焦点
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    FragmentManager fManager = getFragmentManager();
                    FragmentTransaction fTransaction = fManager.beginTransaction();
                    UAV_fragment uavFragment = new UAV_fragment();
                    fTransaction.replace(R.id.fragment_container,uavFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
