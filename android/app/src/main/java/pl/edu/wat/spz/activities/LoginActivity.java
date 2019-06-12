package pl.edu.wat.spz.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pl.edu.wat.spz.R;
import pl.edu.wat.spz.auth.UserDetails;
import pl.edu.wat.spz.exceptions.LoginException;
import pl.edu.wat.spz.http.HttpClient;
import pl.edu.wat.spz.http.LoginService;
import pl.edu.wat.spz.preferences.LoginPreferences;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE);
        if (prefs.contains(LoginPreferences.ID_KEY)) {
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public class UserLoginTask extends AsyncTask<Void, Void, UserDetails> {

        private final String mEmail;
        private final String mPassword;
        private String errorMessage;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            errorMessage = null;
        }

        @Override
        protected UserDetails doInBackground(Void... params) { //request - proba logowania

            LoginService loginService = HttpClient.getInstance().getLoginService();

            Call<UserDetails> login = loginService.login(mEmail, mPassword);
            try {
                Response<UserDetails> response = login.execute();

                if (response.isSuccessful() && response.body() != null) {
                    UserDetails userDetails = response.body();
                    String authorizationHeader = response.headers().get("Authorization");

                    SharedPreferences.Editor editor = getSharedPreferences(LoginPreferences.PREFERENCE_NAME, MODE_PRIVATE).edit();
                    editor.putString(LoginPreferences.ID_KEY, String.valueOf(userDetails.getId()));
                    editor.putString(LoginPreferences.EMAIL_KEY, userDetails.getEmail());
                    editor.putString(LoginPreferences.USERNAME_KEY, userDetails.getFirstName() + " " + userDetails.getLastName());
                    editor.putString(LoginPreferences.AUTHORIZATION_HEADER_KEY, authorizationHeader);
                    editor.commit();

                    return userDetails;

                } else {
                    errorMessage = "Zły login lub hasło";
                }

            } catch (Exception e) {
                errorMessage = "Nie można się połączyć z serwerem";
            }

            return null;
        }

        @Override
        protected void onPostExecute(final UserDetails userDetails) {
            mAuthTask = null;

            if (userDetails != null) {
                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            } else {
                showProgress(false);
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }


    }
}

