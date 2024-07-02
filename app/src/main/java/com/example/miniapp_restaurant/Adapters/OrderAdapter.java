package com.example.miniapp_restaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Models.Food;
import com.example.miniapp_restaurant.Models.Order;
import com.example.miniapp_restaurant.Models.OrderStatus;
import com.example.miniapp_restaurant.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horisontal_order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderedAccName.setText(order.getAssociationName());

        // Display each food item on a new line
        StringBuilder foodDetails = new StringBuilder();
        for (Food food : order.getFoods()) {
            foodDetails.append(food.getName()).append(" x").append(food.getAmount()).append("\n");
        }
        holder.orderedItemsText.setText(foodDetails.toString().trim());

        holder.orderedTimeStamp.setText(order.getOrderDate() + " " + order.getOrderTime());
        holder.orderedItemAmountText.setText("Total: " + order.getFoods().size());
        holder.order_status.setTextColor(order.getOrderStatus() == OrderStatus.ACTIVE ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.black));
        holder.order_status.setText(order.getOrderStatus().toString());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateOrders(List<Order> orders) {
        this.orderList = orders;
        notifyDataSetChanged();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView orderedAccName;
        private MaterialTextView order_status;
        private MaterialTextView orderedItemsText;
        private MaterialTextView orderedTimeStamp;
        private MaterialTextView orderedItemAmountText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderedAccName = itemView.findViewById(R.id.orderedAccName);
            order_status = itemView.findViewById(R.id.order_status);
            orderedItemsText = itemView.findViewById(R.id.orderedItemsText);
            orderedTimeStamp = itemView.findViewById(R.id.orderedTimeStamp);
            orderedItemAmountText = itemView.findViewById(R.id.orderedItemAmountText);
        }
    }
}
