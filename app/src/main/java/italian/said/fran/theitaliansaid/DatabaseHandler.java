package italian.said.fran.theitaliansaid;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Fran on 05/07/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "TIS_DB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Proverbs";
    private static final String COL_0 = "Id";
    private static final String COL_1 = "Ita";
    private static final String COL_2 = "Eng";
    private final Context context;
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(this.context, "ON CREATE DB", Toast.LENGTH_SHORT).show();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COL_0 + " INTEGER PRIMARY KEY, " + COL_1 + " TEXT, " + COL_2 + " TEXT" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS Fav (" + COL_0 + " INTEGER PRIMARY KEY, " + COL_1 + " TEXT, " + COL_2 + " TEXT" + ")");
        insertData(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    
    private boolean insertData(SQLiteDatabase db) {
        db.beginTransaction();
    
        try {
            AssetManager assetManager = this.context.getAssets();
            String line;
            String columns = "Id, Ita, Eng";
            String str1 = "INSERT INTO " + TABLE_NAME + " (" + columns + ") VALUES (";
            String str2 = ");";
            InputStream csvStream = assetManager.open("csv/data.csv");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(csvStream));
            
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(";");
                sb.append("\"" + str[0] + "\",");
                sb.append("\"" + str[1] + "\",");
                sb.append("\"" + str[2] + "\"");
                sb.append(str2);
                db.execSQL(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }
    
    public Cursor getData(String val) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + val + " FROM " + TABLE_NAME + " ORDER BY Id";
        return db.rawQuery(query, null);
    }
    
    public boolean addFav(int id, String ita, String eng){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO Fav (Id, Ita, Eng) VALUES (" + id + ", " + ita + ", " + eng + ")";
        
        try {
            db.execSQL(query);
        }catch (SQLException e) {
            Log.d("addFav", e.toString());
        }
        
        return true;
    }
    
    public boolean remFav(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM Fav WHERE id = " + id;
        
        try {
            db.execSQL(query);
        }catch (SQLException e){
            Log.d("rmFav", e.toString());
        }
        
        return true;
    }
    
    public Cursor getFav(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Fav ORDER BY " + COL_1;
        return db.rawQuery(query, null);
    }
    
    public boolean dbCheckIfFav(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM Fav WHERE id = " + id;
        Cursor data = db.rawQuery(query, null);
        return data.moveToFirst();
    }
}