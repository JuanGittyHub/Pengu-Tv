package za.ac.cput.pengu_tv.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static String getId;
    public static final String DATABASE_NAME="PenguTv.db";
    public static final String USER_TABLE_NAME="User_Table";
    public static final String ANIME_TABLE_NAME="ANIME_Table";
    public static final String  REVIEWS_TABLE_NAME="Review_Table";

    public static final String COLUMN_1= "USERID";
    public static final String COLUMN_2= "REVIEWS";
    public static final String COLUMN_3= "USERNAME";
    public static final String COLUMN_4= "USERLASTNAME";
    public static final String COLUMN_5= "USERUSERNAME";
    public static final String COLUMN_6= "USERPASSWORD";
    public static final String COLUMN_7= "USEREMAIL";

    private static final String TAG = "DatabaseHelper";

public static final String ANIMECOLUMN_1="ANIMEID";
public static final String ANIMECOLUMN_4="ANIMETITLE";
public static final String ANIMECOLUMN_5="ANIMEDESCRIPTION";
public static final String ANIMECOLUMN_6="ANIMETOTAL";
public static final String ANIMECOLUMN_7="ANIMEONGOING";
public static final String ANIMECOLUMN_8="ANIMEEPISODEAMOUNT";
public static final String ANIMECOLUMN_9="ANIMEGENRE";
public static final String ANIMECOLUMN_10="RATINGAVERAGE";

    public static final String REVIEWCOLUMN_1="REVIEWID";
    public static final String REVIEWCOLUMN_2="USERID";
    public static final String REVIEWCOLUMN_3="ANIMEID";
    public static final String REVIEWCOLUMN_4="REVIEWAMOUNT";
    public static final String REVIEWCOLUMN_5="REVIEWDESCRIPTION";
    public static final String REVIEWCOLUMN_6="RATING";

public DBHelper(@Nullable Context context){
super(context,DATABASE_NAME,null,1);
    SQLiteDatabase db= this.getWritableDatabase();
}
@Override
    public void onCreate(SQLiteDatabase db){
    db.execSQL("create table "+USER_TABLE_NAME+"(USERID INTEGER PRIMARY KEY AUTOINCREMENT,REVIEWS INTEGER,USERNAME STRING,USERLASTNAME STRING, USERUSERNAME STRING,USERPASSWORD STRING, USEREMAIL STRING)");

    db.execSQL("create table "+ANIME_TABLE_NAME+"(ANIMEID INTEGER PRIMARY KEY AUTOINCREMENT,ANIMETITLE STRING,ANIMEDESCRIPTION STRING,ANIMETOTAL INTEGER,ANIMEONGOING STRING, ANIMEEPISODEAMOUNT LONG, ANIMEGENRE STRING,RATINGAVERAGE DOUBLE)");
    db.execSQL("create table "+REVIEWS_TABLE_NAME+"(REVIEWID INTEGER PRIMARY KEY AUTOINCREMENT,USERID INTEGER,ANIMEID INTEGER,REVIEWAMOUNT LONG, REVIEWDESCRIPTION STRING,RATING DOUBLE)");

}

