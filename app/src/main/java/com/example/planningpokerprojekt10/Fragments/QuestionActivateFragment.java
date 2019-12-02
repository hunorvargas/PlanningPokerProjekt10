package com.example.planningpokerprojekt10.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokerprojekt10.Objects.Question;
import com.example.planningpokerprojekt10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class QuestionActivateFragment extends Fragment {

    private  RecyclerView mrecyclerView;
    private View mView;
    private ArrayList<Question> questions = new ArrayList<>();
    private String SessionID;



    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

       mView= inflater.inflate(R.layout.questionactivate_fragment,container,false);

       mrecyclerView=mView.findViewById(R.id.questionList);
       mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       mrecyclerView.setAdapter(new RecyclerViewAdapter(questions));
        return mView;
    }
    private class RecylerViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView questionTextView,questitonDescTextView,sessionIDTextView,questionIDTextView;
        private EditText questitonVisibility;
        private EditText questionDateEditTExt;
        private Switch   visibilitySwitch;

        public RecylerViewHolder(View itemView){
            super(itemView);
        }

        public RecylerViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.querstion_view,container,false));

            mCardView=itemView.findViewById(R.id.cardViewid);
            questionTextView=itemView.findViewById(R.id.textViewQuestion);
            questitonDescTextView=itemView.findViewById(R.id.questionDescTextView);
            questionIDTextView=itemView.findViewById(R.id.questionIDTextView);
            sessionIDTextView=itemView.findViewById(R.id.SessionIDTextView);
            questionDateEditTExt=itemView.findViewById(R.id.dateEditText);
            visibilitySwitch=itemView.findViewById(R.id.visibilitySwitch);


        }


    }
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecylerViewHolder>{

        private List<Question> myList;

        public RecyclerViewAdapter(List<Question> list) {
            this.myList=list;
        }

        @NonNull
        @Override
        public RecylerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            return new RecylerViewHolder(inflater,parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final RecylerViewHolder holder, final int position) {
            Log.d("create1", "Fragment ViewHolder: " );
                Log.d("create1", "Fragment While " + questions.size()+ " " + position);
                holder.sessionIDTextView.setText(getSessionID());
                holder.questionTextView.setText(questions.get(position).getQuestion());
                holder.questionIDTextView.setText(questions.get(position).getID());
                holder.questitonDescTextView.setText(questions.get(position).getQuestionDesc());
            if (questions.get(position).getQuestionVisibility().equals("false")) {

                holder.visibilitySwitch.setChecked(false);
            }
            else {
                holder.visibilitySwitch.setChecked(true);
            }
           holder.visibilitySwitch.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Log.d("crate4","holderQuestionID" + questions.get(position).getID());
                   Log.d("create4", "QuestionVisibility True: " );
               }
           });
            }


        @Override
        public int getItemCount() {
            return questions.size();
        }
    }
    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }
}
