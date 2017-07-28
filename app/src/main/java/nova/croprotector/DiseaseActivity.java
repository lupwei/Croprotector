package nova.croprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DiseaseActivity extends AppCompatActivity implements View.OnClickListener{

    private List<diseaseinfo> diseaseinfoList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_activity_layout);
        for(int i=0;i<15;i++){
            diseaseinfo disease1=new diseaseinfo("100,102","2017.7.26",R.drawable.disease_pic1);           //随便瞎加的测试数据，循环是为了防止数据项太少了
            diseaseinfoList.add(disease1);
        }
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DiseaseinfoAdaptar adaptar=new DiseaseinfoAdaptar(diseaseinfoList);
        recyclerView.setAdapter(adaptar);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Button button1=(Button)findViewById(R.id.infolist);
        button1.setOnClickListener(this);
        Button button2=(Button)findViewById(R.id.maplist);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.infolist:
                DiseaseActivity.actionStart(DiseaseActivity.this);
                break;
            case R.id.maplist:
                DiseaseMapActivity.actionStart(DiseaseActivity.this);
                break;
            default:
                break;
        }
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,DiseaseActivity.class);
        context.startActivity(intent);
    }
}
