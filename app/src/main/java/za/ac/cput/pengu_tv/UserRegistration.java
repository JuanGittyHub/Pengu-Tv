package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.application.R;

public class UserRegistration extends AppCompatActivity {
    DBHelper myDb;
    private EditText username, Password, Email, Firstnames, Lastname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_registration);


        Button regBtn = findViewById(R.id.RegButton);
        Button Return = findViewById(R.id.UserLogbtn);
        myDb = new DBHelper(this);
        username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.edtPassword);
        Email = (EditText) findViewById(R.id.editTextEmailAddress);
        Firstnames = (EditText) findViewById(R.id.edtFirstname);
        Lastname = (EditText) findViewById(R.id.edtLastName);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegister();
            }
        });

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UserRegistration.this,UserLogin.class);
                startActivity(intent);
            }
        });


    }
    public void checkRegister()
    {
        if(username.getText().toString().isEmpty()|| Password.getText().toString().isEmpty() || 
                Email.getText().toString().isEmpty() || 
                Firstnames.getText().toString().isEmpty() ||
                Lastname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Registration Failed, Fields Empty", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UserRegistration.this, UserLogin.class);
            startActivity(intent);
        }
        else
        {
            boolean isInserted = myDb.insertUser(Firstnames.getText().toString(),
                                                Lastname.getText().toString(),
                                                username.getText().toString(),
                                                Password.getText().toString(),
                                                Email.getText().toString()) ;
            if(isInserted = true)
            {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
        }
    }
