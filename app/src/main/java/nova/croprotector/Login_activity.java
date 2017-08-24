package nova.croprotector;

/**
 * Created by WT on 2017/7/28.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

//登录活动；
public class Login_activity extends AppCompatActivity{
    private EditText username_edit;
    private EditText password_edit;
    private String message;
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    Gson gson = new Gson();



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        Button login_B = (Button)findViewById(R.id.login);
        Button regist_B = (Button)findViewById(R.id.register);
        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);

        login_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = username_edit.getText().toString();
                String password = password_edit.getText().toString();
                User user = new User();
                user.setPhoneNumber(phonenumber);
                user.setPassword(password);

                String jsonStr=gson.toJson(user);
                RequestBody requestBody=RequestBody.create(JSON,jsonStr);
                HttpUtil.sendHttpRequest("http://172.20.10.14:8080/Croprotector/RegisterServlet",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String responseData = response.body().string();
                        message=gson.fromJson(responseData,String.class);
                        Looper.prepare();
                        Toast.makeText(Login_activity.this, message, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //异常处理
                    }
                });
                if(message=="登陆成功"){
                    MainActivity.actionStart(Login_activity.this);
                }
                else{
                    Login_activity.actionStart(Login_activity.this);
                }
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
