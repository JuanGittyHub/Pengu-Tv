package za.ac.cput.pengu_tv;

import static za.ac.cput.pengu_tv.util.DBHelper.ANIME_TABLE_NAME;
import static za.ac.cput.pengu_tv.util.DBHelper.REVIEWS_TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.text.format.DateFormat;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.*;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.application.R;


import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import za.ac.cput.pengu_tv.util.DBHelper;

public class MainPage extends AppCompatActivity implements ExampleDialog.ExampleDialogListener, RecyclerViewInterface{

    AlertDialog.Builder builder;

    String getUsername;
    EditText edtSearch;
    String id;
    DBHelper db;
    SQLiteDatabase sqLiteDatabase;
    ViewAnimeAdapter viewAnimeAdapter;
    ArrayList<String> animeName,animeGenre,animeRating;
    List<String> suggestList =  new ArrayList<>();
    RecyclerView recyclerView;
    ImageView myImageIcon;
    ListView listViewAnime;


    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        db= new DBHelper(this);
        builder = new AlertDialog.Builder(this);

        edtSearch = (EditText) findViewById(R.id.edtSearch);


    /*searchView= findViewById(R.id.searchView);
    searchView.clearFocus();
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });

     */




        Cursor res;
        res= db.viewAllUsernames();
        if (res.moveToNext()){
            getUsername= res.getString(0);
        }





        ArrayList<String> data = new ArrayList<>();





        animeName= new ArrayList<>();
        animeGenre= new ArrayList<>();
        animeRating= new ArrayList<>();
        myImageIcon = findViewById(R.id.imgAnimeIcon);
        recyclerView= findViewById(R.id.recyclerViewMain);
        viewAnimeAdapter = new ViewAnimeAdapter(this, animeName, animeGenre, animeRating, myImageIcon, this) {


        };

        recyclerView.setAdapter(viewAnimeAdapter);

        //System.out.println( animeName.indexOf("Bleach")+" Anime Name Test");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() <= (edtSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))  {

                        if(edtSearch.getText().toString().isEmpty()){

                            removeItems();
                            Toast.makeText(MainPage.this, "Please type in an anime before searching!", Toast.LENGTH_LONG).show();
                           displayData();
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }else {
                            removeItems();
                            displayDataBySearch(edtSearch.getText().toString());
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        displayData();
getAll();
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
                        Toast.makeText(MainPage.this, "Goodbye, "+getUsername+"!", Toast.LENGTH_SHORT).show();
                        deleteUsername();
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
               Intent intent= new Intent(MainPage.this,AboutPage.class);
               intent.putExtra("extendUser",getUsername);
                startActivity(intent);



                return true;
            case R.id.icMain:
                Intent intent1= new Intent(this,MainPage.class);
                intent1.putExtra("extendUser",getUsername);
                startActivity(intent1);
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
    public void displayDataBySearch(String input) {


        ImageView myImageView5 = findViewById(R.id.imgAnimeIcon);
        Cursor cursor = db.viewAllAnimeBySearch(input);
        //System.out.println(input+" Test 1");
        //System.out.println(cursor+" Test 2");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "That anime does not exist...", Toast.LENGTH_SHORT).show();
            displayData();
            return;
        } else {

            while (cursor.moveToNext()) {

                animeName.add(cursor.getString(1));

                animeGenre.add(cursor.getString(6));

                animeRating.add(cursor.getString(7));
              }

        }

    }



   public void removeItems() {
        animeName.clear();
        animeGenre.clear();
        animeRating.clear();
        recyclerView.getAdapter().notifyDataSetChanged();
    }








    @Override
    public void onItemClick(int position) {


        Intent intent =  new Intent(MainPage.this,DescriptionPage.class);

    }

    public void deleteUsername(){
        db.deleteUsername();

    }
    public void getAll(){
        Cursor res;
        res= db.viewAllAnime();

        while (res.moveToNext()){
            id= res.getString(0);
            getRatings();
        }
    }

    public Double getRatings() {
        db= new DBHelper(getApplicationContext());
        sqLiteDatabase = db.getReadableDatabase();
        String animeId = id;
        Double total = 0.0;
        Double count = 0.0;
        Cursor c = sqLiteDatabase.rawQuery("SELECT RATING from " + REVIEWS_TABLE_NAME + " where ANIMEID = ?", new String[]{animeId});
        while (c.moveToNext()) {
            @SuppressLint("Range") Double average = c.getDouble(c.getColumnIndex("RATING"));
            total += (average);
            count++;
        }
        //Toast.makeText(context, animeId, Toast.LENGTH_SHORT).show();
        String convert= String.format("%.1f",(total / count));
        Boolean isUpdated= db.updateAnimeRatingById(Integer.valueOf(animeId),Double.valueOf(convert));



        return total / count;
    }



}