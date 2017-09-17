package nova.croprotector;
/**
 * Created by WT on 2017/7/28.
 */
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

//注册活动；还有用户名没加
public class regist_activity extends AppCompatActivity {

    private EditText phoneNumber_input;
    private EditText password_edit;
    private EditText userName_input;
    private CommonResponse<String> res=new CommonResponse<String>();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    Gson gson = new Gson();
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);

        Button complete = (Button) findViewById(R.id.complete);
        phoneNumber_input = (EditText) findViewById(R.id.phoneNumber_input);
        password_edit = (EditText) findViewById(R.id.password_edit);
        userName_input = (EditText) findViewById(R.id.userName_input);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = phoneNumber_input.getText().toString();
                String password = password_edit.getText().toString();
                String username= userName_input.getText().toString();
                user.setPhoneNumber(phonenumber);
                user.setPassword(password);
                user.setFirstname(username);
                user.setLastname(username);

                String jsonStr=gson.toJson(user);
                RequestBody requestBody=RequestBody.create(JSON,jsonStr);
                HttpUtil.sendHttpRequest("http://172.20.10.14:8080/Croprotector/RegisterServlet",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException{
                        String responseData = response.body().string();
                        res=GsonToBean.fromJsonObject(responseData,String.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(res.code==0){
                                    Toast.makeText(regist_activity.this, res.data, Toast.LENGTH_SHORT).show();
                                    //存储用户信息到userdata文件中
                                    SharedPreferences sp=getSharedPreferences("userdata",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sp.edit();
                                    editor.putBoolean("isLogin",true);
                                    editor.putString("phonenumber",user.Get_phonenumber());
                                    editor.putString("password",user.Get_password());
                                    editor.putString("username",user.Get_firstname());
                                    editor.commit();
                                    Log.d("regist_activity", user.Get_phonenumber());
                                    Log.d("regist_activity", user.Get_password());
                                    Log.d("regist_activity", user.Get_firstname());
                                    MainActivity.actionStart(regist_activity.this);
                                }
                                else{
                                    Toast.makeText(regist_activity.this, res.data, Toast.LENGTH_SHORT).show();
                                    Login_activity.actionStart(regist_activity.this);
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

    }

            public static void actionStart(Context context) {
                //活动启动器
                Intent intent = new Intent(context, regist_activity.class);
                context.startActivity(intent);
            }

}

