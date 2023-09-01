package bagasekaz.projects.samemovie.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "same_movie";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RELEASE = "releaseDate";
    // ... tambahkan kolom lain yang diperlukan

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_MOVIES + " (" +
                    COLUMN_ID + " TEXT PRIMARY KEY, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_RELEASE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    // Menambahkan data film
    public long addMovie(String id, String title, String releaseDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_RELEASE, releaseDate);
        return db.insert(TABLE_MOVIES, null, values);
    }

    // Menghapus data film berdasarkan ID
    public int deleteMovie(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MOVIES, COLUMN_ID + " = ?", new String[]{id});
    }

    // Mendapatkan semua data film
    public Cursor getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_MOVIES, null, null, null, null, null, null);
    }
}

