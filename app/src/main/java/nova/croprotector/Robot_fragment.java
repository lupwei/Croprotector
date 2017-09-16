package nova.croprotector;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class Robot_fragment extends Fragment {

    //private List<historylist> diseaseList = new ArrayList<>();
    private int choice=-1;

    private RobotInfo[] diseases={
            new RobotInfo("编号：01","在线",R.drawable.disease_pic1),
            new RobotInfo("编号：02","离线",R.drawable.disease_pic1),
            new RobotInfo("未检测到新设备"," ",R.drawable.disease_pic1)
    };

    private List<RobotInfo> diseaseInfoList =new ArrayList<>();
    private RobotInfoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robot, container, false);


        initDiseases();
        SwipeMenuRecyclerView recyclerView=(SwipeMenuRecyclerView)view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this.getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RobotInfoAdapter(diseaseInfoList);
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

        return view;
    }

    //测试阶段，随机生成数据
    private void initDiseases(){
        diseaseInfoList.clear();
        diseaseInfoList.add(diseases[0]);
        diseaseInfoList.add(diseases[1]);
        diseaseInfoList.add(diseases[2]);
    }

}
