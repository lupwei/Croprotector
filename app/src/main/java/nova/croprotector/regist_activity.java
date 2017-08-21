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
import android.widget.EditText;


import okhttp3.OkHttpClient;

//注册活动；
public class regist_activity extends AppCompatActivity {

    private EditText username_edit;
    private EditText password_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);

        Button complete = (Button) findViewById(R.id.complete);
        username_edit=(EditText)findViewById(R.id.username_edit);
        password_edit=(EditText)findViewById(R.id.password_edit);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.complete) {
                    String phonenumber = username_edit.getText().toString();
                    String password=password_edit.getText().toString();
                    sendRequest(phonenumber,password);
                    MainActivity.actionStart(regist_activity.this);
                }
            }
        });
    }
    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,regist_activity.class);
        context.startActivity(intent);
    }

    private void sendRequest(String phonenumber,String password){
        new Thread(new Runnable(){
            @Override
            public void run(){
                OkHttpClient client=new OkHttpClient();
            }
        }).start();
    }
}
