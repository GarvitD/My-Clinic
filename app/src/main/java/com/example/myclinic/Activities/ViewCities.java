package com.example.myclinic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myclinic.Adapters.CitiesAdapter;
import com.example.myclinic.Api.RetroClient;
import com.example.myclinic.R;
import com.example.myclinic.databinding.ActivityViewCitiesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCities extends AppCompatActivity {

    private ActivityViewCitiesBinding binding;
    private List<String> cities;
    private CitiesAdapter citiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewCitiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cities = new ArrayList<>();
        binding.progressBar.setVisibility(View.VISIBLE);
        setUpRecylerView();
    }

    private void setUpRecylerView() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> citiesCall = RetroClient.getInstance()
                .getApi().loadCities();
        citiesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    JSONArray citiesJSON = new JSONObject(response.body().string()).getJSONArray("city");
                    for(int i=0;i<citiesJSON.length();i++){
                        cities.add(citiesJSON.getJSONObject(i).getString("city"));
                    }
                    citiesAdapter = new CitiesAdapter(ViewCities.this,cities);
                    binding.citiesRecyclerView.setHasFixedSize(true);
                    binding.citiesRecyclerView.setLayoutManager(new LinearLayoutManager(ViewCities.this));
                    binding.citiesRecyclerView.setAdapter(citiesAdapter);
                    binding.progressBar.setVisibility(View.GONE);
                } catch (JSONException | IOException e) {
                    StyleableToast.makeText(ViewCities.this, e.getLocalizedMessage(), Toast.LENGTH_LONG, R.style.errorToast).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                StyleableToast.makeText(ViewCities.this, t.getLocalizedMessage(), Toast.LENGTH_LONG, R.style.errorToast).show();
            }
        });
    }
}