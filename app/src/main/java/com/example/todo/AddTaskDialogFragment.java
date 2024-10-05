package com.example.todo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskDialogFragment extends DialogFragment {

    private EditText taskTitleInput, taskDescriptionInput;
    private Button addButton, cancelButton;

    public interface AddTaskListener {
        void onTaskAdded(String title, String description);
    }

    private AddTaskListener listener;

    public AddTaskDialogFragment(AddTaskListener listener) {
        this.listener = listener;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Set dialog size to match parent (full screen width)
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_task, container, false);

        taskTitleInput = view.findViewById(R.id.task_title_input);
        taskDescriptionInput = view.findViewById(R.id.task_description_input);
        addButton = view.findViewById(R.id.add_task_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        addButton.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString();
            String description = taskDescriptionInput.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill out both fields", Toast.LENGTH_SHORT).show();
            } else {
                listener.onTaskAdded(title, description);
                dismiss();
            }
        });
        cancelButton.setOnClickListener(v -> dismiss());

        return view;
    }
}

