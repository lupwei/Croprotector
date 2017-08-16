package nova.croprotector;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;


public class UAV_fragment extends Fragment {

    private Button buttonConnect;
    private Button buttonSet;

    public UAV_fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uav,container,false);

        buttonConnect=(Button)view.findViewById(R.id.connect);
        buttonSet=(Button)view.findViewById(R.id.set);

        buttonConnect.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO 点击连接按钮

            }
        });
        buttonSet.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO 点击设置按钮

            }
        });

        return view;
    }
}
