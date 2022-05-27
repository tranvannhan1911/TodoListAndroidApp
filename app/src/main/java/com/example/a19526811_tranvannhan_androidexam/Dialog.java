package com.example.a19526811_tranvannhan_androidexam;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.a19526811_tranvannhan_androidexam.entity.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dialog extends android.app.Dialog {
    private Task task;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText edtName;

    public Dialog(@NonNull Context context) {
        super(context);
        this.context = context;
        show();
    }

    public Dialog(@NonNull Context context, Task task) {
        super(context);
        this.context = context;
        this.task = task;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tasks");

        System.out.println(myRef);

        edtName = findViewById(R.id.edt_name);
        Button btnAdd = findViewById(R.id.btn_add);

        if(task != null){
            edtName.setText(task.getName());

        }


        btnAdd.setOnClickListener(v -> {
            if(!check())return;
        });


    }

    private boolean check(){

        String name = edtName.getText().toString();

        if(name.equals("")){
            Toast.makeText(context, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}