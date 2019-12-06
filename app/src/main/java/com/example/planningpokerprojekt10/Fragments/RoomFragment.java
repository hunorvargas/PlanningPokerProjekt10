package com.example.planningpokerprojekt10.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    EditText questionEditText,questionDescEditText,maxUserVoteNumberEditText;
    Button addnewQuestionButton;
    private View mView;
    private String sessionID="",questionID="";
    ArrayList<String> questionIDs = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.room_fragment,container,false);

        questionDescEditText=mView.findViewById(R.id.questionDescEditText);
        questionEditText=mView.findViewById(R.id.questionEditText);
        addnewQuestionButton=mView.findViewById(R.id.addnewQuestionButton);
        maxUserVoteNumberEditText=mView.findViewById(R.id.maxUserVoreNumberEditText);

        addnewQuestion();
        return mView;


    }
    private void addnewQuestion() {
        addnewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newQuestion=questionEditText.getText().toString().trim();
                String newQuestionDesc=questionDescEditText.getText().toString().trim();
                String maxVoteNum=maxUserVoteNumberEditText.getText().toString().trim();

                if(!newQuestion.isEmpty() && !newQuestionDesc.isEmpty() &&!maxVoteNum.isEmpty()){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();
                    Log.d("create5", "eddig jo: " + getQuestionID());
                   int questionID=parseInt(getQuestionID());
                    Log.d("create1", "Int QuestionID: " + questionID);
                    setQuestionID(String.valueOf(questionID+1));

                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getQuestionID()).child("Question").setValue(newQuestion);
                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(String.valueOf(getQuestionID())).child("QuestionDesc").setValue(newQuestionDesc);
                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(String.valueOf(getQuestionID())).child("QuestionVisibility").setValue("false");
                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(String.valueOf(getQuestionID())).child("QuestionTime").setValue(" ");
                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(String.valueOf(getQuestionID())).child("Results").child("MaxUserVoteNumber").setValue(maxVoteNum);

                    Log.d("create1", "NextQuestionID: " + getQuestionID());
                    setToastText("New Question added succes!");
                }
                else{
                    setToastText("Please complet all fields!");
                }

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");
        Log.d("create1", "Question ID");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionIDs.clear();
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
        Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
    }

}
