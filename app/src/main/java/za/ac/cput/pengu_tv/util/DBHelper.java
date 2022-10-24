package za.ac.cput.pengu_tv.util;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import za.ac.cput.pengu_tv.AnimeDataEntity;

public class DBHelper extends SQLiteOpenHelper{
    public static String getId;
    public static final String DATABASE_NAME="PenguTv.db";
    public static final String USER_TABLE_NAME="User_Table";
    public static final String ANIME_TABLE_NAME="ANIME_Table";
    public static final String  REVIEWS_TABLE_NAME="Review_Table";
    public static final String  REQUEST_TABLE_NAME="Request_Table";
    public static final String USERNAME_TABLE_NAME="Username_Table";


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


    public static final String REQUESTCOLUMN_1="REQUESTID";
    public static final String REQUESTCOLUMN_2="REQUESTANIMETITLE";
    public static final String REQUESTCOLUMN_3="REQUESTANIMEDESCRIPTION";
    public static final String REQUESTCOLUMN_4="REQUESTANIMEONGOING";
    public static final String REQUESTCOLUMN_5="REQUESTANIMEEPISODEAMOUNT";
    public static final String REQUESTCOLUMN_6="REQUESTANIMEGENRE";
    public static final String REQUESTCOLUMN_7="REQUESTANIMERATING";
    public static final String REQUESTCOLUMN_8="REQUESTUSERNAME";

    public static final String USERNAMEUSERNAME_1= "USERNAMEUSERNAME";


