package nova.croprotector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);
        TextView buttonpassword=(TextView)findViewById(R.id.button_password);
        buttonpassword.setOnClickListener(this);
        TextView buttonhistory=(TextView)findViewById(R.id.button_history);
        buttonhistory.setOnClickListener(this);
        TextView buttonlogout=(TextView)findViewById(R.id.button_logout);
        buttonlogout.setOnClickListener(this);
    }




    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_logout:
                AlertDialog.Builder logoutalert=new AlertDialog.Builder(UserActivity.this);
                logoutalert.setTitle("警告");
                logoutalert.setMessage("您确定要注销吗？");
                logoutalert.setCancelable(false);
                logoutalert.setPositiveButton("确定",new DialogInterface.OnClickListener(){                      //将来要补充注销功能
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        UserActivity.actionStart(UserActivity.this);
                    }
                });
                logoutalert.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                   @Override
                    public void onClick(DialogInterface dialog,int which){

                   }
                });
                logoutalert.show();
                break;
            case R.id.button_password:
                EditpasswordActivity.actionStart(UserActivity.this);
                break;
            case R.id.button_history:
                HistoryActivity.actionStart(UserActivity.this);
                break;
            default:
                break;
        }
    }

    public static void actionStart(Context context){
        //活动启动器
        Intent intent=new Intent(context,UserActivity.class);
        context.startActivity(intent);
    }
}
