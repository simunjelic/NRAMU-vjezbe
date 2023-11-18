package ba.sum.fsre.mojanovaaplikacija;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;

import ba.sum.fsre.mojanovaaplikacija.model.User;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseUser loggedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseDatabase.getInstance();

        this.loggedUser = this.auth.getCurrentUser();
        DatabaseReference usersDatabaseRef = this.db.getReference();

        EditText firstnameTxt = findViewById(R.id.nameTxt);
        EditText lastnameTxt = findViewById(R.id.surnameTxt);
        EditText phoneTxt = findViewById(R.id.phoneTxt);
        EditText addressTxt = findViewById(R.id.addressTxt);
        EditText placeTxt = findViewById(R.id.placeTxt);
        EditText countryTxt = findViewById(R.id.countryTxt);

        Button saveBtn = findViewById(R.id.saveBtn);

        usersDatabaseRef.child("users").child(this.loggedUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    try {
                        User currentUser = task.getResult().getValue(User.class);
                        firstnameTxt.setText(currentUser.firstname);
                        lastnameTxt.setText(currentUser.lastname);
                        phoneTxt.setText(currentUser.phone);
                        addressTxt.setText(currentUser.address);
                        placeTxt.setText(currentUser.place);
                        countryTxt.setText(currentUser.country);
                    } catch (NullPointerException ex){
                        Log.e("No data ", ex.getMessage());
                    }

                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = new User(
                        firstnameTxt.getText().toString(),
                        lastnameTxt.getText().toString(),
                        phoneTxt.getText().toString(),
                        addressTxt.getText().toString(),
                        placeTxt.getText().toString(),
                        countryTxt.getText().toString()
                );
                usersDatabaseRef.child(loggedUser.getUid()).setValue(currentUser);
            }
        });


    }
}