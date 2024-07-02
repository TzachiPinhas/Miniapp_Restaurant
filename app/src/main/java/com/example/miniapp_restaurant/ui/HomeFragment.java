package com.example.miniapp_restaurant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.OrderAdapter;
import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.Models.OrderStatus;
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
import java.util.List;

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
    private View root;
    private List<Order> activeOrders;
    private ApiRepository apiRepository;
    private OrderAdapter orderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        apiRepository = new ApiRepository();
        findViews();
        initView();

        boundaryId = UserSession.getInstance().getBoundaryId();
        userEmail = UserSession.getInstance().getUserEmail();

        activeOrders = new ArrayList<>();
        setupAdapter();
        getRestaurantFromServer();
        fetchOrdersFromServer();

        return root;
    }

    private void initView() {
        btn_active_donations.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_donation));

        btn_view_all_orders.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_orders));
    }

    private void getRestaurantFromServer() {
        apiRepository.getSpecificObject("2024b.gal.said", boundaryId, "2024b.gal.said", userEmail, new ApiCallback<ObjectBoundary>() {
            @Override
            public void onSuccess(ObjectBoundary result) {
                restaurant = new Restaurant(result);
                changeView();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error fetching restaurant: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchOrdersFromServer() {
        apiRepository.getObjectsByAlias(userEmail, "2024b.gal.said", userEmail, 100, 0, new ApiCallback<ArrayList<ObjectBoundary>>() {
            @Override
            public void onSuccess(ArrayList<ObjectBoundary> result) {
                activeOrders.clear();
                for (ObjectBoundary objectBoundary : result) {
                    if ("Order".equals(objectBoundary.getType())) {
                        Order order = new Order(objectBoundary);
                        if (order.getOrderStatus() == OrderStatus.ACTIVE) {
                            activeOrders.add(order);
                        }
                    }
                }
                setupAdapter();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error fetching orders: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAdapter() {
        orderAdapter = new OrderAdapter(root.getContext(), activeOrders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_orders.setLayoutManager(linearLayoutManager);
        recycler_view_orders.setAdapter(orderAdapter);
        updateNoActiveOrdersView();

    }

    private void updateNoActiveOrdersView() {
        if (activeOrders.isEmpty()) {
            txt_no_active_orders.setVisibility(View.VISIBLE);
            recycler_view_orders.setVisibility(View.GONE);
        } else {
            txt_no_active_orders.setVisibility(View.GONE);
            recycler_view_orders.setVisibility(View.VISIBLE);
        }
    }

    private void changeView() {
        txt_greeting.setText(String.format("Hello, %s!", restaurant.getRestaurantName()));
        // You can set other restaurant details here if needed
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
