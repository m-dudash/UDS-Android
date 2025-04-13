package com.example.db_mdudash;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RoomDetailActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private TextView roomNumber, roomPrice, roomArea, roomEquip, roomStatus, roomBeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        roomArea = findViewById(R.id.roomArea);
        roomBeds = findViewById(R.id.roomBeds);
        roomNumber = findViewById(R.id.roomNumber);
        roomPrice = findViewById(R.id.roomPrice);
        roomEquip = findViewById(R.id.roomEquip);
        roomStatus = findViewById(R.id.roomStatus);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(evt -> finish());

        int roomId = getIntent().getIntExtra("room_id", -1);
        loadRoomDetails(roomId);
    }

    private void loadRoomDetails(int id){
        Cursor cursor = dbHelper.getRoomById(id);
        if(cursor.moveToFirst()){
            String number = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_NUMBER));
            int area = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_AREA));
            int beds = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_BEDS));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_PRICE));
            String equip = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_EQUIPMENT));
            int isOccupied = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_IS_OCCUPIED));

            roomNumber.setText(number);
            roomBeds.setText("Beds: " + beds);
            roomArea.setText("Area: " + area + " m²");
            roomEquip.setText("Equipment: " + equip);
            roomPrice.setText("Price: EUR " + price);
            roomStatus.setText("Status: " + (isOccupied == 1 ? "Occupied" : "Available"));
        }
        cursor.close();
    }
}