package com.calidad.sosfidoapp.sosfido.data.repositories.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jairbarzola on 24/09/17.
 */

public class ServiceFactory {
    public  <S> S createService(Class<S> serviceClass) {

        OkHttpClient httpClient = new OkHttpClient();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("http://sosfido.tk/")
                        .addConverterFactory(GsonConverterFactory.create());
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        Retrofit retrofit = builder.client(httpClient).client(client).build();
        return retrofit.create(serviceClass);
    }
}
