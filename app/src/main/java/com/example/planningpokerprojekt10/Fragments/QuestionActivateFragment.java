package com.example.planningpokerprojekt10.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokerprojekt10.Objects.Question;
import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */

public class QuestionActivateFragment extends Fragment {

    private  RecyclerView mrecyclerView;
    private View mView;
    private ArrayList<Question> questions = new ArrayList<>();
    ArrayList<String> questionIDs = new ArrayList<>();
    private String SessionID,activeQuestionID,pickedTime,pickedDate,datePickedQuestionID;
    private int justOneSwitch=0;
    private boolean isModification=false,isActiveQuestion=false;
    private Button activateQuestion;
    private Calendar myCalendar = Calendar.getInstance();


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView= inflater.inflate(R.layout.questionactivate_fragment,container,false);

       mrecyclerView=mView.findViewById(R.id.questionList);
       activateQuestion = mView.findViewById(R.id.activateQuestionButton);

       mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       getDatas(); // call Get datas Function from firebase
        return mView;
    }

    private void activateQuestion() {  // Activate Question Function on Button click
        activateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("create5", "IsModificaton: " +isModification );
                if(isModification) {
                    Log.d("create5", "IsModificaton Justone: " + justOneSwitch );
                    if (justOneSwitch == 1 && isActiveQuestion) {


                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getActiveQuestionID()).child("QuestionVisibility").setValue("true");

                    }
                    else
                        Log.d("create5", "IsModificaton Justone: " + justOneSwitch );
                        if (justOneSwitch <= 0 && !isActiveQuestion) {
                            justOneSwitch=0;
                            //add data to firebase
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").child(getActiveQuestionID()).child("QuestionVisibility").setValue("false");

                        }
                        else {
                            setToastText("Please select Just One Visibility Switch!");
                        }
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
            super(inflater.inflate(R.layout.question_view,container,false));

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
        //Display data in recycler view and set active question variables
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
                isActiveQuestion=true;
                if(justOneSwitch==0)
                ++justOneSwitch;
                else
                Log.d("create5", "JustOneSwitch: " +justOneSwitch );
            }
           holder.visibilitySwitch.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    isModification=true;
                   if(holder.visibilitySwitch.isChecked()){
                       ++justOneSwitch;
                       isActiveQuestion=true;
                       Log.d("create5", "JustOneSwitchIscheked: " +justOneSwitch );
                       setActiveQuestionID(questions.get(position).getID());
                       setToastText("Question Activated");
                   }
                   else {
                       --justOneSwitch;
                       isActiveQuestion=false;
                       Log.d("create5", "JustOneSwitch: " +justOneSwitch );
                     setActiveQuestionID(questions.get(position).getID());
                       setToastText("Question DezActivated");
                   }
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
    //My DatePicker

    DatePickerDialog.OnDateSetListener setdate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yy"; //Change as you need
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String date=(sdf.format(myCalendar.getTime()));

            setTime();
            setPickedDate(date);
            setToastText("Your selected date: "+ date);

        }
    };
    public void setTime(){ // my Time Picker
    Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.d("create5", "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String timetext = DateFormat.format("HH:mm", calendar1).toString();
                setPickedTime(timetext);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.child("Session").child("Groups").child(getSessionID()).child("Questions").
                        child(getDatePickedQuestionID()).child("QuestionTime").setValue(getPickedDate()+ "/" +getPickedTime());
                setToastText("Your selected time is: "+timetext );

            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }

    public void getDatas(){
        //get Datas from Firebase QuestionIDs,QUestion,Question Visibility
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");
        Log.d("create1", "Question ID");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionIDs.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){

                    String questionid=datas.getKey();
                    questionIDs.add(questionid);
                   // setQuestionID(questionid);
                    Log.d("create1", "QuestionIDSnap: " + questionid);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        DatabaseReference  myRef = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questions.clear();
                Log.d("create1", "Questions");
                for(DataSnapshot datas: dataSnapshot.getChildren()){


                    final Question newQuestion = new Question();

                    String questionID=datas.getKey();
                    newQuestion.setID(questionID);

                    Log.d("create1", "QuestionNr: " + questionID);
                    DatabaseReference  myRef2 = database.getReference().child("Session").child("Groups").
                            child(getSessionID()).child("Questions").child(questionID).child("Question");

                    myRef2.addValueEventListener(new ValueEventListener() {
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

                    myRef3.addValueEventListener(new ValueEventListener() {
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

                    myRef4.addValueEventListener(new ValueEventListener() {
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

                    myRef5.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String questiontime = dataSnapshot.getValue(String.class);
                            Log.d("create1", "QuestionTime: " + questiontime);
                            newQuestion.setQuestionTime(questiontime);
                            Log.d("create1", "Question: " + questiontime + " " + newQuestion);
                            questions.add(newQuestion);
                            Log.d("create1", "Question: " + questiontime + " " + questions.toString());
                            mrecyclerView.setAdapter(new RecyclerViewAdapter(questions));
                            activateQuestion();
                            // newRecycler View call activae Question Button;
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

    public String getActiveQuestionID() {
        return activeQuestionID;
    }

    public void setActiveQuestionID(String activeQuestionID) {
        this.activeQuestionID = activeQuestionID;
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

    public String getPickedDate() {
        return pickedDate;
    }

    public void setPickedDate(String pickedDate) {
        this.pickedDate = pickedDate;
    }
}
