package com.example.planningpokerprojekt10.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokerprojekt10.Objects.Question;
import com.example.planningpokerprojekt10.Objects.User;
import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class StaticsFragment extends Fragment {

    private  RecyclerView mrecyclerView;
    private View mView;
    private ArrayList<User> results  = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    private String sessionID,maxVoteNum,currentDateTime;
    private Date expireDate, currentDate;
    private boolean noDateTime=false;


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        getDatas();
        mView= inflater.inflate(R.layout.statics_fragment,container,false);
        mrecyclerView=mView.findViewById(R.id.staticsList);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return mView;
    }

    private void init() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy/HH:mm");
        String formattedDate = df.format(c.getTime());
        setCurrentDateTime(formattedDate);
        Log.d("create1", "Current Date: " +formattedDate);
    }


    private class RecylerViewHolder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView userNameTextView,userVoteTextView;

        public RecylerViewHolder(View itemView){
            super(itemView);
        }

        public RecylerViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.vote_view,container,false));

            mCardView=itemView.findViewById(R.id.mycardViewId2);
            userNameTextView=itemView.findViewById(R.id.userNameCRDView);
            userVoteTextView=itemView.findViewById(R.id.voteTextView);

        }


    }
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecylerViewHolder>{

        private List<User> myList;

        public RecyclerViewAdapter(List<User> list) {
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

            Log.d("create2", "Fragment ViewHolder: " );
            Log.d("create2", "Fragment While " + results.size()+ " " + position);
            holder.userNameTextView.setText(results.get(position).getUserName());
            holder.userVoteTextView.setText(results.get(position).getUserVote());

        }



        @Override
        public int getItemCount() {
            return results.size();
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


    public void getDatas(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions");
        Log.d("create2", "Question ID");

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questions.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    final Question question=new Question();
                    final String questionid=datas.getKey();
                    question.setID(questionid);

                    Log.d("create2", "QuestionIDSnap: " + questionid);
                    DatabaseReference  myRef2 = database.getReference().child("Session").child("Groups").child(getSessionID()).child("Questions").child(questionid);

                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Log.d("create2", "Questions");
                            for(DataSnapshot datas: dataSnapshot.getChildren()){
                                String questionChild=datas.getKey();
                                if(questionChild.equals("QuestionTime")){
                                    String questionTime=datas.getValue().toString();
                                    question.setQuestionTime(questionTime);
                                    Log.d("create2", "QuestionTime: " + questionTime);
                                }
                                if(questionChild.equals("Results")){
                                    Log.d("create2", "Child equal Results " + questionChild);
                                    DatabaseReference  myRef3 = database.getReference().child("Session").child("Groups").child(getSessionID())
                                            .child("Questions").child(questionid).child("Results");
                                    myRef3.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Log.d("create2", "Results");
                                            results.clear();
                                            for(DataSnapshot datas: dataSnapshot.getChildren()) {
                                                String userName = datas.getKey();
                                                String userVote;
                                                Log.d("create2", "Childs: " + userName);
                                                if(!userName.equals("MaxUserVoteNumber")){
                                                     userVote=datas.getValue().toString();
                                                    Log.d("create2", "Results: "+userName + " "+ userVote);
                                                     User user=new User(userName,userVote);
                                                    results.add(user);
                                                    Log.d("create2", "ResultsList: "+ results);
                                                }
                                                else {
                                                    if(userName.equals("MaxUserVoteNumber")){
                                                        Log.d("create2", "Child equal MaxUSerVote"+ userName);
                                                        Log.d("create2", "MaxUSerVote"+ datas.getValue().toString());
                                                        maxVoteNum=datas.getValue().toString();
                                                    }
                                                }
                                            }
                                            question.setUsers(results);
                                            questions.add(question);
                                            int maxvote=Integer.valueOf(maxVoteNum);
                                            Log.d("create2", "MaxVote: " + maxvote + " ResultSize:" + results.size());

                                            Log.d("create1", "showQuestionDesc");
                                            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/HH:mm");
                                            try {
                                                expireDate = formatter.parse(question.getQuestionTime());
                                                currentDate = formatter.parse(currentDateTime);
                                            } catch (ParseException e) {
                                                e.printStackTrace();

                                                Log.d("create1", e.toString());
                                            }
                                            try {
                                                expireDate.after(currentDate);
                                            }
                                            catch (NullPointerException e){
                                                noDateTime=true;
                                            }
                                            if(noDateTime && question.getUsers().size()==maxvote){
                                                    mrecyclerView.setAdapter(new RecyclerViewAdapter(question.getUsers()));
                                                    Log.d("create2", "CallRecycleviewNo date Time: " + question.getUsers());

                                            }
                                            else {
                                                Log.d("create2", "CallRecycleviewDateTIme: " + currentDate.after(expireDate));
                                                if(question.getUsers().size()== maxvote || currentDate.after(expireDate) ) {
                                                    mrecyclerView.setAdapter(new RecyclerViewAdapter(question.getUsers()));
                                                    Log.d("create2", "CallRecycleviewDateTIme: " + question.getUsers());
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                                Log.d("create2", "QuestionNr: " + questionChild);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
