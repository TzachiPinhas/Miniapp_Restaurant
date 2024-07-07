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
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.Models.WhoCarries;
import com.example.miniapp_restaurant.R;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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
        holder.orderedAccName.setText("Association Name: " + order.getAssociationName());

        // Display each food item on a new line
        StringBuilder foodDetails = new StringBuilder();

        for (Food food : order.getFoods()) {
            foodDetails.append(food.getName()).append(" x").append(food.getAmount()).append("\n");
        }
        holder.orderedItemsText.setText(foodDetails.toString().trim());

        holder.orderedTimeStamp.setText(order.getOrderDate() + " " + order.getOrderTime());
        holder.orderedItemAmountText.setText("Total: " + order.getFoods().size());
        holder.order_status.setTextColor(order.getOrderStatus() == OrderStatus.ACTIVE || order.getOrderStatus() == OrderStatus.PENDING ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.black));
        holder.order_status.setText(order.getOrderStatus().toString());
        holder.orderedItemWhoCarries.setText(order.getWhoCarries().toString().equals("DELIVERY") ? "Delivery method: Delivery" : "Delivery method: Take Away");

        if (order.getOrderStatus() == OrderStatus.ACTIVE && order.getWhoCarries() == WhoCarries.TAKE_AWAY) {
            holder.btnFinish.setVisibility(View.VISIBLE);
            holder.btnFinish.setOnClickListener(v -> {
                order.setOrderStatus(OrderStatus.DELIVERED);
                notifyItemChanged(position);
                getAndUpdateSpecificOrderFromServer(order.getOrderID().getId());
            });
        } else {
            holder.btnFinish.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateOrders(List<Order> orders) {
        this.orderList = orders;
        notifyDataSetChanged();
    }

    private void getAndUpdateSpecificOrderFromServer(String orderId) {
        ApiRepository apiRepository = new ApiRepository();
        apiRepository.getSpecificObject("2024b.gal.said", orderId, "2024b.gal.said", UserSession.getInstance().getUserEmail(), new ApiCallback<ObjectBoundary>() {
            @Override
            public void onSuccess(ObjectBoundary result) {
                Order order = new Order(result);
                order.setOrderStatus(OrderStatus.DELIVERED);
                updateOrderStatusOnServer(order, result.getCreatedBy().getUserId().getEmail());
            }

            @Override
            public void onError(String error) {
                // Handle error if needed
            }
        });
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView orderedAccName;
        private MaterialTextView order_status;
        private MaterialTextView orderedItemsText;
        private MaterialTextView orderedTimeStamp;
        private MaterialTextView orderedItemAmountText;
        private MaterialTextView orderedItemWhoCarries;
        private ExtendedFloatingActionButton btnFinish;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderedAccName = itemView.findViewById(R.id.orderedAccName);
            order_status = itemView.findViewById(R.id.order_status);
            orderedItemsText = itemView.findViewById(R.id.orderedItemsText);
            orderedTimeStamp = itemView.findViewById(R.id.orderedTimeStamp);
            orderedItemAmountText = itemView.findViewById(R.id.orderedItemAmountText);
            orderedItemWhoCarries = itemView.findViewById(R.id.orderedWhoCarriesText);
            btnFinish = itemView.findViewById(R.id.BTN_PCK_Finish);

        }
    }

    private void updateOrderStatusOnServer(Order order, String email) {
        ApiRepository apiRepository = new ApiRepository();
        apiRepository.updateObject("2024b.gal.said", order.getOrderID().getId(), "2024b.gal.said", UserSession.getInstance().getUserEmail(), order.convert(order, email), new ApiCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Handle success if needed
            }

            @Override
            public void onError(String error) {
                // Handle error if needed
            }
        });

    }
}
