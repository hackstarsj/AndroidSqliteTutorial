package com.supercoders.androidsqlitetutorial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView datalist;
    TextView datalist_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper=new DatabaseHelper(MainActivity.this);
        Button delete=findViewById(R.id.delete_data);
        Button insert=findViewById(R.id.insert_data);
        Button update=findViewById(R.id.update_data);
        Button read=findViewById(R.id.refresh_data);
        datalist=findViewById(R.id.all_data_list);
        datalist_count=findViewById(R.id.data_list_count);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               refreshData();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInputDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateIdDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showDeleteDialog();
            }
        });

    }

    private void refreshData() {
        datalist_count.setText("ALL DATA COUNT : "+databaseHelper.getTotalCount());

        List<StudentModel> studentModelList=databaseHelper.getAllStudents();
        datalist.setText("");
        for(StudentModel studentModel:studentModelList){
            datalist.append("ID : "+studentModel.getId()+" | Name : "+studentModel.getName()+" | Email : "+studentModel.getEmail()+" | DOB : "+studentModel.getDob()+ " | PHONE : "+studentModel.getPhone()+" \n\n");
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteStudent(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();

            }
        });


    }

    private void showUpdateIdDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_id_dialog,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataDialog(id_input.getText().toString());
                alertDialog.dismiss();
                refreshData();
            }
        });

    }

    private void showDataDialog(final String id) {
        StudentModel studentModel=databaseHelper.getStudent(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText email=view.findViewById(R.id.email);
        final EditText phone=view.findViewById(R.id.phone);
        final EditText dob=view.findViewById(R.id.dob);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        name.setText(studentModel.getName());
        email.setText(studentModel.getEmail());
        phone.setText(studentModel.getPhone());
        dob.setText(studentModel.getDob());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentModel studentModel=new StudentModel();
                studentModel.setName(name.getText().toString());
                studentModel.setId(id);
                studentModel.setEmail(email.getText().toString());
                studentModel.setPhone(phone.getText().toString());
                studentModel.setDob(dob.getText().toString());
                databaseHelper.updateStudent(studentModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }

    private void ShowInputDialog() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert_dialog,null);
        final EditText name=view.findViewById(R.id.name);
        final EditText email=view.findViewById(R.id.email);
        final EditText phone=view.findViewById(R.id.phone);
        final EditText dob=view.findViewById(R.id.dob);
        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);

        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentModel studentModel=new StudentModel();
                studentModel.setName(name.getText().toString());
                studentModel.setEmail(email.getText().toString());
                studentModel.setPhone(phone.getText().toString());
                studentModel.setDob(dob.getText().toString());
                Date date=new Date();
                studentModel.setCreated_at(""+date.getTime());
                databaseHelper.AddStudnet(studentModel);
                alertDialog.dismiss();
                refreshData();
            }
        });
    }
}