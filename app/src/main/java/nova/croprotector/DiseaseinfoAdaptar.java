package nova.croprotector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ZTH on 2017/7/25.
 */

public class DiseaseinfoAdaptar extends RecyclerView.Adapter<DiseaseinfoAdaptar.ViewHolder>{

    private List<diseaseinfo> mydiseaseinfolist;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView location;
        ImageView disease_pic;
        View divideline;

        public ViewHolder(View view){
            super(view);
            time=(TextView)view.findViewById(R.id.time);
            location=(TextView)view.findViewById(R.id.location);
            disease_pic=(ImageView)view.findViewById(R.id.disease_pic);
        }
    }


    public DiseaseinfoAdaptar(List<diseaseinfo> diseaseinfoList){
        mydiseaseinfolist=diseaseinfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_info,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        diseaseinfo disease=mydiseaseinfolist.get(position);
        holder.time.setText(disease.getTime());
        holder.location.setText(disease.getLocation());
        holder.disease_pic.setImageResource(disease.getImageId());
    }

    @Override
    public int getItemCount(){
        return mydiseaseinfolist.size();
    }


}
