package com.example.miniapp_restaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.miniapp_restaurant.Models.Restaurant;
import com.example.miniapp_restaurant.Models.Server.Object.Location;
import com.example.miniapp_restaurant.Models.Server.Object.NewUserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.RoleEnum;
import com.example.miniapp_restaurant.Models.Server.Object.UserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp;
    private ApiRepository apiRepository;
    private PlacesClient placesClient;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
        apiRepository = new ApiRepository();

        btnSignIn.setOnClickListener(view -> showSignInDialog());

        btnSignUp.setOnClickListener(view -> showSignUpDialog());

        // Initialize the Places API using the API key from strings.xml
        String apiKey =BuildConfig.MAPS_API_KEY;
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);
    }

    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign In");

        final EditText inputEmail = new EditText(this);
        inputEmail.setHint("Email");
        inputEmail.setMaxLines(1);

        builder.setView(inputEmail);

        builder.setPositiveButton("Sign In", null);

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button signInButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            signInButton.setOnClickListener(view -> {
                String email = inputEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    setErrorDrawable(inputEmail);
                    Toast.makeText(LoginActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else {
                    clearErrorDrawable(inputEmail);
                    dialog.dismiss();
                    signIn(email);
                }
            });
        });

        dialog.show();
    }

    private void showSignUpDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.dialog_sign_up, null);

        final EditText inputEmail = customView.findViewById(R.id.input_email);
        final EditText inputName = customView.findViewById(R.id.input_name);
        final EditText inputPhone = customView.findViewById(R.id.input_phone);
        final EditText inputAddress = customView.findViewById(R.id.input_address);

        Button btnSignUp = customView.findViewById(R.id.btn_sign_up);
        Button btnCancel = customView.findViewById(R.id.btn_cancel);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(customView)
                .create();

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(31.937750, 34.834125),
                new LatLng(32.225814, 34.896383)
        ));
        autocompleteFragment.setCountries("IL");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                inputAddress.setText(place.getName());
                latLng = place.getLatLng();
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("Places test", "An error occurred: " + status);
            }
        });

        btnSignUp.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String name = inputName.getText().toString().trim();
            String phone = inputPhone.getText().toString().trim();
            String address = inputAddress.getText().toString().trim();

            boolean isValid = true;

            if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                setErrorDrawable(inputEmail);
                Toast.makeText(LoginActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputEmail);
            }
            if (TextUtils.isEmpty(name)) {
                setErrorDrawable(inputName);
                Toast.makeText(LoginActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputName);
            }
            if (TextUtils.isEmpty(phone)) {
                setErrorDrawable(inputPhone);
                Toast.makeText(LoginActivity.this, "Phone cannot be empty", Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputPhone);
            }
            if (TextUtils.isEmpty(address) || latLng == null) {
                setErrorDrawable(inputAddress);
                Toast.makeText(LoginActivity.this, "Please select a valid address from the autocomplete", Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputAddress);
            }

            if (isValid) {
                signUp(email, name, address, phone, latLng);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }


    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void signIn(String email) {
        apiRepository.getUser("2024b.gal.said", email, new ApiCallback<UserBoundary>() {
            @Override
            public void onSuccess(UserBoundary userBoundary) {
                String objectId = userBoundary.getUserName();

                apiRepository.getSpecificObject("2024b.gal.said", objectId, "2024b.gal.said", email, new ApiCallback<ObjectBoundary>() {
                    @Override
                    public void onSuccess(ObjectBoundary objectBoundary) {
                        if ("Restaurant".equals(objectBoundary.getType())) {
                            Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            UserSession.getInstance().setBoundaryId(userBoundary.getUserName());
                            UserSession.getInstance().setUserEmail(email);
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "User is not a restaurant owner", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this, "Failed to verify user role: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, "Failed to login: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signUp(String email, String name, String address, String phone, LatLng latLng) {
        NewUserBoundary newUserBoundary = new NewUserBoundary();
        newUserBoundary.setUsername(name);
        newUserBoundary.setEmail(email);
        newUserBoundary.setRole(RoleEnum.SUPERAPP_USER);
        newUserBoundary.setAvatar("default_avatar");

        apiRepository.createUser(newUserBoundary, new ApiCallback<UserBoundary>() {
            @Override
            public void onSuccess(UserBoundary userResult) {
                Toast.makeText(LoginActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantEmail(email);
                restaurant.setRestaurantName(name);
                restaurant.setRestaurantAddress(address);
                restaurant.setRestaurantPhone(phone);
                restaurant.setRestaurantLocation(new Location(latLng.latitude, latLng.longitude));
                restaurant.setStorage(new ArrayList<>());


                ObjectBoundary objectBoundary = restaurant.toObjectBoundary(email);

                apiRepository.createObject(objectBoundary, new ApiCallback<ObjectBoundary>() {
                    @Override
                    public void onSuccess(ObjectBoundary result) {
                        Toast.makeText(LoginActivity.this, "Restaurant created successfully", Toast.LENGTH_SHORT).show();
                        UserBoundary updatedUser = userResult;
                        updatedUser.setUserName(result.getObjectId().getId());
                        apiRepository.updateUser("2024b.gal.said", email, updatedUser, new ApiCallback<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                UserSession.getInstance().setBoundaryId(updatedUser.getUserName());
                                UserSession.getInstance().setUserEmail(email);
                                navigateToMainActivity();
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(LoginActivity.this, "Failed to update user: " + error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this, "Failed to create restaurant: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, "Failed to create user: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setErrorDrawable(EditText editText) {
        Drawable errorDrawable = ContextCompat.getDrawable(this, R.drawable.ic_error);
        errorDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
        editText.setCompoundDrawables(null, null, errorDrawable, null);
    }

    private void clearErrorDrawable(EditText editText) {
        editText.setCompoundDrawables(null, null, null, null);
    }
}