    public DBHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,1);
        SQLiteDatabase db= this.getWritableDatabase();
    }

    public DBHelper(@Nullable Object adminRequestAdapter) {
        super((Context) adminRequestAdapter,DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table "+USER_TABLE_NAME+"(USERID INTEGER PRIMARY KEY AUTOINCREMENT,REVIEWS INTEGER,USERNAME STRING,USERLASTNAME STRING, USERUSERNAME STRING,USERPASSWORD STRING, USEREMAIL STRING)");
        db.execSQL("create table "+REQUEST_TABLE_NAME+"(REQUESTID INTEGER PRIMARY KEY AUTOINCREMENT, REQUESTANIMETITLE STRING, REQUESTANIMEDESCRIPTION STRING, REQUESTANIMEONGOING STRING, REQUESTANIMEEPISODEAMOUNT LONG, REQUESTANIMEGENRE STRING, REQUESTANIMERATING DOUBLE, REQUESTUSERNAME STRING)");
        db.execSQL("create table "+ANIME_TABLE_NAME+"(ANIMEID INTEGER PRIMARY KEY AUTOINCREMENT,ANIMETITLE STRING,ANIMEDESCRIPTION STRING,ANIMETOTAL INTEGER,ANIMEONGOING STRING, ANIMEEPISODEAMOUNT LONG, ANIMEGENRE STRING,RATINGAVERAGE DOUBLE)");
        db.execSQL("create table "+REVIEWS_TABLE_NAME+"(REVIEWID INTEGER PRIMARY KEY AUTOINCREMENT,USERID INTEGER,ANIMEID INTEGER,REVIEWAMOUNT LONG, REVIEWDESCRIPTION STRING,RATING DOUBLE)");
        db.execSQL("create table "+USERNAME_TABLE_NAME+"(USERNAMEUSERNAME STRING)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int i,int i1){
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ANIME_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEWS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USERNAME_TABLE_NAME);
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
    public boolean insertUsername(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=  new ContentValues();

        contentValues.put(USERNAMEUSERNAME_1,username);

        long result= db.insert(USERNAME_TABLE_NAME,null, contentValues);
        if (result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor searchUsername(String username, SQLiteDatabase sqLiteDatabase){
        String[] projections={USERNAMEUSERNAME_1};
        String selection= USERNAMEUSERNAME_1+" LIKE ? ";
        String[] selection_args={username};
        Cursor res= sqLiteDatabase.query(USERNAME_TABLE_NAME,projections,selection,selection_args,null,null,null);
        return res;
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
    public void deleteUsername(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(USERNAME_TABLE_NAME,null,null);
        db.execSQL("delete from "+ USERNAME_TABLE_NAME);

        db.close();


    }


    public Cursor searchUser(String username, SQLiteDatabase sqLiteDatabase){
        String[] projections={COLUMN_1,COLUMN_2,COLUMN_3,COLUMN_4,COLUMN_5,COLUMN_6,COLUMN_7};
        String selection= COLUMN_5+" LIKE ?";
        String[] selection_args={username};
        Cursor res= sqLiteDatabase.query(USER_TABLE_NAME,projections,selection,selection_args,null,null,null);
        return res;
    }


    public Cursor searchUserAndPassword(String username, String password){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+USER_TABLE_NAME+" where USERUSERNAME = ? and USERPASSWORD = ?",new String[]{username,password});
        return res;
    }
    public Cursor viewAllUsers(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res;
        res= db.rawQuery("select * from "+ USER_TABLE_NAME,null);
        return res;

    }

    public Cursor viewAllUsernames(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res;
        res= db.rawQuery("select * from "+ USERNAME_TABLE_NAME,null);
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
    public boolean updateAnimeRatingById(int animeId, double ratingAverage){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(ANIMECOLUMN_1,animeId);

        contentValues.put(ANIMECOLUMN_10,ratingAverage);

        db.update(ANIME_TABLE_NAME,contentValues,"ANIMEID = ?",(new String[]{String.valueOf(animeId)}));

        return true;
    }
    public Cursor viewAllAnime(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+ANIME_TABLE_NAME,null);
        return res;
    }
    public Cursor viewAllAnimeBySearch(String input){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        System.out.println(input+ "Test 3");
        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+ANIME_TABLE_NAME +" where ANIMETITLE = ?",new String[]{input});
        System.out.println(res+ "Test 4");
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
    public Cursor searchAnimeForList(String animeName,SQLiteDatabase db){
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
    public Cursor viewAllReview(String animeId){
    /*String selection= ANIMECOLUMN_1+ " LIKE ? ";
   String[] selection_args= {String.valueOf(animeId)};
   String[] projections = {REVIEWCOLUMN_1,REVIEWCOLUMN_2,REVIEWCOLUMN_3,REVIEWCOLUMN_5,REVIEWCOLUMN_6};*/

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+REVIEWS_TABLE_NAME+" where ANIMEID = ?",new String[]{animeId});
        return res;
    }
    public Cursor viewUsername(String userId){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+USER_TABLE_NAME+" where USERID = ?",new String[]{userId});
        return res;
    }

/*public Cursor calculateReview() {
    ContentValues contentValues= new ContentValues();
    contentValues.put(ANIMECOLUMN_1,animeId);
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor res;
    res= sqLiteDatabase.rawQuery(" AVERAGE " +" SELECT "+REVIEWCOLUMN_1+" from "+ REVIEWS_TABLE_NAME +" WHERE "+ANIMECOLUMN_1+ " = ? ");
*/
    public boolean insertRequest(String animeTitle, String animeDescription, String animeOngoing, Long animeEpisodeAmount, String animeGenre, Double animeRating, String username){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(REQUESTCOLUMN_2,animeTitle);
        contentValues.put(REQUESTCOLUMN_3,animeDescription);
        contentValues.put(REQUESTCOLUMN_4,animeOngoing);
        contentValues.put(REQUESTCOLUMN_5,animeEpisodeAmount);
        contentValues.put(REQUESTCOLUMN_6,animeGenre);
        contentValues.put(REQUESTCOLUMN_7,animeRating);
        contentValues.put(REQUESTCOLUMN_8,username);

        long result = db.insert(REQUEST_TABLE_NAME,null,contentValues);
        if(result==-1) {
            return false;
        }else

            return true;
    }
public Cursor viewAllRequests(){
    SQLiteDatabase db=this.getWritableDatabase();
    Cursor res;
    res= db.rawQuery("select * from "+ REQUEST_TABLE_NAME,null);
    return res;

}
    public Integer deleteRequest(String animeName){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(REQUEST_TABLE_NAME,"REQUESTANIMETITLE = ?",new String[] {String.valueOf(animeName)});

    }
    public Cursor getAllReviewsById(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor res;
        res= sqLiteDatabase.rawQuery("select * from "+REVIEWS_TABLE_NAME,null);
        return res;

    }
    public Cursor searchUserNames(int reviewId, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {REVIEWCOLUMN_1, REVIEWCOLUMN_2, REVIEWCOLUMN_3, REVIEWCOLUMN_4, REVIEWCOLUMN_5, REVIEWCOLUMN_6};
        String selection = REVIEWCOLUMN_1+ " LIKE ? ";
        String[] selection_args = {String.valueOf(reviewId)};
        Cursor res = sqLiteDatabase.query(REVIEWS_TABLE_NAME, projections, selection, selection_args, null, null, null);
        return res;
    }
    public List<AnimeDataEntity> search(String keyWord){
        List<AnimeDataEntity> animeDataEntities= null;
        try{
            SQLiteDatabase sqLiteDatabase= getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from "+ ANIME_TABLE_NAME+ " where "+ANIMECOLUMN_4+" like ?", new String[] {"%" + keyWord + "%"});
        if (cursor.moveToFirst()){
            animeDataEntities= new ArrayList<AnimeDataEntity>();
            do{
                AnimeDataEntity animeDataEntity = new AnimeDataEntity();
                animeDataEntity.setTitle(cursor.getString(1));
                animeDataEntity.setDescription(cursor.getString(2));
                animeDataEntity.setTotal(cursor.getString(3));
                animeDataEntity.setOngoing(cursor.getString(4));
                animeDataEntity.setEpisodeAmount(cursor.getLong(5));
                animeDataEntity.setGenre(cursor.getString(6));
                animeDataEntity.setAverage(cursor.getDouble(7));

            }while(cursor.moveToNext());
        }
        } catch(Exception e){
            animeDataEntities = null;
        }
        return animeDataEntities;
    }
   /* public void averageRating(String average){
       SQLiteDatabase db= this.getWritableDatabase();
Cursor rating;
      rating=  db.rawQuery("select AVG(RATING)" + " from "+ REVIEWS_TABLE_NAME+" where "+ REVIEWCOLUMN_3 +" = ?", new String[]{average});
        return rating;
    }

    */

   /* SELECT AVG(rating)
    FROM review_table
    WHERE animeId=?;

    */

    public Double getRatings(){
        SQLiteDatabase db = this.getReadableDatabase();

        Double total= 0.0;
        Double count=0.0;
        Cursor c = db.rawQuery("SELECT RATING from "+ REVIEWS_TABLE_NAME+" where ANIMEID = 2", null);
        while(c.moveToNext()){
            @SuppressLint("Range") Double average= c.getDouble(c.getColumnIndex("RATING"));
            total+=(average);
            count++;
        }
        return total/count;
    }
}
