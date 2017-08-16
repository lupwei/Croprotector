package nova.croprotector;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class EditPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        Button buttonSubmit=(Button)findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        EditPasswordActivity.actionStart(EditPasswordActivity.this);                             //还要添加提交逻辑
    }

    public static void actionStart(Context context){                                            //活动启动器
        Intent intent=new Intent(context,EditPasswordActivity.class);
        context.startActivity(intent);
    }
}

