package nova.croprotector;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private List<historylist> diseaselist=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_layout);
        for(int i=0;i<5;i++){
            historylist history_1=new historylist("番茄黄曲叶病毒","3",R.mipmap.arrow_icon);
            diseaselist.add(history_1);

            historylist history_2=new historylist("番茄花叶病毒","4",R.mipmap.arrow_icon);
            diseaselist.add(history_2);

            historylist history_3=new historylist("番茄曲叶病毒","2",R.mipmap.arrow_icon);
            diseaselist.add(history_3);
        }

        historylistAdapter diseaseadapter=new historylistAdapter(HistoryActivity.this,R.layout.history_list,diseaselist);
        ListView listview=(ListView)findViewById(R.id.List_view);
        listview.setAdapter(diseaseadapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historylist list=diseaselist.get(position);
                DiseaseActivity.actionStart(HistoryActivity.this);
            }
        });
    }

    public static void actionStart(Context context){                                   //活动启动器
        Intent intent=new Intent(context,HistoryActivity.class);
        context.startActivity(intent);
    }
}
