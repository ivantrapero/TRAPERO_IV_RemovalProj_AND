package com.trapero.removalproject.network;

import com.trapero.removalproject.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("items/post")
    Call<Item> createItem(@Body Item item);

    @GET("items")
    Call<List<Item>> getAllItems();

    @GET("items/{id}")
    Call<Item> getItem(@Path("id") String id);

    @PUT("items/{id}")
    Call<Item> updateItem(@Path("id") String id, @Body Item item);

    @DELETE("items/{id}")
    Call<Void> deleteItem(@Path("id") String id);
}
