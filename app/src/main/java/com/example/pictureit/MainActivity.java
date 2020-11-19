package com.example.pictureit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        final Button signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                onStart();
            }
        });
        final Button createPrompt = findViewById(R.id.createPromptButton);
        createPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreatePrompt.class);
                startActivity(intent);
            }
        });
        final Button guessPrompt = findViewById(R.id.guessPromptButton);
        guessPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GuessPrompt.class);
                startActivity(intent);
            }
        });
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            TextView userName = findViewById(R.id.nameDisplay);
            userName.setText(currentUser.getDisplayName());
            TextView userPoints = findViewById(R.id.userPointsText);
            userPoints.setText("10000" + " Points");
            ImageView profilePic = findViewById(R.id.profilePic);
            Picasso.get().load(currentUser.getPhotoUrl()).into(profilePic);
            getPoints();

        }


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivity(intent);
        }

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
                    TextView userPoints = findViewById(R.id.userPointsText);
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