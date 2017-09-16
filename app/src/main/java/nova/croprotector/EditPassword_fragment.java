package nova.croprotector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EditPassword_fragment extends Fragment implements View.OnClickListener{

    //密码相关数据项
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String phonenumber;
    private String userPassword;


    //UI控件
    private EditText oldPassword_input;
    private EditText newPassword_input;
    private EditText newPassword_confirm;
    private Button button_submit;
	
	private View view;

    //与服务器通信相关
    private CommonResponse<String> res=new CommonResponse<String>();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    Gson gson = new Gson();
    Password password=new Password();

    //与用户信息文件相关
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_password,container,false);
        return view;
    }

	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

        sp=getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        editor=sp.edit();

		oldPassword_input=(EditText)view.findViewById(R.id.oldPassword_input);
        newPassword_input=(EditText)view.findViewById(R.id.newPassword_input);
        newPassword_confirm=(EditText)view.findViewById(R.id.newPassword_confirm);
        button_submit=(Button)view.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(this);
    }
	
	
    @Override
    public void onClick(View v){

        //获取修改密码所需的全部变量
        oldPassword=oldPassword_input.getText().toString();
        newPassword=newPassword_input.getText().toString();
        confirmPassword=newPassword_confirm.getText().toString();
        phonenumber=sp.getString("phonenumber","用户信息文件受损，请重新登录");
        userPassword=sp.getString("password","用户信息文件受损，请重新登录");

        //修改密码的业务逻辑
        if(oldPassword.equals(userPassword)){
            if(newPassword.equals(confirmPassword)){
                password.setNewPassword(newPassword);
                password.setPhonenumber(phonenumber);


                String jsonStr=gson.toJson(password);
                RequestBody requestBody=RequestBody.create(JSON,jsonStr);
                HttpUtil.sendHttpRequest("http://172.20.10.14:8080/Croprotector/EditPasswordServlet",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        res=GsonToBean.fromJsonObject(responseData,String.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(res.code==0){
                                    Toast.makeText(getActivity(),res.data,Toast.LENGTH_SHORT).show();
                                    editor.putString("password",newPassword);
                                    editor.commit();
                                    FragmentManager fManager = getFragmentManager();
                                    FragmentTransaction fTransaction = fManager.beginTransaction();
                                    EditPassword_fragment editPasswordFragment=new EditPassword_fragment();
                                    fTransaction.replace(R.id.fragment_container,editPasswordFragment).commit();
                                    Log.d("EditPassword_fragment", newPassword);
                                }
                                else{
                                    Toast.makeText(getActivity(),res.data,Toast.LENGTH_SHORT).show();        //服务器端修改密码失败
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
            else{
                Toast.makeText(getActivity(),"确认密码与新密码不一致",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(),"原密码输入错误",Toast.LENGTH_SHORT).show();
        }
    }
}
