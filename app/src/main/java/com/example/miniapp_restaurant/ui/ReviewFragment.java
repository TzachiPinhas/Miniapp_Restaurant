package com.example.miniapp_restaurant.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniapp_restaurant.Adapters.ReviewAdapter;
import com.example.miniapp_restaurant.Models.Review;
import com.example.miniapp_restaurant.R;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.databinding.FragmentReviewBinding;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private RecyclerView recycler_view_orders;
    private ArrayList<Review> reviews;
    private ApiRepository apiRepository;
    private MaterialTextView overallRatingTextView;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        reviews = new ArrayList<>();
        apiRepository = new ApiRepository();

        findView();
        fetchReviewsFromServer();
        return root;
    }

    private void findView() {
        recycler_view_orders = binding.reviewsRecyclerView;
        overallRatingTextView = binding.overallRatingTextView;
    }

    private void fetchReviewsFromServer() {
        apiRepository.getReviewsByCommand(new ApiCallback<List<Review>>() {
            @Override
            public void onSuccess(List<Review> result) {
                reviews.clear();
                reviews.addAll(result);
                updateAdapter();
                updateOverallRating();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error fetching reviews: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAdapter() {
        ReviewAdapter reviewAdapter = new ReviewAdapter(root.getContext(), reviews);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_orders.setLayoutManager(linearLayoutManager);
        recycler_view_orders.setAdapter(reviewAdapter);
    }

    private void updateOverallRating() {
        if (reviews.isEmpty()) {
            overallRatingTextView.setText(getString(R.string.overall_rating_no_reviews));
        } else {
            double totalRating = 0;
            for (Review review : reviews) {
                totalRating += review.getRating(); // Assuming `Review` has a `getRating()` method
            }
            double averageRating = totalRating / reviews.size();
            overallRatingTextView.setText(String.format(getString(R.string.overall_rating), averageRating));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
