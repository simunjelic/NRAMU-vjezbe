package ba.sum.fsre.mojanovaaplikacija;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fsre.mojanovaaplikacija.adapter.MovieAdapter;
import ba.sum.fsre.mojanovaaplikacija.fragment.AddMovieDialogFragment;
import ba.sum.fsre.mojanovaaplikacija.model.Movie;

public class MovieActivity extends AppCompatActivity {

    RecyclerView movieRecyclerView;
    MovieAdapter adapter;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        FloatingActionButton openMovieDialogBtn = findViewById(R.id.showMovieDialogBtn);

        openMovieDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMovieDialogFragment fragment = new AddMovieDialogFragment();
                fragment.show(getSupportFragmentManager(),"movieDialogFragment");
            }
        });

        this.movieRecyclerView = findViewById(R.id.movieListView);

        this.movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Movie> options = new FirebaseRecyclerOptions.Builder<Movie>().setQuery(
                this.mDatabase.getReference("movies"),
                Movie.class
        ).build();
        this.adapter = new MovieAdapter(options);
        this.movieRecyclerView.setAdapter(this.adapter);


    }




    @Override
    protected void onStart() {
        super.onStart();
        this.adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.adapter.stopListening();
    }
}