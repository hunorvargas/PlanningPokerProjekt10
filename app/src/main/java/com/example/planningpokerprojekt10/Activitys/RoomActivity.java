package com.example.planningpokerprojekt10.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planningpokerprojekt10.Methods.Question;
import com.example.planningpokerprojekt10.R;
import com.example.planningpokerprojekt10.Methods.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomActivity extends AppCompatActivity {


    TextView questiontextView,questionDescTextView;
    Button voteButton1,voteButton2,voteButton3,voteButton4,voteButton5,novoteButton;
    private User newUser;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init();
        addUserDatabase();
        addVote();
    }

    private void addVote() {
        Log.d("create", "adduser");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.d("create", "nem kell Onclick");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long currentSessionID=dataSnapshot.getChildrenCount();
               // newUser.setSessionId(Long.toString(currentSessionID));
                Log.d("create", "kell:"+newUser.getSessionId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("create", "kell error");
            }
        });

        vote();


        Log.d("create", "nem kell data added");
        //startActivity(new Intent(CreateActivity.this, RoomActivity.class ));

    }

    private void vote() {

        voteButton1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue("1");
                Toast.makeText(RoomActivity.this, "User Voted 1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomActivity.this, MainActivity.class ));
            }
        });
        voteButton2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue("2");
                Toast.makeText(RoomActivity.this, "User Voted 2", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomActivity.this, MainActivity.class ));
            }
        });
        voteButton3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue("3");
                Toast.makeText(RoomActivity.this, "User Voted 3", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomActivity.this, MainActivity.class ));
            }
        });
        voteButton4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue("4");
                Toast.makeText(RoomActivity.this, "User Voted 4", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomActivity.this, MainActivity.class ));
            }
        });
        voteButton5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue("5");
                Toast.makeText(RoomActivity.this, "User Voted 5", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomActivity.this, MainActivity.class ));
            }
        });

    }

    private void addUserDatabase() {

        Log.d("create", "adduser");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.d("create", "nem kell Onclick");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    long currentSessionID=dataSnapshot.getChildrenCount();
                    Log.d("create", "kell curent:"+newUser.getSessionId());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("create", "kell error");
                }
            });

            myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue(" ");
            novoteed();

            Log.d("create", "nem kell data added");
    }

    private void novoteed() {
        novoteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("session").child(newUser.getSessionId()).child("Users").child(newUser.getUserName()).setValue("No Voted");
                Toast.makeText(RoomActivity.this, "User No Voted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomActivity.this, MainActivity.class ));
            }
        });
    }


    private void init() {

        questiontextView=findViewById(R.id.textViewQuestion);
        questionDescTextView=findViewById(R.id.questionDescpTextView);
        voteButton1 = findViewById(R.id.buttonVote1);
        voteButton2 = findViewById(R.id.buttonVote2);
        voteButton3 = findViewById(R.id.buttonVote3);
        voteButton4 = findViewById(R.id.buttonVote4);
        voteButton5 = findViewById(R.id.buttonVote5);
        novoteButton=findViewById(R.id.novoteButton);

        newUser=new User();
        question=new Question();
        Intent intent= getIntent();

        newUser.setUserName(intent.getStringExtra("Username"));
        Log.d("create", "kell Room:"+newUser.getUserName());
        newUser.setSessionId(intent.getStringExtra("SessionId"));
        Log.d("create", "kell Room:"+newUser.getSessionId());

        showQuestion();
        showQuestionDescrp();
    }

    private void showQuestionDescrp() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("session").child(newUser.getSessionId()).child("Questions").child("QuestionDesc");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                question.setQuestionDesc(dataSnapshot.getValue().toString());
                questionDescTextView.setText(question.getQuestionDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showQuestion() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("session").child(newUser.getSessionId()).child("Questions").child("Question");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                question.setQuestion(dataSnapshot.getValue().toString());
                questiontextView.setText(question.getQuestion());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
