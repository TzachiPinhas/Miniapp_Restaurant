package com.example.miniapp_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniapp_restaurant.Models.Restaurant;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniapp_restaurant.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {



    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ApiRepository apiRepository;
    private View header;
    private Restaurant restaurant;
    private String boundaryId;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_orders, R.id.nav_review, R.id.nav_logout,R.id.nav_donation)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        apiRepository= new ApiRepository();
        header = navigationView.getHeaderView(0);
        boundaryId = UserSession.getInstance().getBoundaryId();
        userEmail = UserSession.getInstance().getUserEmail();
        getResturantFromServer();


        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(item -> {
            logout();
            return true;
        });
    }

    private void getResturantFromServer() {
        apiRepository.getSpecificObject("2024b.gal.said",boundaryId,"2024b.gal.said" , userEmail, new ApiCallback<ObjectBoundary>() {
            @Override
            public void onSuccess(ObjectBoundary result) {
                restaurant = new Restaurant(result);
                changeNavHeader(header, restaurant);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }

    private void changeNavHeader(View header, Restaurant restaurant) {
        TextView textTitleLabel = header.findViewById(R.id.name_LBL);
        TextView emailTitleLabel = header.findViewById(R.id.email_LBL);
        ImageView imageTitleLabel = header.findViewById(R.id.main_image);

        textTitleLabel.setText(restaurant.getRestaurantName());
        emailTitleLabel.setText(restaurant.getRestaurantEmail());
    }

    private void logout() {
        UserSession.getInstance().clearSession();
        // Navigate to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}