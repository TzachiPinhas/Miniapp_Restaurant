package com.example.miniapp_restaurant.Server;
import com.example.miniapp_restaurant.Models.FoodItem;
import com.example.miniapp_restaurant.Models.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/superapp/objects")
    Call<ObjectBoundary> createObject(@Body ObjectBoundary object);

    @GET("objects/{objectId}")
    Call<ObjectBoundary> getObject(@Path("objectId") String objectId);

    @PUT("objects/{objectId}")
    Call<Void> updateObject(@Path("objectId") String objectId, @Body ObjectBoundary object);

}

