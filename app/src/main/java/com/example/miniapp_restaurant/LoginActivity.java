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
import android.widget.FrameLayout;
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
        builder.setTitle(getString(R.string.sign_in));

        final EditText inputEmail = new EditText(this);
        inputEmail.setHint(getString(R.string.email_hint));
        inputEmail.setMaxLines(1);

        builder.setView(inputEmail);

        builder.setPositiveButton(getString(R.string.sign_in), null);
        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button signInButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            signInButton.setOnClickListener(view -> {
                String email = inputEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    setErrorDrawable(inputEmail);
                    Toast.makeText(LoginActivity.this, getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
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
        apiRepository.getUser(UserSession.getInstance().getSUPERAPP(), email, new ApiCallback<UserBoundary>() {
            @Override
            public void onSuccess(UserBoundary userBoundary) {
                String objectId = userBoundary.getUserName();

                apiRepository.getSpecificObject(UserSession.getInstance().getSUPERAPP(), objectId, UserSession.getInstance().getSUPERAPP(), email, new ApiCallback<ObjectBoundary>() {
                    @Override
                    public void onSuccess(ObjectBoundary objectBoundary) {
                        if ("Restaurant".equals(objectBoundary.getType())) {
                            Toast.makeText(LoginActivity.this, getString(R.string.user_logged_in), Toast.LENGTH_SHORT).show();
                            UserSession.getInstance().setBoundaryId(userBoundary.getUserName());
                            UserSession.getInstance().setUserEmail(email);
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.not_restaurant_owner), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this, String.format(getString(R.string.verify_user_failed), error), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, getString(R.string.email_not_exist), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        FrameLayout fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer.getVisibility()==View.VISIBLE) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    private void clearErrorDrawable(EditText editText) {
        editText.setCompoundDrawables(null, null, null, null);
    }
}