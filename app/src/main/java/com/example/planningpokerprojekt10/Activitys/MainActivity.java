package com.example.planningpokerprojekt10.Activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planningpokerprojekt10.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intentCreate, intentJoin, intentHistory;
    private Button btnCreate, btnJoin, btnHistory;
    Button DialogSave, Show;
    TextView Myname;
    Dialog ThisDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentCreate = new Intent(MainActivity.this, CreateActivity.class);
        intentJoin = new Intent(MainActivity.this, JoinActivity.class);
        intentHistory=new Intent(MainActivity.this, HistoryActivity.class);


        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(this);

        btnHistory=(Button) findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if(v == btnCreate){

            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            View view =getLayoutInflater().inflate(R.layout.custom_dialog,null);

            final EditText adminpassword=(EditText)view.findViewById(R.id.adminPasswordEditText);
            Button cancelButton=view.findViewById(R.id.dialogCancelButton);
            Button loginaAdminButton=view.findViewById(R.id.dialogLoginButton);

            alert.setView(view);

            final AlertDialog alertDialog=alert.create();
            alertDialog.setCanceledOnTouchOutside(false);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            loginaAdminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String adminpass=adminpassword.getText().toString();
                    if(adminpass.equals("a")){
                        startActivity(intentCreate);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Wrong AdminPassword!", Toast.LENGTH_LONG).show();
                    }

                }
            });
            alertDialog.show();
        }

        if (v == btnJoin){

            startActivity(intentJoin);

        }

        if (v == btnHistory){

            startActivity(intentHistory);

        }


    }
}