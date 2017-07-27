package nova.croprotector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZTH on 2017/7/23.
 */

public class historylistAdapter extends ArrayAdapter<historylist> {
    private int resourceid;                      //ListView 子布局的id

    public historylistAdapter(Context context, int viewid, List<historylist> objects){
        super(context,viewid,viewid,objects);
        resourceid=viewid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        historylist listitem=getItem(position);                           //获取当前项的historylist实例
        View view;
        ViewHolder viewholder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
            viewholder=new ViewHolder();
            viewholder.diseasename=(TextView)view.findViewById(R.id.diseasename);
            viewholder.item_num=(TextView)view.findViewById(R.id.item_num);
            viewholder.arrowicon=(ImageView)view.findViewById(R.id.arrow);
            view.setTag(viewholder);
        }
        else {
            view = convertView;
            viewholder=(ViewHolder)view.getTag();
        }
        viewholder.diseasename.setText(listitem.getDiseasename());
        viewholder.item_num.setText(listitem.getNum());
        viewholder.arrowicon.setImageResource(listitem.getImageid());
        return view;
    }

    class ViewHolder{
        TextView diseasename;
        TextView item_num;
        ImageView arrowicon;
    }
}
