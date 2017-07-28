package nova.croprotector;
/**
 * Created by WT on 2017/7/28.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
//点击导航栏的“地图”按钮启动该活动；

public class Map_activity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
    }
    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,map_activity.class);
        context.startActivity(intent);
    }
}


