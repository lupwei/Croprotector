package nova.croprotector;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class EditpasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editpassword_activity_layout);
        Button buttonsubmit=(Button)findViewById(R.id.button_submit);
        buttonsubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        EditpasswordActivity.actionStart(EditpasswordActivity.this);                             //还要添加提交逻辑
    }

    public static void actionStart(Context context){                                            //活动启动器
        Intent intent=new Intent(context,EditpasswordActivity.class);
        context.startActivity(intent);
    }
}
