package com.example.todo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.TaskSelected {

    private ListView listView;
    private CustomAdapter adapter;
    private ArrayList<Task> taskList;
    TextView taskTitle, taskDescription, addInstruction;
    FragmentManager fragManager;
    TaskListFrag taskListFrag;
    TaskDetailFrag taskDetailFrag;
    FrameLayout layout;
    View viewTaskDetailFrag;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragManager = getSupportFragmentManager();
        taskListFrag = (TaskListFrag) fragManager.findFragmentById(R.id.task_list_frag);
        taskDetailFrag = (TaskDetailFrag) fragManager.findFragmentById(R.id.task_detail_frag);
        viewTaskDetailFrag = taskDetailFrag.getView();

        taskTitle = viewTaskDetailFrag.findViewById(R.id.task_title);
        taskDescription = viewTaskDetailFrag.findViewById(R.id.task_description);
        addInstruction = findViewById(R.id.addIns); // Instruction TextView
        layout = findViewById(R.id.main);

        if (layout != null) {
            fragManager.beginTransaction().show(taskListFrag).hide(taskDetailFrag).commit();
        } else {
            fragManager.beginTransaction().show(taskListFrag).show(taskDetailFrag).commit();
        }

        taskTitle.setVisibility(View.GONE);
        taskDescription.setVisibility(View.GONE);

        listView = taskListFrag.getView().findViewById(R.id.task_list);
        taskList = new ArrayList<>();

        // Initially, if the list is empty, show the instruction
        if (taskList.isEmpty()) {
            addInstruction.setVisibility(View.VISIBLE);
        } else {
            addInstruction.setVisibility(View.GONE);
        }

        adapter = new CustomAdapter(this, R.layout.task_row, taskList);
        listView.setAdapter(adapter);

        addBtn = findViewById(R.id.fab_add_task);
        addBtn.setOnClickListener(v -> {
            AddTaskDialogFragment dialog = new AddTaskDialogFragment(new AddTaskDialogFragment.AddTaskListener() {
                @Override
                public void onTaskAdded(String title, String description) {
                    taskList.add(new Task(title, description));
                    adapter.notifyDataSetChanged();
                    if (!taskList.isEmpty()) {
                        addInstruction.setVisibility(View.GONE);
                    }

                    Toast.makeText(MainActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show(getSupportFragmentManager(), "AddTaskDialog");
        });
    }

    @Override
    public void onTaskSelected(int position) {
        Toast.makeText(this, "Task: " + position + " selected.", Toast.LENGTH_SHORT).show();

        if (layout != null) {
            fragManager.beginTransaction().hide(taskListFrag).show(taskDetailFrag).addToBackStack(null).commit();
        }

        taskTitle.setVisibility(View.VISIBLE);
        taskDescription.setVisibility(View.VISIBLE);

        Task item = taskList.get(position);
        taskTitle.setText(item.getTitle());
        taskDescription.setText(item.getDescription());
    }
}
