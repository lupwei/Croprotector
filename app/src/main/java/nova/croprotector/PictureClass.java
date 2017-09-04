package nova.croprotector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by ZTH on 2017/9/4.
 */


//Base64格式的字符串和bitmap相互转换
public class PictureClass {

    public static String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] buff=baos.toByteArray();
        return Base64.encodeToString(buff,Base64.DEFAULT);
    }

    public static Bitmap StringToBitmap(String base64Str){
        Bitmap bitmap=null;
        try{
            byte[] bitmapArray=Base64.decode(base64Str,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
            return bitmap;
        }catch(Exception e){
            return null;
        }
    }
}
