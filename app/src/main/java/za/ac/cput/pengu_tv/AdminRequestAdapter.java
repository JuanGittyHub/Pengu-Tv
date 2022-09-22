package za.ac.cput.pengu_tv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import java.util.ArrayList;

public class AdminRequestAdapter extends RecyclerView.Adapter<AdminRequestAdapter.MyViewHolder>{
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

    holder.animeTitle_id.setText(String.valueOf(animeTitle_id.get(position)));
    holder.animeDescription_id.setText(String.valueOf(animeDescription_id.get(position)));
    holder.animeOngoing_id.setText(String.valueOf(animeOngoing_id.get(position)));
    holder.animeEpisode_id.setText(String.valueOf(animeEpisode_id.get(position)));
    holder.animeGenre_id.setText(String.valueOf(animeGenre_id.get(position)));
    holder.animeRating_id.setText(String.valueOf(animeRating_id.get(position)));
        holder.username_id.setText(String.valueOf(username_id.get(position)));
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
