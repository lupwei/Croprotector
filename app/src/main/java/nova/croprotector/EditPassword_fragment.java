package nova.croprotector;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;


public class EditPassword_fragment extends Fragment implements View.OnClickListener{

    //UI控件
    private EditText oldPassword_input;
    private EditText newPassword_input;
    private EditText newPassword_confirm;
    private Button button_submit;

    //与服务器通信相关
    private CommonResponse<List<String>> res=new CommonResponse<List<String>>();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    Gson gson = new Gson();
    Password password=new Password();

    //与用户信息文件相关
    SharedPreferences sp=getActivity().getSharedPreferences("userdata", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=sp.edit();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_password,container,false);
        oldPassword_input=(EditText)view.findViewById(R.id.oldPassword_input);
        newPassword_input=(EditText)view.findViewById(R.id.newPassword_input);
        newPassword_confirm=(EditText)view.findViewById(R.id.newPassword_confirm);
        button_submit=(Button)view.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v){
        //获取修改密码所需的全部变量
        String oldPassword=oldPassword_input.getText().toString();
        String newPassword=newPassword_input.getText().toString();
        String confirmPassword=newPassword_confirm.getText().toString();
        String phonenumber=sp.getString("phonenumber","用户信息文件受损，请重新登录");
        String userPassword=sp.getString("password","用户信息文件受损，请重新登录");

    }
}
