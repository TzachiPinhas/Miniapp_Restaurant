package com.example.miniapp_restaurant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.OrderAdapter;
import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.Models.Restaurant;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.R;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String boundaryId;
    private String userEmail;
    private Restaurant restaurant;
    private ImageFilterView imageViewRestaurant;
    private MaterialTextView txt_greeting;
    private MaterialTextView txt_rating;
    private MaterialTextView txt_active_orders;
    private MaterialTextView txt_no_active_orders;
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
        initView();
        getRestaurantFromServer();

        activeDonations = true;
        activeOrders = new ArrayList<>();
        return root;
    }

    private void initView() {
        boundaryId = UserSession.getInstance().getBoundaryId();
        userEmail = UserSession.getInstance().getUserEmail();

        btn_active_donations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_donation);
            }
        });

        btn_view_all_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_orders);
            }
        });
    }

    private void getRestaurantFromServer() {
        apiRepository.getSpecificObject("2024b.gal.said", boundaryId, "2024b.gal.said", userEmail, new ApiCallback<ObjectBoundary>() {
            @Override
            public void onSuccess(ObjectBoundary result) {
                restaurant = new Restaurant(result);
                changeView();
                setupAdapter();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error fetching restaurant: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeView() {
        txt_greeting.setText(String.format("Hello, %s!", restaurant.getRestaurantName()));
    }

    private void setupAdapter() {
        OrderAdapter orderAdapter = new OrderAdapter(root.getContext(), activeOrders, activeDonations);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_orders.setLayoutManager(linearLayoutManager);
        recycler_view_orders.setAdapter(orderAdapter);
        updateNoActiveOrdersView(orderAdapter.getItemCount());
    }

    private void updateNoActiveOrdersView(int itemCount) {
        if (itemCount == 0) {
            txt_no_active_orders.setVisibility(View.VISIBLE);
            recycler_view_orders.setVisibility(View.GONE);
        } else {
            txt_no_active_orders.setVisibility(View.GONE);
            recycler_view_orders.setVisibility(View.VISIBLE);
        }
    }

    private void findViews() {
        imageViewRestaurant = binding.imageViewRestaurant;
        txt_greeting = binding.txtGreeting;
        txt_rating = binding.txtRating;
        txt_active_orders = binding.txtActiveOrders;
        txt_no_active_orders = binding.txtNoActiveOrders;
        recycler_view_orders = binding.recyclerViewOrders;
        btn_view_all_orders = binding.btnViewAllOrders;
        btn_active_donations = binding.btnActiveDonations;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
