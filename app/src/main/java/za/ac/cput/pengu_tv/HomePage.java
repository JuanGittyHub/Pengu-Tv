package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button btnLogin=findViewById(R.id.btnLoginPage);
        Button btnRegister=findViewById(R.id.btnRegisterPage);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomePage.this,LoginPage.class);
                startActivity(intent);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomePage.this,RegisterPage.class);
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