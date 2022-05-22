package com.example.myclinic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myclinic.Activities.MainActivity;
import com.example.myclinic.Models.DoctorModel;
import com.example.myclinic.R;

import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;


public class DoctorsAdapter extends PagedListAdapter<DoctorModel,DoctorsAdapter.DoctorViewHolder> {

    Context context;
    DoctorClick doctorClick;

    public DoctorsAdapter(Context context, DoctorClick doctorClick) {
        super(diffCallback);
        this.context = context;
        this.doctorClick = doctorClick;
    }

    private static DiffUtil.ItemCallback<DoctorModel> diffCallback = new DiffUtil.ItemCallback<DoctorModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DoctorModel oldItem, @NonNull DoctorModel newItem) {
            return oldItem.getDocId().equals(newItem.getDocId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DoctorModel oldItem, @NonNull DoctorModel newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    @NonNull
    @Override
    public DoctorsAdapter.DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorViewHolder(LayoutInflater.from(context).inflate(R.layout.doctors_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsAdapter.DoctorViewHolder holder, int position) {
        DoctorModel doctorModel = getItem(holder.getBindingAdapterPosition());
        if(doctorModel != null) {
            holder.name.setText(doctorModel.getDocName());
            holder.speciality.setText(doctorModel.getDocSpecialisation());
            holder.age.setText(doctorModel.getDocYoE() + " Yr");
            holder.address.setText(doctorModel.getCity());
            holder.price.setText("\u20B9 " + doctorModel.getDocConsultationFee());
            Glide.with(context).load(doctorModel.getDocProfileImgUrl())
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_img)
                    .into(holder.profile);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doctorClick.bookApt(doctorModel);
                }
            });
        } else {
            StyleableToast.makeText(context, "The item is null", Toast.LENGTH_LONG, R.style.errorToast).show();
        }
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView name,speciality,age,address,price;
        ImageView profile;
        AppCompatButton button;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctor_name);
            speciality = itemView.findViewById(R.id.doctor_speciality);
            age = itemView.findViewById(R.id.doctorAge);
            address = itemView.findViewById(R.id.doctor_address);
            price = itemView.findViewById(R.id.doctorPrice);
            profile = itemView.findViewById(R.id.doctor_profile);
            button = itemView.findViewById(R.id.bookBtn);

        }
    }

    public interface DoctorClick {
        void bookApt(DoctorModel doctorModel);
    }
}
