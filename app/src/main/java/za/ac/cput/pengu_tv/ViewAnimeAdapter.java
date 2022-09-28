package za.ac.cput.pengu_tv;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class ViewAnimeAdapter extends RecyclerView.Adapter<ViewAnimeAdapter.MyViewHolder> {
    private Context context;
        DBHelper db;
        String getUser;
        SQLiteDatabase sqLiteDatabase;

    private ArrayList animeName, animeGenre, animeRating;
    private RecyclerViewInterface recyclerViewInterface;
    private ImageView myImageIcon;

    private MainPage mainPage;

    public ViewAnimeAdapter(Context context, ArrayList animeName, ArrayList animeGenre, ArrayList animeRating, ImageView myImageIcon, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.animeName = animeName;
        this.animeGenre = animeGenre;
        this.animeRating = animeRating;
        this.myImageIcon = myImageIcon;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.allanime,parent,false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.animeName.setText(String.valueOf(animeName.get(position)));
        holder.animeGenre.setText(String.valueOf(animeGenre.get(position)));
        if (holder.animeGenre.getText().toString().equals("Action")){
            holder.imageView.setImageResource(R.drawable.ic_action);
        }else if (holder.animeGenre.getText().toString().equals("School"))
        {
            holder.imageView.setImageResource(R.drawable.ic_school);
        }else if (holder.animeGenre.getText().toString().equals("Martial Arts")){
            holder.imageView.setImageResource(R.drawable.ic_martial_arts);
        }else if(holder.animeGenre.getText().toString().equals("Sports")){
            holder.imageView.setImageResource(R.drawable.ic_sports);
        }else if (holder.animeGenre.getText().toString().equals("Comedy")){
            holder.imageView.setImageResource(R.drawable.ic_comedy);
        }else if (holder.animeGenre.getText().toString().equals("Shonen")){
            holder.imageView.setImageResource(R.drawable.ic_shonen);
        }else{
            Toast.makeText(context, "nu", Toast.LENGTH_SHORT).show();
        }
        holder.animeRating.setText(String.valueOf(animeRating.get(position)));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    db= new DBHelper(context.getApplicationContext());
                    sqLiteDatabase= db.getReadableDatabase();
                    Cursor cursor=db.searchAnime((String) holder.animeName.getText(),sqLiteDatabase);
                    if (cursor.moveToFirst()) {

                        String getAnimeId = cursor.getString(0);
                        String getAnimeName = cursor.getString(1);
                        String getAnimeDescription = cursor.getString(2);
                        String getAnimeOngoing = cursor.getString(4);
                        String getAnimeEpisodeAmount = cursor.getString(5);
                        String getAnimeGenre = cursor.getString(6);
                        String getAnimeRatingAverage = cursor.getString(7);

                        Cursor res;
                        res= db.viewAllUsernames();
                        if (res.moveToNext()) {
                            getUser=res.getString(0);
                        }

                        Intent intent=  new Intent(context, DescriptionPage.class);
                        intent.putExtra("passUsername",getUser);
                        intent.putExtra("passName",getAnimeName);
                        intent.putExtra("passAnimeId",getAnimeId);
                        intent.putExtra("passDescription",getAnimeDescription);
                        intent.putExtra("passGenre",getAnimeGenre);
                        intent.putExtra("passRating",getAnimeRatingAverage);

                        context.startActivity(intent);



                    }



                }
            });



    }




    @Override
    public int getItemCount() {
        return animeName.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView animeName, animeGenre, animeRating;
        String user;
      ImageView imageView;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            animeName= itemView.findViewById(R.id.txtAnimeListNameText);
            animeGenre= itemView.findViewById(R.id.txtAnimeListGenreText);
            animeRating= itemView.findViewById(R.id.txtAnimeListRatingText);
          imageView= itemView.findViewById(R.id.imgAnimeIcon);

        }
    }

}

