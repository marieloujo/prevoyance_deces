package bj.assurance.prevoyancedeces.Utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import bj.assurance.prevoyancedeces.retrofit.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class Utils {

    public static ApiError converErrors(ResponseBody response){
        Converter<ResponseBody, ApiError> converter = RetrofitClientInstance.getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError apiError = null;

        try {
            apiError = converter.convert(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiError;
    }
}