@Override
    public void onUpgrade(SQLiteDatabase db,int i,int i1){
db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
db.execSQL("DROP TABLE IF EXISTS " + ANIME_TABLE_NAME);
db.execSQL("DROP TABLE IF EXISTS " + REVIEWS_TABLE_NAME);
onCreate(db);
}
//-----------------------FOR USER TABLE------------------------//
public boolean insertUser(String userName,String userLastName,String username,String userPassword,String email){
SQLiteDatabase db= this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();

    contentValues.put(COLUMN_3,userName);
contentValues.put(COLUMN_4,userLastName);
contentValues.put(COLUMN_5,username);
contentValues.put(COLUMN_6,userPassword);
contentValues.put(COLUMN_7,email);

long result= db.insert(USER_TABLE_NAME,null,contentValues);




if(result== -1){
return false;
}
else
    getId=String.valueOf(result);
    return true;
}
//----------Update User Section----------//
    public Cursor  checkUserUsername(String username,SQLiteDatabase db){
        String[] projections={COLUMN_5};
        String selection= COLUMN_5+" LIKE ?";
        String[] selectionArgs={username};
        Cursor res= db.query(USER_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
        return res;
    }
    public Cursor  checkUserEmail(String email,SQLiteDatabase db){
        String[] projections={COLUMN_7};
        String selection= COLUMN_7+" LIKE ?";
        String[] selectionArgs={email};
        Cursor res= db.query(USER_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
        return res;
    }

public boolean updateUser(String userName,String userLastName, String username,String userPassword,String email){
    SQLiteDatabase db=this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();

    contentValues.put(COLUMN_3,userName);
    contentValues.put(COLUMN_4,userLastName);
    contentValues.put(COLUMN_5,username);
    contentValues.put(COLUMN_6,userPassword);
    contentValues.put(COLUMN_7,email);


    db.update(USER_TABLE_NAME, contentValues, "USEREMAIL = ? AND USERUSERNAME = ?", new String[]{email,username});

            return true;
//--------------------------------------------//

}
public Integer deleteUser(String email, String username,String password){
    SQLiteDatabase db= this.getWritableDatabase();
    return db.delete(USER_TABLE_NAME,"USEREMAIL = ? AND USERUSERNAME = ? AND USERPASSWORD = ?",new String[]{email,username,password});
}

public Cursor searchUser(String username, SQLiteDatabase sqLiteDatabase){
    String[] projections={COLUMN_1,COLUMN_2,COLUMN_3,COLUMN_4,COLUMN_5,COLUMN_6,COLUMN_7};
    String selection= COLUMN_5+" LIKE ? ";
    String[] selection_args={username};
    Cursor res= sqLiteDatabase.query(USER_TABLE_NAME,projections,selection,selection_args,null,null,null);
return res;
}
public Cursor viewAllUsers(){
SQLiteDatabase db=this.getWritableDatabase();
Cursor res;
res= db.rawQuery("select * from "+ USER_TABLE_NAME,null);
return res;

}
//--------------------------------FOR ANIME TABLE-------------------------------//
public boolean insertAnime(String animeTitle, String animeDescription, String animeOngoing, long animeEpisodeAmount, String animeGenre, double ratingAverage){
SQLiteDatabase db =this.getWritableDatabase();
ContentValues contentValues=new ContentValues();

contentValues.put(ANIMECOLUMN_4,animeTitle);
contentValues.put(ANIMECOLUMN_5,animeDescription);

contentValues.put(ANIMECOLUMN_7,animeOngoing);
contentValues.put(ANIMECOLUMN_8,animeEpisodeAmount);
contentValues.put(ANIMECOLUMN_9,animeGenre);
contentValues.put(ANIMECOLUMN_10,ratingAverage);

long result= db.insert(ANIME_TABLE_NAME,null,contentValues);
if(result==-1){
    return false;
}
else

    return true;

}
//---------Update Anime Section----------//
public Cursor checkAnimeTitle(String animeName,SQLiteDatabase db){
    String[] projections={ANIMECOLUMN_4};
    String selection= ANIMECOLUMN_4+ " LIKE ?" ;
    String[] selectionArgs={animeName};
    Cursor res= db.query(ANIME_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
    return res;
}
    public Cursor checkAnimeRating(String animeRating,SQLiteDatabase db){
        String[] projections={ANIMECOLUMN_10};
        String selection= ANIMECOLUMN_10+ " LIKE ?" ;
        String[] selectionArgs={animeRating};
        Cursor res= db.query(ANIME_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
        return res;
    }
public boolean updateAnime(String animeTitle, String animeDescription, String animeOngoing, long animeEpisodeAmount, String animeGenre, double ratingAverage){
    SQLiteDatabase db= this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();
    contentValues.put(ANIMECOLUMN_4,animeTitle);
    contentValues.put(ANIMECOLUMN_5,animeDescription);

    contentValues.put(ANIMECOLUMN_7,animeOngoing);
    contentValues.put(ANIMECOLUMN_8,animeEpisodeAmount);
    contentValues.put(ANIMECOLUMN_9,animeGenre);
    contentValues.put(ANIMECOLUMN_10,ratingAverage);

    db.update(ANIME_TABLE_NAME,contentValues,"ANIMETITLE = ? AND RATINGAVERAGE = ?",(new String[]{animeTitle, String.valueOf(ratingAverage)}));

    return true;
}
    public boolean updateAnimeRating(String animeTitle, double ratingAverage){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(ANIMECOLUMN_4,animeTitle);

        contentValues.put(ANIMECOLUMN_10,ratingAverage);

        db.update(ANIME_TABLE_NAME,contentValues,"ANIMETITLE = ?",(new String[]{animeTitle}));

        return true;
    }
public Cursor viewAllAnime(){
    SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
    Cursor res;
    res= sqLiteDatabase.rawQuery("select * from "+ANIME_TABLE_NAME,null);
    return res;
}
public Integer deleteAnime(String animeName,String ongoing, double animeRating){
    SQLiteDatabase db= this.getWritableDatabase();
    return db.delete(ANIME_TABLE_NAME,"ANIMETITLE = ? AND ANIMEONGOING = ? AND RATINGAVERAGE = ?",new String[]{animeName,ongoing,String.valueOf(animeRating)});


}
public Cursor searchAnime(String animeName,SQLiteDatabase db){
    String[] projections= {ANIMECOLUMN_1,ANIMECOLUMN_4,ANIMECOLUMN_5,ANIMECOLUMN_6,ANIMECOLUMN_7,ANIMECOLUMN_8,ANIMECOLUMN_9,ANIMECOLUMN_10};
    String selection= ANIMECOLUMN_4+" LIKE ?";
    String[] selectionArgs={animeName};
    Cursor res= db.query(ANIME_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
    return res;

}

//FOR REVIEW
    //-------Insert Section--------//
public boolean insertReview(String reviewDescription, double personalRating,String userId,String animeId){
    SQLiteDatabase db= this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();
    contentValues.put(REVIEWCOLUMN_5,reviewDescription);
    contentValues.put(REVIEWCOLUMN_6,personalRating);
    contentValues.put(REVIEWCOLUMN_2,userId);
    contentValues.put(REVIEWCOLUMN_3,animeId);
    long result= db.insert(REVIEWS_TABLE_NAME,null,contentValues);


    if(result==-1) {
        return false;
    }else

        return true;
    }
public Cursor checkAnimeReview(String animeName,SQLiteDatabase db){
    String[] projections={ANIMECOLUMN_1,ANIMECOLUMN_4,ANIMECOLUMN_10};
    String selection= ANIMECOLUMN_4+ " LIKE ?" ;
    String[] selectionArgs={animeName};
    Cursor res= db.query(ANIME_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
    return res;
}

public Cursor  checkUsernameReview(String username,SQLiteDatabase db){
    String[] projections={COLUMN_1 ,COLUMN_5};
    String selection= COLUMN_5+" LIKE ?";
    String[] selectionArgs={username};
    Cursor res= db.query(USER_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
    return res;
}
    public Cursor  checkRating(double rating,SQLiteDatabase db){
        String[] projections={REVIEWCOLUMN_6};
        String selection= REVIEWCOLUMN_6+" LIKE ?";
        String[] selectionArgs={String.valueOf(rating)};
        Cursor res= db.query(REVIEWS_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
        return res;
    }
    public Cursor  checkReviewId(int reviewId,SQLiteDatabase db){
        String[] projections={REVIEWCOLUMN_1};
        String selection= REVIEWCOLUMN_1+" LIKE ?";
        String[] selectionArgs={String.valueOf(reviewId)};
        Cursor res= db.query(REVIEWS_TABLE_NAME,projections,selection,selectionArgs,null,null,null);
        return res;
    }
    //------------------------------//
    //--------Update Section--------//
public boolean updateReview(String reviewId,String reviewDescription, double reviewPersonalRating){
    SQLiteDatabase db= this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();
    contentValues.put(REVIEWCOLUMN_5,reviewDescription);
    contentValues.put(REVIEWCOLUMN_6,reviewPersonalRating);
    contentValues.put(REVIEWCOLUMN_1,reviewId);
    db.update(REVIEWS_TABLE_NAME,contentValues,"REVIEWID = ?",new String[]{reviewId});

    return true;

}

public Integer deleteReview(int reviewId){
SQLiteDatabase db= this.getWritableDatabase();
return db.delete(REVIEWS_TABLE_NAME,"REVIEWID = ?",new String[] {String.valueOf(reviewId)});

}
    public Cursor searchReview(int reviewId, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {REVIEWCOLUMN_1, REVIEWCOLUMN_2, REVIEWCOLUMN_3, REVIEWCOLUMN_4, REVIEWCOLUMN_5, REVIEWCOLUMN_6};
        String selection = REVIEWCOLUMN_1+ " LIKE ? ";
        String[] selection_args = {String.valueOf(reviewId)};
        Cursor res = sqLiteDatabase.query(REVIEWS_TABLE_NAME, projections, selection, selection_args, null, null, null);
        return res;
    }
    public Cursor viewAllReview(){
    /*String selection= ANIMECOLUMN_1+ " LIKE ? ";
   String[] selection_args= {String.valueOf(animeId)};
   String[] projections = {REVIEWCOLUMN_1,REVIEWCOLUMN_2,REVIEWCOLUMN_3,REVIEWCOLUMN_5,REVIEWCOLUMN_6};*/

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+REVIEWS_TABLE_NAME,null);
        return res;
    }

   /* public double getAverage(String animeName){
    SQLiteDatabase db= this.getReadableDatabase();
    Cursor res = db.rawQuery("select (AVG(RATING)) from " +REVIEWS_TABLE_NAME+" where ANIMETITLE = ? ", new String[]{animeName});
    return res;
    }
public Cursor calculateReview() {
    ContentValues contentValues= new ContentValues();
    contentValues.put(ANIMECOLUMN_1,animeId);
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor res;
    res= sqLiteDatabase.rawQuery(" AVERAGE " +" SELECT "+REVIEWCOLUMN_1+" from "+ REVIEWS_TABLE_NAME +" WHERE "+ANIMECOLUMN_1+ " = ? ");
*/
}
