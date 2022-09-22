package za.ac.cput.pengu_tv;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.android.application.R;

import java.util.Arrays;
import java.util.List;

import za.ac.cput.pengu_tv.util.DBHelper;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText edtTitle, edtDescription, edtRating;
    private ExampleDialogListener listener;
    DBHelper db;
    Spinner spGenre;
    String genre;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        final List<String> genres= Arrays.asList("   Select Genre","Action","Shonen","School","Comedy","Sports","Martial Arts");

        spGenre= view.findViewById(R.id.animeGenre);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(),R.layout.my_selected_item,genres);

        spGenre.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spGenre.setAdapter(adapter);

        builder.setView(view).setTitle("Submit Request")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        genre=spGenre.getSelectedItem().toString();
                        String animeTitle= edtTitle.getText().toString();
                        String animeDescription= edtDescription.getText().toString();
                        String animeRating= edtRating.getText().toString();
                        if (genre.equals("   Select Genre")|| animeTitle.isEmpty()||animeDescription.isEmpty()||animeRating.isEmpty()){
                            Toast.makeText(view.getContext(), "There are empty fields!", Toast.LENGTH_SHORT).show();
                        }else {
                            listener.applyTexts(animeTitle, animeDescription, genre, Double.valueOf(animeRating));
                        }
                    }
                });
        edtTitle = (EditText) view.findViewById(R.id.animeTitle);
        edtDescription = (EditText) view.findViewById(R.id.animeDescription);
        edtRating = (EditText) view.findViewById(R.id.animeRating);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener= (ExampleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement ExampleDialogListener");

        }


    }

    public interface ExampleDialogListener{
        void applyTexts(String animeTitle, String animeDescription, String animeGenre, Double animeRating);
    }
}
