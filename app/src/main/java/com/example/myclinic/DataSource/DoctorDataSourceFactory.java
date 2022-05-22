package com.example.myclinic.DataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.myclinic.Models.DoctorModel;

public class DoctorDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, DoctorModel>> doctorLiveDataSoruce = new MutableLiveData<>();
    private String cityName = "";

    @NonNull
    @Override
    public DataSource create() {
        DoctorsDataSource doctorsDataSource = new DoctorsDataSource(cityName);
        doctorLiveDataSoruce.postValue(doctorsDataSource);
        return doctorsDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, DoctorModel>> getDoctorLiveDataSoruce() {
        return doctorLiveDataSoruce;
    }

    public void setDoctorLiveDataSoruce(MutableLiveData<PageKeyedDataSource<Integer, DoctorModel>> doctorLiveDataSoruce) {
        this.doctorLiveDataSoruce = doctorLiveDataSoruce;
    }

    public void queryCity(String cityName) {
        this.cityName = cityName;
    }
}
