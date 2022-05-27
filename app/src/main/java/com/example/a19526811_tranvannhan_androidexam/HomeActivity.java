package com.example.a19526811_tranvannhan_androidexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a19526811_tranvannhan_androidexam.adapter.RecyclerAdapter;
import com.example.a19526811_tranvannhan_androidexam.dao.TaskDAO;
import com.example.a19526811_tranvannhan_androidexam.database.SqlDatabase;
import com.example.a19526811_tranvannhan_androidexam.entity.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private TaskDAO taskDAO;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recycler = findViewById(R.id.recycler);
        recyclerAdapter
                = new RecyclerAdapter(this);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        SqlDatabase sqlDatabase = Room.databaseBuilder(this, SqlDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        taskDAO = sqlDatabase.getTaskDAO();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books");

        ImageButton imgBtnAdd = findViewById(R.id.btn_add);
        imgBtnAdd.setOnClickListener(v -> {
            Dialog dialog = new Dialog(HomeActivity.this);
        });

        loadData();
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<Task> lst = new ArrayList<>();
//
//                for(DataSnapshot child : snapshot.getChildren()){
//                    Log.d("key", child.getKey());
//
//                    lst.add(child.getValue(Task.class));
//                }
//
//                recyclerAdapter.setTasks(lst);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        ImageButton imgBtnSync = findViewById(R.id.btn_sync);
        imgBtnSync.setOnClickListener(v -> {
            List<Task> tasks = taskDAO.findAll();
            tasks.forEach(task -> {
                myRef.child(task.getId()).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        Log.e("Sync", "success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "Đồng bộ thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            });

            Toast.makeText(HomeActivity.this, "Đồng bộ thành công", Toast.LENGTH_SHORT).show();
        });

        Intent intent = new Intent(this, SyncDatabaseService.class);
        startService(intent);
    }

    public void loadData(){
        List<Task> tasks = taskDAO.findAll();
        recyclerAdapter.setTasks(tasks);
    }
}