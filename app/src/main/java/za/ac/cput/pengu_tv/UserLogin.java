package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.application.R;

import za.ac.cput.pengu_tv.util.DBHelper;

public class UserLogin extends AppCompatActivity {
    DBHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    String username, password, tempPassword, temp_name;
    boolean emptyTextEmptyHolder;
    private EditText edtUsername, edtPassword;
        DescriptionPage descriptionPage;
    int mainPage= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_userlogin);

        Button logBtn = findViewById(R.id.btnLogin);
        Button goRegBtn = findViewById(R.id.RegButton);

        Button btnHome= findViewById(R.id.btnReturn);
        ImageView myImageView7= findViewById(R.id.imageView2);
        myImageView7.setImageResource(R.drawable.logo);
        myDb = new DBHelper(UserLogin.this);

        edtUsername = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.tpPassword);
        password = edtPassword.getText().toString();

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, HomePage.class);
                startActivity(intent);
            }
        });
        goRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, UserRegistration.class);
                startActivity(intent);
            }
        });


    }
       //@SuppressLint("Range")
        public void LoginFunction(){
            password = edtPassword.getText().toString();
            username = edtUsername.getText().toString();

            myDb = new DBHelper(getApplicationContext());
            sqLiteDatabase = myDb.getWritableDatabase();
            Cursor checkUserPass= myDb.searchUserAndPassword(username,password);
            if (checkUserPass.moveToNext()){
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                CheckFinalResult();
            }
            else {
                Toast.makeText(this, "The username or password is incorrect!", Toast.LENGTH_SHORT).show();
            }
        }
        public void CheckFinalResult(){

            Intent intent = new Intent(UserLogin.this, MainPage.class);

            intent.putExtra("loginUser",username);
            boolean isInserted= myDb.insertUsername(username);
            if (isInserted==true){


            }else{
                Toast.makeText(this, "Not Added", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(UserLogin.this, "Welcome, "+username+"!", Toast.LENGTH_SHORT).show();

            startActivity(intent);


        }


        public  void CheckEditTextStatus()
    {
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
        {
            emptyTextEmptyHolder = false;
        }
        else
        {
            emptyTextEmptyHolder = true;
        }

    }



    }
