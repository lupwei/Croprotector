package nova.croprotector;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class History_fragment extends android.app.Fragment {

    //private List<historylist> diseaseList = new ArrayList<>();

    private DiseaseInfo[] diseases={
            new DiseaseInfo("100,102","2017.7.26",R.drawable.disease_pic1),
            new DiseaseInfo("100,58","2017.7.27",R.drawable.disease_pic1),
            new DiseaseInfo("109,202","2017.8.26",R.drawable.disease_pic1)
    };

    private List<DiseaseInfo> diseaseInfoList =new ArrayList<>();
    private DiseaseInfoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        initDiseases();
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this.getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new DiseaseInfoAdapter(diseaseInfoList);
        recyclerView.setAdapter(adapter);



        return view;
    }

    //测试阶段，随机生成数据
    private void initDiseases(){
        diseaseInfoList.clear();
        for (int i = 0; i <50 ; i++) {
            Random random=new Random();
            int index=random.nextInt(diseases.length);
            diseaseInfoList.add(diseases[index]);
        }
    }

}
