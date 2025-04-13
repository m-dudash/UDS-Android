package com.example.db_mdudash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigureRoomActivity extends AppCompatActivity {
    private EditText editTextNumber, editTextBeds, editTextArea, editTextEquipment, editTextPrice;
    private int dormitoryId;
    private DBHelper dbHelper;
    private int roomId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configure_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNumber = findViewById(R.id.numberPlain);
        editTextBeds = findViewById(R.id.bedsPlain);
        editTextArea = findViewById(R.id.areaPlain);
        editTextEquipment = findViewById(R.id.equipmentPlain);
        editTextPrice = findViewById(R.id.pricePlain);
        Button saveButton = findViewById(R.id.saveRoomButton);
        dbHelper = new DBHelper(this);
        dormitoryId = getIntent().getIntExtra("dormitory_id", -1);

        if (getIntent().hasExtra("room_id")) {
            roomId = getIntent().getIntExtra("room_id", -1);
            editTextNumber.setText(getIntent().getStringExtra("number"));
            editTextBeds.setText(String.valueOf(getIntent().getIntExtra("beds", 0)));
            editTextArea.setText(String.valueOf(getIntent().getIntExtra("area", 0)));
            editTextEquipment.setText(getIntent().getStringExtra("equipment"));
            editTextPrice.setText(String.valueOf(getIntent().getDoubleExtra("price", 0.0)));
        }

        saveButton.setOnClickListener(evt ->{
            String number = editTextNumber.getText().toString().trim();
            String bedsStr = editTextBeds.getText().toString().trim();
            String areaStr = editTextArea.getText().toString().trim();
            String equipment = editTextEquipment.getText().toString().trim();
            String priceStr = editTextPrice.getText().toString().trim();


            if (!number.isEmpty() && !bedsStr.isEmpty() && !areaStr.isEmpty() && !equipment.isEmpty() && !priceStr.isEmpty()) {
                int beds = Integer.parseInt(bedsStr);
                int area = Integer.parseInt(areaStr);
                double price = Double.parseDouble(priceStr);

                if (roomId == -1) {
                    long id = dbHelper.addRoom(dormitoryId, number, beds, equipment, area, 0, price);
                        Toast.makeText(this, "Room added", Toast.LENGTH_SHORT).show();
                        finish();
                } else {
                    int currentOccupiedStatus = getRoomOccupiedStatus(roomId);
                    int rowsAffected = dbHelper.updateRoom(roomId, number, beds, equipment, area, currentOccupiedStatus, price);
                        Toast.makeText(this, "Room updated", Toast.LENGTH_SHORT).show();
                        finish();
                }
            } else {
                Toast.makeText(this, "Enter all data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getRoomOccupiedStatus(int roomId) {
        android.database.Cursor cursor = dbHelper.getRoomById(roomId);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_IS_OCCUPIED));
            cursor.close();
            return status;
        }
        cursor.close();
        return 0;
    }
}