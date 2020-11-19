package com.example.pictureit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;


public class CreatePrompt extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prompt);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Button savePhoto = findViewById(R.id.savePhotoButton);
        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("images");
//                UserHelpClass helpClass = new Us
                myRef.setValue(currentUser.getPhotoUrl());
            }
        });
    }
}