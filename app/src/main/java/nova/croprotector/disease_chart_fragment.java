package nova.croprotector;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;


public class disease_chart_fragment extends Fragment {

    /*========= 控件相关 =========*/
    private PieChartView mPieChartView;                 //饼状图控件

    /*========= 状态相关 =========*/
    private boolean isExploded = false;                 //每块之间是否分离
    private boolean isHasLabelsInside = false;          //标签在内部
    private boolean isHasLabelsOutside = false;         //标签在外部
    private boolean isHasCenterCircle = true;          //空心圆环
    private boolean isPiesHasSelected = false;          //块选中标签样式
    private boolean isHasCenterSingleText = true;      //圆环中心单行文字
    private boolean isHasCenterDoubleText = true;      //圆环中心双行文字

    /*========= 数据相关 =========*/
    private PieChartData mPieChartData;                 //饼状图数据

    private View view;

    public disease_chart_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_disease_chart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mPieChartView=(PieChartView)view.findViewById(R.id.disease_piechart);

        //设置环形图数据
        setPieDatas();

        //设置点击事件
        mPieChartView.setOnValueTouchListener(new ValueTouchListener());
    }

    //设置环形图数据
    private void setPieDatas() {
        int numValues = 6;                //把一张饼切成6块

        /*===== 随机设置每块的颜色和数据 =====*/
        List<SliceValue> values = new ArrayList<>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        /*===== 设置相关属性 类似Line Chart =====*/
        mPieChartData = new PieChartData(values);
        mPieChartData.setHasLabels(isHasLabelsInside);
        mPieChartData.setHasLabelsOnlyForSelected(isPiesHasSelected);
        mPieChartData.setHasLabelsOutside(isHasLabelsOutside);
        mPieChartData.setHasCenterCircle(isHasCenterCircle);

        //是否分离
        if (isExploded) {
            mPieChartData.setSlicesSpacing(18);                 //分离间距为18
        }

        //是否显示单行文本
        if (isHasCenterSingleText) {
            mPieChartData.setCenterText1("病虫害");             //文本内容
        }

        //标签显示样式
        mPieChartView.setCircleFillRatio(1.0f);

        //是否显示双行文本
        if (isHasCenterDoubleText) {
            mPieChartData.setCenterText2("种类比例");             //文本内容

            mPieChartData.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_double_text_size)));
        }

        mPieChartView.setPieChartData(mPieChartData);         //设置控件
    }

    //设置点击事件
    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(getActivity(), "当前选中块约占: " + (int) value.getValue() + " %", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
        }
    }
}
