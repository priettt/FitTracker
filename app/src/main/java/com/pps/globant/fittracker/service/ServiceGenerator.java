package com.pps.globant.fittracker.service;

import com.google.gson.GsonBuilder;
import com.pps.globant.fittracker.BuildConfig;
import com.pps.globant.fittracker.utils.HashUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String APIKEY = "apikey";
    private static final String HASH = "hash";
    private static final String TS = "ts";
    private static final String UTC = "UTC";
    private static final long LONG_MIL = 1000L;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long timeStamp=Calendar.getInstance(TimeZone.getTimeZone(UTC)).getTimeInMillis()/ LONG_MIL;
            String hashSignature=HashUtil.md5(String.valueOf(timeStamp) + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY);
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter(TS, String.valueOf(timeStamp))
                    .addQueryParameter(APIKEY, BuildConfig.API_PUBLIC_KEY)
                    .addQueryParameter(HASH, hashSignature)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);

        }
    });

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation().create()));

    public static <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
