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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;

import com.android.application.R;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import za.ac.cput.pengu_tv.util.DBHelper;

public class AdministratorReviews extends AppCompatActivity {

    DBHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    String id,name,animename;
    Double average,sub,tracer;


    int i;
    //-----Add Review Variables-----//
    private EditText edtAddReviewUsername, edtAddReviewDescription, edtAddReviewRating;
    private String checkUsername,checkReviewId;
    private Spinner spAddReviewAnime;

    //-----Update Review Variables------//
    private EditText edtUpdateReviewId, edtUpdateReviewUsername, edtUpdateReviewDescription, edtUpdateReviewRating;
    //-----View Review Variable-----//

    //-----Delete Review-----//
    private EditText edtDeleteAnimeName, edtDeleteUsername, edtDeletePersonalRating, edtDeleteReviewId;
    private String checkPersonalRating, checkAnime;
    //-----Search Review-----//
    private EditText edtSearchReviewId;
    private TextView txtReviewId,txtReviewUserId,txtReviewAnimeId,txtReviewDescription,txtReviewRating;
    String animeName,username, reviewId;
    //------View Review----//
    private EditText edtViewReview;

    AlertDialog.Builder builder;
    AlertDialog.Builder builderHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_reviews);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        setContentView(R.layout.activity_administrator_reviews);
        ImageView myImageView2= findViewById(R.id.reviewIcon);
        myImageView2.setImageResource(R.drawable.ic_baseline_rate_review_24);


        builder= new AlertDialog.Builder(this);
        builderHelp= new AlertDialog.Builder(this);
        //-----Set Text Fields For Add-----//
        mydb= new DBHelper(this);
        //------------List to view all anime---------//


        edtAddReviewUsername= (EditText) findViewById(R.id.edtAddReviewUserName);
        edtAddReviewDescription= (EditText) findViewById(R.id.edtAddReviewAnimeDescription);
        edtAddReviewRating= (EditText) findViewById(R.id.edtAddReviewAnimeRating);
        Button btnAddReviewHelp = findViewById(R.id.btnAddReviewHelp);
        //-----Set Text Fields For Update-----//
        edtUpdateReviewId=(EditText) findViewById(R.id.edtUpdateReviewId);
        edtUpdateReviewUsername=(EditText) findViewById(R.id.edtUpdateReviewUsername);
        edtUpdateReviewDescription= (EditText) findViewById(R.id.edtUpdateReviewAnimeDescription);
        edtUpdateReviewRating=(EditText) findViewById(R.id.edtUpdateReviewRating);
        //-----Set Text Fields For View All-----//

        //-----Set Text Fields For Delete-----//
        edtDeleteAnimeName= (EditText) findViewById(R.id.edtReviewName);
        edtDeleteUsername= (EditText) findViewById(R.id.edtDeleteReviewUserame);
        edtDeletePersonalRating= (EditText) findViewById(R.id.edtDeletePersonalRating);
        edtDeleteReviewId= (EditText) findViewById(R.id.edtDeleteReviewId);
        //-----Set Text Fields For Search----//

        edtSearchReviewId= (EditText) findViewById(R.id.edtSearchFindReviewId);
        //------Set Text Fields For View Review------//
        edtViewReview= (EditText) findViewById(R.id.edtViewReview);

        txtReviewId= findViewById(R.id.txtReviewId);
        txtReviewUserId= findViewById(R.id.txtReviewUserId);
        txtReviewAnimeId= findViewById(R.id.txtReviewAnimeId);
        txtReviewDescription= findViewById(R.id.txtReviewDescription);
        txtReviewRating= findViewById(R.id.txtReviewRating);

        //--------Pull All Anime--------//
        Cursor res;
        res=mydb.viewAllAnime();
        i=0;
        average=0.0;
         String[]  anime=new String[res.getCount()];



        while (res.moveToNext()) {

            id= (res.getString(0));
           name=(res.getString(1));



            anime[i]=name;
            i++;
        }
        i=0;
        tracer= 0.0;
        Cursor getRatings;
        getRatings=mydb.viewAllReview();
        Double[] ratings= new Double[getRatings.getCount()];
        while(getRatings.moveToNext()){

            sub=getRatings.getDouble(5);

            ratings[i]=sub;


            tracer= ratings[i]+tracer;
            ++i;
        }


      //  Toast.makeText(this, String.valueOf(tracer), Toast.LENGTH_SHORT).show();

        spAddReviewAnime= findViewById(R.id.spinnerAddReviewAnime);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.my_selected_item,anime);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);

        spAddReviewAnime.setAdapter(adapter);

        btnAddReviewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add Review Help");
                builderHelp.setMessage("Welcome to the add reviews help. To search a review, make sure to fill all fields correctly! You may not add a review if the anime and username does not match any of the anime of usernames in the database!");
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
        Button btnUpdateReviewHelp = findViewById(R.id.btnUpdateReviewHelp);
        btnUpdateReviewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Update Review Help");
                builderHelp.setMessage("Welcome to the update reviews help. To update a review, make sure to fill all fields correctly! You may not update a review if the anime name and username does not match the anime and the username in the database!");
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
        Button btnDeleteReviewHelp = findViewById(R.id.btnDeleteReviewHelp);
        btnDeleteReviewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Delete Review Help");
                builderHelp.setMessage("Welcome to the delete reviews help. To delete a review, make sure to fill all fields correctly! You may not delete a review if the information supplied in each field does not match the specific review in the database!");
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

        Button btnSearchReviewHelp = findViewById(R.id.btnSearchReviewHelp);
        btnSearchReviewHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Search Review Help");
                builderHelp.setMessage("Welcome to the search reviews help. To search a review, make sure to fill all fields correctly! You may not search a review if the information supplied in each field does not match the specific review in the database!");
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
        Button btnViewAllReviewsHelp = findViewById(R.id.btnViewAllReviewsHelp);
        btnViewAllReviewsHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Search Review Help");
                builderHelp.setMessage("Welcome to the view all reviews help. To view all reviews, simply click the button to display all the reviews in the database!");
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
        //-----Button Function Section-----//

        //-----Button Add Review-----//
        Button btnAddReview= findViewById(R.id.btnAddReview);
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(
                edtAddReviewUsername.getText().toString().isEmpty()||
                edtAddReviewDescription.getText().toString().isEmpty()||
                edtAddReviewRating.getText().toString().isEmpty())
                {
                    Toast.makeText(AdministratorReviews.this, "There are still empty fields!", Toast.LENGTH_SHORT).show();

                }
                else{

                    insertReview();
                }
            }
        });
        //-----View All Reviews-----//
        Button btnViewReviews= findViewById(R.id.btnViewReviews);
        btnViewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtViewReview.getText().toString().isEmpty()){
                    Toast.makeText(AdministratorReviews.this, "There are empty fields!", Toast.LENGTH_SHORT).show();
                }else {
                    getAllReviews();
                }
            }
        });

        //-----Button Update Review-----//
        Button btnUpdateReview= findViewById(R.id.btnUpdateReview);
        btnUpdateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUpdateReviewId.getText().toString().isEmpty()||
                edtUpdateReviewDescription.getText().toString().isEmpty()||
                edtUpdateReviewUsername.getText().toString().isEmpty()||
                edtUpdateReviewRating.getText().toString().isEmpty()){
                    Toast.makeText(AdministratorReviews.this, "There are still empty fields!", Toast.LENGTH_SHORT).show();
                }else{
                    updateReview();
                }
            }
        });
        //-----Button For Delete-----//
        Button btnDeleteReview= findViewById(R.id.btnDeleteReview);
        btnDeleteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDeleteAnimeName.getText().toString().isEmpty()||
                        edtDeletePersonalRating.getText().toString().isEmpty()||
                        edtDeleteUsername.getText().toString().isEmpty()||
                        edtDeleteReviewId.getText().toString().isEmpty()){
                    Toast.makeText(AdministratorReviews.this, "There are still empty fields!", Toast.LENGTH_SHORT).show();
                }else{
                    deleteReview();
                }
            }
        });


    }
    //----------Database Functions----------//
    //-----Insert Review Section-----//
    public void insertReview(){

       animename=spAddReviewAnime.getSelectedItem().toString();


        checkUsername= edtAddReviewUsername.getText().toString();

        mydb= new DBHelper(getApplicationContext());
        sqLiteDatabase= mydb.getReadableDatabase();
        Cursor userCheck,animeCheck;

        userCheck= mydb.checkUsernameReview(checkUsername,sqLiteDatabase);
        animeCheck=mydb.checkAnimeReview(animename,sqLiteDatabase);

        if(userCheck.moveToFirst() && animeCheck.moveToFirst()){
            double rating;

            String userId=userCheck.getString(0);
            String animeId=animeCheck.getString(0);
            rating= Double.valueOf(animeCheck.getString(2));
            Toast.makeText(this, String.valueOf(rating), Toast.LENGTH_SHORT).show();
            boolean isInserted= mydb.insertReview(edtAddReviewDescription.getText().toString(),
                    Double.valueOf(edtAddReviewRating.getText().toString()),userId,animeId);
//Update

average=rating+tracer+Double.valueOf(edtAddReviewRating.getText().toString());

                boolean isUpdated = mydb.updateAnimeRating(animeId,average);
                if (isUpdated==true){
                //Toast.makeText(this, String.valueOf(average), Toast.LENGTH_SHORT).show();
            }


            if (isInserted==true) {
                Toast.makeText(this, "Review Added! " , Toast.LENGTH_SHORT).show();
                edtAddReviewRating.getText().clear();
                edtAddReviewDescription.getText().clear();

                edtAddReviewUsername.getText().clear();
            }


        }else{
            Toast.makeText(this, "Anime name or username does not match or exist!", Toast.LENGTH_SHORT).show();
            edtAddReviewUsername.getText().clear();

        }
    }
    //-----Update Review Section-----//
    public void updateReview(){
        checkUsername= edtUpdateReviewUsername.getText().toString();
        checkReviewId=edtUpdateReviewId.getText().toString();
        mydb= new DBHelper(getApplicationContext());
        sqLiteDatabase= mydb.getReadableDatabase();
        Cursor usernameCheck, reviewIdCheck;
        usernameCheck=mydb.checkUsernameReview(checkUsername,sqLiteDatabase);
        if(usernameCheck.moveToFirst()){

            boolean isUpdated= mydb.updateReview(edtUpdateReviewId.getText().toString(),edtUpdateReviewDescription.getText().toString(),Double.valueOf(edtUpdateReviewRating.getText().toString()));
            if (isUpdated==true){
                Toast.makeText(this, "Review has been updated!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Cannot update account ", Toast.LENGTH_SHORT).show();
        }
    }
    //-----Delete Review Section-----//
    public void deleteReview(){
        checkReviewId= edtDeleteReviewId.getText().toString();
        checkUsername= edtDeleteUsername.getText().toString();
        checkAnime= edtDeleteAnimeName.getText().toString();
        checkPersonalRating=edtDeletePersonalRating.getText().toString();
        mydb= new DBHelper(getApplicationContext());
        sqLiteDatabase= mydb.getReadableDatabase();
        Cursor usernameCheck,reviewIdCheck,animeCheck,personalRatingCheck;
        usernameCheck=mydb.checkUsernameReview(checkUsername,sqLiteDatabase);
        reviewIdCheck=mydb.checkReviewId(Integer.valueOf(checkReviewId),sqLiteDatabase);
       animeCheck=mydb.checkAnimeReview(checkAnime,sqLiteDatabase);
       personalRatingCheck=mydb.checkRating(Double.valueOf(checkPersonalRating),sqLiteDatabase);

        if (usernameCheck.moveToFirst()&&reviewIdCheck.moveToFirst()&&animeCheck.moveToFirst()&&personalRatingCheck.moveToFirst()){
            Toast.makeText(this, "Review deleted successfully", Toast.LENGTH_SHORT).show();
            edtDeleteReviewId.getText().clear();
            edtDeleteUsername.getText().clear();
            edtDeleteAnimeName.getText().clear();
            edtDeletePersonalRating.getText().clear();
          Integer deleteRows= mydb.deleteReview(Integer.valueOf(checkReviewId));
            if(deleteRows<0){


            }else{
                Toast.makeText(this, "Unable to delete account!", Toast.LENGTH_SHORT).show();

            }

        }else{
            Toast.makeText(this, "Account does not exist!", Toast.LENGTH_SHORT).show();
            edtDeleteReviewId.getText().clear();
            edtDeleteUsername.getText().clear();
            edtDeleteAnimeName.getText().clear();
            edtDeletePersonalRating.getText().clear();
        }
    }

    public void search_Review(View view){
        if(edtSearchReviewId.getText().toString().isEmpty()){
            Toast.makeText(this, "There are still empty fields!", Toast.LENGTH_SHORT).show();
            txtReviewId.setText("Review Id: ");
            txtReviewUserId.setText("User Id: ");
            txtReviewAnimeId.setText("Anime Id: ");
            txtReviewDescription.setText("Description: ");
            txtReviewRating.setText("Personal Rating: ");
        }
        else{

        reviewId=edtSearchReviewId.getText().toString();
        mydb= new DBHelper(getApplicationContext());
        sqLiteDatabase= mydb.getReadableDatabase();
        Cursor reviewCheck;

        reviewCheck= mydb.searchReview(Integer.valueOf(reviewId),sqLiteDatabase);


            if (reviewCheck.moveToFirst()) {
                Toast.makeText(this, "Review found!", Toast.LENGTH_SHORT).show();
            String getReviewId= (reviewCheck.getString(0));
            String getReviewUserId= (reviewCheck.getString(1));
            String getReviewAnimeId= (reviewCheck.getString(2));
            String getReviewDescription=(reviewCheck.getString(4));
            String getReviewRating= (reviewCheck.getString(5));

            txtReviewId.setText("Review Id: "+getReviewId);
            txtReviewUserId.setText("User Id: "+getReviewUserId);
            txtReviewAnimeId.setText("Anime Id: "+getReviewAnimeId);
            txtReviewDescription.setText("Description: "+getReviewDescription);
            txtReviewRating.setText("Personal Rating: "+getReviewRating);

            }else{
                Toast.makeText(this, "That review does not exist!", Toast.LENGTH_SHORT).show();
                txtReviewId.setText("Review Id: ");
                txtReviewUserId.setText("User Id: ");
                txtReviewAnimeId.setText("Anime Id: ");
                txtReviewDescription.setText("Description: ");
                txtReviewRating.setText("Personal Rating: ");
            }}


    }

    //-----View All-----//
    public void getAllReviews(){

        int animeId=Integer.valueOf(edtViewReview.getText().toString());
        Cursor res;
        res=mydb.viewAllReview();
        String getReviewAmount;
        getReviewAmount=String.valueOf(res.getCount());
        if(res.getCount()==0){
           displayData("Error","There is no reviews at the moment...");
           return;
        }
        StringBuffer buffer= new StringBuffer();
        while (res.moveToNext()){
            buffer.append("================================\n"+
                    "Review Id: "+res.getString(0)+"\n");
            buffer.append("User Id: "+res.getString(1)+"\n");
            buffer.append("Anime Id: "+res.getString(2)+"\n");
           buffer.append("Review Description: "+res.getString(4)+"\n");
           buffer.append("Personal Rating: "+res.getString(5)+"\n================================\n\n" );
           }
        if (res.getCount()==1) {
            displayData("Currently " + getReviewAmount + " review", buffer.toString());
        }else{
            displayData("Currently " + getReviewAmount + " reviews", buffer.toString());

        }
    }
    public void displayData(String title,String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
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
                Intent intent = new Intent(AdministratorReviews.this,AdministratorUsers.class);
                startActivity(intent);
                return true;
            case R.id.icAnime:
                Toast.makeText(this,"Welcome to the Anime page!",Toast.LENGTH_SHORT).show();
                intent = new Intent(AdministratorReviews.this,AdministratorAnime.class);
                startActivity(intent);
                return true;
            case R.id.icReviews:
                Toast.makeText(this,"Already on the Users page!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icRequests:
                Toast.makeText(this,"Welcome to Requests page!",Toast.LENGTH_SHORT).show();
                intent = new Intent(AdministratorReviews.this,AdministratorRequests.class);
                startActivity(intent);
                return true;
            case R.id.icLogout:

                builder.setTitle("Warning!");
                builder.setMessage("Do you wish to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(AdministratorReviews.this,AdminLogin.class);
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