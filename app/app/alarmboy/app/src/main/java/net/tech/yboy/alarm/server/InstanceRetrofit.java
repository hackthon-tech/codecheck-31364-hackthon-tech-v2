package net.tech.yboy.alarm.server;

import net.tech.yboy.alarm.server.ConstUrl;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by manabu on 2018/03/20.
 */

public class InstanceRetrofit {

    private static Retrofit retrofit;

    private static Retrofit ekiRetrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = provideRetrofit(provideOkHttpClient(), false);
        }
        return retrofit;
    }

    public static Retrofit getEkiRetrofit() {
        if (ekiRetrofit == null) {
            ekiRetrofit = provideRetrofit(provideOkHttpClient(), true);
        }
        return ekiRetrofit;
    }

    private static Retrofit provideRetrofit(OkHttpClient client, boolean eki) {

        String host = ConstUrl.API_HOST;
        if (eki) {
            host = ConstUrl.EKI_API_HOST;
        }

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(host)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new RequestHeaderInterceptor());
        return builder.build();
    }

    private static class RequestHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request.Builder builder = chain.request().newBuilder();
            // 共通ヘッダーの追加があればここに入れる
            return chain.proceed(builder.build());
        }
    }
}
