package com.example.miniapp_restaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private ArrayList<Order> allOrders;
    private ArrayList<Order> filteredOrders;
    private boolean activeDonations;

    public OrderAdapter(Context context, ArrayList<Order> orders, boolean activeDonations) {
        this.context = context;
        this.allOrders = orders;
        this.activeDonations = activeDonations;
        filterOrders();

    }

    private void filterOrders() {
        filteredOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (activeDonations && ("PENDING".equalsIgnoreCase(order.getOrderStatus().toString())||"ACCEPTED".equalsIgnoreCase(order.getOrderStatus().toString()))) {
                filteredOrders.add(order);
            }
            else if (!activeDonations && !"DELIVERED".equalsIgnoreCase(order.getOrderStatus().toString())) {
                filteredOrders.add(order);
            }
        }
    }




    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horisontal_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        Order order = getItem(position);
        holder.associationName.setText(order.getAssociationName());
        holder.orderItems.setText(order.getFoods().toString());
        holder.time.setText(order.getOrderDate()+" "+order.getOrderTime());
        holder.status.setText(order.getOrderStatus().toString());
        holder.orderItemsCount.setText(String.valueOf(order.getFoods().size()));
    }

    private Order getItem(int position) {
        return filteredOrders.get(position);
    }

    @Override
    public int getItemCount() {
        if (filteredOrders == null)
            return 0;
        return filteredOrders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView associationName;
        private MaterialTextView orderItems;
        private MaterialTextView time;
        private MaterialTextView status;
        private MaterialTextView orderItemsCount;



        public OrderViewHolder(@NonNull View view) {
            super(view);
            associationName = view.findViewById(R.id.orderedAccName);
            status = view.findViewById(R.id.order_status);
            orderItems = view.findViewById(R.id.orderedItemsText);
            time = view.findViewById(R.id.orderedTimeStamp);
            orderItemsCount = view.findViewById(R.id.orderedItemAmountText);
        }


    }
}
