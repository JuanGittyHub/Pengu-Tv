package za.ac.cput.pengu_tv;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.application.R;

import za.ac.cput.pengu_tv.util.DBHelper;

public class ReviewsDialog extends AppCompatDialogFragment {
    private EditText editReviewDescription, edtReviewRating;
    private ReviewDialogListener listener;
    String getUser,getAnime;
    DBHelper db;
SQLiteDatabase sqLiteDatabase;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.review_dialog,null);
        getAnime= getActivity().getIntent().getStringExtra("passName");
        getUser= getActivity().getIntent().getStringExtra("passUsername");


       builder.setView(view).setTitle("Submit Review For "+getAnime).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int i) {

           }
       }).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int i) {
               String review= editReviewDescription.getText().toString();
               String rating= edtReviewRating.getText().toString();
               String getUser1 = getUser;
               String getAnime1= getAnime;
               if (review.isEmpty() || rating.isEmpty()){
                   Toast.makeText(view.getContext(), "There are empty fields", Toast.LENGTH_SHORT).show();
               }else
                   listener.applyReviewTexts(review,Double.valueOf(rating),getUser1,getAnime1);
           }
       });
       editReviewDescription= (EditText) view.findViewById(R.id.reviewDescription);
       edtReviewRating= (EditText) view.findViewById(R.id.reviewRating);
       return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener= (ReviewDialogListener) context;

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement ReviewDialogListener");
        }
    }

    public interface ReviewDialogListener{
        void applyReviewTexts(String reviewDescription, Double rating, String username,String animeName);
    }
}
