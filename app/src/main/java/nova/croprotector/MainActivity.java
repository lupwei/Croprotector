package nova.croprotector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private RadioButton aboutRb,introductRb,locationRb,serviceRb;
    private ImageView iv,iv1;
    private boolean firstIc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        aboutRb = (RadioButton) findViewById(R.id.rb_about);
        introductRb = (RadioButton) findViewById(R.id.rb_introduction);
        locationRb = (RadioButton) findViewById(R.id.rb_location);
        serviceRb = (RadioButton) findViewById(R.id.rb_service);

        initListener();
    }

    private void initListener() {
        aboutRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_1);
                iv1.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        });
        introductRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_2);
                iv1.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        });
        locationRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_3);
                iv1.setVisibility(View.GONE);
                iv.setVisibility(View.VISIBLE);
            }
        });
        serviceRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageResource(R.mipmap.ic_4);
                iv1.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
            }
        });

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstIc) {
                    iv1.setImageResource(R.mipmap.ic_5);
                    firstIc = false;
                }else {
                    iv1.setImageResource(R.mipmap.ic_4);
                    firstIc = true;
                }
            }
        });
    }
}
