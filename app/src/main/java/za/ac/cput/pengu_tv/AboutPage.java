package za.ac.cput.pengu_tv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.application.R;

public class AboutPage extends AppCompatActivity {
    private Button signButton;
    private Button animeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        setContentView(R.layout.activity_about_page);



        signButton = (Button) findViewById(R.id.btnSignUp);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();
            }
        });

        animeButton = (Button) findViewById(R.id.btnAnime);
        animeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAnimePage();
            }
        });
    }

    public void openSignUpPage(){
        Intent intent = new Intent(this, AdminLogin.class);
        startActivity(intent);
    }

    public void openAnimePage(){
        Intent intent = new Intent(this, AdminLogin.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainnavigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.icAbout:
                Toast.makeText(this, "Welcome to Login page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AdminLogin.class);
                startActivity(intent);

        }
        return true;
    }
}