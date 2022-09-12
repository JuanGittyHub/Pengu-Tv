package za.ac.cput.pengu_tv;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.application.R;

import java.util.Arrays;
import java.util.List;

import za.ac.cput.pengu_tv.util.DBHelper;

public class AdministratorAnime extends AppCompatActivity{
    //Add Anime Text Fields
private EditText edtAddAnimeName,edtAddAnimeDescription,edtAddAnimeEpisodeAmount,edtAddAnimeRating;
private CheckBox cboAddAnimeOngoing;
    Spinner spGenre;
    String genre;
 //Update Anime Text Fields
    private EditText edtUpdateAnimeName,edtUpdateAnimeDescription, edtUpdateAnimeEpisodeAmount,edtUpdateAnimeRating;
    private CheckBox cboUpdateAnimeOngoing;
    Spinner spUpdateGenre;
    String updateGenre;
    String checkTitle,checkRating;
    //Delete Anime Text Fields
    private EditText edtDeleteAnimeName,edtDeleteAnimeRating;
    private CheckBox cboDeleteAnimeOngoing;
    //Search Anime Text Fields
    private TextView txtAnimeId,txtUserId,txtReviewId,txtAnimeName,txtAnimeDescription,txtAnimeOngoing,txtEpisodeAmount,txtAnimeGenre,txtAnimeRating;
    private EditText edtSeachAnime;
    private String searchAnime;
    SQLiteDatabase sqLiteDatabase;
    AlertDialog.Builder builder;
    AlertDialog.Builder builderHelp;
    DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_anime);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final List<String> genres= Arrays.asList("   Select Genre","Action","Shonen","School","Comedy","Sports","Martial Arts");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        setContentView(R.layout.activity_administrator_anime);
        ImageView myImageView1= findViewById(R.id.animeIcon);
        myImageView1.setImageResource(R.drawable.ic_baseline_tv_24);
        builder= new AlertDialog.Builder(this);
        builderHelp= new AlertDialog.Builder(this);
        myDb= new DBHelper(this);
        //--------Set Buttons--------//
        //--------Set TextFields For Add Anime--------//
        edtAddAnimeName= (EditText) findViewById(R.id.edtAddAnimeName);
        edtAddAnimeDescription= (EditText) findViewById(R.id.edtAddAnimeDescription);
        cboAddAnimeOngoing= (CheckBox) findViewById(R.id.cboAddOngoing);
        edtAddAnimeEpisodeAmount= (EditText) findViewById(R.id.edtAddEpisodeAmount);
        edtAddAnimeRating= (EditText) findViewById(R.id.edtAddRating);
        //--------Set TextFields For Update Anime--------//
        edtUpdateAnimeName=(EditText) findViewById(R.id.edtUpdateAnimeName);
        edtUpdateAnimeDescription=(EditText) findViewById(R.id.edtUpdateAnimeDescription) ;
        cboUpdateAnimeOngoing=(CheckBox) findViewById(R.id.cboUpdateOngoing);
        edtUpdateAnimeEpisodeAmount=(EditText) findViewById(R.id.edtUpdateAnimeEpisodeAmount);
        edtUpdateAnimeRating= (EditText) findViewById(R.id.edtUpdateAnimeRating);
        //--------Set TextFields For Delete Anime---------//
        edtDeleteAnimeName= (EditText) findViewById(R.id.edtDeleteAnimeName);
        cboDeleteAnimeOngoing= (CheckBox) findViewById(R.id.cbodeleteAnimeOngoing);
        edtDeleteAnimeRating= (EditText) findViewById(R.id.edtDeleteAnimeRating);
        //-------Set TextFields For Search------------//
        edtSeachAnime= (EditText) findViewById(R.id.edtSearchAnime);
        txtAnimeId= (TextView) findViewById(R.id.txtAnimeAnimeId);
        txtAnimeName= (TextView) findViewById(R.id.txtAnimeName);
        txtAnimeDescription= (TextView) findViewById(R.id.txtAnimeDescription);
        txtAnimeOngoing= (TextView) findViewById(R.id.txtAnimeOngoing);
        txtEpisodeAmount= (TextView) findViewById(R.id.txtAnimeEpisodeAmount);
        txtAnimeGenre= (TextView) findViewById(R.id.txtAnimeGenre);
        txtAnimeRating= (TextView) findViewById(R.id.txtAnimeRating);

        Button btnAddAnimeHelp = findViewById(R.id.AddAnimeHelp);
        btnAddAnimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add Anime Help");
                builderHelp.setMessage("Welcome to the add anime help. To add a anime, make sure to field all fiends correctly! You may not create an anime if it already exists and the name cannot be the same as other anime!");
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
        builderHelp= new AlertDialog.Builder(this);
        Button btnUpdateAnimeHelp = findViewById(R.id.btnUpdateAnimeHelp);
        btnUpdateAnimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Update Anime Help");
                builderHelp.setMessage("Welcome to the update anime help. To update a specific anime, make sure to field all fiends correctly! You may not update an anime if the name and episode amount does not match any anime in the database!");
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
        builderHelp= new AlertDialog.Builder(this);
        Button btnDeleteAnimeHelp = findViewById(R.id.btnDeleteAnimeHelp);
        btnDeleteAnimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Delete Anime Help");
                builderHelp.setMessage("Welcome to the Delete anime help. To delete a specific anime, make sure to field all fiends correctly! You may not delete an anime if the name and rating of the anime is not correct, as well as well if the anime is ongoing or not!");
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
        builderHelp= new AlertDialog.Builder(this);
        Button btnSearchAnimeHelp = findViewById(R.id.btnSearchAnimeHelp);
        btnSearchAnimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Search Anime Help");
                builderHelp.setMessage("Welcome to the Search anime help. To search a specific anime, make sure to field all fiends correctly! You may not search an anime if the name and rating of the anime is not correct, as well as well if the anime is ongoing or not!");
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
        builderHelp= new AlertDialog.Builder(this);
        Button btnViewAllAnimeHelp = findViewById(R.id.btnSearchAnimeHelp);
        btnViewAllAnimeHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("View All Anime Help");
                builderHelp.setMessage("Welcome to the View All anime help. To view all anime, make sure to field all fiends correctly! Simply just click the button to display all the anime. ");
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

        spGenre= findViewById(R.id.spinnerAddAnimeGenre);
        spUpdateGenre= findViewById(R.id.spinnerUpdateAnimeGenre);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.my_selected_item,genres);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spGenre.setAdapter(adapter);
        spUpdateGenre.setAdapter(adapter);

    //------------------Button Functions For Add-----------------//
        Button btnAddAnime= findViewById(R.id.btnAddAnime);
        btnAddAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genre=spGenre.getSelectedItem().toString();
        if(edtAddAnimeName.getText().toString().isEmpty()||
        edtAddAnimeDescription.getText().toString().isEmpty()||
        edtAddAnimeEpisodeAmount.getText().toString().isEmpty()||
        edtAddAnimeRating.getText().toString().isEmpty()||
        spGenre.getSelectedItemId()==0){
            Toast.makeText(AdministratorAnime.this, "Empty Fields or genre is not selected!", Toast.LENGTH_SHORT).show();
        }else{
            insertAnime();
            edtAddAnimeName.getText().clear();
            edtAddAnimeDescription.getText().clear();
            edtAddAnimeEpisodeAmount.getText().clear();
            edtAddAnimeRating.getText().clear();
            cboAddAnimeOngoing.setChecked(false);
            spGenre.setSelection(0);
        }
            }
        });
        //-----------Button Functions For View All------------//
        Button btnViewAll= findViewById(R.id.btnViewAllAnime);
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllAnime();
            }
        });
        //-----------Button Function For Update------------//
        Button btnUpdateAnime=findViewById(R.id.btnUpdateAnime);
        btnUpdateAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGenre=spUpdateGenre.getSelectedItem().toString();
                if (edtUpdateAnimeName.getText().toString().isEmpty()||
                edtUpdateAnimeDescription.getText().toString().isEmpty()||
                edtUpdateAnimeEpisodeAmount.getText().toString().isEmpty()||
                spUpdateGenre.getSelectedItemId()==0){
                    Toast.makeText(AdministratorAnime.this, "Empty fields or genre is not selected!", Toast.LENGTH_SHORT).show();
                }else

                updateAnime();

            }
        });
        Button btnDeleteAnime=findViewById(R.id.btnDeleteAnime);
        btnDeleteAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDeleteAnimeName.getText().toString().isEmpty()||
                edtDeleteAnimeRating.getText().toString().isEmpty()){
                    Toast.makeText(AdministratorAnime.this, "There are still empty fields", Toast.LENGTH_SHORT).show();

                }else
                    deleteAnime();


            }
        });
    }

    //----------------Database Function For Add-----------------//
    public void insertAnime() {
        checkTitle=edtAddAnimeName.getText().toString();

        myDb= new DBHelper(getApplicationContext());
        sqLiteDatabase= myDb.getReadableDatabase();
        Cursor titleCheck,ratingCheck;
        titleCheck= myDb.checkAnimeTitle(checkTitle,sqLiteDatabase);

        String isChecked;
        if (cboAddAnimeOngoing.isChecked() == true) {
            isChecked = "Yes";
        } else {
            isChecked = "No";
        }
        if (titleCheck.moveToFirst()){
            Toast.makeText(this, "That anime already exists!", Toast.LENGTH_SHORT).show();
        }else{
        boolean isInserted = myDb.insertAnime(edtAddAnimeName.getText().toString(),
                edtAddAnimeDescription.getText().toString(),

                isChecked,
                Integer.valueOf(edtAddAnimeEpisodeAmount.getText().toString()),
                genre, Double.valueOf(edtAddAnimeRating.getText().toString()));
        if(isInserted==true){
            Toast.makeText(this, "New anime added!", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Could not add new anime!", Toast.LENGTH_SHORT).show();
        }}
        }
        //-------Database Function For GetAll--------//
    public void getAllAnime(){
        Cursor res;
        res=myDb.viewAllAnime();
        String getAnimeAmount= String.valueOf(res.getCount());
        if(res.getCount()==0){
            displayData("Error","There are no anime yet!");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while (res.moveToNext()){
            buffer.append("================================\n"+"Anime Id: "+res.getString(0)+"\n");
            buffer.append("Anime Name: "+res.getString(1)+"\n");

            buffer.append("Description: "+res.getString(2)+"\n");
            buffer.append("Ongoing: "+res.getString(4)+"\n");
            buffer.append("Episode Amount: "+res.getString(5)+"\n");
            buffer.append("Genre: "+res.getString(6)+"\n");
            buffer.append("Average Rating: "+res.getString(7)+"/10"+"\n"+"================================\n\n");

        }
        displayData("There is "+ getAnimeAmount+" Anime out of 20",buffer.toString());
    }
    public void displayData(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    //-----------Database Function For Update-------------//
    public void updateAnime() {
        checkTitle=edtUpdateAnimeName.getText().toString();
        checkRating=edtUpdateAnimeRating.getText().toString();
        myDb= new DBHelper(getApplicationContext());
        sqLiteDatabase= myDb.getReadableDatabase();
        Cursor titleCheck,ratingCheck;
        titleCheck= myDb.checkAnimeTitle(checkTitle,sqLiteDatabase);
        ratingCheck=myDb.checkAnimeRating(checkRating,sqLiteDatabase);
        if (titleCheck.moveToFirst() && ratingCheck.moveToFirst()){
        String isChecked;

        if (cboUpdateAnimeOngoing.isChecked() == true) {
            isChecked = "Yes";
        } else {
            isChecked = "No";
        }
        Boolean isUpdated = myDb.updateAnime(edtUpdateAnimeName.getText().toString(),
                edtUpdateAnimeDescription.getText().toString(),
                isChecked,
                Integer.valueOf(edtUpdateAnimeEpisodeAmount.getText().toString()),
                updateGenre, Double.valueOf(edtUpdateAnimeRating.getText().toString()));
        if(isUpdated==true) {
            Toast.makeText(this, "Anime Updated!", Toast.LENGTH_SHORT).show();
            edtUpdateAnimeName.getText().clear();
            edtUpdateAnimeDescription.getText().clear();
            edtUpdateAnimeEpisodeAmount.getText().clear();
            edtUpdateAnimeRating.getText().clear();
            cboUpdateAnimeOngoing.setChecked(false);
            spUpdateGenre.setSelection(0);
        }
        }else{
            Toast.makeText(this, "Anime name or rating is incorrect!", Toast.LENGTH_SHORT).show();
            edtUpdateAnimeRating.getText().clear();
            edtUpdateAnimeName.getText().clear();
        }
    }
    //---------Database For Delete Function------------//

    public void deleteAnime(){
        String isChecked;
        if (cboDeleteAnimeOngoing.isChecked()==true){
            isChecked="Yes";
        }else{
            isChecked="No";
        }
        Integer deleteRows= myDb.deleteAnime(edtDeleteAnimeName.getText().toString(),isChecked,Double.valueOf(edtDeleteAnimeRating.getText().toString()));
        if (deleteRows>0){
            Toast.makeText(this, "Anime deleted successfully", Toast.LENGTH_SHORT).show();
            edtDeleteAnimeName.getText().clear();
            cboDeleteAnimeOngoing.setChecked(false);
            edtDeleteAnimeRating.getText().clear();
        }
        else
            Toast.makeText(this, "That anime does not exist!", Toast.LENGTH_SHORT).show();
    }
    //----------Database Function for Search Anime----------//
    public void search_Anime(View view){
       searchAnime=edtSeachAnime.getText().toString();
       myDb= new DBHelper(getApplicationContext());
       sqLiteDatabase= myDb.getReadableDatabase();
       Cursor cursor=myDb.searchAnime(searchAnime,sqLiteDatabase);
        if(searchAnime.isEmpty()){
            Toast.makeText(this, "Please type the name of the anime in the search box!", Toast.LENGTH_SHORT).show();

            txtAnimeId.setText("Anime Id: ");
            txtAnimeName.setText("Anime Name: ");
            txtAnimeDescription.setText("Description: ");
            txtAnimeOngoing.setText("Ongoing: ");
            txtEpisodeAmount.setText("Episodes: ");
            txtAnimeGenre.setText("Genre: ");
            txtAnimeRating.setText("Average Rating: ");
        }else {
            if (cursor.moveToFirst()) {
                String getAnimeId = cursor.getString(0);
                String getAnimeName = cursor.getString(1);
                String getAnimeDescription = cursor.getString(2);
                String getAnimeOngoing = cursor.getString(4);
                String getAnimeEpisodeAmount = cursor.getString(5);
                String getAnimeGenre = cursor.getString(6);
                String getAnimeRatingAverage = cursor.getString(7);

                Toast.makeText(this, "Anime found!", Toast.LENGTH_SHORT).show();

                txtAnimeId.setText("Anime Id: " + getAnimeId);
                txtAnimeName.setText("Anime Name: " + getAnimeName);
                txtAnimeDescription.setText("Description: " + getAnimeDescription);
                txtAnimeOngoing.setText("Ongoing: " + getAnimeOngoing);
                txtEpisodeAmount.setText("Episodes: " + getAnimeEpisodeAmount);
                txtAnimeGenre.setText("Genre: " + getAnimeGenre);
                txtAnimeRating.setText("Average Rating: " + getAnimeRatingAverage+"/10");
            } else {
                Toast.makeText(AdministratorAnime.this, "Could not find that anime!", Toast.LENGTH_SHORT).show();
                txtAnimeId.setText("Anime Id: ");
                txtAnimeName.setText("Anime Name: ");
                txtAnimeDescription.setText("Description: ");
                txtAnimeOngoing.setText("Ongoing: ");
                txtEpisodeAmount.setText("Episodes: ");
                txtAnimeGenre.setText("Genre: ");
                txtAnimeRating.setText("Average Rating: ");
            }
        }
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.icUsers:
                Toast.makeText(this,"Welcome to Users page!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdministratorAnime.this,AdministratorUsers.class);
                startActivity(intent);
                return true;
            case R.id.icAnime:
                Toast.makeText(this,"Already on the Anime page!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icReviews:
                Toast.makeText(this,"Welcome to Reviews page!",Toast.LENGTH_SHORT).show();
                intent = new Intent(AdministratorAnime.this,AdministratorReviews.class);
                startActivity(intent);
                return true;
            case R.id.icRequests:
                Toast.makeText(this,"Welcome to Requests page!",Toast.LENGTH_SHORT).show();
                intent = new Intent(AdministratorAnime.this,AdministratorRequests.class);
                startActivity(intent);
                return true;
            case R.id.icLogout:

                builder.setTitle("Warning!");
                builder.setMessage("Do you wish to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(AdministratorAnime.this,AdminLogin.class);
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
        }

    }

}