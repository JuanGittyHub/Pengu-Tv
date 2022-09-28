package za.ac.cput.pengu_tv;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class DescriptionDialog extends AppCompatDialogFragment {
    private DescriptionDialog.DescriptionDialogListener listener;
    private ImageView descriptionIcon;
    ArrayList<String> txtUserId,txtDescription,txtRating;
            String txtAnimeId;
    private ImageView imageView;
    ViewReviewsAdapter viewReviewsAdapter;
    RecyclerView recyclerView;
    DBHelper db;
    private Button btnView,btnAdd;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =  getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.review_popup,null);

        txtAnimeId = getActivity().getIntent().getStringExtra("passAnimeId");
        Toast.makeText(getContext(), txtAnimeId, Toast.LENGTH_SHORT).show();
        builder.setView(view).setTitle("Reviews").setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        txtUserId= new ArrayList<>();
        txtRating= new ArrayList<>();
        txtDescription= new ArrayList<>();
        txtRating= new ArrayList<>();
        recyclerView = getActivity().findViewById(R.id.recyclerViewDescription);
        viewReviewsAdapter = new ViewReviewsAdapter(txtAnimeId,txtUserId,txtDescription,txtRating,getContext(),imageView);
        descriptionIcon = view.findViewById(R.id.imgReviewIcon);

        recyclerView.setAdapter(viewReviewsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

      //  displayData();
        return builder.create();
    }

    private void displayData() {
        Cursor cursor = db.getAllReviewsById(txtAnimeId);
        if (cursor.getCount()==0){
            Toast.makeText(getContext(), "There is no reviews for this anime yet!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (cursor.moveToNext()){
                txtUserId.add(cursor.getString(1));
                txtDescription.add(cursor.getString(4));
                txtRating.add(cursor.getString(5));
            }

        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DescriptionDialogListener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement DescriptionDialogListener");
        }
    }

    public interface DescriptionDialogListener{
        void applyDescriptionTexts(String title, String genre, String Description, String rating, ImageView icon);

    }
}
