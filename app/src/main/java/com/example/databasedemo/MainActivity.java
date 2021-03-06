package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // in order to use database you should give a name to your database
    public static final String DATABASE_NAME = "myDatabase";
    SQLiteDatabase mDatabase;

    EditText editTextName,editTextSalary;
    Spinner spinnerDept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextName);
        editTextSalary = findViewById(R.id.edittextSalary);
        spinnerDept = findViewById(R.id.spinnerdept);

        findViewById(R.id.btnAddEmployee).setOnClickListener(this);
        findViewById(R.id.tvViewEmployee).setOnClickListener(this);

        //in order to open or create database we use the following code
        mDatabase = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        createTable();

    }

    private void createTable() {
        String sql = "CREATE TABLE IF  NOT EXISTS employees (" + "id INTEGER NOT NULL CONSTRAINT employee_pk PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(200) NOT NULL,"+ "department VARCHAR(200) NOT NULL,"+"joiningdate DATETIME NOT NULL," +
                "salary DOUBLE NOT NULL);";
        mDatabase.execSQL(sql);


    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAddEmployee:
                    addEmployee();
                    break;
                case R.id.tvViewEmployee:
                    //start
                    break;
            }

    }

    private void addEmployee() {
        String name = editTextName.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String dept = spinnerDept.getSelectedItem().toString();

        //using the calender object to get the current time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(calendar.getTime());

        if (name.isEmpty()){
            editTextName.setError("name field is mandatory");
            editTextName.requestFocus();
            return;
        }
        if (salary.isEmpty()){
            editTextSalary.setError("Salary field cannot be empty");
            editTextSalary.requestFocus();
            return;
        }

        String sql = "INSERT INTO employees(name,department,joiningdate,salary)" + "VALUES(?,?,?,?)";
        mDatabase.execSQL(sql,new String[]{name,dept,joiningDate,salary});
        Toast.makeText(this,"Employee Added",Toast.LENGTH_SHORT).show();
    }
}
