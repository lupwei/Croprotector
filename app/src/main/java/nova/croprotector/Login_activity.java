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

//登录活动；
public class Login_activity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        Button login_B = (Button)findViewById(R.id.login);
        Button regist_B = (Button)findViewById(R.id.register);

        login_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actionStart(Login_activity.this);
            }
        });
        regist_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist_activity.actionStart(Login_activity.this);
            }
        });
    }
    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,Login_activity.class);
        context.startActivity(intent);
    }

}
