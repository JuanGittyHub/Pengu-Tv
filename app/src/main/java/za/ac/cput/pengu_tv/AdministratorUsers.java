package za.ac.cput.pengu_tv;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.application.R;

import za.ac.cput.pengu_tv.util.DBHelper;

public class AdministratorUsers extends AppCompatActivity {

    AlertDialog.Builder builder;
    AlertDialog.Builder builderHelp;
    DBHelper myDb;
    SQLiteDatabase sqLiteDatabase;
    String searchUser;

    //-----Add User Textfields-----//
    private EditText edtAddName,edtAddLastName,edtAddUsername,edtAddEmail,edtAddPassword;
    //-----Delete User Textfields-----//
    private EditText edtDeleteEmail,edtDeleteUsername,edtDeletePassword;
    //-----Search User Textfields-----//
    private EditText edtSearchUser;
    private TextView txtUserId,txtAnimeId,txtName,txtLastName,txtUsername,txtPassword,txtEmail;
    //-----Update User-----//
    private EditText edtUpdateName,edtUpdateLastname,edtUpdateUsername,edtUpdateEmail,edtUpdatePassword;
    String checkUserEmail,checkUserUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));
        setContentView(R.layout.activity_administrator_users);
        ImageView myImageView5 = findViewById(R.id.userIcon);
        myImageView5.setImageResource(R.drawable.ic_baseline_people_24);
        builder = new AlertDialog.Builder(this);
        builderHelp= new AlertDialog.Builder(this);
        myDb = new DBHelper(this);
        //------------------Set Buttons------------------//
        Button btnAddUser= findViewById(R.id.btnAddUser);
        Button btnUpdateUser= findViewById(R.id.btnUpdateUser);
        Button btnDeleteUser= findViewById(R.id.btnDeleteUser);
        Button btnViewAllUsers= findViewById(R.id.btnViewUser);
        Button btnSearchUser= findViewById(R.id.btnSearchUser);
        Button btnAddUserHelp = findViewById(R.id.AddUserHelp);
        //------------------Set Textfields For Add User--------------//
        edtAddName=(EditText) findViewById(R.id.edtAddName);
        edtAddLastName=(EditText) findViewById(R.id.edtAddLastName);
        edtAddUsername=(EditText) findViewById(R.id.edtAddUsername);
        edtAddEmail=(EditText) findViewById(R.id.edtAddEmail);
        edtAddPassword=(EditText) findViewById(R.id.edtAddPassword);
        //------------------Set Textfields For Delete User----------------//
        edtDeleteEmail=(EditText) findViewById(R.id.edtDeleteEmail);
        edtDeleteUsername=(EditText) findViewById(R.id.edtDeleteUsername);
        edtDeletePassword=(EditText) findViewById(R.id.edtDeletePassword);
        //-----------------Set Textfields and TextViews For Search User-----------------//
        edtSearchUser=(EditText) findViewById(R.id.edtSearchUser);
        txtUserId= findViewById(R.id.txtUserId);
        txtAnimeId= findViewById(R.id.txtAnimeId);
        txtName= findViewById(R.id.txtName);
        txtLastName= findViewById(R.id.txtLastName);
        txtUsername= findViewById(R.id.txtUsername);
        txtEmail= findViewById(R.id.txtEmail);
        txtPassword= findViewById(R.id.txtPassword);
        //-----------------Set TextFields for Update User---------------------//
        edtUpdateName=(EditText) findViewById(R.id.edtUpdateName);
        edtUpdateLastname=(EditText) findViewById(R.id.edtUpdateLastName);
        edtUpdateUsername=(EditText) findViewById(R.id.edtUpdateUsername);
        edtUpdateEmail=(EditText) findViewById(R.id.edtUpdateEmail);
        edtUpdatePassword=(EditText) findViewById(R.id.edtUpdatePassword);

        btnAddUserHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add User Help");
                builderHelp.setMessage("Welcome to the add user help. To add a user make sure to field all fiends correctly! You may not create an account if it already exists and the username and email cannot be the same as other accounts!");
                builderHelp.setCancelable(true);
                builderHelp.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builderHelp.show();
            }
        });
        builderHelp= new AlertDialog.Builder(this);
        Button btnUpdateUserHelp = findViewById(R.id.btnUpdateUserHelp);
        btnUpdateUserHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add User Help");
                builderHelp.setMessage("Welcome to the update user help. To update a user make sure to field all fiends correctly! You may not update an account if the username and email do not match an account in the database!");
                builderHelp.setCancelable(true);
                builderHelp.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builderHelp.show();
            }
        });
        builderHelp= new AlertDialog.Builder(this);
        Button btnDeleteUserHelp = findViewById(R.id.btnDeleteUserHelp);
        btnDeleteUserHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add User Help");
                builderHelp.setMessage("Welcome to the Delete user help. To Delete a user make sure to field all fiends correctly! You may not delete an account if the email, username and password do not match an account in the database!");
                builderHelp.setCancelable(true);
                builderHelp.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builderHelp.show();
            }
        });
        builderHelp= new AlertDialog.Builder(this);
        Button btnSearchUserHelp = findViewById(R.id.btnSearchUsersHelp);
        btnSearchUserHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add User Help");
                builderHelp.setMessage("Welcome to the Search user help. To search a user make sure to field all fiends correctly! You may not search an account if the username do not match an account in the database!");
                builderHelp.setCancelable(true);
                builderHelp.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builderHelp.show();
            }
        });
        builderHelp= new AlertDialog.Builder(this);
        Button btnViewAllUsersHelp = findViewById(R.id.btnViewAllUsersHelp);
        btnViewAllUsersHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderHelp.setTitle("Add User Help");
                builderHelp.setMessage("Welcome to the View All users help. To view all users just click the button to display all the users!");
                builderHelp.setCancelable(true);
                builderHelp.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builderHelp.show();
            }
        });
        //------------------button functions------------------//
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtAddName.getText().toString().isEmpty()==true ||
                        edtAddLastName.getText().toString().isEmpty()==true ||
                        edtAddUsername.getText().toString().isEmpty()==true ||
                        (edtAddEmail.getText().toString().isEmpty()==true)||
                        edtAddPassword.getText().toString().isEmpty()==true){

                    Toast.makeText(AdministratorUsers.this, "There are empty or incorrect fields!", Toast.LENGTH_SHORT).show();
                }
                else if(edtAddEmail.getText().toString().contains("@gmail.com")){
                    insertUser();


                }
                else if (!edtAddEmail.getText().toString().contains("@gmail.com")){
                    Toast.makeText(AdministratorUsers.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                    edtAddEmail.getText().clear();
                }
            }



        });
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtUpdateName.getText().toString().isEmpty()==true ||
                        edtUpdateLastname.getText().toString().isEmpty()==true ||
                        edtUpdateUsername.getText().toString().isEmpty()==true ||
                        (edtUpdateEmail.getText().toString().isEmpty()==true)||
                        edtUpdatePassword.getText().toString().isEmpty()==true){

                    Toast.makeText(AdministratorUsers.this, "There are empty or incorrect fields!", Toast.LENGTH_SHORT).show();
                }else
                    updateUser();



            }
        });
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
                edtDeleteEmail.getText().clear();
                edtDeleteUsername.getText().clear();
                edtDeletePassword.getText().clear();
            }
        });
        btnViewAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAllUsers();
            }
        });

    }
    //--------------Database Functions--------------//
    public void insertUser() {
        checkUserEmail = edtAddEmail.getText().toString();
        checkUserUsername = edtAddUsername.getText().toString();
        myDb = new DBHelper(getApplicationContext());
        sqLiteDatabase = myDb.getReadableDatabase();
        Cursor emailCheck, usernameCheck;
        emailCheck = myDb.checkUserEmail(checkUserEmail, sqLiteDatabase);
        usernameCheck = myDb.checkUserUsername(checkUserUsername, sqLiteDatabase);
        if (emailCheck.moveToFirst() || usernameCheck.moveToFirst()) {
            Toast.makeText(this, "There is already an account with that username or email!", Toast.LENGTH_SHORT).show();
        } else {
            boolean isInserted = myDb.insertUser(
                    edtAddName.getText().toString(),
                    edtAddLastName.getText().toString(),
                    edtAddUsername.getText().toString(),
                    edtAddPassword.getText().toString(),
                    edtAddEmail.getText().toString());


            if (isInserted == true) {

                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                edtAddName.getText().clear();
                edtAddLastName.getText().clear();
                edtAddUsername.getText().clear();
                edtAddEmail.getText().clear();
                edtAddPassword.getText().clear();


            } else {
                Toast.makeText(this, "Unable to make account!", Toast.LENGTH_SHORT).show();
            }}

    }
    public void updateUser(){
        checkUserEmail= edtUpdateEmail.getText().toString();
        checkUserUsername=edtUpdateUsername.getText().toString();
        myDb=new DBHelper(getApplicationContext());
        sqLiteDatabase =myDb.getReadableDatabase();
        Cursor emailCheck,usernameCheck;
        emailCheck=myDb.checkUserEmail(checkUserEmail,sqLiteDatabase);
        usernameCheck= myDb.checkUserUsername(checkUserUsername,sqLiteDatabase);
        if(emailCheck.moveToFirst() && usernameCheck.moveToFirst()){
            Boolean isUpdated= myDb.updateUser(edtUpdateName.getText().toString(),
                    edtUpdateLastname.getText().toString(),
                    edtUpdateUsername.getText().toString(),
                    edtUpdatePassword.getText().toString(),
                    edtUpdateEmail.getText().toString());
            if (isUpdated.equals(true)){
                Toast.makeText(this, "Account updated!", Toast.LENGTH_SHORT).show();
                edtUpdateName.getText().clear();
                edtUpdateLastname.getText().clear();
                edtUpdateUsername.getText().clear();
                edtUpdateEmail.getText().clear();
                edtUpdatePassword.getText().clear();}
        }else {

            Toast.makeText(this, "Username or email is incorrect!", Toast.LENGTH_SHORT).show();
            edtUpdateUsername.getText().clear();
            edtUpdateEmail.getText().clear();


        }
    }
    public void searchUsers(View view){
        searchUser=edtSearchUser.getText().toString();
        myDb=new DBHelper(getApplicationContext());
        sqLiteDatabase = myDb.getReadableDatabase();
        Cursor cursor=myDb.searchUser(searchUser,sqLiteDatabase);

        if (cursor.moveToFirst()){
            String getId= (cursor.getString(0));
            String getAnimeId= (cursor.getString(1));
            String getName= (cursor.getString(2));
            String getLastName=(cursor.getString(3));
            String getUsername= (cursor.getString(4));
            String getPassword=(cursor.getString(5));
            String getEmail=(cursor.getString(6));

            Toast.makeText(AdministratorUsers.this, "User Found!", Toast.LENGTH_SHORT).show();
            txtUserId.setText("User Id: "+getId);
            if (cursor.getString(1)==null) {
                txtAnimeId.setText("Number Of Reviews: null");
            }else{
                txtAnimeId.setText("Anime Id: "+ getAnimeId);
            }
            txtName.setText("Name: "+getName);
            txtLastName.setText("Last Name: "+getLastName);
            txtUsername.setText("Username: "+getUsername);
            txtPassword.setText("Password: "+getPassword);
            txtEmail.setText("Email: "+getEmail);
            edtSearchUser.getText().clear();

        }else {
            Toast.makeText(AdministratorUsers.this, "Could not find that user! Please try again!", Toast.LENGTH_SHORT).show();
            txtUserId.setText("User Id: ");
            txtAnimeId.setText("Number Of Reviews: ");
            txtName.setText("Name: ");
            txtLastName.setText("Last Name: ");
            txtUsername.setText("Username: ");
            txtPassword.setText("Password: ");
            txtEmail.setText("Email: ");
        }

    }
    public void viewAllUsers(){
        Cursor res;

        res= myDb.viewAllUsers();
        if (res.getCount()==0){
            displayData("Error","Theres no users yet!");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while(res.moveToNext()){

            buffer.append("================================\n"+"User Id: "+res.getString(0)+"\n");
            buffer.append("Number Of Reviews: "+res.getString(1)+"\n");
            buffer.append("Name: "+ res.getString(2)+"\n");
            buffer.append("Last Name: "+res.getString(3)+"\n");
            buffer.append("Username: "+res.getString(4)+"\n");
            buffer.append("Password: "+res.getString(5)+"\n");
            buffer.append("Email: "+res.getString(6)+"\n"+"================================\n\n");

        }
        if(res.getCount()==1) {
            displayData("There is "+res.getCount()+" User", buffer.toString());
        }else{
            displayData("There is "+res.getCount()+" Users", buffer.toString());
        }

    }

    public void displayData(String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void deleteUser(){

        Integer deleteRow= myDb.deleteUser(edtDeleteEmail.getText().toString(),edtDeleteUsername.getText().toString(),edtDeletePassword.getText().toString());
        if(deleteRow>0){
            Toast.makeText(this, "Account Deleted!", Toast.LENGTH_SHORT).show();

        }else
            Toast.makeText(this, "Unable to delete account! Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.icAnime:
                Toast.makeText(this,"Welcome to Anime page!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdministratorUsers.this,AdministratorAnime.class);
                startActivity(intent);
                return true;
            case R.id.icUsers:
                Toast.makeText(this,"Already on the Users page!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.icReviews:
                Toast.makeText(this,"Welcome to Reviews page!",Toast.LENGTH_SHORT);
                intent = new Intent(AdministratorUsers.this,AdministratorReviews.class);
                startActivity(intent);
                return true;

            case R.id.icRequests:
                Toast.makeText(this,"Welcome to Requests page!",Toast.LENGTH_SHORT).show();
                intent = new Intent(AdministratorUsers.this,AdministratorRequests.class);
                startActivity(intent);
                return true;
            case R.id.icLogout:

                builder.setTitle("Warning!");
                builder.setMessage("Do you wish to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(AdministratorUsers.this,AdminLogin.class);
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
            default:
                return super.onOptionsItemSelected(item);



        }



    }


}