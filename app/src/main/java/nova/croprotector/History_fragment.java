package nova.croprotector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class History_fragment extends Fragment {

    private int choice=-1;
	private View view;

    private Gson gson=new Gson();
    private static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    CommonResponse<List<DiseaseInfo>> res=new CommonResponse<List<DiseaseInfo>>();

    String infoJsonStr;
    DiseaseInfo diseaseinfo;
    String infoNo;
    private List<DiseaseInfo> diseaseInfoList =new ArrayList<DiseaseInfo>();
    private DiseaseInfoAdapter adapter;

    //疾病信息缓存
    private SharedPreferences sp1;
    private SharedPreferences.Editor editor1;

    //用户信息缓存
    private SharedPreferences sp2;
    private SharedPreferences.Editor editor2;

    SwipeMenuRecyclerView recyclerView;
    GridLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView=(SwipeMenuRecyclerView)view.findViewById(R.id.recycler_view);
        return view;
    }

	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        Log.d("tag", "onActivityCreated: 111111111111111111111111111111111");
        layoutManager=new GridLayoutManager(getActivity(),1);
        Log.d("tag", "onActivityCreated: 2222222222222222222222222222222222222");

        DiseaseInfo info1=new DiseaseInfo();
        DiseaseKind kind1=new DiseaseKind();
        kind1.setDiseaseNo("4");
        kind1.setDiseaseName("苹果痂-苹果黑星菌");
        info1.setInfoNo("182173664982017-09-17 16:43:33");
        info1.setDiseaseNo("4");
        info1.setDiseaseKind(kind1);
        info1.setInfoTime("2017-09-17 16:43:33");
        info1.setLongitude(82.37940387558315);
        info1.setLatitude(31.730400581746217);
        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(), R.drawable.disease1);
        String picStr1=PictureClass.BitmapToString(bitmap1);
        info1.setPicture(picStr1);

        DiseaseInfo info2=new DiseaseInfo();
        DiseaseKind kind2=new DiseaseKind();
        kind2.setDiseaseNo("6");
        kind2.setDiseaseName("健康的苹果");
        info2.setInfoNo("182173664982017-09-17 16:44:46");
        info2.setDiseaseNo("6");
        info2.setDiseaseKind(kind2);
        info2.setInfoTime("2017-09-17 16:44:46");
        info2.setLongitude(86.00917825089101);
        info2.setLatitude(35.08582343676832);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.disease2);
        String picStr2=PictureClass.BitmapToString(bitmap2);
        info2.setPicture(picStr2);

        DiseaseInfo info3=new DiseaseInfo();
        DiseaseKind kind3=new DiseaseKind();
        kind3.setDiseaseNo("1");
        kind3.setDiseaseName("苹果黑腐病-榅桲囊孢壳菌");
        info3.setInfoNo("182173664982017-09-17 16:59:57");
        info3.setDiseaseNo("1");
        info3.setDiseaseKind(kind3);
        info3.setInfoTime("2017-09-17 16:59:57");
        info3.setLongitude(87.43472877178868);
        info3.setLatitude(36.178394669998454);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.disease3);
        String picStr3=PictureClass.BitmapToString(bitmap3);
        info3.setPicture(picStr3);

        for(int i=0;i<3;i++){
            diseaseInfoList.add(info1);
            diseaseInfoList.add(info2);
            diseaseInfoList.add(info3);
        }

        //获取要显示的DiseaseInfo的数据
        //先检查本地的缓存文件，如果为空再从服务器获取数据

        /*
        //检查本地的缓存文件
        sp1=getActivity().getSharedPreferences("infodata", Context.MODE_PRIVATE);
        editor1=sp1.edit();
        Boolean isEmpty=sp1.getBoolean("isEmpty",true);
        if(isEmpty){
            //本地缓存文件为空，从服务器获取数据,然后再存入缓存文件
            //取出用户手机号
            sp2=getActivity().getSharedPreferences("userdata",Context.MODE_PRIVATE);
            String phonenumber=sp2.getString("phonenumber","用户信息文件受损，请重新登录");
            Log.d("tag", "onActivityCreated: 33333333333333333333333333333333");

            String jsonStr=gson.toJson(phonenumber);
            RequestBody requestBody=RequestBody.create(JSON,jsonStr);
            HttpUtil.sendHttpRequest("http://172.20.10.14:8080/Croprotector/HistoryServlet",requestBody,new okhttp3.Callback(){
                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    String responseData = response.body().string();
                    res=GsonToBean.fromJsonArray(responseData,DiseaseInfo.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            diseaseInfoList=res.data;
                            recyclerView.setLayoutManager(layoutManager);
                            adapter=new DiseaseInfoAdapter(diseaseInfoList);
                            recyclerView.setAdapter(adapter);
                            //存入缓存
                            editor1.putBoolean("isEmpty",false);
                            for(int i=0;i<diseaseInfoList.size();i++){
                                diseaseinfo=diseaseInfoList.get(i);
                                infoNo=diseaseinfo.getInfoNo();
                                infoJsonStr=gson.toJson(diseaseinfo);
                                editor1.putString(infoNo,infoJsonStr);
                            }
                            editor1.commit();
                        }
                    });
                }
                @Override
                public void onFailure(Call call,IOException e){
                    //异常处理
                }
            });
        }
        else{
            //本地缓存文件不为空，直接从本地获取数据
            Map<String,?> infoMap;
            infoMap=sp1.getAll();
            for(Map.Entry<String,?> entry:infoMap.entrySet()){
                infoJsonStr=entry.getValue().toString();
                if(infoJsonStr=="false"){
                    continue;
                }
                else{
                    diseaseinfo=gson.fromJson(infoJsonStr,DiseaseInfo.class);
                    diseaseInfoList.add(diseaseinfo);
                }
            }
            recyclerView.setLayoutManager(layoutManager);
            adapter=new DiseaseInfoAdapter(diseaseInfoList);
            recyclerView.setAdapter(adapter);
        }
        */

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
        recyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO
                SuggestionActivity.actionStart(getActivity());
            }
        });


        //圆形菜单
        CircleMenu circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#8BC34A"), R.mipmap.ic_menu_white_48dp, R.mipmap.ic_close_white_48dp)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.ic_format_list_bulleted_white_48dp)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.ic_map_white)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    //图标点击事件
                    @Override
                    public void onMenuSelected(int index) {
                        choice=index;
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {
                if(-1==choice) return;
                FragmentManager fManager = getFragmentManager();
                FragmentTransaction fTransaction = fManager.beginTransaction();
                switch(choice){
                    //列表图标
                    case 0:
                        choice=-1;
                        History_fragment historyFragment=new History_fragment();
                        fTransaction.replace(R.id.fragment_container,historyFragment).commit();
                        break;
                    //地图图标
                    case 1:
                        choice=-1;
                        HistoryMap_fragment historyMapFragment=new HistoryMap_fragment();
                        fTransaction.replace(R.id.fragment_container,historyMapFragment).commit();
                        break;
                }
            }

        });



    }
	


}
