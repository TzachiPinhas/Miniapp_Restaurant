package com.example.miniapp_restaurant.Server;

import android.util.Log;
import android.widget.Toast;

import com.example.miniapp_restaurant.Models.Review;
import com.example.miniapp_restaurant.Models.Server.Command.CommandBoundary;
import com.example.miniapp_restaurant.Models.Server.Command.InvokedBy;
import com.example.miniapp_restaurant.Models.Server.Command.TargetObject;
import com.example.miniapp_restaurant.Models.Server.Object.NewUserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.RoleEnum;
import com.example.miniapp_restaurant.Models.Server.Object.UserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void getObjectsByType(String type, String superapp, String email, int size, int page, final ApiCallback<ArrayList<ObjectBoundary>> callback) {
        Call<ArrayList<ObjectBoundary>> call = apiService.getObjectsByType(type, superapp, email, size, page);
        call.enqueue(new Callback<ArrayList<ObjectBoundary>>() {
            @Override
            public void onResponse(Call<ArrayList<ObjectBoundary>> call, Response<ArrayList<ObjectBoundary>> response) {
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
            public void onFailure(Call<ArrayList<ObjectBoundary>> call, Throwable t) {
                Log.e("ApiRepository", "Failure message: " + t.getMessage(), t);
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }

    public void getObjectsByAlias(String alias, String superapp, String email, int size, int page, final ApiCallback<ArrayList<ObjectBoundary>> callback) {
        Call<ArrayList<ObjectBoundary>> call = apiService.getObjectsByAlias(alias, superapp, email, size, page);
        call.enqueue(new Callback<ArrayList<ObjectBoundary>>() {
            @Override
            public void onResponse(Call<ArrayList<ObjectBoundary>> call, Response<ArrayList<ObjectBoundary>> response) {
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
            public void onFailure(Call<ArrayList<ObjectBoundary>> call, Throwable t) {
                Log.e("ApiRepository", "Failure message: " + t.getMessage(), t);
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }


    public void createUser(NewUserBoundary newUserBoundary, final ApiCallback<UserBoundary> callback) {
        Call<UserBoundary> call = apiService.createUser(newUserBoundary);
        call.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
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
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                Log.e("ApiRepository", "Failure message: " + t.getMessage(), t);
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }


    public void updateUser(String superapp, String userEmail, UserBoundary userBoundary, final ApiCallback<Void> callback) {
        Call<Void> call = apiService.updateUser(superapp, userEmail, userBoundary);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    Log.e("ApiRepository", "Error response code: " + response.code());
                    Log.e("ApiRepository", "Error response message: " + response.message());
                    Log.e("ApiRepository", "Error response body: " + response.errorBody());
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ApiRepository", "Failure message: " + t.getMessage(), t);
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }


    public void getUser(String superapp, String email, final ApiCallback<UserBoundary> callback) {
        Call<UserBoundary> call = apiService.getUser(superapp, email);
        call.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
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
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                Log.e("ApiRepository", "Failure message: " + t.getMessage(), t);
                callback.onError("Failure: " + t.getMessage());
            }
        });
    }

    public void getSpecificObject(String superapp, String id, String userSuperApp, String userEmail, final ApiCallback<ObjectBoundary> callback) {
        Call<ObjectBoundary> call = apiService.getSpecificObject(superapp, id, userSuperApp, userEmail);
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

    public void updateObject(String superApp, String id, String userSuperApp, String userEmail, ObjectBoundary objectBoundary, ApiCallback<Void> callback) {
        Call<Void> call = apiService.updateObject(superApp, id, userSuperApp, userEmail, objectBoundary);
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
                callback.onError(t.getMessage());
            }
        });
    }


    public void getReviewsByCommand(ApiCallback<List<Review>> callback) {
        CommandBoundary commandBoundary = new CommandBoundary("SBTA");
        Map<String, Object> commandMap = Map.of("type", "Review", "alias", UserSession.getInstance().getUserEmail());
        commandBoundary.setCommandAttributes(commandMap);
        Log.d("ApiRepository", "getReviewsByCommand: " + commandBoundary.toString());
        getUser(UserSession.getInstance().getSUPERAPP(), UserSession.getInstance().getUserEmail(), new ApiCallback<UserBoundary>() {
            @Override
            public void onSuccess(UserBoundary result) {
                // Update user role to MINIAPP_USER before sending the command
                result.setRole(RoleEnum.MINIAPP_USER);
                updateUser(result.getUserId().getSuperapp(), result.getUserId().getEmail(), result, new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Call<List<ObjectBoundary>> call = apiService.command(UserSession.getInstance().getSUPERAPP(), commandBoundary);
                        call.enqueue(new Callback<List<ObjectBoundary>>() {
                            @Override
                            public void onResponse(Call<List<ObjectBoundary>> call, Response<List<ObjectBoundary>> response) {
                                // Reset user role back to SUPERAPP_USER after the command execution
                                result.setRole(RoleEnum.SUPERAPP_USER);
                                updateUser(result.getUserId().getSuperapp(), result.getUserId().getEmail(), result, new ApiCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if (response.isSuccessful()) {
                                            List<Review> reviews = new ArrayList<>();
                                            for (ObjectBoundary ob : response.body()) {
                                                if ("Review".equals(ob.getType())) {
                                                    reviews.add(new Review(ob));
                                                }
                                            }
                                            callback.onSuccess(reviews);
                                        } else {
                                            callback.onError("Error: " + response.code());
                                            Log.d("ApiRepository", "onError: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onError(String error) {
                                        callback.onError("Failed to reset user role: " + error);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<List<ObjectBoundary>> call, Throwable t) {
                                callback.onError("Failure: " + t.getMessage());
                                Log.d("ApiRepository", "onFailure: " + t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError("Failed to update user role: " + error);
                    }
                });
            }

            @Override
            public void onError(String error) {
                callback.onError("Failed to get user: " + error);
            }
        });
    }


}


