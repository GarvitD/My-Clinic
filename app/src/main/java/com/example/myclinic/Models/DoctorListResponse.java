package com.example.myclinic.Models;
import java.util.List;

public class DoctorListResponse {

    private int total;
    private List<DoctorModel> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DoctorModel> getData() {
        return data;
    }

    public void setData(List<DoctorModel> data) {
        this.data = data;
    }

    public DoctorListResponse(int total, List<DoctorModel> data) {
        this.total = total;
        this.data = data;
    }
}
