package com.common.lib.net;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.common.lib.R;
import org.json.JSONException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/***
 * @author kdp
 * @date 2018/11/30 9:38
 * @description 网络异常处理
 */
public class NetExceptionHandler {

    //判断是否有网
     private static boolean isAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return networkInfo.isAvailable();
        return false;
    }
    /**
     * 处理异常错误
     * @param context
     * @param t
     */
    public static void dealException(Context context, Throwable t) {

        if (t instanceof ConnectException || !isAvailable(context)) {
            //连接错误,网络异常
            onException(HttpConfig.CONNECT_ERROR, context);
        }else if (t instanceof UnknownHostException){
            //无法连接到主机
            onException(HttpConfig.UNKNOWN_HOST,context);
        }
        else if (t instanceof InterruptedException) {
            //连接超时
            onException(HttpConfig.CONNECT_TIMEOUT, context);
        } else if (t instanceof JSONException || t instanceof ParseException) {
            //解析错误
            onException(HttpConfig.PARSE_ERROR, context);
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            onException(HttpConfig.REQUEST_TIMEOUT, context);
        } else if (t instanceof UnknownError) {
            //未知错误
            onException(HttpConfig.UNKNOWN_ERROR, context);
        } else if (t instanceof IllegalArgumentException){
            //非法参数
            onException(HttpConfig.ILLEGAL_PARAMS, context);
        }else  {
            //其它异常
            showToast(context,t.getMessage());
        }
    }


    /**
     * 异常信息
     * @param errorCode
     * @param context
     */
    static void onException(int errorCode, Context context) {
        switch (errorCode) {
            case HttpConfig.CONNECT_ERROR:
                showToast(context, R.string.connect_error);
                break;
            case HttpConfig.UNKNOWN_HOST:
                showToast(context,R.string.unknown_host);
                break;
            case HttpConfig.CONNECT_TIMEOUT:
                showToast(context, R.string.connect_timeout);
                break;
            case HttpConfig.PARSE_ERROR:
                showToast(context, R.string.parse_error);
                break;
            case HttpConfig.REQUEST_TIMEOUT:
                showToast(context, R.string.request_timeout);
                break;
            case HttpConfig.UNKNOWN_ERROR:
                showToast(context, R.string.unknown_error);
                break;
            case HttpConfig.ILLEGAL_PARAMS:
                showToast(context,R.string.illegal_params);
                break;
        }
    }


    private static void showToast(Context context,int resId){
        Toast.makeText(context,resId,Toast.LENGTH_SHORT).show();
    }

    private static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

}
