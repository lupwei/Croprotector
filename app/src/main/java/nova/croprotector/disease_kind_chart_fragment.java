package nova.croprotector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;


public class disease_kind_chart_fragment extends Fragment {

    /*========== 控件相关 ==========*/
    private BubbleChartView mBubbleView;

    /*========== 状态相关 ==========*/
    private boolean isHasAxes = true;                       //是否有坐标轴
    private boolean isHasAxesName = true;                   //坐标轴是否有名称
    private boolean isHasLabels = false;                    //是否显示气泡标签
    private boolean isBubblesHasSelected = true;           //气泡选中是否显示标签
    private ValueShape bubbleShape = ValueShape.CIRCLE;     //默认气泡形状为圆形(还有方形)

    /*========== 数据相关 ==========*/
    private static final int BUBBLES_NUM = 8;               //总气泡数
    private BubbleChartData mBubbleData;                    //气泡图表数据
    public final static String[] labelStrs = new String[]{"苹果痂", "苹果黑腐病", "健康的苹果", "苹果黑腐病", "葡萄黑麻疹", "葡萄黑腐病", "健康的葡萄","葡萄叶枯病"};

    private View view;

    public disease_kind_chart_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_disease_kind_chart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mBubbleView=(BubbleChartView)view.findViewById(R.id.disease_kind_bubblechart);

        //设置气泡图表的数据
        setBubbleDatas();

        //设置点击事件
        mBubbleView.setOnValueTouchListener(new ValueTouchListener());
    }

    //设置气泡图表的数据
    private void setBubbleDatas() {
        List<BubbleValue> values = new ArrayList<>();
        //设置每个气泡的数据、颜色、形状
        for (int i = 0; i < BUBBLES_NUM; i++) {
            BubbleValue value = new BubbleValue(i, (float) Math.random() * 100, (float) Math.random() * 1000);
            value.setColor(ChartUtils.pickColor());
            value.setShape(bubbleShape);
            value.setLabel(labelStrs[i]);
            values.add(value);
        }

        //设置其他属性
        mBubbleData = new BubbleChartData(values);
        mBubbleData.setHasLabels(isHasLabels);
        mBubbleData.setHasLabelsOnlyForSelected(isBubblesHasSelected);

        //设置坐标轴相关属性
        if (isHasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (isHasAxesName) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            mBubbleData.setAxisXBottom(axisX);
            mBubbleData.setAxisYLeft(axisY);
        } else {
            mBubbleData.setAxisXBottom(null);
            mBubbleData.setAxisYLeft(null);
        }

        mBubbleView.setBubbleChartData(mBubbleData);
    }

    //设置点击事件
    private class ValueTouchListener implements BubbleChartOnValueSelectListener {
        @Override
        public void onValueSelected(int bubbleIndex, BubbleValue value) {
            Toast.makeText(getActivity(), "选中的气泡值约: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
        }
    }
}
