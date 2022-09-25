package za.ac.cput.pengu_tv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewAnimeAdapter extends RecyclerView.Adapter<ViewAnimeAdapter.MyViewHolder>{
    private Context context;
    private ArrayList animeName, animeGenre, animeRating;
    private ImageView myImageIcon;

    public ViewAnimeAdapter(Context context, ArrayList animeName, ArrayList animeGenre, ArrayList animeRating, ImageView myImageIcon) {
        this.context = context;
        this.animeName = animeName;
        this.animeGenre = animeGenre;
        this.animeRating = animeRating;
        this.myImageIcon = myImageIcon;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.allanime,parent,false);
        return new MyViewHolder(v);
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

        /*switch (holder.animeGenre.getText().toString()){
            case "Action":
                Toast.makeText(context, "Action", Toast.LENGTH_SHORT).show();
                //
            case "School":
                Toast.makeText(context, "School", Toast.LENGTH_SHORT).show();

                //
            case "Sports":
                Toast.makeText(context, "Sports", Toast.LENGTH_SHORT).show();

                //holder.imageView.setImageResource(R.drawable.ic_sports);
            case "Martial Arts":
                Toast.makeText(context, "Martial Arts", Toast.LENGTH_SHORT).show();

                //holder.imageView.setImageResource(R.drawable.ic_martial_arts);
            case "Comedy":
                Toast.makeText(context, "Comedy", Toast.LENGTH_SHORT).show();

                //holder.imageView.setImageResource(R.drawable.ic_comedy);
            case "Shonen":
                Toast.makeText(context, "Shonen", Toast.LENGTH_SHORT).show();

                //holder.imageView.setImageResource(R.drawable.ic_shonen);
            default:
*/

        holder.animeRating.setText(String.valueOf(animeRating.get(position)));





    }

    @Override
    public int getItemCount() {
        return animeName.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView animeName, animeGenre, animeRating;
      ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            animeName= itemView.findViewById(R.id.txtAnimeListNameText);
            animeGenre= itemView.findViewById(R.id.txtAnimeListGenreText);
            animeRating= itemView.findViewById(R.id.txtAnimeListRatingText);
          imageView= itemView.findViewById(R.id.imgAnimeIcon);

        }
    }
}

