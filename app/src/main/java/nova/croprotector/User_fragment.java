package nova.croprotector;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class User_fragment extends Fragment implements View.OnClickListener{

    private TextView buttonPassword;
    private TextView buttonHistory;
    private TextView buttonLogout;

    public User_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        buttonPassword=(TextView)view.findViewById(R.id.button_password);
        buttonPassword.setOnClickListener(this);
        buttonHistory=(TextView)view.findViewById(R.id.button_history);
        buttonHistory.setOnClickListener(this);
        buttonLogout=(TextView)view.findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(this);
        //TODO

        return view;
    }

    //用户界面的点击事件；
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_logout:
                AlertDialog.Builder logoutAlert=new AlertDialog.Builder(getActivity());
                logoutAlert.setTitle("警告");
                logoutAlert.setMessage("您确定要注销吗？");
                logoutAlert.setCancelable(false);
                logoutAlert.setPositiveButton("确定",new DialogInterface.OnClickListener(){                      //将来要补充注销功能
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        Login_activity.actionStart(getActivity());
                    }
                });
                logoutAlert.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                            //TODO ?
                    }
                });
                logoutAlert.show();
                break;
            case R.id.button_password:
                EditPasswordActivity.actionStart(getActivity());
                break;
            case R.id.button_history:
                HistoryActivity.actionStart(getActivity());
                break;
            default:
                break;
        }
    }
}

