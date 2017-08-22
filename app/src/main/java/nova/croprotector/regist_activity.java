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
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//注册活动；
public class regist_activity extends AppCompatActivity {

    private EditText username_edit;
    private EditText password_edit;
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);

        Button complete = (Button) findViewById(R.id.complete);
        username_edit = (EditText) findViewById(R.id.username_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.complete) {
                    String phonenumber = username_edit.getText().toString();
                    String password = password_edit.getText().toString();
                    User user = new User();
                    user.setPhoneNumber(phonenumber);
                    user.setPassword(password);
                    sendRequest(user);
                    MainActivity.actionStart(regist_activity.this);
                }
            }
        });
    }
            public void actionStart(Context context) {
                //活动启动器
                Intent intent = new Intent(context, regist_activity.class);
                context.startActivity(intent);
                MainActivity.actionStart(regist_activity.this);
            }

            private void sendRequest(final User user) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson = new Gson();
                            String jsonStr = gson.toJson(user);
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = RequestBody.create(JSON, jsonStr);
                            Request request = new Request.Builder()
                                    .url("http://10.110.210.21:8080/Croprotector/RegisterServlet")
                                    .post(requestBody)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            Toast.makeText(regist_activity.this, responseData, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
