package nova.croprotector;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DiseaseMapActivity extends AppCompatActivity implements View.OnClickListener{                          //对应的layout需要添加地图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_map_activity_layout);
        Button button1=(Button)findViewById(R.id.infolist2);
        button1.setOnClickListener(this);
        Button button2=(Button)findViewById(R.id.maplist2);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.infolist2:
                DiseaseActivity.actionStart(DiseaseMapActivity.this);
                break;
            case R.id.maplist2:
                DiseaseMapActivity.actionStart(DiseaseMapActivity.this);
                break;
            default:
                break;
        }
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,DiseaseMapActivity.class);
        context.startActivity(intent);
    }

}
