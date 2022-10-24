package za.ac.cput.pengu_tv;

import static za.ac.cput.pengu_tv.util.DBHelper.REVIEWS_TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.application.R;

import za.ac.cput.pengu_tv.util.DBHelper;

public class HomePage extends AppCompatActivity {
DBHelper db;
SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
        db=new DBHelper(this);


        Button btnLogin=findViewById(R.id.btnLoginPage);
        Button btnRegister=findViewById(R.id.btnRegisterPage);
        ImageView myImageView7= findViewById(R.id.imageView5);
        myImageView7.setImageResource(R.drawable.logo);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomePage.this,UserLogin.class);
                startActivity(intent);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomePage.this,UserRegistration.class);
                startActivity(intent);
            }
        });
        Button btnAdminLogin=findViewById(R.id.btnAdmin);

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomePage.this,AdminLogin.class);
                startActivity(intent);

            }
        });
    }



}