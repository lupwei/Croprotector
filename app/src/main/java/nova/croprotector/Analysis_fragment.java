package nova.croprotector;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Analysis_fragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private View view;
    private AnalysisAdapter adapter;
    private List<Fragment> fragmentList=new ArrayList<>();

    public Analysis_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_analysis, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mTabLayout=(TabLayout)view.findViewById(R.id.tabs);

        disease_num_chart_fragment Numchart=new disease_num_chart_fragment();
        disease_kind_chart_fragment Kindchart=new disease_kind_chart_fragment();
        disease_chart_fragment chart=new disease_chart_fragment();
        fragmentList.add(Numchart);
        fragmentList.add(Kindchart);
        fragmentList.add(chart);

        //去除阴影
        toolbar=(Toolbar)LayoutInflater.from(getActivity()).inflate(R.layout.activity_main,null).findViewById(R.id.toolBar);
        toolbar.setElevation(0);

        mViewPager=(ViewPager)view.findViewById(R.id.viewPager);
        adapter=new AnalysisAdapter(getChildFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }
}
