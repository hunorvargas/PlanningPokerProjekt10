package com.example.planningpokerprojekt10.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CreateActivity extends AppCompatActivity {

    EditText editTexteditSessionID, editTextQuestion,editTextQuestionDesc,editTextAdminName;
    Button creatSessionButton;
    long maxID=0;
    private String sessionid="",questionID="1";
    ArrayList<String> sessionIDs = new ArrayList<>();
     ArrayList<String> admins = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        init();

        creatSession();

        Log.d("create", "nem kell main");

    }

    private void creatSession() {

        creatSessionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString().trim();
                String sessionId = editTexteditSessionID.getText().toString().trim();
                String questionDescrpt=editTextQuestionDesc.getText().toString().trim();
                setSessionid(editTexteditSessionID.getText().toString().trim());
                Log.d("create1", question + " " + sessionId);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                Log.d("create1", "nem kell Onclick");

                if(!question.isEmpty()){

                    Log.d("create1", "nem kell empty");
                    if(isagoodSessionID()) {

                    Log.d("create1", "questionID:");
                   // myRef.child("Questions").child("valami");
                    myRef.child("session").child(sessionId).child("Questions").child(questionID).child("Question").setValue(question);
                    myRef.child("session").child(sessionId).child("Questions").child(questionID).child("QuestionDesc").setValue(questionDescrpt);

                    Log.d("create1", "nem kell data added");
                    Toast.makeText(CreateActivity.this, "SessionCreated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateActivity.this, MainActivity.class ));
                }
                }
                else {
                    Log.d("create1", "nem kell else");
                    Toast.makeText(CreateActivity.this, "Complete the Question field!", Toast.LENGTH_SHORT).show();

                }
            }


        });
    }

    private boolean isagoodSessionID() {
        Log.d("create", "kell isagoodsession");

        int i = 0;
        while (i < sessionIDs.size()) {
            Log.d("create", "Whiile ID"+sessionIDs.get(i));
            if(sessionIDs.get(i).equals(getSessionid())){
                Toast.makeText(CreateActivity.this,"This SessionID is busy!", Toast.LENGTH_LONG).show();
                return false;
            }
            i++;
        }
       // Toast.makeText(CreateActivity.this,"This SessionID is busy!", Toast.LENGTH_LONG).show();
        Log.d("create", "kell session nincs");
        return true;
    }


    private void init() {

        editTexteditSessionID = findViewById(R.id.editSessionID);
        creatSessionButton =  findViewById(R.id.btnC);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextQuestionDesc=findViewById(R.id.questionDescripEditText);
        getsessionids();
    }

    public long getMaxID() {
        return maxID;
    }

    public void setMaxID(long maxID) {
        this.maxID = maxID;
    }

    public void getsessionids(){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference  myRef = database.getReference().child("session").child("Admins");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("create1", "Adminname Snap");
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String adminname=datas.getKey();
                    admins.add(adminname);
                    Log.d("create1", "Adminname: " + adminname);
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


