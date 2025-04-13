package com.example.db_mdudash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.db_mdudash.model.Dormitory;

import java.util.ArrayList;
import java.util.List;

public class DormitoriesActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView listViewDorm;
    private DormitoryAdapter dormitoryAdapter;
    private List<Dormitory> dormitories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dormitories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dbHelper = new DBHelper(this);
        Button addButton = findViewById(R.id.addDorm);
        listViewDorm = findViewById(R.id.dormitoryList);

        dormitories = new ArrayList<>();
        dormitoryAdapter = new DormitoryAdapter(this,dormitories);
        listViewDorm.setAdapter(dormitoryAdapter);

        addButton.setOnClickListener(evt ->{
            Intent intt = new Intent(this, ConfigureDormitoryActivity.class);
            startActivity(intt);
        });


        listViewDorm.setOnItemClickListener((parent, view, position, id) -> {
            Dormitory dormitory = dormitories.get(position);
            Intent intt = new Intent(this, RoomsActivity.class);
            intt.putExtra("dormitory_id", dormitory.getId());
            startActivity(intt);
        });

        registerForContextMenu(listViewDorm);
        //Загрузка даных из БД
        loadDormitories();
    }

    protected void onResume(){
        super.onResume();
        loadDormitories();
    }

    private void loadDormitories(){
        dormitories.clear();
        Cursor cursor = dbHelper.getAllDormitories();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Dormitories.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Dormitories.COLUMN_NAME));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Dormitories.COLUMN_ADDRESS));
                dormitories.add(new Dormitory(id, name, address));
            }while (cursor.moveToNext());
        }
        cursor.close();
        dormitoryAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.dormitoryList){
            menu.add(0,0,0,"Edit");
            menu.add(0,1,1,"Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Dormitory dormitory = dormitories.get(position);

        if(item.getItemId() == 0){
            Intent intt = new Intent(this, ConfigureDormitoryActivity.class);
            intt.putExtra("dormitory_id", dormitory.getId());
            intt.putExtra("name", dormitory.getName());
            intt.putExtra("address", dormitory.getAddress());
            startActivity(intt);
        }else if(item.getItemId() == 1){
            Toast.makeText(this, "Dormitory " + dormitory.getName() + " removed", Toast.LENGTH_SHORT).show();
            dbHelper.deleteDormitory(dormitory.getId());
            loadDormitories();

        }
        return true;
    }
}