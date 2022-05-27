package com.example.a19526811_tranvannhan_androidexam;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.a19526811_tranvannhan_androidexam.dao.TaskDAO;
import com.example.a19526811_tranvannhan_androidexam.database.SqlDatabase;
import com.example.a19526811_tranvannhan_androidexam.entity.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Dialog extends android.app.Dialog {
    private Task task;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText edtName;
    private RadioGroup radioGroup;
    private EditText edtDate;

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

        SqlDatabase sqlDatabase = Room.databaseBuilder(context, SqlDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        TaskDAO taskDAO = sqlDatabase.getTaskDAO();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tasks");


        edtName = findViewById(R.id.edt_name);
        radioGroup = findViewById(R.id.radioGroup);
        edtDate = findViewById(R.id.edt_ngay);
        Button btnAdd = findViewById(R.id.btn_add);



        btnAdd.setOnClickListener(v -> {
            if(!check())return;

            String name = edtName.getText().toString();
            String date = edtDate.getText().toString();
            if(date.equals("")){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = simpleDateFormat.format(new Date());
            }
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);

            Task task = new Task(myRef.push().getKey(), name, radioButton.getText().toString(), date);
            taskDAO.insert(task);
            ((HomeActivity)context).loadData();
            Toast.makeText(context, "Thêm công việc thành công", Toast.LENGTH_SHORT).show();
            dismiss();
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