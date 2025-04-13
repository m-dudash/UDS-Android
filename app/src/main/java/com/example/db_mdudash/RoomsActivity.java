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
import com.example.db_mdudash.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private ListView listViewRooms;
    private RoomAdapter roomAdapter;
    private List<Room> rooms;
    private int dormId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rooms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        Button addButton = findViewById(R.id.addRoom);
        listViewRooms = findViewById(R.id.roomsList);

        rooms = new ArrayList<>();
        roomAdapter = new RoomAdapter(this, rooms);
        listViewRooms.setAdapter(roomAdapter);

        addButton.setOnClickListener(evt ->{
            Intent intt = new Intent(this, ConfigureRoomActivity.class);
            intt.putExtra("dormitory_id", dormId);
            startActivity(intt);
        });

        listViewRooms.setOnItemClickListener(((parent, view, position, id) -> {
            Room room = rooms.get(position);
            Intent intt = new Intent(this, RoomDetailActivity.class);
            intt.putExtra("room_id", room.getId());
            startActivity(intt);
        }));

        registerForContextMenu(listViewRooms);

        loadRooms();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRooms();
    }

    private void loadRooms(){
        rooms.clear();
        Cursor cursor = dbHelper.getAllRooms(dormId);
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_ID));
                int dormitoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_DORMITORY_ID));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_NUMBER));
                int beds = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_BEDS));
                int area = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_AREA));
                String equipment = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_EQUIPMENT));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_PRICE));
                int isOccupiedInt = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.Rooms.COLUMN_IS_OCCUPIED));
                boolean isOccupied = isOccupiedInt != 0;
                rooms.add(new Room(isOccupied,price,equipment,area,beds,number,dormitoryId,id));
            }while (cursor.moveToNext());
        }
        cursor.close();
        roomAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.roomsList){
            menu.add(0,0,0,"Edit");
            menu.add(0,1,1,"Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Room room = rooms.get(position);

        if(item.getItemId() == 0){
            Intent intent = new Intent(this, ConfigureRoomActivity.class);
            intent.putExtra("room_id", room.getId());
            intent.putExtra("dormitory_id", room.getDormitoryId());
            intent.putExtra("number", room.getNumber());
            intent.putExtra("beds", room.getBeds());
            intent.putExtra("area", room.getArea());
            intent.putExtra("equipment", room.getEquipment());
            intent.putExtra("price", room.getPrice());
            intent.putExtra("is_occupied", room.isOccupied());
            startActivity(intent);
        }else if(item.getItemId() == 1){
            Toast.makeText(this, "Room "+room.getNumber() + " removed", Toast.LENGTH_SHORT).show();
            dbHelper.deleteRoom(room.getId());
            loadRooms();
        }
        return true;
    }
}