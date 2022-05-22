package com.example.myclinic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myclinic.Api.RetroClient;
import com.example.myclinic.R;
import com.example.myclinic.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences userSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userSharedPreference = getSharedPreferences("usersSp",MODE_PRIVATE);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = binding.phoneNum.getText().toString().trim();
                binding.progressBar.setVisibility(View.VISIBLE);
                if(isPhoneValid(phoneNum)) {
                    requestLogin(phoneNum);
                } else {
                    binding.phoneNum.requestFocus();
                    StyleableToast.makeText(MainActivity.this,"Please enter a valid Mobile Number!", Toast.LENGTH_LONG, R.style.errorToast).show();
                }
            }
        });

    }

    private void requestLogin(String phone) {
        Call<ResponseBody> loginCall = RetroClient.getInstance()
                .getApi()
                .loginUser(phone)
                ;
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    binding.progressBar.setVisibility(View.GONE);
                    if(response.body() != null) {
                        JSONObject responseJson = new JSONObject(response.body().string());
                        int count = responseJson.length();
                        if (count == 1) {
                            StyleableToast.makeText(MainActivity.this, responseJson.getString("message"), Toast.LENGTH_SHORT, R.style.errorToast).show();
                        } else {
                            SharedPreferences.Editor editor = userSharedPreference.edit();
                            editor.putBoolean("isLoggedIn",true);
                            editor.apply();

                            StyleableToast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_LONG, R.style.successToast).show();
                            startActivity(new Intent(MainActivity.this,ViewDoctors.class));
                            finish();
                        }
                    } else {
                        StyleableToast.makeText(MainActivity.this, "Phone Number Incorrect!", Toast.LENGTH_LONG, R.style.errorToast).show();
                    }
                } catch (JSONException | IOException e) {
                    Log.i("loginException",e.getLocalizedMessage());
                    StyleableToast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG, R.style.errorToast).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                StyleableToast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG, R.style.errorToast).show();
            }
        });
    }

    private boolean isPhoneValid(String phone) {
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }
}