package za.ac.cput.pengu_tv;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.application.R;

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class AdministratorRequests extends AppCompatActivity {
    AlertDialog.Builder builder;
    AlertDialog.Builder builderHelp;
    DBHelper db;
    RecyclerView recyclerView;
    ArrayList<String> animeTitle, animeDescription, animeOngoing, animeEpisode,animeGenre,animeRating,username;
    AdminRequestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_requests);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        setContentView(R.layout.activity_administrator_requests);
        builder= new AlertDialog.Builder(this);
        builderHelp= new AlertDialog.Builder(this);
        db= new DBHelper(this);

        animeTitle = new ArrayList<>();
        animeDescription = new ArrayList<>();
        animeOngoing = new ArrayList<>();
        animeEpisode= new ArrayList<>();
        animeGenre= new ArrayList<>();
        animeRating= new ArrayList<>();
        username= new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AdminRequestAdapter(this,animeTitle,animeDescription,animeOngoing,animeEpisode,animeGenre,animeRating,username);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();


        ImageView myImageView3= findViewById(R.id.requestsIcon);
        myImageView3.setImageResource(R.drawable.ic_baseline_emoji_people_24);
        Button btnRequestHelp = findViewById(R.id. btnRequestHelp);


        btnRequestHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("User Request Help");
                builderHelp.setMessage("Welcome to the user requests help. To accept an anime request from a user, you may either accept or decline the request. You cannot accept a request from a user if the anime already exists or if there are redundant attributes in the request!");
                builderHelp.setCancelable(true);
                builderHelp.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builderHelp.show();
            }
        });

    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.icAnime:
                Toast.makeText(this, "Welcome to Anime page!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdministratorRequests.this, AdministratorAnime.class);
                startActivity(intent);
                return true;
            case R.id.icUsers:
                Toast.makeText(this, "Welcome to the Users page!", Toast.LENGTH_SHORT).show();
                intent = new Intent(AdministratorRequests.this, AdministratorUsers.class);
                startActivity(intent);
                return true;
            case R.id.icReviews:
                Toast.makeText(this, "Welcome to Reviews page!", Toast.LENGTH_SHORT);
                intent = new Intent(AdministratorRequests.this, AdministratorReviews.class);
                startActivity(intent);
                return true;

            case R.id.icRequests:
                Toast.makeText(this, "Already on the Requests page!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icLogout:

                builder.setTitle("Warning!");
                builder.setMessage("Do you wish to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(AdministratorRequests.this,AdminLogin.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}
    private void displayData(){
        Cursor cursor=db.viewAllRequests();
        if (cursor.getCount()==0){
            Toast.makeText(this, "There are no request entries at this time!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while(cursor.moveToNext()){

                animeTitle.add(cursor.getString(1));
                animeDescription.add(cursor.getString(2));
                animeOngoing.add(cursor.getString(3));
                animeEpisode.add(cursor.getString(4));
                animeGenre.add(cursor.getString(5));
                animeRating.add(cursor.getString(6));
                username.add(cursor.getString(7));
            }
        }
    }
}