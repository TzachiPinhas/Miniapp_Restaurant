package com.example.miniapp_restaurant.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.miniapp_restaurant.BuildConfig;
import com.example.miniapp_restaurant.MainActivity;
import com.example.miniapp_restaurant.Models.Restaurant;
import com.example.miniapp_restaurant.Models.Server.Object.Location;
import com.example.miniapp_restaurant.Models.Server.Object.NewUserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.RoleEnum;
import com.example.miniapp_restaurant.Models.Server.Object.UserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.R;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;

public class SignUpFragment extends Fragment {

    private ApiRepository apiRepository;
    private LatLng latLng;
    private EditText inputEmail, inputName, inputPhone, inputAddress;
    private AutocompleteSupportFragment autocompleteFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        inputEmail = view.findViewById(R.id.input_email);
        inputName = view.findViewById(R.id.input_name);
        inputPhone = view.findViewById(R.id.input_phone);
        inputAddress = view.findViewById(R.id.input_address);
        Button btnSignUp = view.findViewById(R.id.btn_sign_up);

        apiRepository = new ApiRepository();

        btnSignUp.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String name = inputName.getText().toString().trim();
            String phone = inputPhone.getText().toString().trim();
            String address = inputAddress.getText().toString().trim();

            boolean isValid = true;

            if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                setErrorDrawable(inputEmail);
                Toast.makeText(getContext(), getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputEmail);
            }
            if (TextUtils.isEmpty(name)) {
                setErrorDrawable(inputName);
                Toast.makeText(getContext(), getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputName);
            }
            if (TextUtils.isEmpty(phone)) {
                setErrorDrawable(inputPhone);
                Toast.makeText(getContext(), getString(R.string.phone_empty), Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputPhone);
            }
            if (TextUtils.isEmpty(address) || latLng == null) {
                setErrorDrawable(inputAddress);
                Toast.makeText(getContext(), getString(R.string.address_invalid), Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                clearErrorDrawable(inputAddress);
            }

            if (isValid) {
                signUp(email, name, address, phone, latLng);
            }
        });

        initializeAutocompleteFragment();

        return view;
    }

    private void initializeAutocompleteFragment() {
        if (getActivity() == null) return;

        // Ensure Places API is initialized
        if (!Places.isInitialized()) {
            String apiKey = BuildConfig.MAPS_API_KEY;
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }

        // Get the child fragment manager and set up the autocomplete fragment
        FragmentManager fragmentManager = getChildFragmentManager();
        autocompleteFragment = (AutocompleteSupportFragment) fragmentManager.findFragmentById(R.id.autocomplete_fragment_container);

        if (autocompleteFragment == null) {
            autocompleteFragment = new AutocompleteSupportFragment();
            fragmentManager.beginTransaction().replace(R.id.autocomplete_fragment_container, autocompleteFragment).commit();
        }

        // Configure the autocomplete fragment
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
    }

    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                        Toast.makeText(getContext(), getString(R.string.user_created), Toast.LENGTH_SHORT).show();
                        UserBoundary updatedUser = userResult;
                        updatedUser.setUserName(result.getObjectId().getId());
                        apiRepository.updateUser(UserSession.getInstance().getSUPERAPP(), email, updatedUser, new ApiCallback<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                UserSession.getInstance().setBoundaryId(updatedUser.getUserName());
                                UserSession.getInstance().setUserEmail(email);
                                navigateToMainActivity();
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(getContext(), String.format(getString(R.string.update_user_failed), error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getContext(), String.format(getString(R.string.create_restaurant_failed), error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), String.format(getString(R.string.create_user_failed), error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    private void setErrorDrawable(EditText editText) {
        Drawable errorDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_error);
        errorDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
        editText.setCompoundDrawables(null, null, errorDrawable, null);
    }

    private void clearErrorDrawable(EditText editText) {
        editText.setCompoundDrawables(null, null, null, null);
    }
}