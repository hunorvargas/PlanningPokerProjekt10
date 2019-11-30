package com.example.planningpokerprojekt10.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static java.lang.Integer.parseInt;

public class RoomActivity extends AppCompatActivity {


    EditText questionEditText,questionDescEditText;
    Button addnewQuestionButton;
    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    private QuestionActivateFragment questionActivateFragment;
    private StaticsFragment staticsFragment;
    private RoomFragment roomFragment;
    String sessionID="",questionID="";
    ArrayList<String> questionIDs = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        init();

    }



    private void init() {

        frameLayout=findViewById(R.id.framelayout);
        navigationView=findViewById(R.id.navigationbottom);

        addnewQuestionButton=findViewById(R.id.addnewQuestionButton);

        questionActivateFragment=new QuestionActivateFragment();
        staticsFragment=new StaticsFragment();
        roomFragment=new RoomFragment();

        Intent intent= getIntent();

        setSessionID(intent.getStringExtra("SessionID"));
        setFragment(roomFragment);
        navigationViewlistener();
        addnewQuestion();
        question=new Question();
    }

    private void addnewQuestion() {
        addnewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuestion=roomFragment.getQuestion();
                String newQuestionDesc=roomFragment.getQuestionDesc();
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

    private void navigationViewlistener() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch ((menuItem.getItemId())){
                    case R.id.roomIcon:
                        setFragment(roomFragment);
                        addnewQuestionButton.setVisibility(View.VISIBLE);
                        addnewQuestionButton.setText("Add Question");
                        return true;
                    case R.id.activateQuestionIcon:
                        setFragment(questionActivateFragment);
                        addnewQuestionButton.setVisibility(View.VISIBLE);
                        questionActivateFragment.setQuestions(questions);
                        questionActivateFragment.setSessionID(sessionID);
                        addnewQuestionButton.setText("Activate Question");
                        return true;
                    case R.id.viewStaticsIcon:
                        setFragment(staticsFragment);

                        addnewQuestionButton.setVisibility(View.GONE);
                        return true;
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
                    String questionID=datas.getKey();
                    //questions.add(questionID);
                    question.setID(questionID);

                    Log.d("create1", "QuestionNr: " + questionID);
                    DatabaseReference  myRef2 = database.getReference().child("Session").child("Groups").
                            child(getSessionID()).child("Questions").child(questionID).child("Question");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String question1 = dataSnapshot.getValue(String.class);
                            question.setQuestion(question1);

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
                            question.setQuestionDesc(question1);

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
                            question.setQuestionVisibility(question1);
                            questions.add(question);


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


}

