package com.gmail.mateendev3.androiddbrcview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.mateendev3.androiddbrcview.adapter.StudentAdapter;
import com.gmail.mateendev3.androiddbrcview.database.DBHelper;
import com.gmail.mateendev3.androiddbrcview.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //declaring members
    private List<Student> mStudentList;
    private EditText etRollNo, etName;
    private Button btnInsertRecord, btnViewData;
    private FloatingActionButton btnSearchPerson;
    private DBHelper mHelper;
    private RecyclerView rvMain;
    private StudentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize members
        initMembers();

        //setting click listeners to button for db operations
        setListenersToButtons();

        //setting data to rv
        mStudentList = mHelper.getData();
        setRV();
    }

    /**
     * method to set listeners to buttons
     */
    private void setListenersToButtons() {
        btnInsertRecord.setOnClickListener(v -> {

            //condition to check edit texts have value or not
            if (etName.getText().toString().isEmpty() || etRollNo.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int rollNo = Integer.parseInt(etRollNo.getText().toString());
            String name = etName.getText().toString();

            if (mHelper.insertData(new Student(rollNo, name)))
                Toast.makeText(this, "Record inserted successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Record insertion failed", Toast.LENGTH_SHORT).show();

            //resetting editTexts
            etRollNo.setText("");
            etName.setText("");
        });
        btnViewData.setOnClickListener(v -> {
            mStudentList = mHelper.getData();
            setRVWithUpdate();
        });
        btnSearchPerson.setOnClickListener(v -> {
            if (etRollNo.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter roll no to find person in the db", Toast.LENGTH_SHORT).show();
                return;
            }

            int rollNo = Integer.parseInt(etRollNo.getText().toString());
            mStudentList = mHelper.searchPerson(rollNo);
            setRVWithUpdate();

            etRollNo.setText("");
            etName.setText("");
        });
    }

    private void setRVWithUpdate() {
        Toast.makeText(this, "Length of list: " + mStudentList.size(), Toast.LENGTH_SHORT).show();
        mAdapter.setStudentList(mStudentList);
        rvMain.setAdapter(mAdapter);
    }

    /**
     * method to set data and adapter to rv also item click listener
     */
    private void setRV() {
        mAdapter = new StudentAdapter(mStudentList);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(mAdapter);

        //setting click listener to rv item
        mAdapter.setOnLongItemClickListener((position, layout) -> {
            //getting student object at position
            Student student = mStudentList.get(position);

            mStudentList.remove(position);
            mAdapter.notifyItemRemoved(position);

            if (mHelper.deleteRecord(student))
                Toast.makeText(this, "Deletion Successful", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Deletion failed", Toast.LENGTH_SHORT).show();

            student = null;
        });
    }

    /**
     * method to init members
     */
    private void initMembers() {
        etRollNo = findViewById(R.id.et_roll_no);
        etName = findViewById(R.id.et_name);
        btnInsertRecord = findViewById(R.id.btn_insert_record);
        btnViewData = findViewById(R.id.btn_view_data);
        rvMain = findViewById(R.id.rv_main);
        btnSearchPerson = findViewById(R.id.fab);
        mHelper = new DBHelper(this);
    }
}