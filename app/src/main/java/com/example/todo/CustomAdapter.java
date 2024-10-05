package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Task> {

    private Context context;
    private List<Task> taskList;
    private int resource;

    public interface TaskSelected{
        public void onTaskSelected(int position);
    }
    TaskSelected currTask;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.taskList = objects;
        currTask = (TaskSelected) context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource , parent , false);
        }
        Task task = getItem(position);
        TextView taskTitle = convertView.findViewById(R.id.task_title);
        ImageButton deleteBtn = convertView.findViewById(R.id.delete_icon);
        taskTitle.setText(task.getTitle());
        convertView.setOnClickListener(v -> {
            if (currTask != null) {
                currTask.onTaskSelected(position);
            }
        });
        deleteBtn.setOnClickListener(v -> {
            taskList.remove(task);
            notifyDataSetChanged();
        });
        return convertView;
    }
}