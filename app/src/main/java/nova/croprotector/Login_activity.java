package nova.croprotector;

/**
 * Created by WT on 2017/7/28.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

//登录活动；还有用户名
public class Login_activity extends AppCompatActivity{

    private EditText username_edit;
    private EditText password_edit;
    private CommonResponse<List<String>> res=new CommonResponse<List<String>>();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    Gson gson = new Gson();
    User user = new User();



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
                user.setPhoneNumber(phonenumber);
                user.setPassword(password);

                String jsonStr=gson.toJson(user);
                RequestBody requestBody=RequestBody.create(JSON,jsonStr);
                HttpUtil.sendHttpRequest("http://172.20.10.14:8080/Croprotector/LoginServlet",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String responseData = response.body().string();
                        res=GsonToBean.fromJsonArray(responseData,String.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<String> list=new ArrayList<String>();
                                String loginMsg;
                                list=res.data;
                                if(res.code==0){
                                    Toast.makeText(Login_activity.this,list.get(0), Toast.LENGTH_SHORT).show();
                                    user.setFirstname(list.get(1));
                                    user.setLastname(list.get(2));
                                    //存储用户信息到userdata文件中
                                    SharedPreferences.Editor editor=getSharedPreferences("userdata",MODE_PRIVATE).edit();
                                    editor.putString("phonenumber",user.Get_phonenumber());
                                    editor.putString("password",user.Get_password());
                                    editor.putString("firstname",user.Get_firstname());
                                    editor.putString("lastname",user.Get_lastname());
                                    MainActivity.actionStart(Login_activity.this);
                                }
                                else{
                                    Toast.makeText(Login_activity.this,list.get(0), Toast.LENGTH_SHORT).show();
                                    Login_activity.actionStart(Login_activity.this);
                                }
                            }
                        });

                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //异常处理
                    }
                });
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
