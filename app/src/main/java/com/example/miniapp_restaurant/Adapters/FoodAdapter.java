package com.example.miniapp_restaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miniapp_restaurant.Models.Food;
import com.example.miniapp_restaurant.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> foodList;
    private OnFoodItemChangeListener onFoodItemChangeListener;

    public FoodAdapter(Context context, List<Food> foodList, OnFoodItemChangeListener onFoodItemChangeListener) {
        this.context = context;
        this.foodList = foodList;
        this.onFoodItemChangeListener = onFoodItemChangeListener;
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = getItem(position);
        holder.food_name.setText(food.getName());
        holder.food_amount.setText(String.valueOf(food.getAmount()));
        holder.food_type.setText(food.getType());
        holder.food_expiration_date.setText(food.getExpiryDate());

        holder.itemView.setOnLongClickListener(v -> {
            if (onFoodItemChangeListener != null) {
                onFoodItemChangeListener.onEdit(position, food);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public Food getItem(int position) {
        return foodList.get(position);
    }

    public void deleteItem(int position) {
        foodList.remove(position);
        notifyItemRemoved(position);
        if (onFoodItemChangeListener != null) {
            onFoodItemChangeListener.onDelete();
        }
    }

    public void editItem(int position, Food updatedFood) {
        foodList.set(position, updatedFood);
        notifyItemChanged(position);
        if (onFoodItemChangeListener != null) {
            onFoodItemChangeListener.onEdit(position, updatedFood);
        }
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView food_name;
        private MaterialTextView food_amount;
        private MaterialTextView food_type;
        private MaterialTextView food_expiration_date;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.food_name);
            food_amount = itemView.findViewById(R.id.food_amount);
            food_type = itemView.findViewById(R.id.food_type);
            food_expiration_date = itemView.findViewById(R.id.food_expiration_date);
        }
    }

    public interface OnFoodItemChangeListener {
        void onEdit(int position, Food food);
        void onDelete();
    }
}
