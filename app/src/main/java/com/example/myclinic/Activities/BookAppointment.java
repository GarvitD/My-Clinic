package com.example.myclinic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myclinic.Api.RetroClient;
import com.example.myclinic.Models.DoctorModel;
import com.example.myclinic.R;
import com.example.myclinic.databinding.ActivityBookAppointmentBinding;

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

public class BookAppointment extends AppCompatActivity {

    private static final String[] genders = {"Male","Female","Others"};

    private ActivityBookAppointmentBinding binding;
    private DoctorModel doctorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctorSelected = getIntent().getParcelableExtra("doctorInfo");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.dropdown_item,genders);
        binding.genderOptions.setAdapter(arrayAdapter);

        binding.bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.patientName.getText().toString().trim();
                String address = binding.pateintAddress.getText().toString().trim();
                String phoneNum = binding.phoneNum.getText().toString().trim();
                String age = binding.patientAge.getText().toString().trim();
                String gender = binding.genderOptions.getText().toString().charAt(0)+"";
                String docName = doctorSelected.getDocName();
                String docId = doctorSelected.getDocId();

                if(!isPhoneValid(phoneNum)) {
                    binding.phoneNum.requestFocus();
                    binding.phoneNum.setError("Please Enter a Valid Phone Number");
                    return;
                }
                if(Integer.parseInt(age) < 0) {
                    binding.patientAge.requestFocus();
                    binding.patientAge.setError("Please Enter a Valid Age");
                    return;
                }
                sendBookRequest(name,address,phoneNum,age,gender,docName,docId);
            }
        });
    }

    private void sendBookRequest(String name, String address, String phoneNum, String age, String gender, String docName, String docId) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetroClient.getInstance()
                .getApi()
                .bookDoctor(
                        name,
                        phoneNum,
                        age,
                        address,
                        gender,
                        docName,
                        docId
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        binding.progressBar.setVisibility(View.GONE);
                        if(response.body() != null) {
                            String responseText = null;
                            try {
                                responseText = new JSONObject(response.body().string()).getString("appointmentId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(responseText != null && responseText.equalsIgnoreCase("originallyitwouldbesomerandomid")) {
                                StyleableToast.makeText(BookAppointment.this, "Appointment Booked Successfully", Toast.LENGTH_LONG, R.style.successToast).show();
                                startActivity(new Intent(BookAppointment.this,ViewDoctors.class));
                                finish();
                            } else {
                                StyleableToast.makeText(BookAppointment.this, "Appointment Booking Failed!", Toast.LENGTH_LONG, R.style.errorToast).show();
                            }
                        } else {
                            StyleableToast.makeText(BookAppointment.this, "Appointment Booking Failed!", Toast.LENGTH_LONG, R.style.errorToast).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        binding.progressBar.setVisibility(View.GONE);
                        StyleableToast.makeText(BookAppointment.this, t.getLocalizedMessage(), Toast.LENGTH_LONG, R.style.errorToast).show();
                    }
                });
    }

    private boolean isPhoneValid(String phone) {
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }
}