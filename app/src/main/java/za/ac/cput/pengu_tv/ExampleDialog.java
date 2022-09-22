package za.ac.cput.pengu_tv;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.android.application.R;

import java.util.Arrays;
import java.util.List;

import za.ac.cput.pengu_tv.util.DBHelper;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText edtTitle, edtDescription, edtRating;
    DBHelper db;
    Spinner spGenre;
    String genre;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        final List<String> genres= Arrays.asList("   Select Genre","Action","Shonen","School","Comedy","Sports","Martial Arts");

       // spGenre= view.findViewById(R.id.animeGenre);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(),R.layout.my_selected_item,genres);

        //spGenre.setAdapter(adapter);
        //adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        builder.setView(view).setTitle("Submit Request")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        edtTitle = (EditText) view.findViewById(R.id.animeTitle);
        edtDescription = (EditText) view.findViewById(R.id.animeDescription);
        edtRating = (EditText) view.findViewById(R.id.animeRating);
        return builder.create();
    }
}
