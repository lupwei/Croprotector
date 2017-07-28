package nova.croprotector;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;



/**
 * Created by ZTH on 2017/7/26.
 */

public class ButtonBar extends LinearLayout {

    public ButtonBar(final Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.button_bar,this);
        RadioGroup radiogroup=(RadioGroup)findViewById(R.id.rg_radio);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radiobuttonId=group.getCheckedRadioButtonId();
                switch (radiobuttonId) {
                    case R.id.rb_about:
                        //跳转到无人机界面
                        break;
                    case R.id.rb_introduction:
                        //跳转到拍照界面
                        Shoot_activity.actionStart(context);
                        break;
                    case R.id.rb_location:
                        //跳转到地图界面
                        Map_activity.actionStart(context);
                        break;
                    case R.id.rb_service:
                        UserActivity.actionStart(context);
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
