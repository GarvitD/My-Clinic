package com.example.myclinic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myclinic.Adapters.DoctorsAdapter;
import com.example.myclinic.Api.RetroClient;
import com.example.myclinic.Models.DoctorListResponse;
import com.example.myclinic.Models.DoctorModel;
import com.example.myclinic.R;
import com.example.myclinic.ViewModels.DoctorViewModel;
import com.example.myclinic.databinding.ActivityViewDoctorsBinding;

import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDoctors extends AppCompatActivity implements DoctorsAdapter.DoctorClick {

    private ActivityViewDoctorsBinding binding;
    private String cityName;
    private List<DoctorModel> doctorList;
    private DoctorViewModel doctorViewModel;
    private DoctorsAdapter doctorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cityName = getIntent().getStringExtra("cityName");
        if(cityName == null) cityName = "";
        Log.i("cityName",cityName);

        binding.progressBar.setVisibility(View.VISIBLE);

        setUpRecyclerView();

        binding.cityPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewDoctors.this,ViewCities.class));
            }
        });
    }

    private void setUpRecyclerView() {
        binding.doctorsList.setHasFixedSize(true);
        binding.doctorsList.setLayoutManager(new LinearLayoutManager(this));

        doctorViewModel = ViewModelProviders.of(this).get(DoctorViewModel.class);
        doctorViewModel.citySelected(cityName);
        doctorsAdapter = new DoctorsAdapter(this,this);
        doctorViewModel.doctorsList.observe(this,
                new Observer<PagedList<DoctorModel>>() {
                    @Override
                    public void onChanged(PagedList<DoctorModel> doctorModels) {
                        doctorsAdapter.submitList(doctorModels);
                    }
                });

        binding.doctorsList.setAdapter(doctorsAdapter);
    }

    @Override
    public void bookApt(DoctorModel doctorModel) {
        Intent intent = new Intent(this,BookAppointment.class);
        intent.putExtra("doctorInfo",doctorModel);
        startActivity(intent);
    }
}