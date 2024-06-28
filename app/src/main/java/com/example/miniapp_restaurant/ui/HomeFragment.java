package com.example.miniapp_restaurant.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.OrderAdapter;
import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.Models.Server.Object.CreatedBy;
import com.example.miniapp_restaurant.Models.Food;
import com.example.miniapp_restaurant.Models.Server.Object.Location;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserId;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ImageFilterView imageViewRestaurant;
    private MaterialTextView txt_greeting;
    private MaterialTextView txt_rating;
    private MaterialTextView txt_active_orders;
    private RecyclerView recycler_view_orders;
    private MaterialButton btn_view_all_orders;
    private MaterialButton btn_active_donations;
    private boolean activeDonations;
    private View root;
    private ArrayList<Order> activeOrders;
    private ApiRepository apiRepository;
    private ArrayList<ObjectBoundary> objectList;
    private  UserId userId;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        Food food = new Food();
        food.setName("Pizza").setAmount(2).setType("Fast Food").setExpiryDate("2021-12-31");
        apiRepository = new ApiRepository();
        findViews();
        objectList = new ArrayList<>();
        Location location = new Location().setLat(40.7128).setLng(-74.0060);
        userId = new UserId().setSuperapp("2024b.gal.said").setEmail("ziv@gmail.com");
        CreatedBy createdBy = new CreatedBy().setUserId(userId);
        ObjectBoundary objectBoundary = new ObjectBoundary();
        objectBoundary.setType("exampleType");
        objectBoundary.setAlias("exampleAlias");
        objectBoundary.setCreatedBy(createdBy);
        objectBoundary.setLocation(location);
        objectBoundary.setActive(true);
        HashMap<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("food", food);
        objectBoundary.setObjectDetails(objectDetails);

        btn_view_all_orders.setOnClickListener(v -> {
            apiRepository.createObject(objectBoundary, new ApiCallback<ObjectBoundary>() {
                @Override
                public void onSuccess(ObjectBoundary result) {
                    Log.d("HomeFragment", "Object created: " + result);                }

                @Override
                public void onError(String error) {
                    Log.e("HomeFragment", "Failed to create object: " + error);
                }
            });
       });
        btn_active_donations.setOnClickListener(v -> {
            fetchObjectsByAlias("ziv", userId.getSuperapp(),userId.getEmail(),10,0 );
        });
        activeDonations = true;
        activeOrders = new ArrayList<>();
        updateAdapter();
        return root;
    }

    private void fetchObjectsByType(String type, String superapp, String email, int size, int page) {
        apiRepository.getObjectsByType(type, superapp, email, size, page, new ApiCallback<ArrayList<ObjectBoundary>>() {
            @Override
            public void onSuccess(ArrayList<ObjectBoundary> result) {
                objectList.clear();
                objectList.addAll(result);
                Log.d("HomeFragment", "Fetched objects by type: " + objectList.get(0).getType());
            }

            @Override
            public void onError(String error) {
                Log.e("HomeFragment", "Failed to fetch objects by type: " + error);
            }
        });
    }

    private void fetchObjectsByAlias(String alias, String superapp, String email, int size, int page) {
        apiRepository.getObjectsByAlias(alias, superapp, email, size, page, new ApiCallback<ArrayList<ObjectBoundary>>() {
            @Override
            public void onSuccess(ArrayList<ObjectBoundary> result) {
                objectList.clear();
                objectList.addAll(result);
                Log.d("HomeFragment", "Fetched objects by type: " + objectList.get(0));
            }

            @Override
            public void onError(String error) {
                Log.e("HomeFragment", "Failed to fetch objects by alias: " + error);
            }
        });
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    private void findViews() {
        imageViewRestaurant = binding.imageViewRestaurant;
        txt_greeting = binding.txtGreeting;
        txt_rating = binding.txtRating;
        txt_active_orders = binding.txtActiveOrders;
        recycler_view_orders = binding.recyclerViewOrders;
        btn_view_all_orders = binding.btnViewAllOrders;
        btn_active_donations = binding.btnActiveDonations;
    }

    private void updateAdapter() {
        OrderAdapter orderAdapter = new OrderAdapter(root.getContext(), activeOrders, activeDonations);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_orders.setLayoutManager(linearLayoutManager);
        recycler_view_orders.setAdapter(orderAdapter);
        int count = orderAdapter.getItemCount();
        if (count == 0) {
            updateNoActiveOrdersView();
        }
    }

    private void updateNoActiveOrdersView() {
        // if there are no active orders, show a message
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}