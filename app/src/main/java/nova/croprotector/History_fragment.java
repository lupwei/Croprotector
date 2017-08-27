package nova.croprotector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class History_fragment extends android.app.Fragment {

    private List<historylist> diseaseList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        for(int i=0;i<5;i++){
            historylist history_1=new historylist("番茄黄曲叶病毒","3",R.mipmap.arrow_icon);
            diseaseList.add(history_1);

            historylist history_2=new historylist("番茄花叶病毒","4",R.mipmap.arrow_icon);
            diseaseList.add(history_2);

            historylist history_3=new historylist("番茄曲叶病毒","2",R.mipmap.arrow_icon);
            diseaseList.add(history_3);
        }

        historylistAdapter diseaseAdapter=new historylistAdapter(this.getActivity(),R.layout.history_list, diseaseList);
        ListView listview=(ListView)view.findViewById(R.id.List_view);
        listview.setAdapter(diseaseAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                historylist list= diseaseList.get(position);
                DiseaseActivity.actionStart(History_fragment.this.getActivity());
            }
        });

        return view;
    }

}
