package com.example.a19526811_tranvannhan_androidexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.a19526811_tranvannhan_androidexam.adapter.RecyclerAdapter;
import com.example.a19526811_tranvannhan_androidexam.entity.Task;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recycler = findViewById(R.id.recycler);
        RecyclerAdapter recyclerAdapter
                = new RecyclerAdapter(this);
        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        ImageButton imgBtnAdd = findViewById(R.id.btn_add);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Task> lst = new ArrayList<>();

                for(DataSnapshot child : snapshot.getChildren()){
                    Log.d("key", child.getKey());

                    lst.add(child.getValue(Task.class));
                }

                recyclerAdapter.setTasks(lst);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}