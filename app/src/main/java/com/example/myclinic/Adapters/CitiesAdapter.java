package com.example.myclinic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclinic.Activities.MainActivity;
import com.example.myclinic.Activities.ViewDoctors;
import com.example.myclinic.R;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> {

    Context context;
    List<String> cities;
//    private int lastChecked;

    public CitiesAdapter(Context context, List<String> cities) {
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CitiesViewHolder(LayoutInflater.from(context).inflate(R.layout.cities_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        holder.radioButton.setText(cities.get(holder.getBindingAdapterPosition()));
//        holder.radioButton.setChecked(position == lastChecked);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                lastChecked = holder.getAdapterPosition();
                notifyDataSetChanged();
                Intent intent = new Intent(context, ViewDoctors.class);
                intent.putExtra("cityName",holder.radioButton.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class CitiesViewHolder extends RecyclerView.ViewHolder {

        TextView radioButton;

        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.city);
        }
    }

}
