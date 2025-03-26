package com.example.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    String task;

    Button add;

    EditText search;

    AlertDialog dialog;

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        search = findViewById(R.id.search);
        layout = findViewById(R.id.layout);

        buildDialog();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


        loadLists();


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTasks(s.toString());
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog,null);

        EditText write = view.findViewById(R.id.write);
        builder.setView(view);
        builder.setTitle("Enter Your Task")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task = write.getText().toString();
                        if (!task.isEmpty()) {
                            String filename = "task_" + System.currentTimeMillis() + ".txt";
                            try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
                                fos.write(task.getBytes());
                                addCard(task, filename);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })

            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); }
    });

    dialog = builder.create();
}


private void addCard(String name, String filename) {
    View view = getLayoutInflater().inflate(R.layout.card, null);


    TextView nameView = view.findViewById(R.id.name);
    Button delete = view.findViewById(R.id.delete);
    CheckBox checkBox = view.findViewById(R.id.checkbox);
    nameView.setText(name);
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layout.removeView(view);
            deleteTaskFile(filename);
            checkIfListIsEmpty();
        }
    });


    Button edit = view.findViewById(R.id.edit);
    edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View editView = getLayoutInflater().inflate(R.layout.dialog, null);
            EditText editWrite = editView.findViewById(R.id.write);
            editWrite.setText(name);
            builder.setView(editView);
            builder.setTitle("Edit Your Task")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String editedTask = editWrite.getText().toString();
                            if (!editedTask.isEmpty()) {
                                nameView.setText(editedTask);
                                try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
                                    fos.write(editedTask.getBytes());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    });



    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
        // Set or remove strike-through effect based on checkbox state
        if (isChecked) {
            nameView.setPaintFlags(nameView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            nameView.setPaintFlags(nameView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
});


        layout.addView(view);


// Clear the EditText after adding the card
EditText write = dialog.findViewById(R.id.write);
   if (write != null){
        write.setText("");
       }

checkIfListIsEmpty();
   }


private void checkIfListIsEmpty() {
    TextView nothing = findViewById(R.id.nothing);
    if (layout.getChildCount() == 0) {
// No items in the list, show the TextView
        nothing.setVisibility(View.VISIBLE);
    } else {
        // Items in the list, hide the TextView
        nothing.setVisibility(View.GONE);
    }
}


    private void loadLists() {
        String[] fileList = getApplicationContext().fileList();

        for (String filename : fileList) {
            if (filename.endsWith(".txt")) {
                try (FileInputStream fis = getApplicationContext().openFileInput(filename);
                     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(isr)) {

                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append('\n');
                    }
                    addCard(stringBuilder.toString(), filename);
                } catch (IOException e) {
                    Toast.makeText(this, "Error loading task: " + filename, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



private void deleteTaskFile(String filename) {
    File dir = getFilesDir();
    File file = new File(dir, filename);
    boolean isDeleted = file.delete();
    if (!isDeleted) {
        Toast.makeText( this, "Error deleting task file: " + filename, Toast.LENGTH_SHORT).show();
    }
}

// Method to filter tasks based on search query
private void filterTasks(String query) {
    for (int i = 0; i < layout.getChildCount(); i++) {
        View view = layout.getChildAt(i);
        TextView nameView = view.findViewById(R.id.name);
        if (nameView.getText().toString().contains(query)) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
}