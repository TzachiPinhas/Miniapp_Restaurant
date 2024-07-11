package com.example.miniapp_restaurant.Server;

import com.example.miniapp_restaurant.Models.Server.Command.CommandBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.NewUserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserBoundary;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/superapp/objects")
    Call<ObjectBoundary> createObject(@Body ObjectBoundary object);

    @GET("/superapp/objects/search/byType/{type}")
    Call<ArrayList<ObjectBoundary>> getObjectsByType(
            @Path("type") String type,
            @Query("userSuperapp") String superapp,
            @Query("userEmail") String email,
            @Query("size") int size,
            @Query("page") int page
    );

    @GET("superapp/objects/search/byAlias/{alias}")
    Call<ArrayList<ObjectBoundary>> getObjectsByAlias(
            @Path("alias") String alias,
            @Query("userSuperapp") String superapp,
            @Query("userEmail") String email,
            @Query("size") int size,
            @Query("page") int page
    );

    @POST("/superapp/users")
    Call<UserBoundary> createUser(@Body NewUserBoundary newUserBoundary);


    @PUT("/superapp/users/{superapp}/{userEmail}")
    Call<Void> updateUser(@Path("superapp") String superapp, @Path("userEmail") String userEmail, @Body UserBoundary userBoundary);


    @GET("/superapp/users/login/{superapp}/{email}")
    Call<UserBoundary> getUser(@Path("superapp") String superapp, @Path("email") String email);

    @POST("/superapp/miniapp/{miniAppName}")
    Call<ObjectBoundary[]> executeCommand(@Path("miniAppName") String miniAppName, @Body CommandBoundary commandBoundary);

    @GET("/superapp/objects/{superapp}/{id}")
    Call<ObjectBoundary> getSpecificObject(@Path("superapp") String superapp,
                                           @Path("id") String id,
                                           @Query("userSuperapp") String userSuperApp,
                                           @Query("userEmail") String userEmail);


    @PUT("/superapp/objects/{superapp}/{id}")
    Call<Void> updateObject(
            @Path("superapp") String superapp,
            @Path("id") String id,
            @Query("userSuperapp") String userSuperApp,
            @Query("userEmail") String userEmail,
            @Body ObjectBoundary objectBoundary
    );


    @POST("superapp/miniapp/{miniAppName}")
    Call<List<ObjectBoundary>> command(
            @Path("miniAppName") String miniAppName,
            @Body CommandBoundary boundary
    );


}

