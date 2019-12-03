package com.example.planningpokerprojekt10.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokerprojekt10.Activitys.RoomActivity;
import com.example.planningpokerprojekt10.Objects.Question;
import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class QuestionActivateFragment extends Fragment {

    private  RecyclerView mrecyclerView;
    private View mView;
    private ArrayList<Question> questions = new ArrayList<>();
    private String SessionID,activeQuestionID,pickedTime,datePickedQuestionID;
    private int justOneSwitch=0;
    private Button activateQuestion;
    private Calendar myCalendar = Calendar.getInstance();


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

       mView= inflater.inflate(R.layout.questionactivate_fragment,container,false);

       mrecyclerView=mView.findViewById(R.id.questionList);
       activateQuestion = mView.findViewById(R.id.activateQuestionButton);

       mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       mrecyclerView.setAdapter(new RecyclerViewAdapter(questions));

        activateQuestion();


        return mView;
    }





    private void activateQuestion() {
        activateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setToastText("Please select Just One Visibility Switch!");
              //  Toast.makeText(getActivity(),"Please select Just One Visibility Switch!",Toast.LENGTH_LONG).show();
                if(justOneSwitch==1){


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getActiveQuestionID()).child("QuestionVisibility").setValue("true");

                }
                else
                if(justOneSwitch<=0){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getActiveQuestionID()).child("QuestionVisibility").setValue("false");
                    setToastText("Please select Just One Visibility Switch!");
                   // Toast.makeText(getActivity(),"Please select Just One Visibility Switch!",Toast.LENGTH_LONG).show();
                }
            }


        });
    }



    private class RecylerViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView questionTextView,questitonDescTextView,questionIDTextView,selectedDateTextView;
        private Button   selectDateButton;
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
            visibilitySwitch=itemView.findViewById(R.id.visibilitySwitch);
            selectDateButton=itemView.findViewById(R.id.datePickerButton);
            selectedDateTextView=itemView.findViewById(R.id.selectedDateTextView);
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
                holder.questionTextView.setText(questions.get(position).getQuestion());
                holder.questionIDTextView.setText(questions.get(position).getID());
                holder.questitonDescTextView.setText(questions.get(position).getQuestionDesc());
                holder.selectedDateTextView.setText(questions.get(position).getQuestionTime());
            if (questions.get(position).getQuestionVisibility().equals("false")) {

                holder.visibilitySwitch.setChecked(false);
            }
            else {
                holder.visibilitySwitch.setChecked(true);
                ++justOneSwitch;
            }
           holder.visibilitySwitch.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if(holder.visibilitySwitch.isChecked()){
                       ++justOneSwitch;
                       setActiveQuestionID(questions.get(position).getID());
                       setToastText("Question Activated");
                       //Toast.makeText(getActivity(),"Question Activated",Toast.LENGTH_SHORT).show();
                   }
                   else {
                       --justOneSwitch;

                     setActiveQuestionID(questions.get(position).getID());
                       setToastText("Question DezActivated");
                       //Toast.makeText(getActivity(),"Question DezActivated",Toast.LENGTH_SHORT).show();
                      // Log.d("create4", "QuestionID False: " + questions.get(position).getID() );
                   }
                  // Log.d("create4", "QuestionVisibility True: " + justOneSwitch );
               }
           });

            holder.selectDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setDatePickedQuestionID(questions.get(position).getID());
                    new DatePickerDialog(getActivity(), setdate, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

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

    DatePickerDialog.OnDateSetListener setdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yy"; //Change as you need
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String time=(sdf.format(myCalendar.getTime()));
            setPickedTime(time);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getDatePickedQuestionID()).child("QuestionTime").setValue(getPickedTime());
            setToastText("Your selected date: "+ time);
        }
    };

    public String getActiveQuestionID() {
        return activeQuestionID;
    }

    public void setActiveQuestionID(String activeQuestionID) {
        this.activeQuestionID = activeQuestionID;
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

    public String getPickedTime() {
        return pickedTime;
    }

    public void setPickedTime(String pickedTime) {
        this.pickedTime = pickedTime;
    }

    public String getDatePickedQuestionID() {
        return datePickedQuestionID;
    }

    public void setDatePickedQuestionID(String datePickedQuestionID) {
        this.datePickedQuestionID = datePickedQuestionID;
    }

    private void setToastText(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
}
