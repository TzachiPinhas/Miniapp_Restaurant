package com.example.miniapp_restaurant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.OrderAdapter;
import com.example.miniapp_restaurant.Adapters.ReviewAdapter;
import com.example.miniapp_restaurant.Models.Review;
import com.example.miniapp_restaurant.databinding.FragmentReviewBinding;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    View root;
    private RecyclerView recycler_view_orders;
    private ArrayList<Review> reviews;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        reviews = new ArrayList<>();
        findView();
        fakeViews();
        return root;
    }

    private void fakeViews() {
        reviews.add(new Review().setName("Alice Smith").setOverview("Great food!").setDate("2023-01-01").setRating(4.5f));
        reviews.add(new Review().setName("Bob Johnson").setOverview("Good food!").setDate("2023-01-02").setRating(4.0f));
        reviews.add(new Review().setName("Charlie Brown").setOverview("Amazing food!").setDate("2023-01-03").setRating(4.7f));
        reviews.add(new Review().setName("Dana White").setOverview("Nice food!").setDate("2023-01-04").setRating(3.8f));
        reviews.add(new Review().setName("Eve Black").setOverview("Delicious food!").setDate("2023-01-05").setRating(4.6f));
        reviews.add(new Review().setName("Frank Green").setOverview("Tasty food!").setDate("2023-01-06").setRating(4.2f));
        reviews.add(new Review().setName("Grace Blue").setOverview("Lovely food!").setDate("2023-01-07").setRating(4.3f));
        reviews.add(new Review().setName("Hank Red").setOverview("Fantastic food!").setDate("2023-01-08").setRating(4.8f));

        updateAdapter();
    }

    private void findView() {
        recycler_view_orders = binding.reviewsRecyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void updateAdapter() {
        ReviewAdapter reviewAdapter = new ReviewAdapter(root.getContext(), reviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_orders.setLayoutManager(linearLayoutManager);
        recycler_view_orders.setAdapter(reviewAdapter);
        int count = reviewAdapter.getItemCount();
        if (count == 0) {

        }
    }
}