package com.example.planningpokerprojekt10.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.planningpokerprojekt10.Fragments.QuestionActivateFragment;
import com.example.planningpokerprojekt10.Fragments.RoomFragment;
import com.example.planningpokerprojekt10.Fragments.StaticsFragment;
import com.example.planningpokerprojekt10.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class RoomActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    private QuestionActivateFragment questionActivateFragment;
    private StaticsFragment staticsFragment;
    private RoomFragment roomFragment;
    private String sessionID="";

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
                        return true;
                    case R.id.activateQuestionIcon:
                        questionActivateFragment.setSessionID(getSessionID());
                        setFragment(questionActivateFragment);
                        return true;
                    case R.id.viewStaticsIcon:
                        setFragment(staticsFragment);
                        staticsFragment.setSessionID(sessionID);

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


    }



    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

}

