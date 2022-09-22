package za.ac.cput.pengu_tv;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.application.R;

import androidx.annotation.NonNull;

public class MainPage extends AppCompatActivity{
    AlertDialog.Builder builder;
    String getUsername;
    private TextView textViewTitle, textViewDescription, textViewGenre,textViewRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        builder = new AlertDialog.Builder(this);
        getUsername= getIntent().getStringExtra("loginUser");
        Toast.makeText(this, "Welcome, "+getUsername+"!", Toast.LENGTH_SHORT).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.mainnavigation,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.icLogout:
                builder.setTitle("Warning!");
                builder.setMessage("Do you wish to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =  new Intent(MainPage.this,UserLogin.class);
                        Toast.makeText(MainPage.this, "Goodbye!", Toast.LENGTH_SHORT).show();
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
            case R.id.icAbout:
                Toast.makeText(this, "Welcome to the about page!", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(MainPage.this,AboutPage.class);
                startActivity(intent);
                return true;
            case R.id.icMain:
                Toast.makeText(this, "You're already on the main page!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icRequest:
                    openDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
public void openDialog(){
        ExampleDialog exampleDialog =  new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
}

}