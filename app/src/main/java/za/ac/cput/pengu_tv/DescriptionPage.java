package za.ac.cput.pengu_tv;

import static za.ac.cput.pengu_tv.util.DBHelper.REVIEWS_TABLE_NAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;

import za.ac.cput.pengu_tv.util.DBHelper;

public class DescriptionPage extends AppCompatActivity implements DescriptionDialog.DescriptionDialogListener,ReviewsDialog.ReviewDialogListener{
    private ImageView descriptionIcon;
    private TextView txtTitle,txtGenre,txtDescription, txtRating,txtEpisodes;
    String getTitle;
    String getGenre;
    String getDescription;
    String getRating;
    String getUser;
    String getEpisodes;
    String getId;
    private Button btnView,btnAdd, btnReturn;
    DBHelper db;
    ViewReviewsAdapter viewReviewsAdapter;
    //ArrayList<String> animeUserId,animeReview;
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

        getTitle =  getIntent().getStringExtra("passName");
        getDescription =  getIntent().getStringExtra("passDescription");
        getGenre =  getIntent().getStringExtra("passGenre");
        getRating =  getIntent().getStringExtra("passRating");
        getEpisodes = getIntent().getStringExtra("passEpisodes");
        getId = getIntent().getStringExtra("passAnimeId");
        db=new DBHelper(this);

        txtTitle= (TextView) findViewById(R.id.txtDescriptionTitle);
        txtGenre= (TextView) findViewById(R.id.txtDescriptionGenreText);
        txtDescription= (TextView) findViewById(R.id.txtDescriptionDescription);
        txtRating= (TextView) findViewById(R.id.txtDescriptionRatingText);
        txtEpisodes= (TextView) findViewById(R.id.txtDescriptionEpisodeText);
        descriptionIcon = findViewById(R.id.imgDescriptionIcon);
        btnReturn =findViewById(R.id.btnDescriptionReturn);
        btnView =  findViewById(R.id.btnDescriptionReviews);
        btnAdd = findViewById(R.id.btnDescriptionAddReview);



        txtTitle.setText(getTitle);
        txtDescription.setText(getDescription);
        txtGenre.setText(getGenre);
        txtEpisodes.setText(getEpisodes);
        getRatings();


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
                getRatings();
                startActivity(intent);
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // openReviewDialog();
            getAllReview();

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
            getRatings();
            if (isInserted==true){
                Toast.makeText(this, "Your review has been submitted!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "account or anime does not exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void getAllReview(){

        getId = getIntent().getStringExtra("passAnimeId");
        Cursor res;
        res=db.viewAllReview(getId);

        String getReviewAmount;
        getReviewAmount=String.valueOf(res.getCount());
        if(res.getCount()==0){
            displayData("Error","There is no reviews for "+getTitle+" at the moment...");
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
    public Double getRatings() {
        //db= new DBHelper(getApplicationContext());
        sqLiteDatabase= db.getReadableDatabase();
        String animeId= getId;
        Double total = 0.0;
        Double count = 0.0;
        Cursor c = sqLiteDatabase.rawQuery("SELECT RATING from " + REVIEWS_TABLE_NAME + " where ANIMEID = ?", new String[]{animeId});
        while (c.moveToNext()) {
            @SuppressLint("Range") Double average = c.getDouble(c.getColumnIndex("RATING"));
            total += (average);
            count++;
        }
        String convert= String.format("%.1f",(total / count));
        txtRating.setText(convert+"/10");
        Boolean isUpdated= db.updateAnimeRatingById(Integer.valueOf(animeId),Double.valueOf(convert));

        return total / count;
    }



}


