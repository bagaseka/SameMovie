package bagasekaz.projects.samemovie.model;

import android.content.Context;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import bagasekaz.projects.samemovie.helper.DatabaseHelper;

public class MovieViewModel extends ViewModel {

    public MovieViewModel() {
    }

    private final MutableLiveData<ArrayList<Movie>> _listMovie = new MutableLiveData<>();
    public LiveData<ArrayList<Movie>> getListMovie() {
        return _listMovie;
    }

    public void addMovie(Context context) {
        ArrayList<Movie> list = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Cursor cursor = databaseHelper.getAllMovies();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RELEASE));
                Movie movie = new Movie(id,title,releaseDate);
                list.add(movie);
            }
        }
        _listMovie.setValue(list);
    }

    public void deleteMovie(ArrayList<Movie> updatedItems) {

    }
}



