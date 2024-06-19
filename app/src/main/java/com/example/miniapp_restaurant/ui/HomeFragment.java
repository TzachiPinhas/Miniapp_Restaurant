package com.example.miniapp_restaurant.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.OrderAdapter;
import com.example.miniapp_restaurant.Models.CreatedBy;
import com.example.miniapp_restaurant.Models.FoodItem;
import com.example.miniapp_restaurant.Models.Location;
import com.example.miniapp_restaurant.Models.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.Models.UserId;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Date;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        apiRepository = new ApiRepository();
        findViews();
        Location location = new Location().setLat(40.7128).setLng(-74.0060);


        UserId userId = new UserId().setSuperapp("2024b.gal.said").setEmail("ziv@gmail.com");

        CreatedBy createdBy = new CreatedBy().setUserId(userId);

        ObjectBoundary objectBoundary = new ObjectBoundary();
        objectBoundary.setType("exampleType");
        objectBoundary.setAlias("exampleAlias");
        objectBoundary.setCreatedBy(createdBy);
        objectBoundary.setLocation(location);
        objectBoundary.setActive(true);
        objectBoundary.setObjectDetails(new HashMap<>());


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
        activeDonations = true;
        return root;
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