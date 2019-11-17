package com.example.planningpokerprojekt10.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planningpokerprojekt10.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class JoinActivity extends AppCompatActivity {
    EditText editUsername,editSessID;
    Button btnJoin;
    TextView errorText;
    private String sessionid="";
    private String usernamesesion="";
    final ArrayList<String> sessionIDs = new ArrayList<>();
    final ArrayList<String> Users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        init();
        joinSession();
    }

    private void joinSession() {

        btnJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(JoinActivity.this,RoomActivity.class);
                errorText.setText(" ");
                intent.putExtra("Username",editUsername.getText().toString().trim());
                intent.putExtra("SessionId",editSessID.getText().toString().trim());
                setSessionid(editSessID.getText().toString().trim());
                setUsernamesesion(editUsername.getText().toString().trim());

                getsessionUsernames();

                Log.d("create", "kell join:"+editSessID.getText().toString().trim());

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(isCompletdata()){
                            startActivity(intent);
                        }
                        else {

                           // Toast.makeText(JoinActivity.this,"Error!", Toast.LENGTH_LONG).show();
                            Log.d("create", "Hiba:");
                            //Ide kell egy WrongUsername or SessionID toast
                        }
                    }
                }, 2000);
            }
        });
    }

    private void init() {
        editUsername = findViewById(R.id.editUsername);
        editSessID =  findViewById(R.id.editSessID);
        btnJoin = findViewById(R.id.btnJoin);
        errorText=findViewById(R.id.errorTextView);
        getsessionids();

    }

   private boolean isCompletdata(){
       if (editUsername.getText().toString().isEmpty() && editSessID.getText().toString().isEmpty()){

          // Toast.makeText(JoinActivity.this,"Missing UserName or SessionID!", Toast.LENGTH_SHORT).show();
            errorText.setText("Missing UserName or SessionID!");

       }
       else {
           if(isagoodSessionID() && isagoodusername()) {
               Log.d("create", "kell sessionID jo");
                   return true;
               }
           }
       return  false;
   }

    private boolean isagoodusername() {
        Log.d("create", "kell isagoodusername");
        Log.d("create", "Users Size: " + Users.size());
        int i = 0;
        while (i < Users.size()) {
            Log.d("create", "Whiile Username: "+Users.get(i));
            if(Users.get(i).equals(getUsernamesesion())){
                Log.d("create", "kell username egyenlo");
                errorText.setText("This UserName is busy!");
                return false;

            }
            i++;
        }

        Log.d("create", "jo az username");
        return true;
    }

    private boolean isagoodSessionID() {

        Log.d("create", "kell isagoodsession");

        int i = 0;
        while (i < sessionIDs.size()) {
            Log.d("create", "Whiile ID"+sessionIDs.get(i));
            if(sessionIDs.get(i).equals(getSessionid())){
                Log.d("create", "kell session egyenlo");
                return true;
            }
            i++;
        }
        errorText.setText("This Session is not available!");
        //Toast.makeText(JoinActivity.this,"This Session is not available!", Toast.LENGTH_LONG).show();
        Log.d("create", "kell session nincs");
        return false;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUsernamesesion() {
        return usernamesesion;
    }

    public void setUsernamesesion(String usernamesesion) {
        this.usernamesesion = usernamesesion;
    }

    public void getsessionids(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference  myRef = database.getReference();
        final CountDownLatch done = new CountDownLatch(1);
        myRef.addChildEventListener((new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Get the node from the datasnapshot
                String myParentNode = dataSnapshot.getKey();
                for (DataSnapshot child: dataSnapshot.getChildren())
                {
                    String key = child.getKey().toString();
                    sessionIDs.add(key);
                    Log.d("create", "ID:"+ key);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }));
    }

    public void getsessionUsernames(){
        Log.d("create", "Users");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Log.d("create", "Users ID:"+getSessionid());
        DatabaseReference  myRef = database.getReference().child("session").child(getSessionid()).child("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("create", "UsersName Snap");
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String classnames=datas.getKey();
                    Users.add(classnames);
                    Log.d("create", "UsersName: " + classnames);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}


