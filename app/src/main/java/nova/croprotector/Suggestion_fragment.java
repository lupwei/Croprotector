package nova.croprotector;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Suggestion_fragment extends android.app.Fragment {


    public Suggestion_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_suggestion, container, false);

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
