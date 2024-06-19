package com.example.miniapp_restaurant.Server;

import android.util.Log;

import com.example.miniapp_restaurant.Models.FoodItem;
import com.example.miniapp_restaurant.Models.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private ApiService apiService;

    public ApiRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void createObject(ObjectBoundary object, final ApiCallback<ObjectBoundary> callback) {
        Call<ObjectBoundary> call = apiService.createObject(object);
        call.enqueue(new Callback<ObjectBoundary>() {
            @Override
            public void onResponse(Call<ObjectBoundary> call, Response<ObjectBoundary> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    Log.e("ApiRepository", "Error response code: " + response.code());
                    Log.e("ApiRepository", "Error response message: " + response.message());
                    Log.e("ApiRepository", "Error response body: " + response.errorBody());
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ObjectBoundary> call, Throwable t) {
                Log.e("ApiRepository", "Failure message: " + t.getMessage(), t);
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }
    public void getObject(String objectId, final ApiCallback<ObjectBoundary> callback) {
        Call<ObjectBoundary> call = apiService.getObject(objectId);
        call.enqueue(new Callback<ObjectBoundary>() {
            @Override
            public void onResponse(Call<ObjectBoundary> call, Response<ObjectBoundary> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ObjectBoundary> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }

    public void updateObject(String objectId, ObjectBoundary object, final ApiCallback<Void> callback) {
        Call<Void> call = apiService.updateObject(objectId, object);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }

    // Add other methods as needed
}
