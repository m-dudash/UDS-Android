package com.example.db_mdudash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigureDormitoryActivity extends AppCompatActivity {
    private int dormitoryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configure_dormitory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView nameText = findViewById(R.id.numberPlain);
        TextView addressText = findViewById(R.id.addressTextPlain);
        Button saveButton = findViewById(R.id.saveRoomButton);
        DBHelper dbHelper = new DBHelper(this);



        if(getIntent().hasExtra("dormitory_id")){
            dormitoryId = getIntent().getIntExtra("dormitory_id",-1);
            String nameDorm = getIntent().getStringExtra("name");
            String addressDorm = getIntent().getStringExtra("address");
            nameText.setText(nameDorm);
            addressText.setText(addressDorm);
        }

        saveButton.setOnClickListener(evt -> {
            String name = nameText.getText().toString();
            String address = addressText.getText().toString();
            if(!name.isEmpty() && !address.isEmpty()){
                if(dormitoryId == -1){
                    long id = dbHelper.addDormitory(name, address);
                    Toast.makeText(this, "Dormitory added", Toast.LENGTH_SHORT).show();
                }else{
                    int affected = dbHelper.updateDormitory(dormitoryId, name, address);
                    Toast.makeText(this, "Dormitory updated", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Enter all data", Toast.LENGTH_SHORT).show();
            }
            finish();

        });
    }
}