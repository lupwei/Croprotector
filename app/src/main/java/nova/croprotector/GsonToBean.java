package nova.croprotector;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import ikidou.reflect.TypeBuilder;

/**
 * Created by ZTH on 2017/8/24.
 */


//Gson泛型封装
public class GsonToBean {

    public static <T> CommonResponse<T> fromJsonObject(String jsonStr, Class<T> clazz)throws IOException {
        Reader reader=new StringReader(jsonStr);
        Type type= TypeBuilder.newInstance(CommonResponse.class)
                .addTypeParam(clazz)
                .build();
        Gson gson=new Gson();
        return gson.fromJson(reader,type);
    }

    public static <T> CommonResponse<List<T>> fromJsonArray(String jsonStr,Class<T> clazz)throws IOException{
        Reader reader=new StringReader(jsonStr);
        Type type=TypeBuilder
                .newInstance(CommonResponse.class)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        Gson gson=new Gson();
        return gson.fromJson(reader, type);
    }


}
