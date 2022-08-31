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
import android.widget.Toast;

import com.android.application.R;

public class UserLogin extends AppCompatActivity {
    DBHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    String username, password, tempPassword, temp_name;
    boolean emptyTextEmptyHolder;
    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_userlogin);

        Button logBtn = findViewById(R.id.btnLogin);
        Button goRegBtn = findViewById(R.id.RegButton);
        Button goAdminBtn = findViewById(R.id.AdminB);
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

        goRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, UserRegistration.class);
                startActivity(intent);
            }
        });

        goAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLogin.this, AdminLogin.class);
                startActivity(intent);
            }
        });
    }
       @SuppressLint("Range")
        public void LoginFunction(){
            if(emptyTextEmptyHolder)
            {
                username=edtUsername.getText().toString();
                myDb = new DBHelper(getApplicationContext());
                sqLiteDatabase = myDb.getWritableDatabase();
                Cursor cursor = sqLiteDatabase.query(DBHelper.USER_TABLE_NAME,null," "+DBHelper.COLUMN_5+"=?",new String[]{username},null,null,null);

                while(cursor.moveToNext()) {
                    if (cursor.isFirst()) {
                        cursor.moveToFirst();
                        tempPassword = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_6));
                        temp_name= cursor.getString(1);
                        cursor.close();
                    }
                }
                CheckFinalResult();
            }
            else
            {
                Toast.makeText(UserLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
        public void CheckFinalResult()
    {
        if(tempPassword.equalsIgnoreCase(password))
        {
            Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserLogin.this, UserLogin.class);

            intent.putExtra("UserLogin",username);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(UserLogin.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
        }

        tempPassword = "NOT_FOUND";
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
