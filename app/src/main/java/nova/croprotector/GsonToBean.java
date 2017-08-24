package nova.croprotector;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import ikidou.reflect.TypeBuilder;

/**
 * Created by ZTH on 2017/8/24.
 */


//Gson泛型封装
public class GsonToBean {

    public static <T> CommonResponse<T> fromJsonObject(Reader reader, Class<T> clazz){
        Type type= TypeBuilder.newInstance(CommonResponse.class)
                .addTypeParam(clazz)
                .build();
        Gson gson=new Gson();
        return gson.fromJson(reader,type);
    }

    public static <T> CommonResponse<List<T>> fromJsonArray(Reader reader,Class<T> clazz){
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
