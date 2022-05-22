package com.example.myclinic.DataSource;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.myclinic.Activities.MainActivity;
import com.example.myclinic.Activities.ViewDoctors;
import com.example.myclinic.Api.RetroClient;
import com.example.myclinic.Models.DoctorListResponse;
import com.example.myclinic.Models.DoctorModel;
import com.example.myclinic.R;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsDataSource extends PageKeyedDataSource<Integer, DoctorModel> {

    private static final Integer FIRST_PAGE = 1;
    private String cityName;
    private static final Integer NUM_DOCTORS = 5;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public DoctorsDataSource(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DoctorModel> loadCallback) {
        RetroClient.getInstance()
                .getApi()
                .doctorList(loadParams.key,cityName)
                .enqueue(new Callback<DoctorListResponse>() {
                    @Override
                    public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                        if (response.body() != null) {
                            loadCallback.onResult(response.body().getData(),loadParams.key == (response.body().getTotal())/NUM_DOCTORS ? null : loadParams.key + 1);
                            Log.i("successwork","Loading next");

                        } else {
                            Log.i("ResponseNull","Response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorListResponse> call, Throwable t) {
                        Log.i("ResponseNull",t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, DoctorModel> loadCallback) {
        RetroClient.getInstance()
                .getApi()
                .doctorList(loadParams.key,cityName)
                .enqueue(new Callback<DoctorListResponse>() {
                    @Override
                    public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                        if (response.body() != null) {
                            loadCallback.onResult(response.body().getData(),loadParams.key > 1 ? loadParams.key -1 : null);
                            Log.i("successwork","Loading previous");
                        } else {
                            Log.i("ResponseNull","Response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorListResponse> call, Throwable t) {
                        Log.i("ResponseNull",t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, DoctorModel> loadInitialCallback) {
        RetroClient.getInstance()
                .getApi()
                .doctorList(FIRST_PAGE,cityName)
                .enqueue(new Callback<DoctorListResponse>() {
                    @Override
                    public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                        if (response.body() != null) {
                            loadInitialCallback.onResult(response.body().getData(),null,FIRST_PAGE + 1);
                        } else {
                            Log.i("ResponseNull","Response is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorListResponse> call, Throwable t) {
                        Log.i("ResponseNull",t.getLocalizedMessage());
                    }
                });
    }
}
