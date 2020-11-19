package com.example.pictureit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            TextView userName = findViewById(R.id.nameDisplay);
            userName.setText(currentUser.getDisplayName());
            TextView userPoints = findViewById(R.id.userPointsText);
            userPoints.setText("10000" + " Points");
            ImageView profilePic = findViewById(R.id.profilePic);
            Picasso.get().load(currentUser.getPhotoUrl()).into(profilePic);

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
}