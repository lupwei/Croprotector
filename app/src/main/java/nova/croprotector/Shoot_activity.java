package nova.croprotector;
/**
 * Created by WT on 2017/7/28.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
//点击导航栏的“相机”按钮启动该活动；
public class Shoot_activity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);

        Button camera = (Button) findViewById(R.id.shoot_button);
        Button album = (Button) findViewById(R.id.album_button);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //编写点击拍照按钮的函数
                Shoot_activity.actionStart(Shoot_activity.this);
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //编写点击从相册选取照片的函数
                Shoot_activity.actionStart(Shoot_activity.this);
            }
        });
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Shoot_activity.class);
        context.startActivity(intent);
    }
}
