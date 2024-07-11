package com.example.miniapp_restaurant.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.OrderAdapter;
import com.example.miniapp_restaurant.Models.Food;
import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.Models.OrderStatus;
import com.example.miniapp_restaurant.Models.Restaurant;
import com.example.miniapp_restaurant.Models.Server.Object.Location;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.Models.WhoCarries;
import com.example.miniapp_restaurant.R;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.databinding.FragmentOrdersBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;
    private String boundaryId;
    private String userEmail;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private RecyclerView recyclerView;
    private FloatingActionButton btnResetFilter;
    private TextInputEditText editTextDate;
    private MaterialButtonToggleGroup toggleButtonGroup;
    private ApiRepository apiRepository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        findViews(root);

        boundaryId = UserSession.getInstance().getBoundaryId();
        userEmail = UserSession.getInstance().getUserEmail();
        apiRepository = new ApiRepository();

        orderList = new ArrayList<>(); // Initialize orderList
        fetchOrdersByAlias(userEmail);
        setupDateFilter();
        return root;
    }


    private void fetchOrdersByAlias(String alias) {
        apiRepository.getObjectsByAlias(alias, "2024b.gal.said", userEmail, 50, 0, new ApiCallback<ArrayList<ObjectBoundary>>() {
            @Override
            public void onSuccess(ArrayList<ObjectBoundary> result) {
                orderList = new ArrayList<>();
                for (ObjectBoundary ob : result) {
                    if ("Order".equals(ob.getType())) {
                        orderList.add(new Order(ob));
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
        orderAdapter = new OrderAdapter(getContext(), orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(orderAdapter);
        updateOrderCount();
        setupToggleButtons(); // Call setupToggleButtons after setupAdapter
    }

    private void setupDateFilter() {
        final Calendar calendar = Calendar.getInstance();

        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                editTextDate.setText(sdf.format(calendar.getTime()));
                filterOrdersByDate(sdf.format(calendar.getTime())); // Add this line to filter orders by date
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set min date to today
            datePickerDialog.show();
        });

        btnResetFilter.setOnClickListener(v -> {
            editTextDate.setText("");
            orderAdapter.updateOrders(orderList);
            updateOrderCount();
            toggleButtonGroup.check(R.id.button1); // Reset to default selection
        });
    }

    private void filterOrdersByDate(String date) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getOrderDate().equals(date)) {
                filteredOrders.add(order);
            }
        }
        orderAdapter.updateOrders(filteredOrders);
        updateOrderCount();
    }

    private void setupToggleButtons() {
        toggleButtonGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                List<Order> filteredOrders = new ArrayList<>();
                if (checkedId == R.id.button1) {
                    filteredOrders.addAll(orderList); // All orders
                } else if (checkedId == R.id.button2) {
                    for (Order order : orderList) {
                        if (order.getOrderStatus() == OrderStatus.ACTIVE || order.getOrderStatus() == OrderStatus.PENDING) {
                            filteredOrders.add(order);
                        }
                    }
                } else if (checkedId == R.id.button3) {
                    for (Order order : orderList) {
                        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
                            filteredOrders.add(order);
                        }
                    }
                }
                orderAdapter.updateOrders(filteredOrders);
                updateOrderCount();
            }
        });
        toggleButtonGroup.check(R.id.button1); // Ensure one button is always checked
    }

    private void updateOrderCount() {
        int count = orderAdapter.getItemCount();
        binding.textOrderCount.setText("Total Orders: " + count);
    }

    private void findViews(View root) {
        recyclerView = binding.recyclerViewOrders;
        btnResetFilter = binding.fabResetFilter;
        editTextDate = binding.editTextDateInput;
        toggleButtonGroup = binding.toggleButton;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
