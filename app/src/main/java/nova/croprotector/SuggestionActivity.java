package nova.croprotector;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SuggestionActivity extends AppCompatActivity {

    public static final String FRUIT_NAME="fruit_name";
    public static final String FRUIT_IMAGE_ID="fruit_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        Intent intent=getIntent();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView=(ImageView)findViewById(R.id.fruit_image_view);
        TextView fruitContentText=(TextView)findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("苹果雪松生锈-雪松苹果锈病菌");
        Glide.with(this).load(R.drawable.disease_pic1).into(fruitImageView);
        fruitContentText.setText("危害性状  危害苹果属、刺柏属。在刺柏的细枝、分枝和叶上产生菌痪，或在茎部引起纺锤形肿胀，在潮湿条件下可产生冬孢子角。在锈子器寄主上，锈子器引起的叶片症状和性孢子器引起的症状是一致的。能在感病品种上造成非常严重的落叶。偶尔能在苹果表面引起褐色坏死斑。\n" +
                "\n" +
                "生物特性  冬孢子堆在枝条边上的球形或肾形，直径1-3cm的菌瘿上形成，长圆柱形，逐渐变细，长10-20mm，宽1-2mm，锈褐色。冬孢子：双细胞，圆柱形至拟纺锤形。15-21μm× 35-65μm，壁厚0.5-1μm。在蔷薇科寄主上，锈子器：毛型锈子器为叶背生，具包被，高3-5mm。在顶端开裂。锈孢子团为红褐色。锈孢子：直径15-28μm。 转主寄生，需要刺柏属和蔷薇科寄主来完成它的生活史。春季，冬孢子堆产生在刺柏属的茎、嫩枝和叶上。在潮湿条件下，冬孢子萌发并产生担孢子，担孢子飞散再侵染邻近的蔷薇科寄主。担孢子侵染蔷薇科寄主的叶的上表面，产生性孢子器；在春末到初夏季节用肉眼即可看到，随后，在叶背部的管状保护鞘（包被）内产生夏孢子。夏孢子堆以独特的形式出现在果实上。据推侧，侵染果实的病菌可能是其他的种。当包被破裂时，夏孢子即被释放，并随风作长距离传播至刺柏属寄主上。在刺柏属上萌发后，产生越冬的潜伏菌丝。受侵染的叶或果实脱落后，对蔷薇科寄主的侵染便不能继续。春天，冬孢子阶段出现在刺柏属上，开始下一轮生活史。\n" +
                "\n" +
                "传染途径  在自然条件下通过蔷薇科寄主上的担孢子传播，也可通过刺柏属寄主上的锈孢子风传。远距离传播主要借助染病的植株体（特别是用其做包装材料时）等。\n" +
                "\n" +
                "防疫方法  由于病菌可系统地侵染桧柏的茎和常绿的叶，因此没有一种化学处理方法能取得令人满意的效果。有人提出应用放线菌酮来抑制雪松-苹果锈病菌的冬孢子产生，这项措施可作为对受侵染植物的短期检疫处理方法以防植物死亡。冬孢子极可能由受侵染植物制成的包装材料携带。");
    }

    public static void actionStart(Context context){                                            //活动启动器
        Intent intent=new Intent(context,SuggestionActivity.class);
        context.startActivity(intent);
    }

}
