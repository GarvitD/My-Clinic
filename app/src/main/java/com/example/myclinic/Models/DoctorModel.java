package com.example.myclinic.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class DoctorModel implements Parcelable {

    private String docId;
    private String docName;
    private String docSpecialisation;
    private String docYoE;
    private String docConsultationFee;
    private String city;
    private String docProfileImgUrl;


    public DoctorModel(String docId, String docName, String docSpecialisation, String docYoE, String docConsultationFee, String city, String docProfileImgUrl) {
        this.docId = docId;
        this.docName = docName;
        this.docSpecialisation = docSpecialisation;
        this.docYoE = docYoE;
        this.docConsultationFee = docConsultationFee;
        this.city = city;
        this.docProfileImgUrl = docProfileImgUrl;
    }

    protected DoctorModel(Parcel in) {
        docId = in.readString();
        docName = in.readString();
        docSpecialisation = in.readString();
        docYoE = in.readString();
        docConsultationFee = in.readString();
        city = in.readString();
        docProfileImgUrl = in.readString();
    }

    public static final Creator<DoctorModel> CREATOR = new Creator<DoctorModel>() {
        @Override
        public DoctorModel createFromParcel(Parcel in) {
            return new DoctorModel(in);
        }

        @Override
        public DoctorModel[] newArray(int size) {
            return new DoctorModel[size];
        }
    };

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocSpecialisation() {
        return docSpecialisation;
    }

    public void setDocSpecialisation(String docSpecialisation) {
        this.docSpecialisation = docSpecialisation;
    }

    public String getDocYoE() {
        return docYoE;
    }

    public void setDocYoE(String docYoE) {
        this.docYoE = docYoE;
    }

    public String getDocConsultationFee() {
        return docConsultationFee;
    }

    public void setDocConsultationFee(String docConsultationFee) {
        this.docConsultationFee = docConsultationFee;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDocProfileImgUrl() {
        return docProfileImgUrl;
    }

    public void setDocProfileImgUrl(String docProfileImgUrl) {
        this.docProfileImgUrl = docProfileImgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(docId);
        parcel.writeString(docName);
        parcel.writeString(docSpecialisation);
        parcel.writeString(docYoE);
        parcel.writeString(docConsultationFee);
        parcel.writeString(city);
        parcel.writeString(docProfileImgUrl);
    }
}
