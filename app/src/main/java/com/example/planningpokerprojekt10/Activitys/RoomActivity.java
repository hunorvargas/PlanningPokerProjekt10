package com.example.planningpokerprojekt10.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.planningpokerprojekt10.Fragments.QuestionActivateFragment;
import com.example.planningpokerprojekt10.Fragments.RoomFragment;
import com.example.planningpokerprojekt10.Fragments.StaticsFragment;
import com.example.planningpokerprojekt10.Objects.Question;
import com.example.planningpokerprojekt10.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    private QuestionActivateFragment questionActivateFragment;
    private StaticsFragment staticsFragment;
    private RoomFragment roomFragment;
    private String sessionID="",questionID="",currentFragment="";
    ArrayList<String> questionIDs = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init();

    }



    private void init() {

        frameLayout=findViewById(R.id.framelayout);
        navigationView=findViewById(R.id.navigationbottom);

        questionActivateFragment=new QuestionActivateFragment();
        staticsFragment=new StaticsFragment();
        roomFragment=new RoomFragment();

        Intent intent= getIntent();

        setSessionID(intent.getStringExtra("SessionID"));

            setFragment(roomFragment);
            roomFragment.setSessionID(getSessionID());
            roomFragment.setQuestionID(getQuestionID());
            setCurrentFragment("roomFragment");

            roomFragment.setSessionID(getSessionID());
            roomFragment.setQuestionID(getQuestionID());
            setCurrentFragment("roomFragment");
            setFragment(roomFragment);

        navigationViewlistener();


    }



    private void navigationViewlistener() {

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch ((menuItem.getItemId())){
                    case R.id.roomIcon:
                        setFragment(roomFragment);
                        roomFragment.setQuestionID(questionID);
                        setCurrentFragment("roomFragment");
                        return true;
                    case R.id.activateQuestionIcon:
                        setFragment(questionActivateFragment);
                        questionActivateFragment.setQuestions(questions);
                        questionActivateFragment.setSessionID(sessionID);
                        setCurrentFragment("questionActivateFragment");
                        return true;
                    case R.id.viewStaticsIcon:
                        setFragment(staticsFragment);
                        setCurrentFragment("staticsFragment");
                        return true;
                    case R.id.refreshIcon:
                                  Intent intent = getIntent();
                                  finish();
                                  startActivity(intent);
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragments) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragments);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");
        Log.d("create1", "Question ID");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
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



        DatabaseReference  myRef = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("create1", "Questions");
                for(DataSnapshot datas: dataSnapshot.getChildren()){


                    final Question newQuestion = new Question();

                    String questionID=datas.getKey();
                    newQuestion.setID(questionID);

                    Log.d("create1", "QuestionNr: " + questionID);
                    DatabaseReference  myRef2 = database.getReference().child("Session").child("Groups").
                            child(getSessionID()).child("Questions").child(questionID).child("Question");

                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String question1 = dataSnapshot.getValue(String.class);
                            newQuestion.setQuestion(question1);

                            Log.d("create1", "Question: " + question1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference  myRef3 = database.getReference().child("Session").child("Groups").
                            child(getSessionID()).child("Questions").child(questionID).child("QuestionDesc");

                    myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String question1 = dataSnapshot.getValue(String.class);
                            newQuestion.setQuestionDesc(question1);

                            Log.d("create1", "QuestionDesc: " + question1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference  myRef4 = database.getReference().child("Session").child("Groups").
                            child(getSessionID()).child("Questions").child(questionID).child("QuestionVisibility");

                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String question1 = dataSnapshot.getValue(String.class);
                            Log.d("create1", "QuestionVisibility: " + question1);
                            newQuestion.setQuestionVisibility(question1);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference  myRef5 = database.getReference().child("Session").child("Groups").
                            child(getSessionID()).child("Questions").child(questionID).child("QuestionTime");

                    myRef5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String questiontime = dataSnapshot.getValue(String.class);
                            Log.d("create1", "QuestionTime: " + questiontime);
                            newQuestion.setQuestionTime(questiontime);
                            Log.d("create1", "Question: " + questiontime + " " + newQuestion);
                            questions.add(newQuestion);
                            Log.d("create1", "Question: " + questiontime + " " + questions.toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

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

    public String getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }
}

