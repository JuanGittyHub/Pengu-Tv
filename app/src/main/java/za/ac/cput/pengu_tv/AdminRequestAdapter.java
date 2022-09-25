package za.ac.cput.pengu_tv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class AdminRequestAdapter extends RecyclerView.Adapter<AdminRequestAdapter.MyViewHolder>{
DBHelper db;
SQLiteDatabase sqLiteDatabase;
    AlertDialog.Builder builder;
    private Context context;
    private ArrayList animeTitle_id, animeDescription_id, animeOngoing_id, animeEpisode_id,animeGenre_id,animeRating_id,username_id;


    public AdminRequestAdapter(Context context, ArrayList animeTitle_id, ArrayList animeDescription_id, ArrayList animeOngoing_id, ArrayList animeEpisode_id, ArrayList animeGenre_id, ArrayList animeRating_id, ArrayList username_id) {

        this.context = context;
        this.animeTitle_id = animeTitle_id;
        this.animeDescription_id = animeDescription_id;
        this.animeOngoing_id = animeOngoing_id;
        this.animeEpisode_id = animeEpisode_id;
        this.animeGenre_id = animeGenre_id;
        this.animeRating_id = animeRating_id;
        this.username_id = username_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.requestentries,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        builder = new AlertDialog.Builder(context);
    holder.animeTitle_id.setText(String.valueOf(animeTitle_id.get(position)));
    holder.animeDescription_id.setText(String.valueOf(animeDescription_id.get(position)));
    holder.animeOngoing_id.setText(String.valueOf(animeOngoing_id.get(position)));
    holder.animeEpisode_id.setText(String.valueOf(animeEpisode_id.get(position)));
    holder.animeGenre_id.setText(String.valueOf(animeGenre_id.get(position)));
    holder.animeRating_id.setText(String.valueOf(animeRating_id.get(position)));
    holder.username_id.setText(String.valueOf(username_id.get(position)));


holder.itemView.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        builder.setTitle("User Request!");
        builder.setMessage(holder.username_id.getText()+" has made a request for "+holder.animeTitle_id.getText()+". Would you like to accept this request?");
        builder.setCancelable(true);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db= new DBHelper(context.getApplicationContext());
                sqLiteDatabase = db.getReadableDatabase();
                boolean insertAnime= db.insertAnime(holder.animeTitle_id.getText().toString(),holder.animeDescription_id.getText().toString(),"Yes",Long.valueOf(0),holder.animeGenre_id.getText().toString(),Double.valueOf(holder.animeRating_id.getText().toString()));
                if(insertAnime==true){
                    Toast.makeText(context, "Request Accepted!", Toast.LENGTH_SHORT).show();
                    Integer deleteRow= db.deleteRequest((String) holder.animeTitle_id.getText());
                    Intent intent= new Intent(context, AdministratorRequests.class);
                    context.startActivity(intent);

                }
                else{
                    Toast.makeText(context, "Could not make request", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db= new DBHelper(context.getApplicationContext());
               Integer deleteRow= db.deleteRequest((String) holder.animeTitle_id.getText());
                Toast.makeText(context, "Request has been declined!", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, AdministratorRequests.class);
                context.startActivity(intent);
            }
        });

        builder.show();


    }
});
    }


    @Override
    public int getItemCount() {
        return animeTitle_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView animeTitle_id, animeDescription_id, animeOngoing_id, animeEpisode_id,animeGenre_id,animeRating_id,username_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            animeTitle_id = itemView.findViewById(R.id.txtAnimeTitleText);
            animeDescription_id = itemView.findViewById(R.id.txtRequestAnimeDescriptionText);
            animeOngoing_id = itemView.findViewById(R.id.txtRequestOngoingText);
            animeEpisode_id = itemView.findViewById(R.id.txtRequestEpisodesText);
            animeGenre_id = itemView.findViewById(R.id.txtRequestGenreText);
            animeRating_id = itemView.findViewById(R.id.txtRequestRatingText);
            username_id = itemView.findViewById(R.id.txtUsernameText);
        }
    }
}
