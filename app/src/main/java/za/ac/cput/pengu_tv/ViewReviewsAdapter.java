package za.ac.cput.pengu_tv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class ViewReviewsAdapter extends RecyclerView.Adapter<ViewReviewsAdapter.MyViewHolder> {
    private ArrayList txtUserId;
    private ArrayList txtDescription;
    private ArrayList txtRating;
    private String txtAnime;
    private Context context;
    private ImageView myImageIcon;
    DBHelper db;
    SQLiteDatabase sqLiteDatabase;

    public ViewReviewsAdapter(String txtAnime, ArrayList txtUserId, ArrayList txtDescription, ArrayList txtRating, Context context, ImageView imageView) {
        this.txtUserId = txtUserId;
        this.txtDescription = txtDescription;
        this.txtRating = txtRating;
        this.context = context;
        this.txtAnime = txtAnime;
        this.myImageIcon= imageView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.allreviews,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtUserId.setText(String.valueOf(txtUserId.get(position)));
        holder.txtDescription.setText(String.valueOf(txtDescription.get(position)));
        holder.txtRating.setText(String.valueOf(txtRating.get(position)));


    }

    @Override
    public int getItemCount() {
       return txtDescription.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserId, txtDescription, txtRating;
ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserId = itemView.findViewById(R.id.txtReviewListNameText);
            txtDescription = itemView.findViewById(R.id.txtReviewListGenreText);
            txtRating = itemView.findViewById(R.id.txtReviewListRatingText);
            imageView= itemView.findViewById(R.id.imgReviewIcon);

        }
    }
}
