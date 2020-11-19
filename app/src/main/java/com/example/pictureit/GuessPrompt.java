package com.example.pictureit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class GuessPrompt extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        setContentView(R.layout.activity_guess_prompt);
        getPoints();
        Button guessSubmit = findViewById(R.id.guessSubmitButton);
        guessSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = currentUser.getUid();

                TextView pointsViewer = findViewById(R.id.pointsView);
                int currentPoints = Integer.parseInt((String) pointsViewer.getText()) + 100;
                String points = Integer.toString(currentPoints);
                UserProfile user = new UserProfile(id, points);
                myRef.child(id).setValue(user);
            }
        });
    }
    public void getPoints(){
        final String id = currentUser.getUid();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        Query getUser = myRef.orderByChild("userId").equalTo(id);
        getUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    String points = dataSnapshot.child(id).child("points").getValue(String.class);
                    TextView userPoints = findViewById(R.id.pointsView);
                    userPoints.setText(points);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });
    }
}