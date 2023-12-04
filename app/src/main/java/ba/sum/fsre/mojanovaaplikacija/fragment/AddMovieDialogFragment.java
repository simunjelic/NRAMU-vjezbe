package ba.sum.fsre.mojanovaaplikacija.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.sum.fsre.mojanovaaplikacija.MovieActivity;
import ba.sum.fsre.mojanovaaplikacija.R;
import ba.sum.fsre.mojanovaaplikacija.model.Movie;

public class AddMovieDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.movie_item_add,container,false);

        EditText addNameTxt = view.findViewById(R.id.addName);
        EditText addDirectorTxt = view.findViewById(R.id.addDirector);
        EditText addDateTxt = view.findViewById(R.id.addDate);
        Button addMovieBtn = view.findViewById(R.id.addMovieBtn);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("movies");

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("1");
                String name = addNameTxt.getText().toString();
                String director = addDirectorTxt.getText().toString();
                String date = addDateTxt.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(director) || TextUtils.isEmpty(date)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(getActivity(), "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("IN");
                    Movie newMovie = new Movie(name,
                            director,
                            date
                            , "blank") {

                    };

                    DatabaseReference newMovieRef = dbRef.push();
                    newMovieRef.setValue(newMovie);
                    Toast.makeText(getActivity(), "New movite tittle: " + newMovie.name + " added", Toast.LENGTH_SHORT).show();

                    // Clear the input fields
                    addNameTxt.setText("");
                    addDirectorTxt.setText("");
                    addDateTxt.setText("");
                    dismiss();


                }

            }
        });

        return view;
    }



}
