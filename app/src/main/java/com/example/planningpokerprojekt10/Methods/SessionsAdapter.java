package com.example.planningpokerprojekt10.Methods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planningpokerprojekt10.R;

import java.util.List;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {
    @NonNull
    @Override
    public SessionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View sessionView = inflater.inflate(R.layout.session_container, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(sessionView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(SessionsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Session session = mSessions.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.sessionNameTxt;
        textView.setText(session.getSessionID());
        Button button = viewHolder.showButton;
        button.setText("Show");
    }


    @Override
    public int getItemCount() {
        return mSessions.size();
    }

    private List<Session> mSessions;
    public SessionsAdapter(List<Session> Sessions){
        mSessions=Sessions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView sessionNameTxt;
        public Button showButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            sessionNameTxt = (TextView) itemView.findViewById(R.id.session_name);
            showButton = (Button) itemView.findViewById(R.id.show_button);
        }
    }
}
