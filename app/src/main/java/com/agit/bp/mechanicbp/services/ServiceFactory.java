package com.agit.bp.mechanicbp.services;

import android.util.Log;

import com.agit.bp.mechanicbp.database.ConstantaWO;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by knax on 6/14/16.
 */
public class ServiceFactory {
    //private static String BASE_URL = "http://103.10.253.158:30111/binapertiwi/rest/"; //server java public
    //private static String BASE_URL = "http://192.168.43.193:8180/binapertiwi/rest/"; //server java public
    //private static String BASE_URL = "http://10.21.224.171:8199/binapertiwi/rest/"; //server java kak nyur
    //private static String BASE_URL = "http://172.16.150.58:8888/binapertiwi/rest/"; //server java local
    //private static String BASE_URL = "http://10.21.62.220:8199/binapertiwi/rest/"; //server java local
    //private static String BASE_URL = "http://192.168.1.13:8888/binapertiwi/rest/"; //server ogel
    //private static String BASE_URL = "http://192.168.43.98/retrofit/"; //server hp
    //private static String BASE_URL = "http://192.168.43.193:8199/binapertiwi/rest/"; //server mas hendra
    private static String BASE_URL = "http://192.168.43.193:8199/binapertiwi/rest/"; //server hp
    public static <T> T createRetrofitService(final Class<T> clazz) {

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        T service = restAdapter.create(clazz);
        if (ConstantaWO.IS_DEBUG) {
            Log.e("isi service : ", "" + service);
        }
        return service;
    }
}
