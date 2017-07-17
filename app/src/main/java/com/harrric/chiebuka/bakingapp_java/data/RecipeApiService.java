package com.harrric.chiebuka.bakingapp_java.data;

import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by chiebuka on 6/24/17.
 */

public interface RecipeApiService {

    @GET("baking.json")
    Observable<ArrayList<Recipe>> recipes();


    class creator {
        public static RecipeApiService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_" +
                            "baking/")
                    .build();
            return retrofit.create(RecipeApiService.class);
        }
    }
}
