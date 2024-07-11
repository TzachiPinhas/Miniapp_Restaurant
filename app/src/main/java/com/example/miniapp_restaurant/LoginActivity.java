package com.example.miniapp_restaurant;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.example.miniapp_restaurant.Server.ApiCallback;
import com.example.miniapp_restaurant.Server.ApiRepository;
import com.example.miniapp_restaurant.ui.SignUpFragment;
import com.google.android.libraries.places.api.Places;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn, btnSignUp;
    private ApiRepository apiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);

        apiRepository = new ApiRepository();

        btnSignIn.setOnClickListener(view -> showSignInDialog());
        btnSignUp.setOnClickListener(view -> showSignUpFragment());

        // Initialize the Places API using the API key from strings.xml
        String apiKey = BuildConfig.MAPS_API_KEY;
        Places.initialize(getApplicationContext(), apiKey);
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

    private void showSignUpFragment() {
        btnSignUp.setVisibility(View.GONE);

        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Make the fragment container visible
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
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
                Toast.makeText(LoginActivity.this, "The email does not exist in the system", Toast.LENGTH_SHORT).show();
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