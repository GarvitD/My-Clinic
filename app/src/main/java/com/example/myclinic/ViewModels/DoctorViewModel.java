package com.example.myclinic.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PagedListConfigKt;

import com.example.myclinic.DataSource.DoctorDataSourceFactory;
import com.example.myclinic.Models.DoctorModel;

public class DoctorViewModel extends ViewModel {

    public LiveData<PagedList<DoctorModel>> doctorsList;
    public LiveData<PageKeyedDataSource<Integer,DoctorModel>> liveDataSource;
    public DoctorDataSourceFactory doctorDataSourceFactory;

    public DoctorViewModel() {
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(5)
                .build();

        doctorDataSourceFactory = new DoctorDataSourceFactory();
        liveDataSource = doctorDataSourceFactory.getDoctorLiveDataSoruce();

        doctorsList = new LivePagedListBuilder<Integer,DoctorModel>(doctorDataSourceFactory,config).build();
    }

    public void citySelected(String cityName) {
        doctorDataSourceFactory.queryCity(cityName);
    }
}
