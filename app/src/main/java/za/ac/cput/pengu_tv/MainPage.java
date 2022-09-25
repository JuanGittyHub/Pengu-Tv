package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.application.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class MainPage extends AppCompatActivity implements ExampleDialog.ExampleDialogListener{
    AlertDialog.Builder builder;
    String getUsername;
    String returnUser=null;
    DBHelper db;
    SQLiteDatabase sqLiteDatabase;
    ViewAnimeAdapter viewAnimeAdapter;
    ArrayList<String> animeName,animeGenre,animeRating;
    RecyclerView recyclerView;
    ImageView myImageIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db= new DBHelper(this);
        builder = new AlertDialog.Builder(this);
        returnUser= getIntent().getStringExtra("returnUser");

        getUsername= getIntent().getStringExtra("loginUser");
        getUsername=returnUser;
        Toast.makeText(this, "Welcome, "+getUsername+"!", Toast.LENGTH_SHORT).show();

        animeName= new ArrayList<>();
        animeGenre= new ArrayList<>();
        animeRating= new ArrayList<>();
        myImageIcon = findViewById(R.id.imgAnimeIcon);
        recyclerView= findViewById(R.id.recyclerViewMain);
        viewAnimeAdapter = new ViewAnimeAdapter(this,animeName,animeGenre,animeRating,myImageIcon);

        recyclerView.setAdapter(viewAnimeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.mainnavigation,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.icLogout:
                builder.setTitle("Warning!");
                builder.setMessage("Do you wish to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =  new Intent(MainPage.this,UserLogin.class);
                        Toast.makeText(MainPage.this, "Goodbye!", Toast.LENGTH_SHORT).show();
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
            case R.id.icAbout:
                Toast.makeText(this, "Welcome to the about page!", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(MainPage.this,AboutPage.class);
                intent.putExtra("extendUsername",getUsername);
                startActivity(intent);
                return true;
            case R.id.icMain:
                Toast.makeText(this, "You're already on the main page!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icRequest:
                    openDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
public void openDialog(){
        ExampleDialog exampleDialog =  new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
}

    @Override
    public void applyTexts(String animeTitle, String animeDescription, String animeGenre, Double animeRating) {

        db = new DBHelper(getApplicationContext());
        sqLiteDatabase = db.getReadableDatabase();



        boolean isInserted= db.insertRequest(animeTitle,animeDescription,"Yes", Long.valueOf(0),animeGenre,Double.valueOf(animeRating),getUsername);
        if(isInserted==true){
            Toast.makeText(this, "Request has been sent to the admin!", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Could not make request", Toast.LENGTH_SHORT).show();
        }
    }


    private void displayData() {
        ImageView myImageView5 = findViewById(R.id.imgAnimeIcon);
        Cursor cursor = db.viewAllAnime();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "There is no anime at this time!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                animeName.add(cursor.getString(1));
                animeGenre.add(cursor.getString(6));
                animeRating.add(cursor.getString(7));
            }
        }
    }



}