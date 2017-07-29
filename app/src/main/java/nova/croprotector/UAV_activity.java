package nova.croprotector;

/**
 * Created by WT on 2017/7/29.
 */
//点击导航栏中的无人机按钮启动该活动；
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
public class UAV_activity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.regist_layout);

            Button connected = (Button) findViewById(R.id.connected);
            connected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编写连接无人机的函数
                    UAV_activity.actionStart(UAV_activity.this);
                }
            });
    }

    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,UAV_activity.class);
        context.startActivity(intent);
    }
}
