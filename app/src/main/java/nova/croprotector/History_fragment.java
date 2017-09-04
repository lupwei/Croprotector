package nova.croprotector;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class History_fragment extends android.app.Fragment {

    //private List<historylist> diseaseList = new ArrayList<>();

    private diseaseinfo[] diseases={
            new diseaseinfo("100,102","2017.7.26",R.drawable.disease_pic1),
            new diseaseinfo("100,58","2017.7.27",R.drawable.disease_pic1),
            new diseaseinfo("109,202","2017.8.26",R.drawable.disease_pic1)
    };

    private List<diseaseinfo> diseaseInfoList =new ArrayList<>();
    private DiseaseInfoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        initDiseases();
        SwipeMenuRecyclerView recyclerView=(SwipeMenuRecyclerView)view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this.getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new DiseaseInfoAdapter(diseaseInfoList);
        recyclerView.setAdapter(adapter);


        recyclerView.setItemViewSwipeEnabled(true); // 开启滑动删除。

        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();

                // Item被拖拽时，交换数据，并更新adapter。
                Collections.swap(diseaseInfoList, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int position = srcHolder.getAdapterPosition();
                // Item被侧滑删除时，删除数据，并更新adapter。
                diseaseInfoList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        recyclerView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。


        //圆形菜单
        CircleMenu circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#8BC34A"), R.mipmap.arrow_icon, R.mipmap.album)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.avatar)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {}

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });

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
