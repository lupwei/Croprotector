package nova.croprotector;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ZTH on 2017/7/25.
 */

public class DiseaseInfoAdapter extends RecyclerView.Adapter<DiseaseInfoAdapter.ViewHolder>{

    public List<diseaseinfo> myDiseaseInfoList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView location;
        ImageView disease_pic;
        CardView cardView;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            time=(TextView)view.findViewById(R.id.disease_time);
            location=(TextView)view.findViewById(R.id.disease_gps);
            disease_pic=(ImageView)view.findViewById(R.id.disease_image);
        }
    }


    public DiseaseInfoAdapter(List<diseaseinfo> diseaseInfoList){
        myDiseaseInfoList = diseaseInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if (mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.disease_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        diseaseinfo disease= myDiseaseInfoList.get(position);
        holder.time.setText(disease.getTime());
        holder.location.setText(disease.getLocation());
        Glide.with(mContext).load(disease.getImageId()).into(holder.disease_pic);
        //holder.disease_pic.setImageResource(disease.getImageId());
    }

    @Override
    public int getItemCount(){
        return myDiseaseInfoList.size();
    }


}
