package com.example.niramaya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.niramaya.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiseaseListAdapter extends ArrayAdapter<Disease> {

    private Context mContext;
    int mResource;



    public DiseaseListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Disease> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String disease= Objects.requireNonNull(getItem(position)).getDiseae();
        int count= Objects.requireNonNull(getItem(position)).getCoun();

        Disease disease1=new Disease(count,disease);

        LayoutInflater inflater= LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView disText=convertView.findViewById(R.id.diseaseName);
        TextView countText=convertView.findViewById(R.id.countNo);

        disText.setText(disease);
        countText.setText(String.valueOf(count));

        return convertView;
    }
}

