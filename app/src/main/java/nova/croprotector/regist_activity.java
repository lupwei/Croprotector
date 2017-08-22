package nova.croprotector;
/**
 * Created by WT on 2017/7/28.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//注册活动；
public class regist_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);

        Button complete = (Button) findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UAVControllerActivity.actionStart(regist_activity.this);
            }
        });
    }
    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,regist_activity.class);
        context.startActivity(intent);
    }
}
