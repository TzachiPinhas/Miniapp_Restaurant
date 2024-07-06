package com.example.miniapp_restaurant.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.FoodAdapter;
import com.example.miniapp_restaurant.Models.Food;
import com.example.miniapp_restaurant.Models.Restaurant;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.R;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.databinding.FragmentDonationBinding;
import com.example.miniapp_restaurant.extra.SwipeToDeleteCallback;
import com.example.miniapp_restaurant.extra.SwipeToEditCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DonationFragment extends Fragment {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList;
    private MaterialButton btnAddItem;
    private ApiRepository apiRepository;
    private Restaurant restaurant;
    private String boundaryId;
    private String userEmail;

    private FragmentDonationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDonationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        findViews(root);

        boundaryId = UserSession.getInstance().getBoundaryId();
        userEmail = UserSession.getInstance().getUserEmail();
        apiRepository = new ApiRepository();

        getRestaurantFromServer();

        return root;
    }

    private void getRestaurantFromServer() {
        apiRepository.getSpecificObject("2024b.gal.said", boundaryId, "2024b.gal.said", userEmail, new ApiCallback<ObjectBoundary>() {
            @Override
            public void onSuccess(ObjectBoundary result) {
                restaurant = new Restaurant(result);
                foodList = restaurant.getStorage();
                if (foodList == null) {
                    foodList = new ArrayList<>();
                }
                setupAdapter();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error fetching restaurant: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAdapter() {
        foodAdapter = new FoodAdapter(getContext(), foodList, new FoodAdapter.OnFoodItemChangeListener() {
            @Override
            public void onEdit(int position, Food food) {
                showEditItemDialog(position, food);
            }

            @Override
            public void onDelete() {
                updateRestaurantInDB();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(foodAdapter);

        ItemTouchHelper itemTouchHelperDelete = new ItemTouchHelper(new SwipeToDeleteCallback(foodAdapter, getContext()));
        itemTouchHelperDelete.attachToRecyclerView(recyclerView);

        ItemTouchHelper itemTouchHelperEdit = new ItemTouchHelper(new SwipeToEditCallback(foodAdapter, getContext()));
        itemTouchHelperEdit.attachToRecyclerView(recyclerView);

        btnAddItem.setOnClickListener(view -> showAddItemDialog());
    }

    private void showAddItemDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_item, null);
        TextInputEditText inputName = dialogView.findViewById(R.id.input_name);
        TextInputEditText inputAmount = dialogView.findViewById(R.id.input_amount);
        TextInputEditText inputExpiryDate = dialogView.findViewById(R.id.input_expiry_date);
        ChipGroup chipGroup = dialogView.findViewById(R.id.chip_group);
        MaterialButton btnSave = dialogView.findViewById(R.id.btn_save);

        Calendar calendar = Calendar.getInstance();

        inputExpiryDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                inputExpiryDate.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set min date to today
            datePickerDialog.show();
        });

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

        btnSave.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String amountStr = inputAmount.getText().toString().trim();
            String expiryDate = inputExpiryDate.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                inputName.setError("Name is required");
                return;
            }

            if (TextUtils.isEmpty(amountStr)) {
                inputAmount.setError("Amount is required");
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
                if (amount <= 0) {
                    inputAmount.setError("Amount must be greater than zero");
                    return;
                }
            } catch (NumberFormatException e) {
                inputAmount.setError("Invalid amount");
                return;
            }

            if (TextUtils.isEmpty(expiryDate)) {
                inputExpiryDate.setError("Expiry date is required");
                return;
            }

            List<String> types = new ArrayList<>();
            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                if (chip.isChecked()) {
                    types.add(chip.getText().toString());
                }
            }

            if (types.isEmpty()) {
                Toast.makeText(getContext(), "At least one type must be selected", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check for duplicate food
            for (Food food : foodList) {
                if (food.getName().equalsIgnoreCase(name)) {
                    inputName.setError("Food item already exists");
                    return;
                }
            }

            // Create a new Food object and add it to the list
            Food newFood = new Food(name, amount, expiryDate, TextUtils.join(", ", types));
            foodList.add(newFood);
            foodAdapter.notifyItemInserted(foodList.size() - 1);

            // Update the restaurant object in the DB
            restaurant.setStorage(foodList);
            updateRestaurantInDB();

            // Dismiss the dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showEditItemDialog(int position, Food food) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_amount, null);

        TextInputEditText inputAmount = dialogView.findViewById(R.id.input_amount);

        MaterialButton btnSave = dialogView.findViewById(R.id.btn_save);

        inputAmount.setText(String.valueOf(food.getAmount()));

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

        btnSave.setOnClickListener(v -> {
            String amountStr = inputAmount.getText().toString().trim();

            if (TextUtils.isEmpty(amountStr)) {
                inputAmount.setError("Amount is required");
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountStr);
                if (amount <= 0) {
                    inputAmount.setError("Amount must be greater than zero");
                    return;
                }
            } catch (NumberFormatException e) {
                inputAmount.setError("Invalid amount");
                return;
            }

            // Update the Food object and notify the adapter
            food.setAmount(amount);
            foodAdapter.notifyItemChanged(position);

            // Update the restaurant object in the DB
            restaurant.setStorage(foodList);
            updateRestaurantInDB();

            // Dismiss the dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateRestaurantInDB() {
        ObjectBoundary objectBoundary = restaurant.toObjectBoundary(userEmail);
        apiRepository.updateObject("2024b.gal.said", boundaryId, "2024b.gal.said", userEmail, objectBoundary, new ApiCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(getContext(), "Restaurant updated successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error updating restaurant: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews(View root) {
        recyclerView = binding.recyclerView;
        btnAddItem = binding.btnAddItem;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}