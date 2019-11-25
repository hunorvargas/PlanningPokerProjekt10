package com.example.planningpokerprojekt10.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    EditText questionEditText,questionDescEditText;

    private View mView;
    String sessionID="",questionID="";
    ArrayList<String> questionIDs = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.room_fragment,container,false);

        questionDescEditText=mView.findViewById(R.id.questionDescEditText);
        questionEditText=mView.findViewById(R.id.questionEditText);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public String getQuestion(){
        String question = questionEditText.getText().toString().trim();
        return question;
    }
    public String getQuestionDesc(){
        String questionDesc = questionDescEditText.getText().toString().trim();
        return questionDesc;
    }

    @Override
    public void onStart() {
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
        Toast.makeText(getActivity(),"Text!",Toast.LENGTH_LONG).show();
    }

}
