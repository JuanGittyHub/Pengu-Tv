package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.application.R;

public class AdminLogin extends AppCompatActivity {
private EditText edtUsername,edtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_login);
        ImageView myImageView5= findViewById(R.id.pngAppLogo);
        myImageView5.setImageResource(R.drawable.pengu_tv);

        edtUsername= (EditText) findViewById(R.id.edtUsername);
        edtpassword= (EditText) findViewById(R.id.edtPassword);


        Button btnLogin = findViewById(R.id.btnLogin);
        Button goRegBtn = findViewById(R.id.Register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtUsername.getText().toString().equals("admin") && edtpassword.getText().toString().equals("admin")){
                    Intent intent= new Intent(AdminLogin.this, AdministratorUsers.class);
                    Toast.makeText(AdminLogin.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AdminLogin.this, "The username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        goRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminLogin.this, UserRegistration.class);
                startActivity(intent);
            }
        });
    }
}