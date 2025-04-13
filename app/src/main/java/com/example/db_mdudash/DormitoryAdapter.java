package com.example.db_mdudash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db_mdudash.model.Dormitory;

import java.util.List;

public class DormitoryAdapter extends ArrayAdapter<Dormitory> {


    public DormitoryAdapter(Context context, List<Dormitory> dormitories) {
        super(context,0,dormitories);

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dormitory, parent,false);
        }

        Dormitory dormitory = getItem(position);

        TextView textName = convertView.findViewById(R.id.text_view_name);
        TextView textAddress = convertView.findViewById(R.id.text_view_address);

        textName.setText(dormitory.getName());
        textAddress.setText(dormitory.getAddress());

        return convertView;
    }
}
