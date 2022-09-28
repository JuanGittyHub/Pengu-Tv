package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.application.R;

import za.ac.cput.pengu_tv.util.DBHelper;

public class DescriptionPage extends AppCompatActivity implements DescriptionDialog.DescriptionDialogListener,ReviewsDialog.ReviewDialogListener{
    private ImageView descriptionIcon;
    private TextView txtTitle,txtGenre,txtDescription, txtRating;
    String getTitle,getGenre,getDescription, getRating, getUser,getId;
    private Button btnView,btnAdd, btnReturn;
    DBHelper db;
    SQLiteDatabase sqLiteDatabase;
MainPage mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_description_page);


        getUser= getIntent().getStringExtra("passUsername");
      // getUser= getIntent().getStringExtra("extendUser");
        //Toast.makeText(this, "Welcome, "+getUser+"!", Toast.LENGTH_SHORT).show();
        getTitle =  getIntent().getStringExtra("passName");
        getDescription =  getIntent().getStringExtra("passDescription");
        getGenre =  getIntent().getStringExtra("passGenre");
        getRating =  getIntent().getStringExtra("passRating");
        getId = getIntent().getStringExtra("passAnimeId");


        txtTitle= (TextView) findViewById(R.id.txtDescriptionTitle);
        txtGenre= (TextView) findViewById(R.id.txtDescriptionGenreText);
        txtDescription= (TextView) findViewById(R.id.txtDescriptionDescription);
        txtRating= (TextView) findViewById(R.id.txtDescriptionRatingText);
        descriptionIcon = findViewById(R.id.imgDescriptionIcon);
        btnReturn =findViewById(R.id.btnDescriptionReturn);
        btnView =  findViewById(R.id.btnDescriptionReviews);
        btnAdd = findViewById(R.id.btnDescriptionAddReview);



        txtTitle.setText(getTitle);
        txtDescription.setText(getDescription);
        txtGenre.setText(getGenre);
        txtRating.setText(getRating);

        if (txtGenre.getText().toString().equals("Action")){
            descriptionIcon.setImageResource(R.drawable.ic_action);
        }else if (txtGenre.getText().toString().equals("School"))
        {
            descriptionIcon.setImageResource(R.drawable.ic_school);
        }else if (txtGenre.getText().toString().equals("Martial Arts")){
            descriptionIcon.setImageResource(R.drawable.ic_martial_arts);
        }else if(txtGenre.getText().toString().equals("Sports")){
            descriptionIcon.setImageResource(R.drawable.ic_sports);
        }else if (txtGenre.getText().toString().equals("Comedy")){
            descriptionIcon.setImageResource(R.drawable.ic_comedy);
        }else if (txtGenre.getText().toString().equals("Shonen")){
            descriptionIcon.setImageResource(R.drawable.ic_shonen);
        }else{
            Toast.makeText(this, "nu", Toast.LENGTH_SHORT).show();
        }


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionPage.this,MainPage.class);
                intent.putExtra("loginUser",getUser);
                startActivity(intent);
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReviewDialog();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.putExtra("sendUser",getUser);
                intent.putExtra("sendAnime",getTitle);
                openSubmitReview();
            }
        });
    }

    private void openSubmitReview() {
        ReviewsDialog reviewsDialog= new ReviewsDialog();
        reviewsDialog.show(getSupportFragmentManager(),"submit review");
    }


    private void openReviewDialog() {
        DescriptionDialog descriptionDialog =  new DescriptionDialog();
        descriptionDialog.show(getSupportFragmentManager(),"review dialog");
    }


    @Override
    public void applyDescriptionTexts(String title, String genre, String Description, String rating, ImageView icon) {

    }

    @Override
    public void applyReviewTexts(String reviewDescription, Double rating, String username, String animeName) {
        db= new DBHelper(getApplicationContext());
        sqLiteDatabase= db.getReadableDatabase();
        Cursor usernameCheck,animeCheck;

        usernameCheck=db.checkUsernameReview(username,sqLiteDatabase);
        animeCheck= db.checkAnimeReview(animeName,sqLiteDatabase);

        if (usernameCheck.moveToFirst() && animeCheck.moveToFirst()){
            String userId= usernameCheck.getString(0);
            String animeId= animeCheck.getString(0);


            boolean isInserted= db.insertReview(reviewDescription,rating,userId,animeId);

            if (isInserted==true){
                Toast.makeText(this, "Your review has been submitted!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "account or anime does not exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}