package com.example.planningpokerprojekt10.Activitys;

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


public class CreateActivity extends AppCompatActivity {

    EditText editTexteditSessionID, editTextQuestion,editTextQuestionDesc,editMaxUserVoteNumber;
    Button creatSessionButton;
    private String sessionid="",questionID="1";
    private Admin admin;
    ArrayList<String> sessionIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        init(); //init values and compontents
        creatSession();

    }

    private void creatSession() {

        creatSessionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {  // on buttton click veryfi if all fields is completed,insert data in Firebase

                String question = editTextQuestion.getText().toString().trim();
                String sessionId = editTexteditSessionID.getText().toString().trim();
                String questionDescrpt=editTextQuestionDesc.getText().toString().trim();
                String maxUserVoteNumber=editMaxUserVoteNumber.getText().toString().trim();

                setSessionid(editTexteditSessionID.getText().toString().trim());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                if(!question.isEmpty() && !maxUserVoteNumber.isEmpty() ){

                    Log.d("create1", "nem kell empty");
                    if(isagoodSessionID()) {


                    myRef.child("Session").child("Groups").child(sessionId).child("Questions").child(questionID).child("Question").setValue(question);
                    myRef.child("Session").child("Groups").child(sessionId).child("Questions").child(questionID).child("QuestionDesc").setValue(questionDescrpt);
                    myRef.child("Session").child("Groups").child(sessionId).child("Questions").child(questionID).child("QuestionVisibility").setValue("false");
                    myRef.child("Session").child("Groups").child(sessionId).child("Questions").child(questionID).child("QuestionTime").setValue(" ");
                    myRef.child("Session").child("Groups").child(sessionId).child("Questions").child(questionID).child("Results").child("MaxUserVoteNumber").setValue(maxUserVoteNumber);
                    myRef.child("Session").child("Admins").child(admin.getAdminName()).child(sessionId).setValue(sessionId);

                    Log.d("create1", "nem kell data added");

                    setToastText("SessionCreated");
                    startActivity(new Intent(CreateActivity.this, MainActivity.class ));
                    }
                }
                else {
                    Log.d("create1", "nem kell else");

                    setToastText("Please complet all field!");

                }
            }


        });
    }

    private boolean isagoodSessionID() {  //check if the sessionID is not Busy
        Log.d("create", "kell isagoodsession");

        int i = 0;
        while (i < sessionIDs.size()) {
            Log.d("create", "Whiile ID"+sessionIDs.get(i));
            if(sessionIDs.get(i).equals(getSessionid())){

                setToastText("This SessionID is busy!");
                return false;
            }
            i++;
        }
        Log.d("create", "kell session nincs");
        return true;
    }


    private void init() {  //init

        editTexteditSessionID = findViewById(R.id.editSessionID);
        creatSessionButton =  findViewById(R.id.btnC);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextQuestionDesc=findViewById(R.id.questionDescripEditText);
        editMaxUserVoteNumber=findViewById(R.id.maxUserVoteNumber);
        admin = new Admin();

        Intent intent= getIntent();
        admin.setAdminName(intent.getStringExtra("AdminName"));
    }


    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    private void setToastText(String text){
        Toast.makeText(CreateActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {  // on Start Get from Firebase Session IDs
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference  myRef = database.getReference().child("Session").child("Groups");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sessionIDs.clear();
                Log.d("create1", "SessionIDSnap");
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String sessionID=datas.getKey();
                    sessionIDs.add(sessionID);
                    Log.d("create1", "SessionID: " + sessionID);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


