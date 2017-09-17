package nova.croprotector;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by ZTH on 2017/7/25.
 */

public class DiseaseInfoAdapter extends RecyclerView.Adapter<DiseaseInfoAdapter.ViewHolder>{

    private List<DiseaseInfo> myDiseaseInfoList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView location;
        TextView name;
        ImageView disease_pic;
        CardView cardView;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            time=(TextView)view.findViewById(R.id.disease_time);
            location=(TextView)view.findViewById(R.id.disease_gps);
            name=(TextView)view.findViewById(R.id.disease_name);
            disease_pic=(ImageView)view.findViewById(R.id.disease_image);

        }
    }


    public DiseaseInfoAdapter(List<DiseaseInfo> diseaseInfoList){
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
        //得到当前项DiseaseInfo实例
        DiseaseInfo disease= myDiseaseInfoList.get(position);
        //得到时间
        holder.time.setText(disease.getInfoTime());
        //得到疾病名称
        holder.name.setText(disease.getDiseaseKind().getDiseaseName());
        //得到疾病位置，精确到个位
        int longitude=(int)disease.getLongitude();
        int latitude=(int)disease.getLatitude();
        String longitudeStr=String.valueOf(longitude);
        String latitudeStr=String.valueOf(latitude);
        holder.location.setText("("+longitudeStr+","+latitudeStr+")");
        //得到疾病图片
        Bitmap bm=PictureClass.StringToBitmap(disease.getPicture());
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] buff=baos.toByteArray();
        Glide.with(mContext).load(buff).into(holder.disease_pic);
    }

    @Override
    public int getItemCount(){
        return myDiseaseInfoList.size();
    }


}
