package com.example.db_mdudash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.db_mdudash.model.Room;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {

    public RoomAdapter(Context context,  List<Room> rooms) {
        super(context,0, rooms);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_room, parent, false);
        }

        Room room = getItem(position);

        TextView textNumber = convertView.findViewById(R.id.text_view_number);
        TextView textBeds = convertView.findViewById(R.id.text_view_beds);
        TextView textOccupied = convertView.findViewById(R.id.text_view_occupied);

        textNumber.setText("Room: " + room.getNumber());
        textBeds.setText("Beds: " + room.getBeds());
        textOccupied.setText(room.isOccupied() ? "Occupied" : "Available");

        return convertView;
    }
}
