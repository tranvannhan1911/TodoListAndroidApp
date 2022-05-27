package com.example.a19526811_tranvannhan_androidexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.a19526811_tranvannhan_androidexam.R;
import com.example.a19526811_tranvannhan_androidexam.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final Context context;
    private List<Task> tasks;

    public RecyclerAdapter(Context context){
        this.context = context;
        tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.txtName.setText(task.getName());
        holder.txtMucDo.setText(task.getPriority());
        holder.txtDate.setText(""+task.getDate());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtMucDo;
        private TextView txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
            txtMucDo = itemView.findViewById(R.id.txt_mucdo);
            txtDate = itemView.findViewById(R.id.txt_ngay);
        }
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
}
