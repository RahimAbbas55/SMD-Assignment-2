package com.example.todo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskListFrag extends Fragment implements CustomAdapter.TaskSelected {

    private ListView listView;
    private CustomAdapter adapter;
    private ArrayList<Task> taskList;

    public TaskListFrag() {
        // Required empty public constructor
    }

    public static TaskListFrag newInstance(String param1, String param2) {
        TaskListFrag fragment = new TaskListFrag();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize any necessary components here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment's layout
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // Initialize the ListView
//        listView = view.findViewById(R.id.task_list);
//
//        // Create a list of tasks
//        taskList = new ArrayList<>();
//        taskList.add(new Task("Buy groceries", "Milk, Eggs, Bread"));
//        taskList.add(new Task("Walk the dog", "Evening walk in the park"));
//        taskList.add(new Task("Finish homework", "Math and Science assignments"));
//
//        // Initialize the adapter with 'this' as the TaskSelected listener
//        adapter = new CustomAdapter(getContext(), R.layout.task_row, taskList);
//
//        // Set the adapter to the ListView
//        listView.setAdapter(adapter);
//    }

    @Override
    public void onTaskSelected(int position) {
        // Handle task selection within the fragment or communicate with the activity
        Toast.makeText(getContext(), "Task: " + position + " selected.", Toast.LENGTH_SHORT).show();

        // Optionally, communicate with MainActivity if needed
        if (getActivity() instanceof CustomAdapter.TaskSelected) {
            ((CustomAdapter.TaskSelected) getActivity()).onTaskSelected(position);
        }
    }
}
