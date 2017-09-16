package nova.croprotector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



import java.util.List;

/**
 * Created by ZTH on 2017/9/16.
 */

public class AnalysisAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public AnalysisAdapter(FragmentManager manager,List list){
        super(manager);
        mList=list;
    }

    @Override
    public Fragment getItem(int position){
        return mList.get(position);
    }

    @Override
    public int getCount(){
        return mList.size();
    }


}
