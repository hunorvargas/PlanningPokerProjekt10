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

import static java.lang.Integer.parseInt;

public class RoomActivity extends AppCompatActivity {


    EditText questionEditText,questionDescEditText;
    Button addnewQuestionButton;
    String sessionID="",questionID="";
    ArrayList<String> questionIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init();
        addnewQuestion();

    }

    private void addnewQuestion() {
        addnewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuestion=questionEditText.getText().toString().trim();
                String newQuestionDesc=questionDescEditText.getText().toString().trim();
                if(!newQuestion.isEmpty() && !newQuestionDesc.isEmpty()){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    int questionID=parseInt(getQuestionID());
                    Log.d("create1", "Int QuestionID: " + questionID);
                    setQuestionID(String.valueOf(questionID+1));

                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getQuestionID())
                    .child("Question").setValue(newQuestion);
                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(String.valueOf(getQuestionID()))
                           .child("QuestionDesc").setValue(newQuestionDesc);
                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(String.valueOf(getQuestionID()))
                            .child("QuestionVisibility").setValue("false");

                    Log.d("create1", "NextQuestionID: " + getQuestionID());
                   setToastText("New Question added succes!");
                }
                else{
                    setToastText("Please complet all fields!");
                }

            }
        });
    }


    private void init() {

        questionEditText=findViewById(R.id.questionEditText);
        questionDescEditText=findViewById(R.id.questionDescEditText);
        addnewQuestionButton=findViewById(R.id.addnewQuestionButton);

        Intent intent= getIntent();

        setSessionID(intent.getStringExtra("SessionID"));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");
        Log.d("create1", "Question ID");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datas: dataSnapshot.getChildren()){

                    String questionid=datas.getKey();
                    questionIDs.add(questionid);
                    setQuestionID(questionid);
                    Log.d("create1", "QuestionIDSnap: " + questionid);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    private void setToastText(String text){
        Toast.makeText(RoomActivity.this, text, Toast.LENGTH_LONG).show();
    }
}

