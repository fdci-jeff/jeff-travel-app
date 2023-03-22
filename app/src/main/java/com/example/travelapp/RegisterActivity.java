package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameText;

    EditText emailText;

    EditText passwordText;

    EditText confirm_passwordText;

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register);

        usernameText = findViewById(R.id.username);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        confirm_passwordText = findViewById(R.id.password_confirmation);
        registerButton = findViewById(R.id.btn_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String pass_con = confirm_passwordText.getText().toString();

                register(username, email, password, pass_con);
            }
        });
    }

    private void register(String username, String email, String password, String pass_con) {
        usernameText = findViewById(R.id.username);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String requestBody =
                "{\"username\":\"" + username + "\",\"name\":\"" + username +
                "\",\"password\":\"" + password +
                "\",\"email\":\"" +  email +
                "\",\"password_confirmation\":\"" + pass_con + "\"}";

        RequestBody body = RequestBody.create(requestBody, mediaType);

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8000/api/auth/register")
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("test", "onResponse: " + response);
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONObject errorObject = jsonObject.getJSONObject("error");

                        JSONArray usernameErrors = errorObject.optJSONArray("username");
                        JSONArray emailErrors = errorObject.optJSONArray("email");
                        JSONArray passErrors = errorObject.optJSONArray("password");

                        if (usernameErrors != null && usernameErrors.length() > 0) {
                            String usernameError = usernameErrors.getString(0);
                            runOnUiThread(() -> usernameText.setError(usernameError));
                        }

                        if (emailErrors != null && emailErrors.length() > 0) {
                            String emailError = emailErrors.getString(0);
                            runOnUiThread(() -> emailText.setError(emailError));
                        }

                        if (passErrors != null && passErrors.length() > 0) {
                            String passError = passErrors.getString(0);
                            runOnUiThread(() -> passwordText.setError(passError));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
    }
}