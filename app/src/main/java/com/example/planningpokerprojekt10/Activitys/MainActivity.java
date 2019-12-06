package com.example.planningpokerprojekt10.Activitys;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planningpokerprojekt10.Objects.Admin;
import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intentCreate,intentRoom;
    private Button creatSessionButton, staticsSessionButton,createAdminButton;
    ArrayList<String> admins = new ArrayList<>();
    private String sessionid="";
    ArrayList<Admin> adminss = new ArrayList<>();

    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        intentCreate = new Intent(MainActivity.this, CreateActivity.class);
        intentRoom= new Intent(MainActivity.this, RoomActivity.class);


        creatSessionButton = (Button) findViewById(R.id.buttonCreate);
        creatSessionButton.setOnClickListener(this);


        staticsSessionButton = (Button) findViewById(R.id.buttonStatics);
        staticsSessionButton.setOnClickListener(this);

        createAdminButton= (Button) findViewById(R.id.buttonCreateAdmin);
        createAdminButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v == createAdminButton) {

            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            View view = getLayoutInflater().inflate(R.layout.createadmin_dialog, null);

            final EditText adminname =view.findViewById(R.id.createAdminNameEditText);
            Button cancelButton = view.findViewById(R.id.dialogCancelButton);
            Button createAdminButton2 = view.findViewById(R.id.createAdminButton);

            alert.setView(view);

            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            createAdminButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String adminName = adminname.getText().toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference();

                    if(!adminName.isEmpty()){
                        Log.d("create1", "isempty");

                                if(isagoodadminname(adminName)){
                                    setToastText("Welcome " + adminName +"!");
                                    myRef.child("Session").child("Admins").child(adminName).setValue(adminName);
                                    finish();
                                    startActivity(getIntent());
                                }
                                else
                                    setToastText("Admin Name is Busy!");
                    } else {
                        setToastText("Admin Name is Empty!");
                    }alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

       if (v == creatSessionButton) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            View view = getLayoutInflater().inflate(R.layout.loginadmin_dialog, null);

            final EditText adminname =view.findViewById(R.id.loginAdminNameEditText);
            Button cancelButton = view.findViewById(R.id.adminLoginCancelButton);
            Button loginAdminButton = view.findViewById(R.id.loginadminButton);

            alert.setView(view);

            final AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            loginAdminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String adminName = adminname.getText().toString().trim();
                    if(!adminName.isEmpty()){
                        Log.d("create1", "nameisempy");

                        if(!isagoodadminname(adminName)){
                            Log.d("create1", "joadmin");
                            intentCreate.putExtra("AdminName",adminName);
                            startActivity(intentCreate);
                        }else{
                            setToastText("This Admin Name not exsist!");
                        }

                    } else {
                       setToastText("Admin Name is Empty!");
                    }
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();

        }
       if(v==staticsSessionButton){

           final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
           View view = getLayoutInflater().inflate(R.layout.session_dialog, null);

           final EditText sessionAdminName =view.findViewById(R.id.sessionAdminNameEditText);
           final EditText sessionIDedittxt =view.findViewById(R.id.sessionSessionIdEditText);
           Button sessionCancelButton = view.findViewById(R.id.sessionCancelButton);
           Button sessionLoginAdminButton = view.findViewById(R.id.sessionAdminLoginButton);

           alert.setView(view);

           final AlertDialog alertDialog = alert.create();
           alertDialog.setCanceledOnTouchOutside(false);

           sessionCancelButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   alertDialog.dismiss();
               }
           });

           sessionLoginAdminButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   final String adminName = sessionAdminName.getText().toString().trim();
                   final String sessionID = sessionIDedittxt.getText().toString().trim();
                   setSessionid(sessionID);

                   if(!adminName.isEmpty() &&!sessionID.isEmpty()){
                       Log.d("create1", "nameisempty");

                       if(isagooddata(adminName,sessionID)){
                           Log.d("create1", "joadmin");
                           intentRoom.putExtra("AdminName",adminName);
                           intentRoom.putExtra("SessionID",sessionID);
                           startActivity(intentRoom);
                       }else{
                           setToastText("This Admin Name or Session not exist!");
                       }

                   } else {
                       setToastText("Admin Name or SesisonID is Empty!");
                   }
                   alertDialog.dismiss();
               }
           });
           alertDialog.show();

       }
    }

    private boolean isagooddata(String adminName, String sessionID) {
        Log.d("create3", "isagoodata");
        int i = 0;
        int j = 0;
        Log.d("create3", "adminss size: " +adminss.size());
        while (i < adminss.size()) {

            Admin admin;

            ArrayList<String> adminIDs;

            admin=adminss.get(i);
            Log.d("create3", "Whiile admin: " +i + " " +admin.toString());
            if(admin.getAdminName().equals(adminName)){

                adminIDs=adminss.get(i).getGroupIDs();
                Log.d("create3", "adminIDs size: "+adminIDs);

                while (j < adminIDs.size()){
                    Log.d("create3", "Whiile j ID: "+adminIDs.get(j));
                    if(adminIDs.get(j).equals(sessionID)){
                        return true;
                    }
                    j++;
                }
            }

            i++;
        }
        Log.d("create3", "kell admin nincs");
        return false;
    }

    private boolean isagoodadminname(String adminName) {
        int i = 0;
        while (i < admins.size()) {
            Log.d("create", "Whiile ID"+admins.get(i));
            if(admins.get(i).equals(adminName)){
                return false;
            }
            i++;
        }
        Log.d("create", "kell session nincs");
        return true;

    }

    private void setToastText(String text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Session").child("Admins");
        Log.d("create2", "isagoodadminname");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String adminName = " ";
                adminss.clear();
                Log.d("create1", "Adminname Snap");
                for(DataSnapshot datas: dataSnapshot.getChildren()){

                    ArrayList<String> adminsID = new ArrayList<>();
                    for (DataSnapshot datas2: datas.getChildren()){
                        String adminIDG=datas2.getKey();
                        adminsID.add(adminIDG);
                        Log.d("create2", "Adminname: " + adminIDG);
                    }
                    adminName=datas.getKey();
                    Log.d("create2", "AdminnameSnapID: " + adminName);
                    Log.d("create2", "AdminnameSnapID: " + adminsID);

                    Admin newAdmin = new Admin (adminName,adminsID);

                    Log.d("create2", "NewAdminName: " + newAdmin.getAdminName());
                    Log.d("create2", "NewAdminIDs: " + newAdmin.getGroupIDs());
                    adminss.add(newAdmin);
                    admins.add(adminName);


                 //   Log.d("create2", "AdminnameSnap: " + adminss.get(getI()).toString()+" "+ getI());
                  //  adminsID.clear();
                    setI(++i);
                }





            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

}
