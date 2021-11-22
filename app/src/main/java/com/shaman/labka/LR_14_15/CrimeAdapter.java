package com.shaman.labka.LR_14_15;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shaman.labka.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.ViewHolder> {
    private final LayoutInflater _inflater;
    private final List<Crime> _crimes;

    public CrimeAdapter(LayoutInflater inflater, List<Crime> crimes){
        _crimes=crimes;
        _inflater= inflater;
    }

    @NonNull
    @Override
    public CrimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.list_item_crime, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeAdapter.ViewHolder holder, int position) {

        Crime crime = _crimes.get(position);
        if (crime.isSolved()){
            holder.crimeSolvedImgView.setVisibility(View.VISIBLE);
        }else {
            holder.crimeSolvedImgView.setVisibility(View.INVISIBLE);
        }
        holder.crimeSolvedImgView.setImageResource(R.drawable.ic_solved);
        holder.titleView.setText(crime.getTitle());

        Date date=crime.getDate();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat postFormatter = new SimpleDateFormat("dd-MMM-yyyy");

        String newDateStr = postFormatter.format(date);
        holder.dateView.setText(newDateStr);
    }

    @Override
    public int getItemCount() {
        return _crimes.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView crimeSolvedImgView;
        final TextView titleView, dateView;
        ViewHolder(View view){
            super(view);
            crimeSolvedImgView = view.findViewById(R.id.crime_solved);
            titleView = view.findViewById(R.id.crime_title);
            dateView = view.findViewById(R.id.crime_date);
        }
    }
}
